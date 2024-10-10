package id.my.hendisantika.resilience4jretry.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-resilience4j-retry
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/10/24
 * Time: 10.58
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class MovieApiClient {

    private final RestTemplate restTemplate;
}
