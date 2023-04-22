package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.exceptions.FigurinhaNotAvailableForSaleException;
import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.service.*;
import br.com.ada.figurinhas.service.producer.BalanceService;
import br.com.ada.figurinhas.strategy.FigurinhaPacoteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Design Pattern: Strategy Pattern.
 * In the buyFigurinhaPacote method, we need to get figurinhas to add to a pacote.
 * There are many strategies for choosing figurinhas. Therefore, we can create an
 * interface and several implementations of this interface that choose
 * the figurinhas in different ways. Here, we have created an algorithm that
 * chooses figurinhas randomly (RandomFigurinhaPacoteStrategyImpl.java).
 */
@Slf4j
@Service
public class FigurinhaServiceWithJournalImpl implements FigurinhaServiceWithJournal {

    private final FigurinhaJournalService figurinhaJournalService;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;
    private final FigurinhaToSellService figurinhaToSellService;
    private final FigurinhaPacoteStrategy figurinhaPacoteStrategy;
    private final AlbumService albumService;
    private final BalanceService balanceService;
    public FigurinhaServiceWithJournalImpl(final FigurinhaService figurinhaService,
                                         final FigurinhaJournalService figurinhaJournalService,
                                         final FigurinhaMapper figurinhaMapper,
                                         final FigurinhaToSellService figurinhaToSellService,
                                         final FigurinhaPacoteStrategy figurinhaPacoteStrategy,
                                         final AlbumService albumService,
                                         final BalanceService balanceService) {
        this.figurinhaService = figurinhaService;
        this.figurinhaJournalService = figurinhaJournalService;
        this.figurinhaMapper = figurinhaMapper;
        this.figurinhaToSellService = figurinhaToSellService;
        this.figurinhaPacoteStrategy = figurinhaPacoteStrategy;
        this.albumService = albumService;
        this.balanceService = balanceService;
    }
    
    @Override
    public List<Figurinha> buyFigurinhaPacote(final FigurinhaBuyPacoteDTO figurinhaBuyPacoteDTO) {
        List<Figurinha> soldFigurinhas = new ArrayList<>();
        final Integer size = 5;
        final String albumId = figurinhaBuyPacoteDTO.getAlbumId();
        final String destinationAlbumId = figurinhaBuyPacoteDTO.getDestinationAlbumId();

        // Get list of figurinhas from album
        final List<Figurinha> figurinhasFromAlbumId = figurinhaService.findByAlbumId(albumId);
        //final List<Figurinha> figurinhasFromDestnationAlbumId = figurinhaService.findByAlbumId(destinationAlbumId);

        // It generates a pacote of figurinhas to be sold
        soldFigurinhas = figurinhaPacoteStrategy.createFigurinhaPacote(figurinhasFromAlbumId, size);

        // Edit all figurinhas with new album id
        soldFigurinhas.forEach(figurinha -> figurinha.setAlbumId(destinationAlbumId));
        soldFigurinhas = figurinhaService.editAll(soldFigurinhas);

        // Add the sale to the transaction history (figurinha journal).
        soldFigurinhas.forEach(soldFigurinha -> this.addFigurinhaJournal(albumId, destinationAlbumId, soldFigurinha,
                soldFigurinha.getFigurinhaPrototipo().getFigurinhaPrice()));
        return soldFigurinhas;
    }

    @Override
    public Figurinha buyFigurinhaFromAlbum(final FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO) {
        Figurinha figurinha = null;
        final String figurinhaId = figurinhaBuyFromAlbumDTO.getFigurinhaId();
        final String destinationAlbumId = figurinhaBuyFromAlbumDTO.getDestinationAlbumId();

        // Get figurinha for sale
        final Optional<FigurinhaToSell> optional = figurinhaToSellService.findByFigurinhaId(figurinhaId);
        FigurinhaToSell figurinhaToSell = null;
        if (optional.isPresent()) {
            figurinhaToSell = optional.get();
            figurinha = figurinhaToSell.getFigurinha();
        } else {
            // Figurinha is not available for sale.
            String errorMsg = "Figurinha " + figurinhaId + " not available for sale";
            log.error(errorMsg);
            throw new FigurinhaNotAvailableForSaleException();
        }

        // Update balance on Redis.
        final String sourceAlbumId = figurinha.getAlbumId();
        Boolean result = this.updateBalanceUsuario(sourceAlbumId, figurinhaToSell.getPrice());
        if (!result) {
            throw new RuntimeException("Fail to increment balance to usuario");
        }

        // Update figurinha album.
        figurinha.setAlbumId(destinationAlbumId);
        final FigurinhaUpdateDTO figurinhaUpdateDTO = FigurinhaUpdateDTO.builder()
                .albumId(figurinha.getAlbumId())
                .figurinhaPrototipoId(figurinha.getFigurinhaPrototipo().getId())
                .build();
        figurinha = figurinhaService.edit(figurinhaId, figurinhaUpdateDTO);

        // It makes the figurinha unavailable for sale.
        this.figurinhaToSellService.deleteByFigurinhaId(figurinhaId);

        // Add the sale to the transaction history (figurinha journal).
        this.addFigurinhaJournal(sourceAlbumId, destinationAlbumId, figurinha, figurinhaToSell.getPrice());
        return figurinha;
    }

    private FigurinhaJournalDTO addFigurinhaJournal(final String sourceAlbum, final String destinationAlbumId,
                                                final Figurinha figurinha, final BigDecimal price) {
        final FigurinhaJournalCreationDTO figurinhaJournalCreationDTO = FigurinhaJournalCreationDTO.builder()
                .sourceAlbumId(sourceAlbum)
                .destinationAlbumId(destinationAlbumId)
                .figurinha(figurinha)
                .price(price)
                .build();
        return figurinhaJournalService.create(figurinhaJournalCreationDTO);
    }

    private Boolean updateBalanceUsuario(String sourceAlbumId, BigDecimal price) {
        try {
            Optional<String> usuarioIdOp = albumService.findUsuarioIdByAlbumId(sourceAlbumId);
            if (usuarioIdOp.isPresent()) {
                final String usuarioId = usuarioIdOp.get();
                try {
                    this.balanceService.incrementBalance(usuarioId, price);
                } catch (Exception e) {
                    log.error("[REDIS] Fail to add {} to balance of usuario with id {}...", price, usuarioId);
                    return Boolean.FALSE;
                }
                log.info("[REDIS] Adding {} to usuario with id {}...", price, usuarioId);
            }
        } catch (Exception exception) {
            log.error("[FEIGN] Fail to get usuarioId from album id = {}", sourceAlbumId);
        }
        return Boolean.TRUE;
    }

}
