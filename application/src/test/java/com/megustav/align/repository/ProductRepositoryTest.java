package com.megustav.align.repository;

import com.megustav.align.configuration.JpaConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.domain.entity.Brand;
import com.megustav.align.domain.entity.Product;
import com.megustav.align.domain.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Product repository tests.
 *
 * Testing only used/custom methods and database constraints
 *
 * Not using a repository itself for data preparation and checks.
 * Though it's inconvenient and one could say that repository
 * generation is tested anyway by the devs (therefore making
 * us free to use repository in any case) for the sake
 * of the assignment I decided to use {@link JdbcTemplate}
 *
 * @author MeGustav
 * 27/05/2018 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        JpaConfiguration.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    /** Using template to provide test data without using the repository itself */
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private ProductRepository repository;

    /**
     * Test {@link ProductRepository#findAll()} basic functionality
     */
    @Test
    public void testGetAllProductsBasic() {
        insertBrand(1, "Reebok");
        insertBrand(2, "Not Reebok");
        insertProduct("Shirt", 2, "1000.00", 10);
        insertProduct("Sneakers", 1, "3000.00", 5);

        List<Product> products = repository.findAll();
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
        assertThat(product.getBrand().getName()).as("Product brand").isEqualTo("Reebok");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("3000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(5);
    }

    /**
     * Test that {@link ProductRepository#findAll()} won't fail if no products are present
     */
    @Test
    public void testGetAllProductsEmpty() {
        assertThat(repository.findAll()).as("Products").hasSize(0);
    }

    /**
     * Tesing pageable functionality
     */
    @Test
    public void testGetProductsPageable() {
        insertBrand(1, "Nike");
        IntStream.range(1, 10).forEach(idx -> insertProduct("Product" + idx, 1, idx + "0.00", idx));
        List<Product> products = repository.findAll(PageRequest.of(2, 2)).getContent();
        assertThat(products).hasSize(2).extracting(Product::getName).contains("Product5", "Product6");
    }

    /**
     * Test {@link ProductRepository#findAllByQuantityLessThanEqual(int, Pageable)} basic
     */
    @Test
    public void testGetProductsLeftovers() {
        insertBrand(1, "Nike");
        IntStream.range(1, 20).forEach(idx -> insertProduct("Product" + idx, 1, idx + "0.00", idx));
        int leftoverQuantity = 10;
        List<Product> products = repository.findAllByQuantityLessThanEqual(leftoverQuantity, PageRequest.of(0, 20)).getContent();
        assertThat(products).hasSize(10)
                .extracting(Product::getQuantity)
                .allMatch(quantity -> quantity <= leftoverQuantity);
    }

    /**
     * Test {@link ProductRepository#findAllByName(String)} basic functionality
     */
    @Test
    public void testFindProductsBasic() {
        insertBrand(1, "Reebok");
        insertBrand(2, "Adidas");
        insertProduct("Shirt", 2, "1000.00", 10);
        insertProduct("Sneakers", 1, "3000.00", 5);

        List<Product> found = repository.findAllByName("Shirt");
        assertThat(found).as("Found products").hasSize(1);
        Product product = found.get(0);
        assertThat(product.getBrand().getName()).as("Product brand").isEqualTo("Adidas");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(10);
    }

    /**
     * Test {@link ProductRepository#findAllByName(String)} with multiple results
     */
    @Test
    public void testFindProductsMultiple() {
        insertBrand(1, "Reebok");
        insertBrand(2, "Adidas");
        insertProduct("Shirt", 2, "5000.00", 10);
        insertProduct("Shirt", 1, "2000.00", 5);

        List<Product> found = repository.findAllByName("Shirt");
        assertThat(found).as("Found products").hasSize(2);
        assertThat(found)
                .extracting(Product::getBrand).as("Brand")
                .extracting(Brand::getName)
                .contains("Reebok", "Adidas");
    }

    /**
     * Test {@link ProductRepository#findAllByName(String)} with no results
     */
    @Test
    public void testFindProductsNoProducts() {
        assertThat(repository.findAllByName("non existent")).as("Products").hasSize(0);
    }

    /**
     * Test {@link ProductRepository#findByNameAndBrandName(String, String)} basic functionality
     */
    @Test
    public void testFindProductBasic() {
        insertBrand(1, "Nike");
        insertProduct("Shirt", 1, "5000.00", 10);
        insertProduct("Sneakers", 1, "2000.00", 5);

        Optional<Product> productOpt = repository.findByNameAndBrandName("Shirt", "Nike");
        assertThat(productOpt).as("Product").isPresent();
        Product product = productOpt.get();
        assertThat(product.getBrand().getName()).as("Product brand").isEqualTo("Nike");
        assertThat(product.getPrice()).as("Product price").
                isEqualByComparingTo(new BigDecimal("5000.00"));
        assertThat(product.getQuantity()).as("Product quantity").isEqualTo(10);
    }

    /**
     * Test {@link ProductRepository#findByNameAndBrandName(String, String)} no product for name
     */
    @Test
    public void testFindProductNoProductAtAll() {
        insertBrand(1, "Nike");
        insertProduct("Shirt", 1, "5000.00", 10);
        assertThat(repository.findByNameAndBrandName("Sneakers", "Nike")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#findByNameAndBrandName(String, String)} with brand not containing product
     */
    @Test
    public void testFindProductNoProductInBrand() {
        insertBrand(1, "Nike");
        insertProduct("Shirt", 1, "5000.00", 5);

        assertThat(repository.findByNameAndBrandName("Shirt", "Adidas")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#findByNameAndBrandName(String, String)} with brand not existing
     */
    @Test
    public void testFindProductNoBrand() {
        insertBrand(1, "Nike");
        insertProduct("Shirt", 1, "5000.00", 5);

        assertThat(repository.findByNameAndBrandName("Shirt", "Adidas")).as("Product").isNotPresent();
    }

    /**
     * Test {@link ProductRepository#save(Object)} basic functionality
     *
     */
    @Test
    public void testInsertProductBasic() {
        Brand brand = createBrandWithId(1);
        insertBrand(brand.getId(), "Nike");

        Product product1 = new Product("Sweater", brand, BigDecimal.TEN, 30);
        Product product2 = new Product("Shirt", brand, BigDecimal.TEN, 30);

        repository.save(product1);
        repository.save(product2);
        List<String> products =
                template.query("SELECT name FROM products", (rs, num) -> rs.getString("name"));
        assertThat(products).as("Products").hasSize(2);
        assertThat(products).contains("Shirt", "Sweater");
    }

    /**
     * Test {@link ProductRepository#save(Object)}
     * when inserting product with same name and different brands
     */
    @Test
    public void testInsertProductSameNameDifferentBrands() {
        insertBrand(1, "Nike");
        insertBrand(2, "Adidas");

        Brand brand1 = createBrandWithId(1);
        Brand brand2 = createBrandWithId(2);
        Product product1 = new Product("Shirt", brand1, BigDecimal.TEN, 30);
        Product product2 = new Product("Shirt", brand2, BigDecimal.TEN, 30);

        repository.save(product1);
        repository.save(product2);

        List<String> products =
                template.query("SELECT name FROM products", (rs, num) -> rs.getString("name"));
        assertThat(products).as("Products").hasSize(2);
        assertThat(products).containsExactly("Shirt", "Shirt");
    }

    /**
     * Test {@link ProductRepository#save(Object)}
     * when inserting product with same name and same brand
     */
    @Test(expected = PersistenceException.class)
    public void testInsertProductSameNameSameBrands() {
        insertBrand(1, "Nike");

        Brand brand = createBrandWithId(1);
        Product product1 = new Product("Sweater", brand, BigDecimal.TEN, 30);
        Product product2 = new Product("Sweater", brand, BigDecimal.TEN, 30);
        repository.save(product1);
        repository.save(product2);
    }

    /** Insertion scripts */
    private static final String INSERT_BRAND = "INSERT INTO brands (id, name) VALUES (?, ?)";
    private static final String INSERT_PRODUCT = "INSERT INTO products (name, brand_id, price, quantity) " +
            "VALUES (?, ?, ?, ?)";

    /**
     * Insert brand
     *
     * @param id id
     * @param name name
     */
    private void insertBrand(long id, String name) {
        template.update(INSERT_BRAND, id, name);
    }

    /**
     * Insert product
     *
     * @param name name
     * @param brandId brand id
     * @param price price
     * @param quantity quantity
     */
    private void insertProduct(String name, long brandId, String price, long quantity) {
        template.update(INSERT_PRODUCT, name, brandId, new BigDecimal(price), quantity);
    }

    /**
     * Create an existing brand (with just an id)
     *
     * @param id brand id
     * @return brand with id
     */
    public Brand createBrandWithId(long id) {
        Brand brand = new Brand();
        brand.setId(id);
        return brand;
    }

}
