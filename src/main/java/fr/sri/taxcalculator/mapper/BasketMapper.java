package fr.sri.taxcalculator.mapper;

import fr.sri.taxcalculator.dto.BasketDto;
import fr.sri.taxcalculator.dto.ProductDto;
import fr.sri.taxcalculator.entity.Basket;
import fr.sri.taxcalculator.entity.Product;
import fr.sri.taxcalculator.util.TaxCalculatorUtil;

import java.util.List;
import java.util.stream.Collectors;


public final class BasketMapper {

    public static BasketDto entityToDto(Basket basket) {

        List<Product> products = basket.getProducts();
        List<ProductDto> productDtos = products.stream()
                .map(ProductMapper::entityToDto)
                .collect(Collectors.toList());

        return BasketDto.builder()
                .total(TaxCalculatorUtil.computeTotal(productDtos))
                .taxesAmount(TaxCalculatorUtil.computeTaxesAmount(products))
                .products(productDtos)
                .build();
    }

    public static Basket dtoToEntity(BasketDto basketDto){
        List<ProductDto> productDtos = basketDto.getProducts();

        List<Product> products = productDtos.stream()
                .map(ProductMapper::dtoToEntity)
                .collect(Collectors.toList());

        return Basket.builder().products(products).build();
    }
}
