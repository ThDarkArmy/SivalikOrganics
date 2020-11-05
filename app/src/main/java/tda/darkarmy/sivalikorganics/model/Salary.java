package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isPaid",
        "datePaid",
        "_id",
        "amount",
        "ofMonthAndYear",
        "user"
})
@SuppressWarnings("serial")
public class Salary implements Serializable {

    @JsonProperty("isPaid")
    private Boolean isPaid;
    @JsonProperty("datePaid")
    private String datePaid;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("ofMonthAndYear")
    private String ofMonthAndYear;
    @JsonProperty("user")
    private String user;

    public Salary(Boolean isPaid, String datePaid, Integer amount, String ofMonthAndYear, String user) {
        this.isPaid = isPaid;
        this.datePaid = datePaid;
        this.amount = amount;
        this.ofMonthAndYear = ofMonthAndYear;
        this.user = user;
    }

    public Salary(Integer amount, String ofMonthAndYear, String user) {
        this.amount = amount;
        this.ofMonthAndYear = ofMonthAndYear;
        this.user = user;
    }

    @JsonProperty("isPaid")
    public Boolean getIsPaid() {
        return isPaid;
    }

    @JsonProperty("isPaid")
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    @JsonProperty("datePaid")
    public String getDatePaid() {
        return datePaid;
    }

    @JsonProperty("datePaid")
    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("ofMonthAndYear")
    public String getOfMonthAndYear() {
        return ofMonthAndYear;
    }

    @JsonProperty("ofMonthAndYear")
    public void setOfMonthAndYear(String ofMonthAndYear) {
        this.ofMonthAndYear = ofMonthAndYear;
    }

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

}