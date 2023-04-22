package br.com.ada.figurinhas.model.dto;

import br.com.ada.figurinhas.model.entity.Figurinha;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaToSellCreationDTO {
    @NotNull(message = "Figurinha field is missing or blank.")
    private Figurinha figurinha;

    @NotNull(message = "Price field is missing or blank.")
    private BigDecimal price;
}
