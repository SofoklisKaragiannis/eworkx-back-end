package test.eworx.webshop.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


/**
 * supports SelectedProduct json contents
 * id - counter - statusMessage
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectedProduct {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("counter")
    private Integer counter;

    @JsonProperty("statusMessage")
    private ResponseStatusMessage statusMessage;

    public Integer getCounter() {
        return counter;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
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
        SelectedProduct that = (SelectedProduct) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(counter, that.counter) &&
                Objects.equals(statusMessage, that.statusMessage) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, counter, statusMessage);
    }
}
