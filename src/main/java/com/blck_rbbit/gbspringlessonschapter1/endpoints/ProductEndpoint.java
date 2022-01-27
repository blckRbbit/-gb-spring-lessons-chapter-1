package com.blck_rbbit.gbspringlessonschapter1.endpoints;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.services.ProductService;
import com.blck_rbbit.gbspringlessonschapter1.soap.products.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Objects;


/** Классы GetProductByIdResponse, GetProductByIdRequest, GetAllProductsResponse, GetAllProductsRequest
 * получаем так: maven -> Plugins -> jaxb2 -> xjc
 * по пути target -> generatedSources -> com... нагенерятся данные классы,
 * их нужно скопировать и перенести в какой-то пакет проекта (тут - пакет soap)
 **/
@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.blck_rbbit.com/gbspringlessonschapter1/products";
    private final ProductService productService;
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        ProductSoap soapProduct = new ProductSoap();
        Product product = productService.findById(request.getId()).orElse(null);
        soapProduct.setId(Objects.requireNonNull(product).getId());
        soapProduct.setTitle(product.getTitle());
        soapProduct.setCost(product.getCost());
        response.setProduct(soapProduct);
        response.setProduct(soapProduct);
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProducts().forEach(product -> response.getProducts().add(
                new ProductSoap(product.getId(), product.getTitle(), product.getCost())));
        return response;
    }
    
        /*
        Пример запроса: POST http://localhost:8187/app/ws
        Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.blck_rbbit.com/gbspringlessonschapter1/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */
}
