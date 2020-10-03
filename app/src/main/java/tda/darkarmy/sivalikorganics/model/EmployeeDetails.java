package tda.darkarmy.sivalikorganics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "name",
        "email",
        "profilePic",
        "mobile",
        "dateJoined",
        "salaryStatus"
})
@SuppressWarnings("serial")
public class EmployeeDetails implements Serializable {

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
    @JsonProperty("dateJoined")
    private String dateJoined;
    @JsonProperty("salaryStatus")
    private String salaryStatus;

    public EmployeeDetails(String _id, String name, String email, String profilePic, String mobile, String dateJoined, String salaryStatus) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.mobile = mobile;
        this.dateJoined = dateJoined;
        this.salaryStatus = salaryStatus;
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

    @JsonProperty("dateJoined")
    public String getDateJoined() {
        return dateJoined;
    }

    @JsonProperty("dateJoined")
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    @JsonProperty("salaryStatus")
    public String getSalaryStatus() {
        return salaryStatus;
    }

    @JsonProperty("salaryStatus")
    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dateJoined='" + dateJoined + '\'' +
                ", salaryStatus='" + salaryStatus + '\'' +
                '}';
    }
}
