package com.product.product.service;

import com.product.product.document.Produto;
import com.product.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public Optional<Produto> getById (String id){
        return productRepository.findById(id);
    }
    public List<Produto> listarProdutos() {
        return productRepository.findAll();
    }
    public Produto create(Produto produto){
        return productRepository.save(produto);
    }
    public void deleteProduct(String id){
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
    public Optional<Produto> updateProduct(String id, Produto produtoAtualizado) {
        return productRepository.findById(id).map(produto -> {
            produto.setName(produtoAtualizado.getName());
            produto.setPrice(produtoAtualizado.getPrice());
            produto.setAmount(produtoAtualizado.getAmount());
            produto.setSupplierId(produtoAtualizado.getSupplierId());
            return productRepository.save(produto);
        });
    }
}