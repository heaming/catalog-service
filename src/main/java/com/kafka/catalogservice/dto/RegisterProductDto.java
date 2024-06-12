package com.kafka.catalogservice.dto;

import org.springframework.data.cassandra.core.mapping.Column;

import java.util.List;

public class RegisterProductDto {
    public Long sellerId;
    public String name;
    public String description;
    public Long price;
    public Long stockCount;
    public List<String> tags;
}
