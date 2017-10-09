package fr.sri.taxcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {

    private String name;
    private BigDecimal price;
    private boolean isExempted;
    private boolean isImported;
}
