package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.exception.PaymentException;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.payment.BalanceResponse;
import ru.azmeev.intershop.showcase.web.dto.payment.ErrorResponse;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentRequest;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Qualifier("paymentService")
    private final WebClient webClient;
    @Value("${shop.clientRegistrationId}")
    private String clientRegistrationId;

    private final ReactiveOAuth2AuthorizedClientManager manager;

    @Override
    public Mono<BalanceResponse> getBalance(UserEntity user) {
        return manager.authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId(clientRegistrationId)
                        .principal(user.getUsername())
                        .build())
                .map(OAuth2AuthorizedClient::getAccessToken)
                .map(AbstractOAuth2Token::getTokenValue)
                .flatMap(accessToken -> webClient.get()
                        .uri("/api/payment/balance")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(BalanceResponse.class)
                );
    }

    @Override
    public Mono<PaymentResponse> process(double cartTotal, UserEntity user) {
        PaymentRequest paymentRequest = new PaymentRequest()
                .amount(BigDecimal.valueOf(cartTotal))
                .userId(user.getId());

        return manager.authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId(clientRegistrationId)
                        .principal(user.getUsername())
                        .build())
                .map(OAuth2AuthorizedClient::getAccessToken)
                .map(AbstractOAuth2Token::getTokenValue)
                .flatMap(accessToken -> webClient.post()
                        .uri("/api/payment/process") // Сохранение пользователя
                        .bodyValue(paymentRequest)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::isError,
                                response -> response.bodyToMono(ErrorResponse.class)
                                        .flatMap(errorBody -> Mono.error(new PaymentException(errorBody)))
                        )
                        .bodyToMono(PaymentResponse.class)
                );
    }
}