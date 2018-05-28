package com.megustav.align.repository.impl;

import com.megustav.align.repository.ProductRepository;
import com.megustav.align.repository.entity.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Products repository implementation
 *
 * @author MeGustav
 * 27/05/2018 23:56
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate template;

    public ProductRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    /** Get all products */
    private static final String GET_ALL_PRODUCTS =
            "SELECT " +
                    "pr.id as product_id, " +
                    "pr.name as product_name, " +
                    "br.name as brand_name, " +
                    "price, " +
                    "quantity " +
            "FROM products pr " +
            "INNER JOIN brands br " +
            "ON pr.brand_id = br.id";

    @Override
    public List<Product> getAllProducts() {
        return template.query(GET_ALL_PRODUCTS, PRODUCT_ROW_MAPPER);
    }

    /** Get all brands */
    private static final String GET_ALL_BRANDS = "SELECT name FROM brands";

    @Override
    public List<String> getAllBrands() {
        return template.query(GET_ALL_BRANDS, (rs, row) -> rs.getString("name"));
    }

    /** Find products */
    private static final String FIND_PRODUCTS_BY_NAME =
            GET_ALL_PRODUCTS + " WHERE pr.name = ?";

    @Override
    public List<Product> findProducts(String name) {
        return template.query(FIND_PRODUCTS_BY_NAME, PRODUCT_ROW_MAPPER, name);
    }

    /** Find product */
    private static final String FIND_PRODUCT_BY_NAME_AND_BRAND =
            GET_ALL_PRODUCTS + " WHERE pr.name = ? AND br.name = ?";

    @Override
    public Optional<Product> findProduct(String name, String brand) {
        try {
            Product product = template.queryForObject(
                    FIND_PRODUCT_BY_NAME_AND_BRAND,
                    PRODUCT_ROW_MAPPER,
                    name, brand
            );
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException ex) {
            // This exception literally means entity wasn't found
            return Optional.empty();
        }
    }

    /** Insert product */
    private static final String INSERT_PRODUCT =
            "INSERT INTO products (name, brand_id, price, quantity) VALUES (?, ?, ?, ?)";

    @Override
    public boolean insertProduct(String name, long brandId, BigDecimal price, long quantity) {
        int updated = template.update(
                INSERT_PRODUCT,
                ps -> {
                    ps.setString(1, name);
                    ps.setLong(2, brandId);
                    ps.setBigDecimal(3, price);
                    ps.setLong(4, quantity);
                }
        );
        return updated > 0;
    }

    /** Insert brand */
    private static final String INSERT_BRAND = "INSERT INTO brands (name) VALUES (?)";

    @Override
    public boolean insertBrand(String brand) {
        int updated = template.update(
                INSERT_BRAND,
                ps -> ps.setString(1, brand)
        );
        return updated > 0;
    }

    /** Callback for extracting multiple products info */
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, row) -> new Product(
            rs.getLong("product_id"),
            rs.getString("product_name"),
            rs.getString("brand_name"),
            rs.getBigDecimal("price"),
            rs.getLong("quantity")
    );

}
