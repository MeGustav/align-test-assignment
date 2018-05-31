package com.megustav.align.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Products response
 *
 * @author MeGustav
 * 31/05/2018 22:08
 */
public class ProductsResponse {

    /** Total number of filtered products */
    @JsonProperty(value = "total", required = true)
    private final long totalSize;
    /** Products to show on the page */
    @JsonProperty(value = "products", required = true)
    private final List<ProductDTO> pageProducts;

    public ProductsResponse(long totalSize, List<ProductDTO> pageProducts) {
        this.totalSize = totalSize;
        this.pageProducts = pageProducts;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public List<ProductDTO> getPageProducts() {
        return pageProducts;
    }

    @Override
    public String toString() {
        return "ProductsResponse{" +
                "totalSize=" + totalSize +
                ", pageProducts=" + pageProducts +
                '}';
    }
}
