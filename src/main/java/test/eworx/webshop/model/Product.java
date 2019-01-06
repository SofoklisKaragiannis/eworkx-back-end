package test.eworx.webshop.model;

import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * supports Product json contents
 * id - title - description - price - weightFactor
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("weightFactor")
    private float weightFactor;

    public Integer getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public float getWeightFactor() {
        return weightFactor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setWeightFactor(float weightFactor) {
        this.weightFactor = weightFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product that = (Product) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(weightFactor, that.weightFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title , description, price, weightFactor);
    }
}
