package tda.darkarmy.sivalikorganics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dateJoined",
        "totalAmountPaid",
        "_id",
        "name",
        "email",
        "profilePic",
        "mobile",
        "totalAmountOfMilkBought"
})
@SuppressWarnings("serial")
public class ConsumerDetails implements Serializable {

    @JsonProperty("dateJoined")
    private String dateJoined;
    @JsonProperty("totalAmountPaid")
    private Integer totalAmountPaid;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profilePic")
    private String profilePic;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("totalAmountOfMilkBought")
    private Integer totalAmountOfMilkBought;


    @JsonProperty("dateJoined")
    public String getDateJoined() {
        return dateJoined;
    }

    @JsonProperty("dateJoined")
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    @JsonProperty("totalAmountPaid")
    public Integer getTotalAmountPaid() {
        return totalAmountPaid;
    }

    @JsonProperty("totalAmountPaid")
    public void setTotalAmountPaid(Integer totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("profilePic")
    public String getProfilePic() {
        return profilePic;
    }

    @JsonProperty("profilePic")
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("totalAmountOfMilkBought")
    public Integer getTotalAmountOfMilkBought() {
        return totalAmountOfMilkBought;
    }

    @JsonProperty("totalAmountOfMilkBought")
    public void setTotalAmountOfMilkBought(Integer totalAmountOfMilkBought) {
        this.totalAmountOfMilkBought = totalAmountOfMilkBought;
    }

    @Override
    public String toString() {
        return "ConsumerDetails{" +
                "dateJoined='" + dateJoined + '\'' +
                ", totalAmountPaid=" + totalAmountPaid +
                ", _id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", mobile='" + mobile + '\'' +
                ", totalAmountOfMilkBought=" + totalAmountOfMilkBought +
                '}';
    }
}
