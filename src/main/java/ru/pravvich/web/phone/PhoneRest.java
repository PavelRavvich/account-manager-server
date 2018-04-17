package ru.pravvich.web.phone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("number")
    private String number;

    @JsonProperty("operatorType")
    private String operatorType;

    @JsonProperty("operatorUrl")
    private String operatorUrl;

    @JsonProperty("regDate")
    private long regDate;

    @JsonProperty("socialAccountIds")
    private int[] socialAccountIds;

    @JsonProperty("operatorAccLogin")
    private String operatorAccLogin;

    @JsonProperty("operatorAccPassword")
    private String operatorAccPassword;

}
