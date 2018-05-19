package ru.pravvich.web.phone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Pavel Ravvich.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PhoneRest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("status")
    private String isActive;

    @JsonProperty("num")
    private String number;

    @JsonProperty("opLogin")
    private String opLogin;

    @JsonProperty("opPassword")
    private String opPassword;

    @JsonProperty("opName")
    private String opType;

    @JsonProperty("opUrl")
    private String opUrl;

    @JsonProperty("note")
    private String note;

    @JsonProperty("socialAccIds")
    private List<Integer> socialAccIds;

    @JsonProperty("regDate")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Timestamp regDate;

}
