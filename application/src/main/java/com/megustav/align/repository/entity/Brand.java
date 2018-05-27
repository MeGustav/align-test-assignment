package com.megustav.align.repository.entity;

/**
 * Brand entity
 *
 * @author MeGustav
 * 28/05/2018 00:10
 */
public class Brand {

    /** Brand id */
    private final long id;
    /** Brand name */
    private final String name;

    public Brand(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
