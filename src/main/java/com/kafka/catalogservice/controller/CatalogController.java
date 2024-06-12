package com.kafka.catalogservice.controller;

import com.kafka.catalogservice.cassandra.entity.Product;
import com.kafka.catalogservice.dto.DecreaseStockCountDto;
import com.kafka.catalogservice.dto.RegisterProductDto;
import com.kafka.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @PostMapping("/product")
    public Product registerProduct(@RequestBody RegisterProductDto request) {
        return catalogService.regeisterProduct(
                request.sellerId,
                request.name,
                request.description,
                request.price,
                request.stockCount,
                request.tags
        );
    }

    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return catalogService.getProductById(productId);
    }

    @GetMapping("/seller/{sellerId}/products")
    public List<Product> getProductBySellerId(@PathVariable Long sellerId) throws Exception {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @PostMapping("/product/{productId}/stock-count")
    public Product decreaseStockCount(@PathVariable Long productId,
                                      @RequestBody DecreaseStockCountDto request) {
        return catalogService.decreaseStockCount(productId, request.decreaseCount);
    }
}
