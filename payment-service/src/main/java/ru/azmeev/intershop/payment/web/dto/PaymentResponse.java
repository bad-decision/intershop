package ru.azmeev.intershop.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * PaymentResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-16T11:24:06.010167700+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class PaymentResponse {

    private @Nullable Boolean success;

    private @Nullable BigDecimal remainingBalance;

    private @Nullable String message;

    public PaymentResponse success(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * Payment status
     *
     * @return success
     */

    @Schema(name = "success", example = "true", description = "Payment status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PaymentResponse remainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
        return this;
    }

    /**
     * Remaining balance after payment
     *
     * @return remainingBalance
     */
    @Valid
    @Schema(name = "remainingBalance", example = "1400.51", description = "Remaining balance after payment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("remainingBalance")
    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public PaymentResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Additional information
     *
     * @return message
     */

    @Schema(name = "message", example = "Payment processed successfully", description = "Additional information", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentResponse paymentResponse = (PaymentResponse) o;
        return Objects.equals(this.success, paymentResponse.success) &&
                Objects.equals(this.remainingBalance, paymentResponse.remainingBalance) &&
                Objects.equals(this.message, paymentResponse.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, remainingBalance, message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PaymentResponse {\n");
        sb.append("    success: ").append(toIndentedString(success)).append("\n");
        sb.append("    remainingBalance: ").append(toIndentedString(remainingBalance)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

