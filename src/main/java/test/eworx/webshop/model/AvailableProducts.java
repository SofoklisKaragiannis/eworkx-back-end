package test.eworx.webshop.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * supports AvailableProducts json contents
 * results - statusMessage
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailableProducts {

    @JsonProperty("results")
    private List<Product> results;

    @JsonProperty("statusMessage")
    private ResponseStatusMessage statusMessage;

    public List<Product> getResults() {
        if (results == null) {
            results = new ArrayList<>();
        }
        return results;
    }

    public ResponseStatusMessage getStatusMessage() {
        return statusMessage;
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
        AvailableProducts that = (AvailableProducts) o;
        return Objects.equals(results, that.results) &&
                Objects.equals(statusMessage, that.statusMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, statusMessage);
    }
}
