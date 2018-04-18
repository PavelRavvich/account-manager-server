package ru.pravvich.web.vds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonProperty("activatedDate")
    private long activatedDate;

    @JsonProperty("deactivatedDate")
    private long deactivatedDate;

}
