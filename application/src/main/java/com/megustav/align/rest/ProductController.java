package com.megustav.align.rest;

import com.megustav.align.service.ProductService;
import com.megustav.align.service.entity.ProductDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Product REST controller
 *
 * @author MeGustav
 * 27/05/2018 22:29
 */
@RestController
@RequestMapping(path = "products")
public class ProductController {

    /** Product service */
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ProductDTO> getProducts() {
        return service.getAllProducts();
    }

}
