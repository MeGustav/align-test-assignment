package com.megustav.align.repository;

import com.megustav.align.configuration.JpaConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.domain.entity.Brand;
import com.megustav.align.domain.repository.BrandRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 * Brand repository tests.
 *
 * Not using a repository itself for data preparation and checks.
 * Though it's inconvenient and one could say that repository
 * generation is tested anyway by the devs (therefore making
 * us free to use repository in any case) for the sake
 * of the assignment I decided to use {@link JdbcTemplate}
 *
 * @author MeGustav
 * 30/05/2018 22:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        JpaConfiguration.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BrandRepositoryTest {

    /** Using template to provide test data without using the repository itself */
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private BrandRepository repository;

    /**
     * Test that {@link BrandRepository#findAll()} basic functionality
     */
    @Test
    public void testBrandsBasic() {
        template.update("INSERT INTO brands (name) VALUES (?)", "Nike");

        List<Brand> brands = repository.findAll();
        Assertions.assertThat(brands).hasSize(1).extracting(Brand::getName).containsExactly("Nike");
    }

    /**
     * Test that {@link BrandRepository#findAll()} basic functionality
     */
    @Test
    public void testBrandsFindByName() {
        template.update("INSERT INTO brands (name) VALUES (?)", "Nike");
        template.update("INSERT INTO brands (name) VALUES (?)", "Adidas");

        Optional<Brand> brand = repository.findByName("Adidas");
        Assertions.assertThat(brand)
                .isPresent()
                .containsInstanceOf(Brand.class)
                .map(Brand::getName).get().isEqualTo("Adidas");
    }

    /**
     * Test that {@link BrandRepository#findAll()} basic functionality
     */
    @Test
    public void testBrandsFindByNameIfNothingFound() {
        template.update("INSERT INTO brands (name) VALUES (?)", "Nike");

        Optional<Brand> brand = repository.findByName("Adidas");
        Assertions.assertThat(brand).isNotPresent();
    }

    /**
     * Test that {@link BrandRepository#findAll()} won't fail if no products are present
     */
    @Test
    public void testBrandsEmpty() {
        List<String> brands =
                template.query("SELECT name FROM brands", (rs, num) -> rs.getString("name"));
        Assertions.assertThat(brands).hasSize(0);
    }

    /**
     * Test {@link BrandRepository#save(Object)} basic functionality
     */
    @Test
    public void testBrandSave() {
        repository.save(new Brand("Nike"));
        List<String> brands =
                template.query("SELECT name FROM brands", (rs, num) -> rs.getString("name"));
        Assertions.assertThat(brands).hasSize(1).containsExactly("Nike");
    }

    /**
     * Test {@link BrandRepository#save(Object)} when trying to save a duplicate
     */
    @Test(expected = PersistenceException.class)
    public void testBrandSaveDuplicate() {
        repository.save(new Brand("Nike"));
        repository.save(new Brand("Nike"));
    }

}
