package ru.pravvich.web.vds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VdsListRest {

    @JsonProperty("vdsList")
    private List<VdsRest> vdsList;

    @JsonProperty("total")
    private int total;
}
