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
        "amountOfMilkSold",
        "user",
        "userId"
})
@SuppressWarnings("serial")
public class ExportDetail implements Serializable{

    @JsonProperty("amountPaid")
    private Integer amountPaid;
    @JsonProperty("date")
    private String date;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("amountOfMilkSold")
    private Integer amountOfMilkSold;
    @JsonProperty("user")
    private User user;
    @JsonProperty("userId")
    private String userId;

    public ExportDetail(Integer amountPaid, String date, String _id, Integer amountOfMilkSold, User user) {
        this.amountPaid = amountPaid;
        this.date = date;
        this._id = _id;
        this.amountOfMilkSold = amountOfMilkSold;
        this.user = user;
    }

    public ExportDetail(Integer amountPaid, String date, Integer amountOfMilkSold, String userId) {
        this.amountPaid = amountPaid;
        this.date = date;
        this.amountOfMilkSold = amountOfMilkSold;
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

    @JsonProperty("amountOfMilkSold")
    public Integer getAmountOfMilkSold() {
        return amountOfMilkSold;
    }

    @JsonProperty("amountOfMilkSold")
    public void setAmountOfMilkSold(Integer amountOfMilkSold) {
        this.amountOfMilkSold = amountOfMilkSold;
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
        return "ExportDetail{" +
                "amountPaid=" + amountPaid +
                ", date='" + date + '\'' +
                ", id='" + _id + '\'' +
                ", amountOfMilkSold=" + amountOfMilkSold +
                ", user=" + user +
                '}';
    }
}