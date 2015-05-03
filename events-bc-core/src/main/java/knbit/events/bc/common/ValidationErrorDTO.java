package knbit.events.bc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationErrorDTO extends ErrorDTO {
    private static final String DEFAULT_TYPE = "ValidationFailed";
    private static final String DEFAULT_MESSAGE = "Validation hasn't succeeded!";
    private static final String DEFAULT_DEVELOPER_MESSAGE = "Invalid request arguments! Check proper format in swagger";

    private final Map<String, FieldErrorDTO> fieldErrorMap = new HashMap<>();
    private final List<GlobalErrorDTO> globalErrorList = new LinkedList<>();

    public ValidationErrorDTO() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_TYPE, DEFAULT_MESSAGE, DEFAULT_DEVELOPER_MESSAGE);
    }

    public void addFieldError(FieldErrorDTO fed) {
        fieldErrorMap.put(fed.getField(), fed);
    };

    public void addGlobalError(GlobalErrorDTO ged) {
        globalErrorList.add(ged);
    };

    @Getter
    public static class FieldErrorDTO extends GlobalErrorDTO {
        private final String field;
        private final String invalidValue;

        public FieldErrorDTO(String resource, String field, String code,
                             String message, String invalidValue) {
            super(resource, code, message);
            this.field = field;
            this.invalidValue = invalidValue;
        }

        @Setter
        @Accessors(fluent = true)
        @NoArgsConstructor(staticName = "instance")
        public static class Builder {
            private String field;
            private String invalidValue;
            private String resource;
            private String code;
            private String message;

            public FieldErrorDTO build() {
                return new FieldErrorDTO(
                        resource, field, code, message, invalidValue
                );
            }

        }
    }

    @AllArgsConstructor
    @Getter
    public static class GlobalErrorDTO {
        private final String resource;
        private final String code;
        private final String message;

        @Setter
        @Accessors(fluent = true)
        @NoArgsConstructor(staticName = "instance")
        public static class Builder {

            private String resource;
            private String code;
            private String message;

            public GlobalErrorDTO build() {
                return new GlobalErrorDTO(
                        resource, code, message
                );
            }

        }
    }
}
