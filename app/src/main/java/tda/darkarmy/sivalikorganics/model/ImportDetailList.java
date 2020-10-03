package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "importDetails"
})
@SuppressWarnings("serial")
public class ImportDetailList implements Serializable {
    @JsonProperty("importDetails")
    private List<ImportDetail> importDetails = null;


    @JsonProperty("importDetails")
    public List<ImportDetail> getImportDetails() {
        return importDetails;
    }

    @JsonProperty("importDetails")
    public void setImportDetails(List<ImportDetail> importDetails) {
        this.importDetails = importDetails;
    }

    @Override
    public String toString() {
        return "ImportDetailList{" +
                "importDetails=" + importDetails +
                '}';
    }
}
