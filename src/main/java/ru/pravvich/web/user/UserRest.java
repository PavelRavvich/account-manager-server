package ru.pravvich.web.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRest {

    @JsonProperty("id")
    private int id;

    @Size(min = 4, max = 14)
    @JsonProperty("username")
    private String username;

    @Size(min = 4, max = 14)
    @JsonProperty("password")
    private String password;

    @JsonProperty("roles")
    private List<String> roles;

    @JsonProperty("accountNonLocked")
    private Boolean accountNonLocked;

}
