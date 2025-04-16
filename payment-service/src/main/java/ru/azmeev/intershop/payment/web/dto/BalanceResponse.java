package ru.azmeev.intershop.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BalanceResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-16T11:24:06.010167700+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class BalanceResponse {

    private @Nullable BigDecimal balance;

    public BalanceResponse balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Current account balance
     *
     * @return balance
     */
    @Valid
    @Schema(name = "balance", example = "1500.5", description = "Current account balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceResponse balanceResponse = (BalanceResponse) o;
        return Objects.equals(this.balance, balanceResponse.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BalanceResponse {\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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

