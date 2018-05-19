package ru.pravvich.web.social;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Pavel Ravvich.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAccountRest {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("vdsId")
    private Integer vdsId;

    @JsonProperty("phoneId")
    private Integer phoneId;

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

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    @JsonProperty("regDate")
    private Timestamp regDate;

}
