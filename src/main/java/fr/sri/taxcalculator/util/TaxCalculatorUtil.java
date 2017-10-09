package fr.sri.taxcalculator.util;


import fr.sri.taxcalculator.dto.ProductDto;
import fr.sri.taxcalculator.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;

public final class TaxCalculatorUtil {

    public static final BigDecimal IMPORTATION_TAX_RATE = valueOf(0.05);
    public static final BigDecimal BASIC_TAX_RATE = valueOf(0.10);

    public static BigDecimal computeTaxesAmount(List<Product> products) {
        Optional<BigDecimal> taxesAmount = products.stream()
                .map(product -> {
                    BigDecimal taxRate = product.isExempted() ? BigDecimal.ZERO : BASIC_TAX_RATE;
                    BigDecimal netPrice = product.getPrice();
                    BigDecimal basicTaxAmount = roundToUpperFiveCent(netPrice.multiply(taxRate));
                    BigDecimal importationTaxAmount = BigDecimal.ZERO;

                    if (product.isImported()) {
                        importationTaxAmount = roundToUpperFiveCent(netPrice.multiply(IMPORTATION_TAX_RATE));

                    }
                    return basicTaxAmount.add(importationTaxAmount);
                })
                .reduce(BigDecimal::add);

        if (taxesAmount.isPresent()) {
            return taxesAmount.get();
        }

        return BigDecimal.ZERO;
    }

    public static BigDecimal computeTotal(List<ProductDto> products) {
        Optional<BigDecimal> total = products.stream()
                .map(ProductDto::getPrice)
                .reduce(BigDecimal::add);

        if (total.isPresent()) {
            return total.get();
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal computeTaxesInclusivePrice(Product product) {
        BigDecimal taxRate = product.isExempted() ? BigDecimal.ZERO : BASIC_TAX_RATE;
        BigDecimal netPrice = product.getPrice();
        BigDecimal basicTaxPrice;

        basicTaxPrice = roundToUpperFiveCent(netPrice.multiply(taxRate)).add(netPrice);

        if (product.isImported()) {
            return roundToUpperFiveCent(netPrice.multiply(IMPORTATION_TAX_RATE))
                    .add(basicTaxPrice);
        }

        return basicTaxPrice;
    }

    public static BigDecimal roundToUpperFiveCent(BigDecimal price) {
        return price.divide(BigDecimal.valueOf(0.05), 0, BigDecimal.ROUND_UP)
                .multiply(BigDecimal.valueOf(0.05));
    }
}
