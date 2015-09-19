package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.configuration.AllConfigurationQuery;
import knbit.events.bc.announcement.configuration.PublisherConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by novy on 07.04.15.
 */

@RestController
@RequestMapping("/announcements/config")
public class BasicConfigurationInfoController {

    private final AllConfigurationQuery query;

    @Autowired
    public BasicConfigurationInfoController(AllConfigurationQuery query) {
        this.query = query;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<PublisherConfiguration> configurations() {
        return query.allConfigurations();
    }
}
