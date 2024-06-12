package com.kafka.catalogservice.cassandra.repository;

import com.kafka.catalogservice.cassandra.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {
}
