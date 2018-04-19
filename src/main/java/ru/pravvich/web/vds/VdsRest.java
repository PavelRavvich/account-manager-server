package ru.pravvich.web.vds;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VdsRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("note")
    private String note;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    @JsonProperty("activatedDate")
    private Timestamp activatedDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    @JsonProperty("deactivatedDate")
    private Timestamp deactivatedDate;

}
