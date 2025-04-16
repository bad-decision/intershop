/*
 * Payment Service API
 * Reactive RESTful API for payment operations
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ru.azmeev.intershop.showcase.web.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * ErrorResponse
 */
@JsonPropertyOrder({
        ErrorResponse.JSON_PROPERTY_ERROR,
        ErrorResponse.JSON_PROPERTY_DETAILS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-04-16T11:53:58.024048100+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ErrorResponse {
    public static final String JSON_PROPERTY_ERROR = "error";
    @jakarta.annotation.Nullable
    private String error;

    public static final String JSON_PROPERTY_DETAILS = "details";
    @jakarta.annotation.Nullable
    private String details;

    public ErrorResponse() {
    }

    public ErrorResponse error(@jakarta.annotation.Nullable String error) {
        this.error = error;
        return this;
    }

    /**
     * Error message
     *
     * @return error
     */
    @jakarta.annotation.Nullable

    @JsonProperty(JSON_PROPERTY_ERROR)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getError() {
        return error;
    }


    @JsonProperty(JSON_PROPERTY_ERROR)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setError(@jakarta.annotation.Nullable String error) {
        this.error = error;
    }

    public ErrorResponse details(@jakarta.annotation.Nullable String details) {
        this.details = details;
        return this;
    }

    /**
     * Additional error details
     *
     * @return details
     */
    @jakarta.annotation.Nullable

    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getDetails() {
        return details;
    }


    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setDetails(@jakarta.annotation.Nullable String details) {
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

