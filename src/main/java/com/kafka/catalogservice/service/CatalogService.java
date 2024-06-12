package com.kafka.catalogservice.service;

import com.kafka.catalogservice.cassandra.entity.Product;
import com.kafka.catalogservice.cassandra.repository.ProductRepository;
import com.kafka.catalogservice.dto.ProductTagDto;
import com.kafka.catalogservice.feign.SearchClient;
import com.kafka.catalogservice.mysql.entity.SellerProduct;
import com.kafka.catalogservice.mysql.repository.SellerProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    @Autowired
    SellerProductRepository sellerProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SearchClient searchClient;

    public Product regeisterProduct(Long sellerId,
                                    String name,
                                    String description,
                                    Long price,
                                    Long stockCount,
                                    List<String> tags) {

        var sellerProduct = new SellerProduct(sellerId);
        sellerProductRepository.save(sellerProduct);

        Product product = new Product(
                sellerProduct.id,
                sellerId,
                name,
                description,
                price,
                stockCount,
                tags
        );

        var productTag = new ProductTagDto();
        productTag.tags = tags;
        productTag.productId = product.id;

        searchClient.addTagCache(productTag);

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId);

        if(product.isPresent()) {
            var productTag = new ProductTagDto();
            productTag.tags = product.get().tags;
            productTag.productId = product.get().id;
        }

        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);


    }

    public List<Product> getProductsBySellerId(Long sellerId) {
        var sellerProducts = sellerProductRepository.findBySellerId(sellerId);

        var products = new ArrayList<Product>();
        for(var item : sellerProducts) {
            var product = productRepository.findById(item.id);
            product.ifPresent(products::add);
        }

        return products;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Product decreaseStockCount(Long productId, Long decreaseCount) {
        var product = productRepository.findById(productId).orElseThrow();
        product.stockCount = product.stockCount - Math.min(decreaseCount, product.stockCount);

        return productRepository.save(product);
    }


}
