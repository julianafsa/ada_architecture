package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.exceptions.StickerNotAvailableForSale;
import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.entity.StickerToSell;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.service.StickerJournalService;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerServiceWithJournal;
import br.com.ada.stickers.service.StickerToSellService;
import br.com.ada.stickers.strategy.StickerPackStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Design Pattern: Strategy Pattern.
 * In the buyStickerPack method, we need to get stickers to add to a pack.
 * There are many strategies for choosing stickers. Therefore, we can create an
 * interface and several implementations of this interface that choose
 * the stickers in different ways. Here, we have created an algorithm that
 * chooses stickers randomly (RandomStickerPackStrategyImpl.java).
 */
@Slf4j
@Service
public class StickerServiceWithJournalImpl implements StickerServiceWithJournal {

    private final StickerJournalService stickerJournalService;
    private final StickerService stickerService;
    private final StickerMapper stickerMapper;
    private final StickerToSellService stickerToSellService;
    private final StickerPackStrategy stickerPackStrategy;
    public StickerServiceWithJournalImpl(final StickerService stickerService,
                                         final StickerJournalService stickerJournalService,
                                         final StickerMapper stickerMapper,
                                         final StickerToSellService stickerToSellService, StickerPackStrategy stickerPackStrategy) {
        this.stickerService = stickerService;
        this.stickerJournalService = stickerJournalService;
        this.stickerMapper = stickerMapper;
        this.stickerToSellService = stickerToSellService;
        this.stickerPackStrategy = stickerPackStrategy;
    }
    
    @Override
    public List<Sticker> buyStickerPack(final StickerBuyPackDTO stickerBuyPackDTO) {
        List<Sticker> soldStickers = new ArrayList<>();
        final Integer size = 5;
        final String albumId = stickerBuyPackDTO.getAlbumId();
        final String destinationAlbumId = stickerBuyPackDTO.getDestinationAlbumId();

        // Get list of stickers from album template
        final List<Sticker> stickersFromAlbumId = stickerService.findByAlbumId(albumId);
        final List<Sticker> stickersFromDestnationAlbumId = stickerService.findByAlbumId(destinationAlbumId);

        // It generates a pack of stickers to be sold
        soldStickers = stickerPackStrategy.createStickerPack(stickersFromAlbumId, size);

        // Edit all stickers with new album id
        soldStickers.forEach(sticker -> sticker.setAlbumId(destinationAlbumId));
        soldStickers = stickerService.editAll(soldStickers);

        // Add the sale to the transaction history (sticker journal).
        soldStickers.forEach(soldSticker -> this.addStickerJournal(albumId, destinationAlbumId, soldSticker,
                soldSticker.getStickerTemplate().getStickerPrice()));
        return soldStickers;
    }

    @Override
    public Sticker buyStickerFromAlbum(final StickerBuyFromAlbumDTO stickerBuyFromAlbumDTO) {
        Sticker sticker = null;
        final String stickerId = stickerBuyFromAlbumDTO.getStickerId();
        final String destinationAlbumId = stickerBuyFromAlbumDTO.getDestinationAlbumId();

        // Get sticker for sale
        final Optional<StickerToSell> optional = stickerToSellService.findByStickerId(stickerId);
        StickerToSell stickerToSell = null;
        if (optional.isPresent()) {
            stickerToSell = optional.get();
            sticker = stickerToSell.getSticker();
        } else {
            // Sticker is not available for sale.
            String errorMsg = "Sticker " + stickerId + " not available for sale";
            log.error(errorMsg);
            throw new StickerNotAvailableForSale();
        }

        // Update sticker album.
        final String sourceAlbum = sticker.getAlbumId();
        sticker.setAlbumId(destinationAlbumId);
        final StickerUpdateDTO stickerUpdateDTO = StickerUpdateDTO.builder()
                .albumId(sticker.getAlbumId())
                .stickerTemplateId(sticker.getStickerTemplate().getId())
                .build();
        sticker = stickerService.edit(stickerId, stickerUpdateDTO);

        // It makes the sticker unavailable for sale.
        this.stickerToSellService.deleteByStickerId(stickerId);

        // Add the sale to the transaction history (sticker journal).
        this.addStickerJournal(sourceAlbum, destinationAlbumId, sticker, stickerToSell.getPrice());
        return sticker;
    }

    private StickerJournalDTO addStickerJournal(final String sourceAlbum, final String destinationAlbumId,
                                                final Sticker sticker, final BigDecimal price) {
        final StickerJournalCreationDTO stickerJournalCreationDTO = StickerJournalCreationDTO.builder()
                .sourceAlbumId(sourceAlbum)
                .destinationAlbumId(destinationAlbumId)
                .sticker(sticker)
                .price(price)
                .build();
        return stickerJournalService.create(stickerJournalCreationDTO);
    }

}
