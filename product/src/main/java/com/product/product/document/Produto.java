package com.product.product.document;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Document(collection = "produto_config")
public class Produto {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field("name")
    private String name;

    @Field("price")
    private BigDecimal price;

    @Field("amount")
    private int amount;

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "O supplierId deve ser um UUID válido")
    @Field("supplierId")
    private String supplierId;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }
    public String getId() {
        return id;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getSupplierId() { // Alterando para "supplierId"
        return supplierId;
    }

    public void setSupplierId(String supplierId) { // Alterando para "supplierId"
        this.supplierId = supplierId;
    }

}
