package id.my.hendisantika.resilience4jretry.controller.mock;

import id.my.hendisantika.resilience4jretry.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-resilience4j-retry
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/10/24
 * Time: 11.01
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/mock/movies")
public class MockMovieController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id) {
        Movie movie = null;
        switch (id) {
            case "1" -> movie = new Movie("1", "The Matrix", "Lana Wachowski, Lilly Wachowski", 8.7);
            case "2" -> movie = new Movie("2", "Inception", "Christopher Nolan", 8.8);
            case "3" -> {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Not Found");
            }
        }
        return ResponseEntity.ok(movie);
    }
}
