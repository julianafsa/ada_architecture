package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.exceptions.FigurinhaNaoDisponivelAVendaException;
import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.service.*;
import br.com.ada.stickers.strategy.PacoteDeFigurinhaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import br.com.ada.stickers.service.Redis;

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
public class FigurinhaServiceWithTransacaoImpl implements FigurinhaServiceWithTransacao {

    private final TransacaoFigurinhaService transacaoFigurinhaService;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;
    private final FigurinhaAVendaService figurinhaAVendaService;
    private final PacoteDeFigurinhaStrategy pacoteDeFigurinhaStrategy;
    private final AlbumService albumService;
    private final Redis jedis;
    public FigurinhaServiceWithTransacaoImpl(final FigurinhaService figurinhaService,
                                             final TransacaoFigurinhaService transacaoFigurinhaService,
                                             final FigurinhaMapper figurinhaMapper,
                                             final FigurinhaAVendaService figurinhaAVendaService,
                                             final PacoteDeFigurinhaStrategy pacoteDeFigurinhaStrategy,
                                             final AlbumService albumService,
                                             final Redis jedis) {
        this.figurinhaService = figurinhaService;
        this.transacaoFigurinhaService = transacaoFigurinhaService;
        this.figurinhaMapper = figurinhaMapper;
        this.figurinhaAVendaService = figurinhaAVendaService;
        this.pacoteDeFigurinhaStrategy = pacoteDeFigurinhaStrategy;
        this.albumService = albumService;
        this.jedis = jedis;
    }
    
    @Override
    public List<Figurinha> buyStickerPack(final FigurinhaBuyPackDTO figurinhaBuyPackDTO) {
        List<Figurinha> soldFigurinhas = new ArrayList<>();
        final Integer size = 5;
        final String albumId = figurinhaBuyPackDTO.getAlbumId();
        final String destinationAlbumId = figurinhaBuyPackDTO.getDestinationAlbumId();

        // Get list of stickers from album template
        final List<Figurinha> stickersFromAlbumId = figurinhaService.findByAlbumId(albumId);
        //final List<Sticker> stickersFromDestnationAlbumId = stickerService.findByAlbumId(destinationAlbumId);

        // It generates a pack of stickers to be sold
        soldFigurinhas = pacoteDeFigurinhaStrategy.createStickerPack(stickersFromAlbumId, size);

        // Edit all stickers with new album id
        soldFigurinhas.forEach(sticker -> sticker.setAlbumId(destinationAlbumId));
        soldFigurinhas = figurinhaService.editAll(soldFigurinhas);

        // Add the sale to the transaction history (sticker journal).
        soldFigurinhas.forEach(soldSticker -> this.addStickerJournal(albumId, destinationAlbumId, soldSticker,
                soldSticker.getStickerTemplate().getStickerPrice()));
        return soldFigurinhas;
    }

    @Override
    public Figurinha buyStickerFromAlbum(final FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO) {
        Figurinha figurinha = null;
        final String stickerId = figurinhaBuyFromAlbumDTO.getStickerId();
        final String destinationAlbumId = figurinhaBuyFromAlbumDTO.getDestinationAlbumId();

        // Get sticker for sale
        final Optional<FigurinhaAVenda> optional = figurinhaAVendaService.findByStickerId(stickerId);
        FigurinhaAVenda figurinhaAVenda = null;
        if (optional.isPresent()) {
            figurinhaAVenda = optional.get();
            figurinha = figurinhaAVenda.getSticker();
        } else {
            // Sticker is not available for sale.
            String errorMsg = "Sticker " + stickerId + " not available for sale";
            log.error(errorMsg);
            throw new FigurinhaNaoDisponivelAVendaException();
        }

        // Update sticker album.
        final String sourceAlbumId = figurinha.getAlbumId();
        figurinha.setAlbumId(destinationAlbumId);
        final FigurinhaUpdateDTO figurinhaUpdateDTO = FigurinhaUpdateDTO.builder()
                .albumId(figurinha.getAlbumId())
                .stickerTemplateId(figurinha.getStickerTemplate().getId())
                .build();
        figurinha = figurinhaService.edit(stickerId, figurinhaUpdateDTO);

        // Update balance on Redis.
        this.updateBalanceUser(sourceAlbumId, figurinhaAVenda.getPrice());

        // It makes the sticker unavailable for sale.
        this.figurinhaAVendaService.deleteByStickerId(stickerId);

        // Add the sale to the transaction history (sticker journal).
        this.addStickerJournal(sourceAlbumId, destinationAlbumId, figurinha, figurinhaAVenda.getPrice());
        return figurinha;
    }

    private TransacaoFigurinhaDTO addStickerJournal(final String sourceAlbum, final String destinationAlbumId,
                                                    final Figurinha figurinha, final BigDecimal price) {
        final TransacaoFigurinhaCreationDTO transacaoFigurinhaCreationDTO = TransacaoFigurinhaCreationDTO.builder()
                .sourceAlbumId(sourceAlbum)
                .destinationAlbumId(destinationAlbumId)
                .sticker(figurinha)
                .price(price)
                .build();
        return transacaoFigurinhaService.create(transacaoFigurinhaCreationDTO);
    }

    private boolean updateBalanceUser(String sourceAlbumId, BigDecimal price) {
        final Optional<String> userIdOp = albumService.findUserIdByAlbumId(sourceAlbumId);
        if (userIdOp.isPresent()) {
            final String userId = userIdOp.get();
            log.info("[REDIS] Trying adding {} to user with id {}...", price, userId);
            this.jedis.save(userId, price);
        }
        return true;
    }

}
