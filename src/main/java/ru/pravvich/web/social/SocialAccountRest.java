package ru.pravvich.web.social;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccountRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("vdsId")
    private int vdsId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("socialType")
    private String socialType;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("regDate")
    private long regDate;

}
