package tda.darkarmy.sivalikorganics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "_id",
        "title",
        "description"
})
@SuppressWarnings("serial")
public class Notice implements Serializable {

    @JsonProperty("date")
    private String date;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    public Notice(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Notice(String date, String _id, String title, String description) {
        this.date = date;
        this._id = _id;
        this.title = title;
        this.description = description;
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

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "date='" + date + '\'' +
                ", _id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
