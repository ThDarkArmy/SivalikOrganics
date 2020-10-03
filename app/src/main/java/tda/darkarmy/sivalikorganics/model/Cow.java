package tda.darkarmy.sivalikorganics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isProductive",
        "isPregnant",
        "pregnantFrom",
        "isHealthy",
        "_id",
        "name",
        "age",
        "amountOfMilk"
})
@SuppressWarnings("serial")
public class Cow implements Serializable {

    @JsonProperty("isProductive")
    private Boolean isProductive;
    @JsonProperty("isPregnant")
    private Boolean isPregnant;
    @JsonProperty("pregnantFrom")
    private String pregnantFrom;
    @JsonProperty("isHealthy")
    private Boolean isHealthy;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("amountOfMilk")
    private Integer amountOfMilk;

    public Cow(Boolean isProductive, Boolean isPregnant, String pregnantFrom, Boolean isHealthy, String id, String name, Integer age, Integer amountOfMilk) {
        this.isProductive = isProductive;
        this.isPregnant = isPregnant;
        this.pregnantFrom = pregnantFrom;
        this.isHealthy = isHealthy;
        this._id = id;
        this.name = name;
        this.age = age;
        this.amountOfMilk = amountOfMilk;
    }

    public Cow(Boolean isProductive, Boolean isPregnant, String pregnantFrom, Boolean isHealthy, String name, Integer age, Integer amountOfMilk) {
        this.isProductive = isProductive;
        this.isPregnant = isPregnant;
        this.pregnantFrom = pregnantFrom;
        this.isHealthy = isHealthy;
        this.name = name;
        this.age = age;
        this.amountOfMilk = amountOfMilk;
    }

    @JsonProperty("isProductive")
    public Boolean getIsProductive() {
        return isProductive;
    }

    @JsonProperty("isProductive")
    public void setIsProductive(Boolean isProductive) {
        this.isProductive = isProductive;
    }

    @JsonProperty("isPregnant")
    public Boolean getIsPregnant() {
        return isPregnant;
    }

    @JsonProperty("isPregnant")
    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    @JsonProperty("pregnantFrom")
    public String getPregnantFrom() {
        return pregnantFrom;
    }

    @JsonProperty("pregnantFrom")
    public void setPregnantFrom(String pregnantFrom) {
        this.pregnantFrom = pregnantFrom;
    }

    @JsonProperty("isHealthy")
    public Boolean getIsHealthy() {
        return isHealthy;
    }

    @JsonProperty("isHealthy")
    public void setIsHealthy(Boolean isHealthy) {
        this.isHealthy = isHealthy;
    }

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("amountOfMilk")
    public Integer getAmountOfMilk() {
        return amountOfMilk;
    }

    @JsonProperty("amountOfMilk")
    public void setAmountOfMilk(Integer amountOfMilk) {
        this.amountOfMilk = amountOfMilk;
    }

    @Override
    public String toString() {
        return "Cow{" +
                "isProductive=" + isProductive +
                ", isPregnant=" + isPregnant +
                ", pregnantFrom='" + pregnantFrom + '\'' +
                ", isHealthy=" + isHealthy +
                ", id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", amountOfMilk=" + amountOfMilk +
                '}';
    }
}
