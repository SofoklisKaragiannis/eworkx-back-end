package test.eworx.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application
 * Implements RESTful service back-end
 *
 * Support 4 API calls:
 *
 * * 1. Products retrieve done by call ProductsController
 * * (Method: GET Url: https://{host}/rest/v1/getProducts
 * * which responds AvailableProducts in JSON format
 *
 * * 2. Basket data retrieve done by call BasketController
 * * (Method: GET Url: https://{host}/rest/v1/getBasket
 * * which responds Basket in JSON format
 *
 * * 3. Product counter increment done by call AddProductController
 * * (Method: GET Url: https://{host}/rest/v1/addProduct?id={id}
 * * which responds SelectedProduct in JSON format
 *
 * * 4. Product counter decrement done by call RemoveProductController
 * * (Method: GET Url: https://{host}/rest/v1/removeProduct?id={id}
 * * which responds SelectedProduct in JSON format
 */
@SpringBootApplication
public class WebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }

}

