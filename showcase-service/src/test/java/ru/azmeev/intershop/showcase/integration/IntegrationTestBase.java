package ru.azmeev.intershop.showcase.integration;

import com.redis.testcontainers.RedisContainer;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
public abstract class IntegrationTestBase {

    @Autowired
    private ConnectionFactory connectionFactory;
    @Value("classpath:/sql/data.sql")
    protected Resource initDataSql;

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1");
    @ServiceConnection
    static final RedisContainer redisContainer = new RedisContainer("redis:7.4.2-bookworm");

    static {
        postgreSQLContainer.start();
        redisContainer.start();
    }

    protected void executeSqlBlocking(final Resource sql) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sql))
                .block();
    }
}