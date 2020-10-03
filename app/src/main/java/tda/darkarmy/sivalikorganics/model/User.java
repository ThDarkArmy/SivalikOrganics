package tda.darkarmy.sivalikorganics.model;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "role",
        "dateJoined",
        "isConfirm",
        "_id",
        "name",
        "email",
        "mobile",
        "profilePic"
})
@SuppressWarnings("serial")
public class User implements Serializable {

    @JsonProperty("role")
    private String role;
    @JsonProperty("dateJoined")
    private String dateJoined;
    @JsonProperty("isConfirm")
    private Boolean isConfirm;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("password")
    private String password;
    @JsonProperty("profilePic")
    private String profilePic;

    public User(String role, String dateJoined, String id, String name, String email, String mobile, String profilePic) {
        this.role = role;
        this.dateJoined = dateJoined;
        this._id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.profilePic = profilePic;
    }

    public User(String role, String name, String email, String mobile, String password, String profilePic) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.profilePic = profilePic;
    }

    public User(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("dateJoined")
    public String getDateJoined() {
        return dateJoined;
    }

    @JsonProperty("dateJoined")
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    @JsonProperty("isConfirm")
    public Boolean getIsConfirm() {
        return isConfirm;
    }

    @JsonProperty("isConfirm")
    public void setIsConfirm(Boolean isConfirm) {
        this.isConfirm = isConfirm;
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

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("profilePic")
    public String getProfilePic() {
        return profilePic;
    }

    @JsonProperty("profilePic")
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", dateJoined='" + dateJoined + '\'' +
                ", isConfirm=" + isConfirm +
                ", _id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}