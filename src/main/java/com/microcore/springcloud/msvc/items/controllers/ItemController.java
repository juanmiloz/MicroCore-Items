package com.microcore.springcloud.msvc.items.controllers;

import com.microcore.springcloud.msvc.items.models.Item;
import com.microcore.springcloud.msvc.items.models.Product;
import com.microcore.springcloud.msvc.items.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory cBreakerFactory;

    public ItemController(@Qualifier("itemServiceFeign") ItemService itemService, CircuitBreakerFactory cBreakerFactory) {
        this.itemService = itemService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping
    public ResponseEntity<List<Item>> list(
            @RequestParam(name = "name", required = false) String name,
            @RequestHeader(name = "token-request", required = false) String token
    )
    {
        System.out.println(name); //To recover the request parameter from gateway filter
        System.out.println(token);//To recover the request header from gateway filter
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) throws InterruptedException {
        Optional<Item> itemOptional = cBreakerFactory.create("items").run(
                () -> itemService.findById(id),
                e -> {
                    System.out.println(e.getMessage());
                    logger.error(e.getMessage());

                    Product product = new Product();
                    product.setCreateAt(LocalDate.now());
                    product.setId(1L);
                    product.setName("Camara Sony");
                    product.setPrice(500.00);
                    return Optional.of(new Item(product, 5));
                }
        );
        if(itemOptional.isPresent()){
            return ResponseEntity.ok(itemOptional.get());
        }else{
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Dont exist the searched product in the microservice msvc-products"));
        }
    }

}
