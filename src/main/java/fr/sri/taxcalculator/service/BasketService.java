package fr.sri.taxcalculator.service;

import fr.sri.taxcalculator.dto.BasketDto;
import fr.sri.taxcalculator.entity.Basket;
import fr.sri.taxcalculator.mapper.BasketMapper;
import fr.sri.taxcalculator.repository.BasketRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j
public class BasketService {

    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public BasketDto getBasketById(Long id) {
        Basket basket = basketRepository.findOne(id);

        return BasketMapper.entityToDto(basket);
    }

    @Transactional
    public Long createBasket(BasketDto basket) {

        Basket basketToSave = BasketMapper.dtoToEntity(basket);

        Basket createdBasket = basketRepository.save(basketToSave);

        return createdBasket.getId();

    }
}
