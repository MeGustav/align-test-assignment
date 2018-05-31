package com.megustav.align.domain.repository;

import com.megustav.align.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Brand repository
 *
 * @author MeGustav
 * 30/05/2018 21:19
 */
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * Find brand by name
     *
     * @param name brand name
     * @return optional brand
     */
    Optional<Brand> findByName(String name);
}
