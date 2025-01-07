package com.microcore.springcloud.msvc.items.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {

    private Product product;
    private Integer quantity;

    public Double getTotal() {
        return product.getPrice() * quantity.doubleValue();
    }

}
