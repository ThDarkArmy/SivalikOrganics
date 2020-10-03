package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "calves"
})
@SuppressWarnings("serial")
public class CalfList implements Serializable {

    @JsonProperty("calves")
    private List<Calf> calves = null;

    @JsonProperty("calves")
    public List<Calf> getCalves() {
        return calves;
    }

    @JsonProperty("calves")
    public void setCalves(List<Calf> calves) {
        this.calves = calves;
    }

}