package tda.darkarmy.sivalikorganics.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "exportDetails"
})
public class ExportDetailsList {
    @JsonProperty("exportDetails")
    private List<ExportDetail> exportDetails = null;


    @JsonProperty("exportDetails")
    public List<ExportDetail> getExportDetails() {
        return exportDetails;
    }

    @JsonProperty("exportDetails")
    public void setExportDetails(List<ExportDetail> exportDetails) {
        this.exportDetails = exportDetails;
    }

    @Override
    public String toString() {
        return "ExportDetailsList{" +
                "exportDetails=" + exportDetails +
                '}';
    }
}
