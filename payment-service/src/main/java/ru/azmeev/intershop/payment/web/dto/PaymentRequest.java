package ru.azmeev.intershop.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * PaymentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-16T11:24:06.010167700+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class PaymentRequest {

    private BigDecimal amount;

    public PaymentRequest() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public PaymentRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentRequest amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Order amount to deduct from balance
     * minimum: 0.01
     *
     * @return amount
     */
    @NotNull
    @Valid
    @DecimalMin("0.01")
    @Schema(name = "amount", example = "99.99", description = "Order amount to deduct from balance", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentRequest paymentRequest = (PaymentRequest) o;
        return Objects.equals(this.amount, paymentRequest.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PaymentRequest {\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

