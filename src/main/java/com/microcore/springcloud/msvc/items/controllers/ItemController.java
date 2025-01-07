package com.microcore.springcloud.msvc.items.controllers;

import com.microcore.springcloud.msvc.items.models.Item;
import com.microcore.springcloud.msvc.items.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ItemController {

    final private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> list() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Item> itemOptional = itemService.findById(id);

        if(itemOptional.isPresent()){
            return ResponseEntity.ok(itemOptional.get());
        }else{
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Dont exist the searched product in the microservice msvc-products"));
        }
    }

}
