package knbit.events.bc.common.mailnotifications.rest;

import java.util.Optional;

/**
 * Created by novy on 06.12.15.
 */
public interface TokenProvider {

    Optional<String> provideToken();

    default String tokenHeaderName() {
        return "knbit-aa-auth";
    }
}
