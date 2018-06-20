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
 *
 * Rest representation Phone obj.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PhoneRest {
    /**
     * Phone id.
     */
    @JsonProperty("id")
    private int id;
    /**
     * Active status of phone.
     */
    @JsonProperty("status")
    private String isActive;
    /**
     * Phone number.
     */
    @JsonProperty("num")
    private String number;
    /**
     * Account login on GSM operator website.
     */
    @JsonProperty("opLogin")
    private String opLogin;
    /**
     * Account password on GSM operator website.
     */
    @JsonProperty("opPassword")
    private String opPassword;
    /**
     * Name of GSM operator.
     */
    @JsonProperty("opName")
    private String opType;
    /**
     * URL link to GSM operator website.
     */
    @JsonProperty("opUrl")
    private String opUrl;
    /**
     * Any note about current phone.
     */
    @JsonProperty("note")
    private String note;
    /**
     * SocialAccounts which registered with this phone number.
     */
    @JsonProperty("socialAccIds")
    private List<Integer> socialAccIds;
    /**
     * Registration date.
     */
    @JsonProperty("regDate")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Timestamp regDate;

}
