package com.microcore.springcloud.msvc.items.services;

import com.microcore.springcloud.msvc.items.clients.ProductFeignClient;
import com.microcore.springcloud.msvc.items.models.Item;
import com.microcore.springcloud.msvc.items.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceFeign implements ItemService {

    ProductFeignClient client;

    @Override
    public List<Item> findAll() {
        Random random = new Random();

        return client.findAll().stream().map(product ->
            new Item(
                    product,
                    random.nextInt(10) + 1
            )
        ).toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Product product = client.details(id);
        if(product == null) return Optional.empty();

        return Optional.of(new Item(product, new Random().nextInt(10)+1));
    }
}
