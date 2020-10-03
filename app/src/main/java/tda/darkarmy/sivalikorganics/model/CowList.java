package tda.darkarmy.sivalikorganics.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CowList implements Serializable {
    @JsonProperty("cows")
    private List<Cow> cows;

    public CowList(List<Cow> cows) {
        this.cows = cows;
    }

    @JsonProperty("cows")
    public List<Cow> getCows() {
        return cows;
    }

    @JsonProperty("cows")
    public void setCows(List<Cow> cows) {
        this.cows = cows;
    }


    @Override
    public String toString() {
        return "CowList{" +
                "cows=" + cows +
                '}';
    }
}
