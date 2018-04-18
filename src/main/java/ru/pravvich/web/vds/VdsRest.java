package ru.pravvich.web.vds;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
