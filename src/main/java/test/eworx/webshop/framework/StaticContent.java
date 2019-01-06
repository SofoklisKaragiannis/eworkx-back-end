package test.eworx.webshop.framework;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import test.eworx.webshop.model.Product;
import test.eworx.webshop.model.ResponseStatusMessage;
import test.eworx.webshop.model.SelectedProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of static methods
 */
public class StaticContent {

    /**
     * check if a string contains only numeric characters
     * @param s
     * @return
     */
    public static boolean isNumeric(String s) {
        if (s != null) {
            return s.matches("[-+]?\\d*\\.?\\d+");
        }
        return false;
    }

    /**
     * handle error response for add/remove product to basket
     * @param deferredResult
     * @param selectedProduct
     * @param httpStatus
     * @param message
     * @return
     */
    public static DeferredResult<ResponseEntity<SelectedProduct>> getAddErrorResult(DeferredResult<ResponseEntity<SelectedProduct>> deferredResult,
                                                                                    SelectedProduct selectedProduct,
                                                                                    HttpStatus httpStatus,
                                                                                    String message) {
        ResponseStatusMessage statusMessage = new ResponseStatusMessage();
        statusMessage.setStatus(httpStatus.name());
        statusMessage.setMessage(message);
        selectedProduct.setStatusMessage(statusMessage);
        deferredResult.setResult(new ResponseEntity<>(selectedProduct, httpStatus));
        return deferredResult;
    }

    /**
     * Retrieve (hardcoded) products
     * @return
     */
    public static List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("product1");
        product1.setDescription("description1");
        product1.setPrice(BigDecimal.valueOf(10));
        product1.setWeightFactor((float) 0.1);
        products.add(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("product2");
        product2.setDescription("description2");
        product2.setPrice(BigDecimal.valueOf(20));
        product2.setWeightFactor((float) 0.2);
        products.add(product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setTitle("product3");
        product3.setDescription("description3");
        product3.setPrice(BigDecimal.valueOf(30));
        product3.setWeightFactor((float) 0.3);
        products.add(product3);

        Product product4 = new Product();
        product4.setId(4);
        product4.setTitle("product4");
        product4.setDescription("description4");
        product4.setPrice(BigDecimal.valueOf(40));
        product4.setWeightFactor((float) 0.4);
        products.add(product4);

        Product product5 = new Product();
        product5.setId(5);
        product5.setTitle("product5");
        product5.setDescription("description5");
        product5.setPrice(BigDecimal.valueOf(50));
        product5.setWeightFactor((float) 0.5);
        products.add(product5);

        return products;
    }
}
