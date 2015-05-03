package knbit.events.bc.common;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ErrorDTO {

    private final HttpStatus status;
    private final String cause;
    private final String message;
    private final String developerMessage;

    @Setter
    @Accessors(fluent = true)
    @NoArgsConstructor(staticName = "instance")
    public static class Builder {
        private HttpStatus status;
        private String cause;
        private String message;
        private String developerMessage;

        public ErrorDTO build() {
            return new ErrorDTO(
                    status, cause, message, developerMessage
            );
        }
    }

}
