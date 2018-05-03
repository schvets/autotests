package entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class Feature {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isTest")
    @Expose
    private Boolean isTest;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("features")
    @Expose
    private List<Feature> features = null;
}
