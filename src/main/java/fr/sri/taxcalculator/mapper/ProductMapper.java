package fr.sri.taxcalculator.mapper;


import fr.sri.taxcalculator.dto.ProductDto;
import fr.sri.taxcalculator.entity.Product;
import fr.sri.taxcalculator.util.TaxCalculatorUtil;

public final class ProductMapper {

    public static ProductDto entityToDto(Product product) {

        return ProductDto.builder()
                .name(product.getName())
                .price(TaxCalculatorUtil.computeTaxesInclusivePrice(product))
                .isImported(product.isImported())
                .isExempted(product.isExempted())
                .build();
    }

    public static Product dtoToEntity(ProductDto productDto) {

        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .isImported(productDto.isImported())
                .isExempted(productDto.isExempted())
                .build();
    }

}
