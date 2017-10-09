package fr.sri.taxcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@Getter
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "product_generator", sequenceName = "product_generator")
    @GeneratedValue(generator = "product_generator")
    private Long id;
    @ManyToOne
    private Basket basket;
    private String name;
    private BigDecimal price;
    private boolean isImported;
    private boolean isExempted;

    public Product() { //Jpa needs this
    }

}
