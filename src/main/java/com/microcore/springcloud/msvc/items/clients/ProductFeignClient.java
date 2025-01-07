package com.microcore.springcloud.msvc.items.clients;

import com.microcore.springcloud.msvc.items.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-product") //Note: Actually we don't have implemented eureka our server discovery service, so we need to specify the url of the service we want to consume
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product details(@PathVariable  Long id);

}
