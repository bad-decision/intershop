package ru.azmeev.intershop.payment.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.model.AccountEntity;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    @Query("SELECT * FROM payment_account i WHERE i.username = :username")
    Mono<AccountEntity> getAccount(String username);
}
