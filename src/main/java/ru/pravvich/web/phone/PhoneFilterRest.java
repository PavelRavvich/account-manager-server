package ru.pravvich.web.phone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhoneFilterRest {

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("pageNumber")
    private Integer pageNumber;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("note")
    private String note;

    @JsonProperty("num")
    private String number;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("opName")
    private String opName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("from")
    private Long from;

    @JsonProperty("to")
    private Long to;
}
