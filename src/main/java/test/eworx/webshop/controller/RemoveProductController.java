package test.eworx.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import test.eworx.webshop.framework.StaticContent;
import test.eworx.webshop.framework.V1;
import test.eworx.webshop.model.SelectedProduct;
import test.eworx.webshop.service.BasketAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * RemoveProductController takes care calls of type
 * Method: GET Url: https://{host}/rest/v1/removeProduct?id={productId}
 *
 *  Response body of type SelectedProduct in JSON format
 *
 */
@CrossOrigin
@RestController
@RequestMapping(V1.URI_REMOVE_PRODUCT_ABSOLUTE)
public class RemoveProductController {

    @Autowired
    BasketAdapter basketAdapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResponseEntity<SelectedProduct>> removeProduct(@RequestParam Map<String, String> parameters,
                                                                            HttpServletRequest request) {

        DeferredResult<ResponseEntity<SelectedProduct>> deferredResult = new DeferredResult<>();
        // receive parameter values
        Map<String, String> requestParameters = new HashMap<>(parameters);
        String id = requestParameters.get("id");

        SelectedProduct selectedProduct = new SelectedProduct();
        // if invalid parameters --> bad request
        if (requestParameters.isEmpty() || !StaticContent.isNumeric(id))  {
            return StaticContent.getAddErrorResult(deferredResult, selectedProduct, HttpStatus.BAD_REQUEST, "Wrong parameters!");
        }

        //can not request from null basket
        if (!basketAdapter.getInitialised()) {
            return StaticContent.getAddErrorResult(deferredResult, selectedProduct, HttpStatus.EXPECTATION_FAILED, "Basket not created yet!");
        }


        // remove from basket process
        if (!basketAdapter.removeFromBasket(Integer.valueOf(id))) {
            return StaticContent.getAddErrorResult(deferredResult, selectedProduct, HttpStatus.BAD_REQUEST, "Can not find product!");
        }

        selectedProduct.setId(Integer.valueOf(id));
        selectedProduct.setCounter(basketAdapter.getSelectedProduct(Integer.valueOf(id)));
        deferredResult.setResult(new ResponseEntity<>(selectedProduct, HttpStatus.OK));

        return deferredResult;
    }



}
