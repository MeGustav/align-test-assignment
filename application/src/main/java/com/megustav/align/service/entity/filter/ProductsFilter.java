package com.megustav.align.service.entity.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter according to which products should be found
 *
 * @author MeGustav
 * 31/05/2018 19:40
 */
public class ProductsFilter {

    /** Pagination configuration */
    private final Pagination pagination;
    /** Sort configuration */
    private final Sort sort;
    /** Search configuration */
    private final Search search;

    @JsonCreator
    public ProductsFilter(@JsonProperty(value = "pagination", required = true) Pagination pagination,
                          @JsonProperty(value = "sort") Sort sort,
                          @JsonProperty(value = "search") Search search) {
        this.pagination = pagination;
        this.sort = sort;
        this.search = search;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public Sort getSort() {
        return sort;
    }

    public Search getSearch() {
        return search;
    }

    /**
     * Pagination configuration
     */
    public static class Pagination {

        /** Page number */
        private final int page;
        /** Page size */
        private final int size;

        @JsonCreator
        public Pagination(@JsonProperty(value = "page", required = true) int page,
                          @JsonProperty(value = "size", required = true) int size) {
            this.page = page;
            this.size = size;
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "Pagination{" +
                    "page=" + page +
                    ", size=" + size +
                    '}';
        }
    }

    /**
     * Sort configuration
     */
    public static class Sort {

        /** Sorted field */
        private String field;
        /** Whether or no to sort ascending */
        private boolean asc;

        @JsonCreator
        public Sort(@JsonProperty(value = "field", required = true) String field,
                    @JsonProperty(value = "asc", required = true) boolean asc) {
            this.field = field;
            this.asc = asc;
        }

        public String getField() {
            return field;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        @Override
        public String toString() {
            return "Sort{" +
                    "field='" + field + '\'' +
                    ", asc=" + asc +
                    '}';
        }
    }

    /**
     * Search configuration
     */
    public static class Search {

        /** Searched product name */
        private String name;
        /** Searched brand name */
        private String brand;

        public Search(@JsonProperty("name") String name,
                      @JsonProperty("brand") String brand) {
            this.name = name;
            this.brand = brand;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        @Override
        public String toString() {
            return "Search{" +
                    "name='" + name + '\'' +
                    ", brand='" + brand + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductsFilter{" +
                "pagination=" + pagination +
                ", sort=" + sort +
                ", search=" + search +
                '}';
    }
}
