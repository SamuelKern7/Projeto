package com.product.product.supplierService;

import com.product.product.document.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierService {

    private final String SUPPLIER_API_URL = "http://26.208.228.78:8000/api/v1/supplier?uuid=";

    @Autowired
    private RestTemplate restTemplate;

    public boolean checkSupplierExists(String supplierId) {
        String url = SUPPLIER_API_URL + supplierId;

        try {
            // Fazendo a requisição GET para a API de fornecedores
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Verificando se o fornecedor foi encontrado (status 200 OK)
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // Caso ocorra algum erro na requisição (como fornecedor não encontrado ou problema de rede)
            return false;
        }
    }
}
