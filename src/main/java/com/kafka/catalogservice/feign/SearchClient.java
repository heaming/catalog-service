package com.kafka.catalogservice.feign;

import com.kafka.catalogservice.dto.ProductTagDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "searchClient", url = "http://search-service:8080")
public interface SearchClient {

    @PostMapping(value = "/search/tagCache")
    void addTagCache(@RequestBody ProductTagDto request);
    @DeleteMapping(value = "/search/tagCache")
    void removeTagCache(@RequestBody ProductTagDto request);
}
