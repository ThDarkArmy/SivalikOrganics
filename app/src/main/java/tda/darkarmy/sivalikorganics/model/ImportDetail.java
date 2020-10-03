package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amountPaid",
        "date",
        "_id",
        "amountOfMilkBought",
        "user",
        "userId"
})
@SuppressWarnings("serial")
public class ImportDetail implements Serializable {

    @JsonProperty("amountPaid")
    private Integer amountPaid;
    @JsonProperty("date")
    private String date;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("amountOfMilkBought")
    private Integer amountOfMilkBought;
    @JsonProperty("user")
    private User user;
    @JsonProperty("user")
    private String userId;


    public ImportDetail(Integer amountPaid, String date, String id, Integer amountOfMilkBought, User user) {
        this.amountPaid = amountPaid;
        this.date = date;
        this._id = id;
        this.amountOfMilkBought = amountOfMilkBought;
        this.user = user;
    }

    public ImportDetail(Integer amountPaid, String date, Integer amountOfMilkBought, String userId) {        this.amountPaid = amountPaid;
        this.date = date;
        this.amountOfMilkBought = amountOfMilkBought;
        this.userId = userId;
    }

    @JsonProperty("amountPaid")
    public Integer getAmountPaid() {
        return amountPaid;
    }

    @JsonProperty("amountPaid")
    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
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

    @JsonProperty("amountOfMilkBought")
    public Integer getAmountOfMilkBought() {
        return amountOfMilkBought;
    }

    @JsonProperty("amountOfMilkBought")
    public void setAmountOfMilkBought(Integer amountOfMilkBought) {
        this.amountOfMilkBought = amountOfMilkBought;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ImportDetail{" +
                "amountPaid=" + amountPaid +
                ", date='" + date + '\'' +
                ", id='" + _id + '\'' +
                ", amountOfMilkBought=" + amountOfMilkBought +
                ", user=" + user +
                '}';
    }
}
