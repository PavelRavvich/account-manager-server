package ru.pravvich.web.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAccountRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("vdsId")
    private int vdsId;

    @JsonProperty("phoneId")
    private int phoneId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("socialType")
    private String socialType;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("note")
    private String note;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("regDate")
    private long regDate;

}
