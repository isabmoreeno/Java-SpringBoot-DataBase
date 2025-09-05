package com.example.product.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import com.example.product.entities.Product;
import com.example.product.repositories.ProductRepository;
import com.example.product.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//chamadas http do Angular

@RestController
@RequestMapping("products")

public class ProductController {

    @Autowired
    private ProductService service;
    
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(service.getAllProducts());
    }

  @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @DeleteMapping("{id}")
     public ResponseEntity<Void> deleteProductById(@PathVariable long id){
        service.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

     @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product newProduct = service.saveProduct(product);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.getId())
                .toUri();
        return ResponseEntity.created(location)
                             .body(newProduct);
    }


}

