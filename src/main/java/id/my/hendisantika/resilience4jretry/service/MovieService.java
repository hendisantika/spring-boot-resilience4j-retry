package id.my.hendisantika.resilience4jretry.service;

import id.my.hendisantika.resilience4jretry.client.MovieApiClient;
import id.my.hendisantika.resilience4jretry.entity.Movie;
import id.my.hendisantika.resilience4jretry.exception.MovieNotFoundException;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

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

    @Retry(name = "retryUsingExponentialBackoff")
    public Movie getMovieDetailsRetryUsingExponentialBackoff(String movieId) {
        return fetchMovieDetails(movieId);
    }

    @Retry(name = "retryUsingRandomizedWait")
    public Movie getMovieDetailsRetryUsingRandomizedWait(String movieId) {
        return fetchMovieDetails(movieId);
    }

    private Movie fetchMovieDetails(String movieId) {
        Movie movie = null;
        try {
            movie = movieApiClient.getMovieDetails(movieId);
        } catch (HttpServerErrorException httpServerErrorException) {
            log.error("Received HTTP server error exception while fetching the movie details. Error Message: {}", httpServerErrorException.getMessage());
            throw httpServerErrorException;
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error("Received HTTP client error exception while fetching the movie details. Error Message: {}", httpClientErrorException.getMessage());
            throw httpClientErrorException;
        } catch (ResourceAccessException resourceAccessException) {
            log.error("Received Resource Access exception while fetching the movie details.");
            throw resourceAccessException;
        } catch (Exception exception) {
            log.error("Unexpected error encountered while fetching the movie details");
            throw exception;
        }
        return movie;
    }

    private Movie getMovieDetailsFallbackMethod(String movieId, MovieNotFoundException movieNotFoundException) {
        log.info("Fallback method called.");
        log.info("Original exception message: {}", movieNotFoundException.getMessage());
        return new Movie("Default", "N/A", "N/A", 0.0);
    }

    @PostConstruct
    public void postConstruct() {
        io.github.resilience4j.retry.Retry.EventPublisher eventPublisher = retryRegistry.retry("retryWithEventDetails").getEventPublisher();

        eventPublisher.onEvent(event -> log.info("Simple Retry - On Event. Event Details: {}", event));
        eventPublisher.onError(event -> log.info("Simple Retry - On Error. Event Details: {}", event));
        eventPublisher.onRetry(event -> log.info("Simple Retry - On Retry. Event Details: {}", event));
        eventPublisher.onSuccess(event -> log.info("Simple Retry - On Success. Event Details: {}", event));
        eventPublisher.onIgnoredError(event -> log.info("Simple Retry - On Ignored Error. Event Details: {}", event));
    }
}
