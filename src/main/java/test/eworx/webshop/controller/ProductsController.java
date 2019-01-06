package test.eworx.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import test.eworx.webshop.framework.StaticContent;
import test.eworx.webshop.framework.V1;
import test.eworx.webshop.model.AvailableProducts;
import test.eworx.webshop.model.ResponseStatusMessage;
import test.eworx.webshop.service.BasketAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ProductsController takes care calls of type
 * Method: GET Url: https://{host}/rest/v1/getProducts
 *
 *  Response body of type AvailableProducts in JSON format
 *
 */
@CrossOrigin
@RestController
@RequestMapping(V1.URI_GET_PRODUCTS_ABSOLUTE)
public class ProductsController {

    @Autowired
    BasketAdapter basketAdapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResponseEntity<AvailableProducts>> getProducts(@RequestParam Map<String, String> parameters,
                                                                            HttpServletRequest request) {

        DeferredResult<ResponseEntity<AvailableProducts>> deferredResult = new DeferredResult<>();
        // receive parameter values
        Map<String, String> requestParameters = new HashMap<>(parameters);

        AvailableProducts availableProducts = new AvailableProducts();
        ResponseStatusMessage statusMessage = new ResponseStatusMessage();
        // if invalid parameters --> bad request
        if (!requestParameters.isEmpty())  {
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.name());
            statusMessage.setMessage("Wrong parameters!");
            availableProducts.setStatusMessage(statusMessage);
            deferredResult.setResult(new ResponseEntity<>(availableProducts, HttpStatus.BAD_REQUEST));
            return deferredResult;
        }

        // get available products
        availableProducts.getResults().addAll(StaticContent.getAvailableProducts());
        // initialise basket if null
        if (!basketAdapter.getInitialised()) {
            basketAdapter.initialiseBasket();
        }
        deferredResult.setResult(new ResponseEntity<>(availableProducts, HttpStatus.OK));

        return deferredResult;
    }
}
