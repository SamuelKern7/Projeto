package com.product.product.PROtest;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoResponse {

    private String id;

    private String nome;

    private BigDecimal price;

    private int amount;

    private String supplier_id;
}

