package com.megustav.align.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Product entity
 *
 * @author MeGustav
 * 30/05/2018 21:11
 */
@Entity
@Table(name = "products",
        uniqueConstraints = @UniqueConstraint(columnNames = { "name", "brand_id" }))
public class Product {

    /** Product id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** Product name */
    @NotNull
    @Column(name = "name")
    private String name;
    /** Brand information */
    @OneToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @NotNull
    private Brand brand;
    /** Product price */
    @NotNull
    private BigDecimal price;
    /** Product quantity */
    @NotNull
    private int quantity;

    public Product(@NotNull String name,
                   @NotNull Brand brand,
                   @NotNull BigDecimal price,
                   @NotNull int quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Constructor for JPA
     */
    public Product() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
