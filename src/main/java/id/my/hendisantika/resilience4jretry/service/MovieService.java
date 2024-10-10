package id.my.hendisantika.resilience4jretry.service;

import id.my.hendisantika.resilience4jretry.entity.Movie;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-resilience4j-retry
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/10/24
 * Time: 10.54
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final RetryRegistry retryRegistry;

    private final MovieApiClient movieApiClient;

    @Retry(name = "simpleRetry")
    public Movie getMovieDetails(String movieId) {
        return fetchMovieDetails(movieId);
    }
}
