package com.microcore.springcloud.msvc.items.services;

import com.microcore.springcloud.msvc.items.clients.ProductFeignClient;
import com.microcore.springcloud.msvc.items.models.Item;
import com.microcore.springcloud.msvc.items.models.Product;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        try{
            Product product = client.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10)+1));
        }catch (FeignException e){
            return Optional.empty();
        }
    }
}
