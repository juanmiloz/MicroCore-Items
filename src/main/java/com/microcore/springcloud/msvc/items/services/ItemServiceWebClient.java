package com.microcore.springcloud.msvc.items.services;

import com.microcore.springcloud.msvc.items.models.Item;
import com.microcore.springcloud.msvc.items.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@Service
//@Primary
@AllArgsConstructor
public class ItemServiceWebClient implements ItemService{

    private final WebClient.Builder client;

    @Override
    public List<Item> findAll() {
        return client.build()
                .get()
                .uri("http://msvc-product")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        try{
            return Optional.ofNullable(
                    client.build()
                            .get()
                            .uri("http://msvc-product/{id}", params)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(Product.class)
                            .map(product -> new Item(product, new Random().nextInt(10) + 1))
                            .block()
            );
        }catch (WebClientResponseException e){
            return Optional.empty();
        }
    }
}
