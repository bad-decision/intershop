package ru.azmeev.intershop.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BalanceResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-30T09:01:32.998419300+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class BalanceResponse {

    private BigDecimal balance;

    private String username;

    public BalanceResponse() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public BalanceResponse(BigDecimal balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    public BalanceResponse balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Current account balance
     *
     * @return balance
     */
    @NotNull
    @Valid
    @Schema(name = "balance", example = "1500.5", description = "Current account balance", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BalanceResponse username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Username
     *
     * @return username
     */
    @NotNull
    @Schema(name = "username", example = "user01", description = "Username", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return Objects.equals(this.balance, balanceResponse.balance) &&
                Objects.equals(this.username, balanceResponse.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BalanceResponse {\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
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

