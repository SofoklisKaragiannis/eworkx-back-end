package test.eworx.webshop.service;

import org.springframework.stereotype.Service;
import test.eworx.webshop.framework.StaticContent;
import test.eworx.webshop.model.Basket;
import test.eworx.webshop.model.Product;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Take care of Basket requests in order to serve API calls
 */
@Service
public class BasketAdapter {

    private final Integer DISCOUNT_LIMIT = 100;
    private final Integer MAX_DELIVERY_AMOUNT = 5;
    private Map<Integer, Integer> selectedProductMap = new HashMap<>();
    private Map<Integer, Product> productMap = new HashMap<>();
    private Boolean isInitialised = false;

    /**
     * sets basket initial data
     * which will be used in requested actions
     *
     */
    public void initialiseBasket(){
        List<Product> products = StaticContent.getAvailableProducts();
        productMap = products.stream().collect(Collectors.toMap(Product::getId, c -> c));
        selectedProductMap = products.stream().collect(Collectors.toMap(Product::getId, c -> 0));
        isInitialised = true;
    }

    /** increases product to basket by 1
     * if id is valid
     *
     * @param id
     * @return true for successful increment
     */
    public Boolean addToBasket(Integer id){
        Integer selectedValue = getSelectedProduct(id);
        if (selectedValue != null) {
            return selectedProductMap.replace(id, selectedValue, selectedValue + 1);
        }
        return false;
    }

    /** decreases product to basket by 1
     * if id is valid
     *
     * @param id
     * @return true for successful decrement
     */
    public Boolean removeFromBasket(Integer id) {
        Integer selectedValue = getSelectedProduct(id);
        if (selectedValue != null && selectedValue > 0) {
            return selectedProductMap.replace(id, selectedValue, selectedValue - 1);
        }
        return false;
    }

    public Boolean getInitialised() {
        return isInitialised;
    }

    /**
     * returns selected product counter if valid product id
     * else null
     * @param id
     * @return
     */
    public Integer getSelectedProduct(Integer id) {
        if (selectedProductMap.containsKey(id)) {
            return selectedProductMap.get(id);
        }
        return null;
    }

    /**
     * populates basket data
     * in order to serve getBasket request
     * @return
     */
    public Basket calculateBasket() {
        Integer discount = 0;

        //Calculates total product cost by summing the products of prices and counters
        BigDecimal productCost =
                new BigDecimal(selectedProductMap.keySet().stream().mapToDouble(id -> productMap.get(id).getPrice().doubleValue() * selectedProductMap.get(id) ).sum(), MathContext.DECIMAL64);

        //Calculates delivery cost by summing the products of weight factors of MAX_DELIVERY_AMOUNT and counters
        BigDecimal deliveryCost =
                new BigDecimal(selectedProductMap.keySet().stream().mapToDouble(id -> (productMap.get(id).getWeightFactor() * MAX_DELIVERY_AMOUNT) * selectedProductMap.get(id) ).sum(), MathContext.DECIMAL64);

        //discount calculation
        if (productCost.compareTo(BigDecimal.valueOf(DISCOUNT_LIMIT)) > 0 ){
            discount=10;
            //apply discount to total product cost
            productCost = productCost.subtract(productCost.multiply(new BigDecimal(discount)).divide(new BigDecimal(DISCOUNT_LIMIT)));
        }

        Basket basket = new Basket();
        basket.getSelectedProductMap().putAll(selectedProductMap);
        basket.setDiscount(discount);
        basket.setDeliveryCost(deliveryCost);
        basket.setTotalPrice(productCost.add(deliveryCost));

        return basket;
    }

}
