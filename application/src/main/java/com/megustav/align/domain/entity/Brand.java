package com.megustav.align.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Brand entity
 *
 * @author MeGustav
 * 30/05/2018 21:12
 */
@Entity
@Table(name = "brands")
public class Brand {

    /** Brand id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** Brand name */
    @NotNull
    @Column(unique = true)
    private String name;

    public Brand(@NotNull String name) {
        this.name = name;
    }

    /**
     * Constructor for JPA
     */
    public Brand() { }

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

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
