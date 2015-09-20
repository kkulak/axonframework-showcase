package knbit.events.bc.common.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static<T> T map(byte[] src, Class<T> dest) {
        try {
            return mapper.readValue(src, dest);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot deserialize given body. Reason: ", e);
        }
    }

}
