package id.my.hendisantika.resilience4jretry.service;

import id.my.hendisantika.resilience4jretry.entity.Movie;
import id.my.hendisantika.resilience4jretry.exception.MovieNotFoundException;
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

    @Retry(name = "retryWithEventDetails")
    public Movie getMovieDetailsWithRetryEventDetails(String movieId) {
        return fetchMovieDetails(movieId);
    }

    @Retry(name = "simpleRetry", fallbackMethod = "getMovieDetailsFallbackMethod")
    public Movie getMovieDetailsWithFallback(String movieId) {
        return fetchMovieDetails(movieId);
    }

    @Retry(name = "customRetryConfig")
    public Movie getMovieDetailsWithCustomRetryConfig(String movieId) {
        return fetchMovieDetails(movieId);
    }

    @Retry(name = "retryOnException")
    public Movie getMovieDetailsRetryOnException(String movieId) {
        return fetchMovieDetails(movieId);
    }

    @Retry(name = "retryBasedOnConditionalPredicate")
    public Movie getMovieDetailsRetryOnConditionalPredicate(String movieId) {
        try {
            return fetchMovieDetails(movieId);
        } catch (MovieNotFoundException movieNotFoundException) {
            log.info("Movie not found exception encountered. Returning default value");
            return new Movie("Default", "N/A", "N/A", 0.0);
        }
    }

    @Retry(name = "retryBasedOnExceptionPredicate")
    public Movie getMovieDetailsRetryOnExceptionPredicate(String movieId) {
        return fetchMovieDetails(movieId);
    }
}
