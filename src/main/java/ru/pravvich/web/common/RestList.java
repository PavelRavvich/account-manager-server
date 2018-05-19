package ru.pravvich.web.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collection;

/**
 * @author Pavel Ravvich.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestList<T> {

    @NonNull
    @JsonProperty("pageNumber")
    private Integer pageNumber;

    @NonNull
    @JsonProperty("pageSize")
    private Integer pageSize;

    @NonNull
    @JsonProperty("total")
    private Integer total;

    @NonNull
    @JsonProperty("data")
    private Collection<T> data;
}
