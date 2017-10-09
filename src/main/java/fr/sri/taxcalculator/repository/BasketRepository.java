package fr.sri.taxcalculator.repository;

import fr.sri.taxcalculator.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
}
