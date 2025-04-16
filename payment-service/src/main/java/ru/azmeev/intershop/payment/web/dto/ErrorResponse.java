package ru.azmeev.intershop.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * ErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-16T11:24:06.010167700+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ErrorResponse {

    private @Nullable String error;

    private @Nullable String details;

    public ErrorResponse error(String error) {
        this.error = error;
        return this;
    }

    /**
     * Error message
     *
     * @return error
     */

    @Schema(name = "error", example = "Insufficient funds", description = "Error message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ErrorResponse details(String details) {
        this.details = details;
        return this;
    }

    /**
     * Additional error details
     *
     * @return details
     */

    @Schema(name = "details", example = "Current balance is 50.00, but required 99.99", description = "Additional error details", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse errorResponse = (ErrorResponse) o;
        return Objects.equals(this.error, errorResponse.error) &&
                Objects.equals(this.details, errorResponse.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, details);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorResponse {\n");
        sb.append("    error: ").append(toIndentedString(error)).append("\n");
        sb.append("    details: ").append(toIndentedString(details)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

