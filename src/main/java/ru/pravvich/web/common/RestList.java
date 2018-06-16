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
 *
 * Rest representation for collections with pagination support.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestList<T> {
    /**
     * Page number for pagination.
     */
    @NonNull
    @JsonProperty("pageNumber")
    private Integer pageNumber;
    /**
     * Page size for pagination
     */
    @NonNull
    @JsonProperty("pageSize")
    private Integer pageSize;
    /**
     * Amount available pages.
     */
    @NonNull
    @JsonProperty("total")
    private Integer total;
    /**
     * Data collection for current page.
     */
    @NonNull
    @JsonProperty("data")
    private Collection<T> data;
}
