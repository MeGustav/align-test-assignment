package com.megustav.align.repository.entity;

import java.math.BigDecimal;

/**
 * Product entity
 *
 * @author MeGustav
 * 28/05/2018 00:10
 */
public class Product {

    /** Product id */
    private final long id;
    /** Product name */
    private final String name;
    /** Brand information */
    private final Brand brand;
    /** Product price */
    private final BigDecimal price;
    /** Product quantity */
    private final long quantity;

    public Product(long id, String name, Brand brand, BigDecimal price, long quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
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
