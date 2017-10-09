package fr.sri.taxcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class BasketDto {

    private List<ProductDto> products;
    private BigDecimal taxesAmount;
    private BigDecimal total;

    public BasketDto() {
    }
}
