package fr.sri.taxcalculator.service;

import fr.sri.taxcalculator.dto.BasketDto;
import fr.sri.taxcalculator.entity.Basket;
import fr.sri.taxcalculator.entity.Product;
import fr.sri.taxcalculator.repository.BasketRepository;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketServiceTest {

    @Autowired
    BasketService basketService;

    @Autowired
    BasketRepository basketRepository;

    @Test
    @Transactional
    public void should_calculate_taxes_of_non_imported_products() throws Exception {

        //Given
        Product book = Product.builder()
                .name("Book")
                .price(BigDecimal.valueOf(12.49))
                .isImported(false)
                .isExempted(true)
                .build();

        Product cd = Product.builder()
                .name("Compact Disc")
                .price(BigDecimal.valueOf(14.99))
                .isImported(false)
                .isExempted(false)
                .build();

        Product chocolate = Product.builder()
                .name("Chocolate")
                .price(BigDecimal.valueOf(0.85))
                .isImported(false)
                .isExempted(true)
                .build();

        Basket basket = Basket.builder()
                .products(Arrays.asList(book, cd, chocolate))
                .build();

        Basket savedBasket = basketRepository.save(basket);

        //When
        BasketDto basketById = basketService.getBasketById(savedBasket.getId());

        //Then
        assertThat(basketById.getProducts()).hasSize(3)
                .extracting("name", "price", "isImported", "isExempted")
                .contains(
                        Tuple.tuple("Book", BigDecimal.valueOf(12.49), false, true),
                        Tuple.tuple("Compact Disc", BigDecimal.valueOf(16.49), false, false),
                        Tuple.tuple("Chocolate", BigDecimal.valueOf(0.85), false, true)
                );

        assertThat(basketById).extracting("taxesAmount", "total")
                .contains(BigDecimal.valueOf(1.50).setScale(2), BigDecimal.valueOf(29.83));

    }

    @Test
    @Transactional
    public void should_calculate_taxes_of_imported_products() throws Exception {

        //Given
        Product importedChocolate = Product.builder()
                .name("Chocolate")
                .price(BigDecimal.valueOf(10.00))
                .isImported(true)
                .isExempted(true)
                .build();

        Product importedParfume = Product.builder()
                .name("Parfume")
                .price(BigDecimal.valueOf(47.50))
                .isImported(true)
                .isExempted(false)
                .build();


        Basket basket = Basket.builder()
                .products(Arrays.asList(importedChocolate, importedParfume))
                .build();

        Basket savedBasket = basketRepository.save(basket);

        //When
        BasketDto basketById = basketService.getBasketById(savedBasket.getId());

        //Then
        assertThat(basketById.getProducts()).hasSize(2)
                .extracting("name", "price", "isImported", "isExempted")
                .contains(
                        Tuple.tuple("Chocolate", BigDecimal.valueOf(10.50).setScale(2), true, true),
                        Tuple.tuple("Parfume", BigDecimal.valueOf(54.65), true, false)
                );

        assertThat(basketById).extracting("taxesAmount", "total")
                .contains(BigDecimal.valueOf(7.65), BigDecimal.valueOf(65.15));

    }

    @Test
    @Transactional
    public void should_calculate_taxes_of_both_imported_and_non_imported_products() throws Exception {

        //Given
        Product importedParfume = Product.builder()
                .name("Imported Parfume")
                .price(BigDecimal.valueOf(27.99))
                .isImported(true)
                .isExempted(false)
                .build();

        Product parfume = Product.builder()
                .name("Parfume")
                .price(BigDecimal.valueOf(18.99))
                .isImported(false)
                .isExempted(false)
                .build();

        Product drug = Product.builder()
                .name("Drug")
                .price(BigDecimal.valueOf(9.75))
                .isImported(false)
                .isExempted(true)
                .build();

        Product importedChocolate = Product.builder()
                .name("Imported Chocolate")
                .price(BigDecimal.valueOf(11.25))
                .isImported(true)
                .isExempted(true)
                .build();


        Basket basket = Basket.builder()
                .products(Arrays.asList(importedChocolate, importedParfume, parfume, drug))
                .build();

        Basket savedBasket = basketRepository.save(basket);

        //When
        BasketDto basketById = basketService.getBasketById(savedBasket.getId());

        //Then
        assertThat(basketById.getProducts()).hasSize(4).extracting("name", "price", "isImported", "isExempted")
                .contains(
                        Tuple.tuple("Imported Parfume", BigDecimal.valueOf(32.19), true, false),
                        Tuple.tuple("Parfume", BigDecimal.valueOf(20.89), false, false),
                        Tuple.tuple("Drug", BigDecimal.valueOf(9.75), false, true),
                        Tuple.tuple("Imported Chocolate", BigDecimal.valueOf(11.85), true, true)
                );

        assertThat(basketById).extracting("taxesAmount", "total")
                .contains(BigDecimal.valueOf(6.70).setScale(2), BigDecimal.valueOf(74.68));

    }


}