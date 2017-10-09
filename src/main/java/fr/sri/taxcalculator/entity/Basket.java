package fr.sri.taxcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "basket_generator", sequenceName = "basket_generator")
    @GeneratedValue(generator = "basket_generator")
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "basket")
    private List<Product> products;

    public Basket() { //Jpa needs this
    }
}
