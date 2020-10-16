package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "notices"
})
@SuppressWarnings("serial")
public class NoticeList implements Serializable {

    @JsonProperty("notices")
    private List<Notice> notices = null;


    @JsonProperty("notices")
    public List<Notice> getNotices() {
        return notices;
    }

    @JsonProperty("notices")
    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

}
