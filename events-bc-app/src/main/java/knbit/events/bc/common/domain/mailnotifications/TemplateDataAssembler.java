package knbit.events.bc.common.domain.mailnotifications;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by novy on 13.12.15.
 */

public class TemplateDataAssembler {

    private final ImmutableMap.Builder<String, String> data = ImmutableMap.builder();

    private TemplateDataAssembler(Map<String, String> initialData) {
        data.putAll(initialData);
    }

    public TemplateDataAssembler with(String key, String value) {
        data.put(key, value);
        return this;
    }

    public static TemplateDataAssembler prepopulate(Map<String, String> initialValues) {
        return new TemplateDataAssembler(initialValues);
    }

    public Map<String, String> data() {
        return data.build();
    }
}