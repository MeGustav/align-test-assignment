package com.megustav.align.repository.impl;

import com.megustav.align.repository.ProductRepository;
import com.megustav.align.repository.entity.Brand;
import com.megustav.align.repository.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Products repository
 *
 * @author MeGustav
 * 27/05/2018 23:56
 */
public class ProductRepositoryImpl implements ProductRepository {

    /** JDBC template */
    private final JdbcTemplate template;

    public ProductRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    /** SQL statement providing all products */
    private static final String GET_ALL_PRODUCTS =
            "SELECT " +
                    "pr.id as product_id, " +
                    "pr.name as product_name, " +
                    "brand_id, " +
                    "br.name as brand_name, " +
                    "price, " +
                    "quantity " +
            "FROM products pr " +
            "INNER JOIN brands br " +
            "ON pr.brand_id = br.id";

    @Override
    public List<Product> getAllProducts() {
        return template.query(GET_ALL_PRODUCTS, (rs, row) -> new Product(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                new Brand(rs.getLong("brand_id"), rs.getString("brand_name")),
                rs.getBigDecimal("price"),
                rs.getLong("quantity")
        ));
    }

}
