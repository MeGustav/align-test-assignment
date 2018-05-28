package com.megustav.align;

import com.megustav.align.configuration.RepositoryConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.repository.ProductRepository;
import com.megustav.align.repository.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Product repository tests
 *
 * @author MeGustav
 * 27/05/2018 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        RepositoryConfiguration.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    /** Using template to provide test data without using the repository itself */
    @Autowired
    private JdbcTemplate template;

    /**
     * Test {@link ProductRepository#getAllBrands()} basic functionality
     */
    @Test
    public void testGetAllBrandsBasic() {
        template.execute("INSERT INTO brands (name) VALUES ('Nike')");
        template.execute("INSERT INTO brands (name) VALUES ('Adidas')");

        List<String> brands = repository.getAllBrands();
        assertThat(brands).as("Brands").hasSize(2);
        assertThat(brands).contains("Nike", "Adidas");
    }

    /**
     * Test that {@link ProductRepository#getAllBrands()} won't fail if no brands are present
     */
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(repository.getAllBrands()).as("Brands").hasSize(0);
    }

    /**
     * Test {@link ProductRepository#getAllProducts()} basic functionality
     */
    @Test
    public void testGetAllProductsBasic() {
        template.execute("INSERT INTO brands VALUES (1, 'Reebok')");
        template.execute("INSERT INTO brands VALUES (2, 'Not Reebok')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 2, 1000.00, 10)");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Sneakers', 1, 3000.00, 5)");

        List<Product> products = repository.getAllProducts();
        assertThat(products).as("Products").hasSize(2);
        assertThat(products)
                .extracting(Product::getName).as("Product name")
                .contains("Sneakers", "Shirt");

        Optional<Product> sneakersOpt = products.stream()
                .filter(prod -> prod.getName().equals("Sneakers"))
                .findAny();
        // We already know it is present, but check nevertheless
        assertThat(sneakersOpt).isPresent();
        Product product = sneakersOpt.get();
        assertThat(product.getBrand()).as("Product brand").isEqualTo("Reebok");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("3000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(5);
    }

    /**
     * Test that {@link ProductRepository#getAllProducts()} won't fail if no brands are present
     */
    @Test
    public void testGetAllProductsEmpty() {
        assertThat(repository.getAllProducts()).as("Products").hasSize(0);
    }

    /**
     * Test {@link ProductRepository#findProducts(String)} basic functionality
     */
    @Test
    public void testFindProductsBasic() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO brands VALUES (2, 'Adidas')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 2, 1000.00, 10)");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Sneakers', 1, 3000.00, 5)");

        List<Product> found = repository.findProducts("Shirt");
        assertThat(found).as("Found products").hasSize(1);
        Product product = found.get(0);
        assertThat(product.getBrand()).as("Product brand").isEqualTo("Adidas");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(10);
    }

    /**
     * Test {@link ProductRepository#findProducts(String)} with multiple results
     */
    @Test
    public void testFindProductsMultiple() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO brands VALUES (2, 'Adidas')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 2, 2000.00, 10)");

        List<Product> found = repository.findProducts("Shirt");
        assertThat(found).as("Found products").hasSize(2);
        assertThat(found)
                .extracting(Product::getBrand).as("Brand")
                .contains("Nike", "Adidas");

    }

    /**
     * Test {@link ProductRepository#findProducts(String)} with no results
     */
    @Test
    public void testFindProductsNoProducts() {
        assertThat(repository.findProducts("non existent")).as("Products").hasSize(0);
    }

    /**
     * Test {@link ProductRepository#findProduct(String, String)} basic functionality
     */
    @Test
    public void testFindProductBasic() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Sneakers', 1, 2000.00, 10)");
        Optional<Product> productOpt = repository.findProduct("Shirt", "Nike");
        assertThat(productOpt).as("Product").isPresent();
        Product product = productOpt.get();
        assertThat(product.getBrand()).as("Product brand").isEqualTo("Nike");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("5000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(5);
    }

    /**
     * Test {@link ProductRepository#findProduct(String, String)} no product for name
     */
    @Test
    public void testFindProductNoProductAtAll() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        assertThat(repository.findProduct("Sneakers", "Nike")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#findProduct(String, String)} with brand not containing product
     */
    @Test
    public void testFindProductNoProductInBrand() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO brands VALUES (2, 'Adidas')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        assertThat(repository.findProduct("Shirt", "Adidas")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#findProduct(String, String)} with brand not existing
     */
    @Test
    public void testFindProductNoBrand() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        assertThat(repository.findProduct("Shirt", "Adidas")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#insertBrand(String)} basic functionality
     */
    @Test
    public void testInsertBrandBasic() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        boolean inserted = repository.insertBrand("Adidas");
        assertThat(inserted).as("Brand was inserted").isTrue();
        List<String> brands =
                template.query("SELECT name FROM brands", (rs, num) -> rs.getString("name"));
        assertThat(brands).as("Brands").hasSize(2);
        assertThat(brands).contains("Nike", "Adidas");
    }

    /**
     * Test {@link ProductRepository#insertBrand(String)} when passing existing brand
     */
    @Test(expected = DuplicateKeyException.class)
    public void testInsertBrandDuplicateFails() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        repository.insertBrand("Nike");
    }

    /**
     * Test {@link ProductRepository#insertProduct(String, long, BigDecimal, long)}
     * basic functionality
     *
     * TODO more complex data check
     */
    @Test
    public void testInsertProductBasic() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        boolean inserted = repository.insertProduct("Sweater", 1, BigDecimal.TEN, 30);
        assertThat(inserted).as("Product were inserted").isTrue();
        List<String> products =
                template.query("SELECT name FROM products", (rs, num) -> rs.getString("name"));
        assertThat(products).as("Products").hasSize(2);
        assertThat(products).contains("Shirt", "Sweater");
    }

    /**
     * Test {@link ProductRepository#insertProduct(String, long, BigDecimal, long)}
     * when inserting product with same name and different brands
     */
    @Test
    public void testInsertProductSameNameDifferentBrands() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO brands VALUES (2, 'Adidas')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        boolean inserted = repository.insertProduct("Shirt", 2, BigDecimal.TEN, 30);
        assertThat(inserted).as("Product were inserted").isTrue();
        List<String> products =
                template.query("SELECT name FROM products", (rs, num) -> rs.getString("name"));
        assertThat(products).as("Products").hasSize(2);
        assertThat(products).containsExactly("Shirt", "Shirt");
    }

    /**
     * Test {@link ProductRepository#insertProduct(String, long, BigDecimal, long)}
     * when inserting product with same name and same brand
     */
    @Test(expected = DuplicateKeyException.class)
    public void testInsertProductSameNameSameBrands() {
        template.execute("INSERT INTO brands VALUES (1, 'Nike')");
        template.execute("INSERT INTO products (name, brand_id, price, quantity) " +
                "VALUES ('Shirt', 1, 5000.00, 5)");
        repository.insertProduct("Shirt", 1, BigDecimal.TEN, 30);

    }

}
