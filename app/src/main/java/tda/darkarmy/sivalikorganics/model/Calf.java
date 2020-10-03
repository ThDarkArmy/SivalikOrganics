package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isHealthy",
        "_id",
        "name",
        "dob",
        "gender",
        "cow"
})
@SuppressWarnings("serial")
public class Calf implements Serializable {

    @JsonProperty("isHealthy")
    private Boolean isHealthy;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("cow")
    private Cow cow;
    @JsonProperty("cowId")
    private String cowId;

    public Calf(Boolean isHealthy, String id, String name, String dob, String gender, Cow cow) {
        this.isHealthy = isHealthy;
        this._id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.cow = cow;
    }

    public Calf(Boolean isHealthy, String name, String dob, String gender, String cowId) {
        this.isHealthy = isHealthy;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.cowId = cowId;
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

    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("cow")
    public Cow getCow() {
        return cow;
    }

    @JsonProperty("cow")
    public void setCow(Cow cow) {
        this.cow = cow;
    }

    @Override
    public String toString() {
        return "Calf{" +
                "isHealthy=" + isHealthy +
                ", _id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", cow=" + cow +
                ", cowId='" + cowId + '\'' +
                '}';
    }
}