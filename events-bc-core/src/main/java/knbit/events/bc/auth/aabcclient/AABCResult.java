package knbit.events.bc.auth.aabcclient;

import org.springframework.http.HttpStatus;

/**
 * Created by novy on 26.07.15.
 */
public interface AABCResult  {

    boolean wasSuccessful();

    HttpStatus statusCode();

    String refreshedToken();
}
