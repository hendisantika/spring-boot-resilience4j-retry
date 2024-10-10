package id.my.hendisantika.resilience4jretry.config;

import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

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
}
