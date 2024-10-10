package id.my.hendisantika.resilience4jretry.predicate;

import id.my.hendisantika.resilience4jretry.entity.Movie;

import java.util.function.Predicate;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-resilience4j-retry
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/10/24
 * Time: 10.53
 * To change this template use File | Settings | File Templates.
 */
public class ConditionPredicate implements Predicate<Movie> {
    @Override
    public boolean test(Movie movie) {
        System.out.println("Condition predicate is called.");
        return movie.getId().equals("Default");
    }
}
