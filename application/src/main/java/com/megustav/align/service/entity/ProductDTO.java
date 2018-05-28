package com.megustav.align.service.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Product DTO
 *
 * @author MeGustav
 * 28/05/2018 01:22
 */
public class ProductDTO {

    /** Product name */
    private final String name;
    /** Product brand */
    private final String brand;
    /** Product price */
    private final BigDecimal price;
    /** Product quantity */
    private final long quantity;

    @JsonCreator
    public ProductDTO(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "brand", required = true) String brand,
            @JsonProperty(value = "price", required = true) BigDecimal price,
            @JsonProperty(value = "quantity", required = true) long quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
