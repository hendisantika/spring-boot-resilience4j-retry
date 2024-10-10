package id.my.hendisantika.resilience4jretry.predicate;

import id.my.hendisantika.resilience4jretry.exception.MovieNotFoundException;

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
public class ExceptionPredicate implements Predicate<Throwable> {
    @Override
    public boolean test(Throwable throwable) {
        System.out.println("Exception predicate is called.");
        return throwable instanceof MovieNotFoundException;
    }
}
