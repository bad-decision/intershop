/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.12.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.azmeev.intershop.payment.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.web.dto.*;
import ru.azmeev.intershop.payment.web.dto.ErrorResponse;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-04-16T10:22:52.967121300+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
@Validated
@Tag(name = "PaymentController", description = "All payment operations")
public interface PaymentApi {

    /**
     * GET /payment/balance : Get current account balance
     * Returns the current balance of the user&#39;s account
     *
     * @return Successful operation (status code 200)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "getBalance",
        summary = "Get current account balance",
        description = "Returns the current balance of the user's account",
        tags = { "PaymentController" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/payment/balance",
        produces = { "application/json" }
    )
    default Mono<ResponseEntity<BalanceResponse>> getBalance(
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"balance\" : 1500.5 }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"code\" : \"INSUFFICIENT_FUNDS\", \"details\" : \"Current balance is 50.00, but required 99.99\", \"error\" : \"Insufficient funds\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * POST /payment/process : Process payment
     * Deducts the order amount from the account balance
     *
     * @param paymentRequest Payment request object (required)
     * @return Payment processed successfully (status code 200)
     *         or Insufficient funds or invalid request (status code 400)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "processPayment",
        summary = "Process payment",
        description = "Deducts the order amount from the account balance",
        tags = { "PaymentController" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/payment/process",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default Mono<ResponseEntity<PaymentResponse>> processPayment(
        @Parameter(name = "PaymentRequest", description = "Payment request object", required = true) @Valid @RequestBody Mono<PaymentRequest> paymentRequest,
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"success\" : true, \"remainingBalance\" : 1400.51, \"message\" : \"Payment processed successfully\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"code\" : \"INSUFFICIENT_FUNDS\", \"details\" : \"Current balance is 50.00, but required 99.99\", \"error\" : \"Insufficient funds\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"code\" : \"INSUFFICIENT_FUNDS\", \"details\" : \"Current balance is 50.00, but required 99.99\", \"error\" : \"Insufficient funds\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(paymentRequest).then(Mono.empty());

    }

}
