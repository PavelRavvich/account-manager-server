package ru.pravvich.web.phone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("status")
    private String isActive;

    @JsonProperty("num")
    private String number;

    @JsonProperty("operatorName")
    private String operatorType;

    @JsonProperty("operatorUrl")
    private String operatorUrl;

    @JsonProperty("regDate")
    private long regDate;

    @JsonProperty("socialAccountIds")
    private List<Integer> socialAccountIds;

    @JsonProperty("operatorAccLogin")
    private String operatorAccLogin;

    @JsonProperty("operatorAccPass")
    private String operatorAccPassword;

    @JsonProperty("note")
    private String note;

}
