package knbit.events.bc.common.domain.mailnotifications;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

/**
 * Created by novy on 06.12.15.
 */

@Component
class TemplateReader {

    private final Configuration templateConfiguration;

    @Autowired
    public TemplateReader(Configuration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    @SneakyThrows
    public TemplateToFill templateFrom(String path) {
        return new TemplateToFill(templateConfiguration.getTemplate(path));
    }

    @RequiredArgsConstructor
    class TemplateToFill {
        private final Template template;

        @SneakyThrows
        public String fillWith(Map<String, String> data) {
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        }
    }

}


