package id.my.hendisantika.resilience4jretry.config;

import id.my.hendisantika.resilience4jretry.exception.MovieNotFoundException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Duration;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-resilience4j-retry
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/10/24
 * Time: 10.59
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {

    private final RetryRegistry retryRegistry;

    @Bean
    public Retry retryWithCustomConfig() {
        RetryConfig customConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryExceptions(HttpClientErrorException.class, HttpServerErrorException.class)
                .ignoreExceptions(MovieNotFoundException.class)
                .build();

        return retryRegistry.retry("customRetryConfig", customConfig);
    }
}
