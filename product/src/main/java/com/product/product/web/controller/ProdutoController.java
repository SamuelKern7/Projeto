package com.product.product.web.controller;

import com.product.product.PROtest.ProdutoResponse;
import com.product.product.document.Produto;
import com.product.product.service.ProductService;
import com.product.product.supplierService.SupplierService;
import com.product.product.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/product")
@RestController
public class ProdutoController {

    private final ProductService produtoService;

    private final SupplierService supplierService;

    private final String SUPPLIER_API_URL = "http://26.208.228.78:8000/api/v1/supplier?uuid=";


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ProdutoController(ProductService produtoService, SupplierService supplierService) {
        this.produtoService = produtoService;
        this.supplierService = supplierService;
    }

    @Operation(summary = "Criar um novo Produto", description = "Recurso para criar um novo Produto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))),
                    @ApiResponse(responseCode = "409", description = "´Produto já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public Produto saveProduct(@RequestBody @Valid Produto produto) {
        // Verificar se o supplierId existe via API
        boolean isSupplierValid = supplierService.checkSupplierExists(produto.getSupplierId());

        if (isSupplierValid) {
            // Se o fornecedor existe, salvar o produto
            return produtoService.create(produto);
        } else {
            // Se o fornecedor não existe, lançar exceção ou retornar erro
            throw new IllegalArgumentException("Fornecedor não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Produto por ID", description = "Recurso para buscar um produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public Optional<Produto> getUserById(@PathVariable("id") String id) {
        System.out.println(produtoService.getById(id));
        return produtoService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Listar todos os Produtos", description = "Recurso para listar todos os produtos disponíveis",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de produtos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class)))
            })
    public List<Produto> listarProdutos() {
        System.out.println(produtoService.listarProdutos().toString());
        return produtoService.listarProdutos();
    }

    @Operation(summary = "Atualizar um Produto", description = "Recurso para atualizar um produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduct(@PathVariable("id") String id, @RequestBody Produto produtoAtualizado) {
        Optional<Produto> produto = produtoService.updateProduct(id, produtoAtualizado);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar um Produto", description = "Recurso para deletar um produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        produtoService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
