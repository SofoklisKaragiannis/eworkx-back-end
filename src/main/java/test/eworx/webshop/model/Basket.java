package test.eworx.webshop.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.*;


/**
 * supports Basket json contents
 * selectedProductMap - discount - deliveryCost - totalPrice - statusMessage
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Basket {

    @JsonProperty("selectedProductMap")
    private Map<Integer, Integer> selectedProductMap;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("deliveryCost")
    private BigDecimal deliveryCost;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("statusMessage")
    private ResponseStatusMessage statusMessage;

    public Map<Integer, Integer> getSelectedProductMap() {
        if (selectedProductMap == null) {
            selectedProductMap = new HashMap<>();
        }
        return selectedProductMap;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatusMessage(ResponseStatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Basket that = (Basket) o;
        return Objects.equals(selectedProductMap, that.selectedProductMap) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(deliveryCost, that.deliveryCost) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(statusMessage, that.statusMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedProductMap, discount, deliveryCost, totalPrice, statusMessage);
    }
}
