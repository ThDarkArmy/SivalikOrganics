package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foodItems",
        "medicinalItems",
        "others",
        "date",
        "_id"
})
@SuppressWarnings("serial")
public class Expense implements Serializable {

    @JsonProperty("foodItems")
    private Integer foodItems;
    @JsonProperty("medicinalItems")
    private Integer medicinalItems;
    @JsonProperty("others")
    private Integer others;
    @JsonProperty("date")
    private String date;
    @JsonProperty("_id")
    private String _id;

    public Expense(Integer foodItems, Integer medicinalItems, Integer others) {
        this.foodItems = foodItems;
        this.medicinalItems = medicinalItems;
        this.others = others;
    }

    public Expense(Integer foodItems, Integer medicinalItems, Integer others, String date, String _id) {
        this.foodItems = foodItems;
        this.medicinalItems = medicinalItems;
        this.others = others;
        this.date = date;
        this._id = _id;
    }

    @JsonProperty("foodItems")
    public Integer getFoodItems() {
        return foodItems;
    }

    @JsonProperty("foodItems")
    public void setFoodItems(Integer foodItems) {
        this.foodItems = foodItems;
    }

    @JsonProperty("medicinalItems")
    public Integer getMedicinalItems() {
        return medicinalItems;
    }

    @JsonProperty("medicinalItems")
    public void setMedicinalItems(Integer medicinalItems) {
        this.medicinalItems = medicinalItems;
    }

    @JsonProperty("others")
    public Integer getOthers() {
        return others;
    }

    @JsonProperty("others")
    public void setOthers(Integer others) {
        this.others = others;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "foodItems=" + foodItems +
                ", medicinalItems=" + medicinalItems +
                ", others=" + others +
                ", date='" + date + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
