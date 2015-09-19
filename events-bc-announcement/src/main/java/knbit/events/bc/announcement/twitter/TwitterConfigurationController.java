package knbit.events.bc.announcement.twitter;

import knbit.events.bc.announcement.twitter.configuration.TwitterConfiguration;
import knbit.events.bc.announcement.twitter.configuration.TwitterConfigurationConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 19.09.15.
 */

@RestController
@RequestMapping("/announcements/config/twitter")
public class TwitterConfigurationController {

    private final TwitterConfigurationConfigurationRepository repository;

    @Autowired
    public TwitterConfigurationController(TwitterConfigurationConfigurationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TwitterConfiguration configurationFor(@PathVariable("id") Long id) {
        return repository.findOne(id).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<TwitterConfiguration> allConfigurations() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void newConfiguration(@RequestBody @Valid TwitterConfiguration configuration) {
        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateConfiguration(@RequestBody @Valid TwitterConfiguration configuration,
                                    @PathVariable("id") Long id) {

        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConfiguration(@PathVariable("id") Long id) {
        repository.delete(id);
    }
}
