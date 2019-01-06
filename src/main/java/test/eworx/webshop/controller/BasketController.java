package test.eworx.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import test.eworx.webshop.framework.V1;
import test.eworx.webshop.model.Basket;
import test.eworx.webshop.model.ResponseStatusMessage;
import test.eworx.webshop.service.BasketAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * BasketController takes care calls of type
 * Method: GET Url: https://{host}/rest/v1/getBasket
 *
 *  Response body of type Basket in JSON format
 *
 */
@CrossOrigin
@RestController
@RequestMapping(V1.URI_GET_BASKET_ABSOLUTE)
public class BasketController {

    @Autowired
    BasketAdapter basketAdapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResponseEntity<Basket>> getBasket(@RequestParam Map<String, String> parameters,
                                                            HttpServletRequest request) {

        DeferredResult<ResponseEntity<Basket>> deferredResult = new DeferredResult<>();
        // receive parameter values
        Map<String, String> requestParameters = new HashMap<>(parameters);

        Basket basket = new Basket();
        ResponseStatusMessage statusMessage = new ResponseStatusMessage();
        // if invalid parameters --> bad request
        if (!requestParameters.isEmpty())  {
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.name());
            statusMessage.setMessage("Wrong parameters!");
            basket.setStatusMessage(statusMessage);
            deferredResult.setResult(new ResponseEntity<>(basket, HttpStatus.BAD_REQUEST));
            return deferredResult;
        }

        //can not request from null basket
        if (!basketAdapter.getInitialised()) {
            statusMessage.setStatus(HttpStatus.EXPECTATION_FAILED.name());
            statusMessage.setMessage("Basket not created yet!");
            basket.setStatusMessage(statusMessage);
            deferredResult.setResult(new ResponseEntity<>(basket, HttpStatus.EXPECTATION_FAILED));
        }

        // calculate basket data
        basket = basketAdapter.calculateBasket();
        deferredResult.setResult(new ResponseEntity<>(basket, HttpStatus.OK));

        return deferredResult;
    }
}
