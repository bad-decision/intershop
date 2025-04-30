package ru.azmeev.intershop.payment.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.azmeev.intershop.payment.integration.IntegrationTestBase;
import ru.azmeev.intershop.payment.model.AccountEntity;
import ru.azmeev.intershop.payment.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountRepositoryIT extends IntegrationTestBase {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void getAccount_mustReturnCorrectAccount() {
        AccountEntity account = accountRepository.getAccount("user01").block();
        assertEquals("user01", account.getUsername());
        assertEquals(new BigDecimal(1000), account.getBalance());
    }

    @Test
    void getAccount_mustReturnNull() {
        AccountEntity account = accountRepository.getAccount("not-exist-user").block();
        assertNull(account);
    }
}