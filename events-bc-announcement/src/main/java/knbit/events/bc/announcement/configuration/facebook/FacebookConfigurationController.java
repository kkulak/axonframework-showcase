package knbit.events.bc.announcement.configuration.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 19.09.15.
 */

@RestController
@RequestMapping("/announcements/config/facebook")
public class FacebookConfigurationController {

    private final FacebookConfigurationRepository repository;

    @Autowired
    public FacebookConfigurationController(FacebookConfigurationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FacebookConfiguration configurationFor(@PathVariable("id") Long id) {
        return repository.findOne(id).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<FacebookConfiguration> allConfigurations() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void newConfiguration(@RequestBody @Valid FacebookConfiguration configuration) {
        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateConfiguration(@RequestBody @Valid FacebookConfiguration configuration,
                                    @PathVariable("id") Long id) {

        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConfiguration(@PathVariable("id") Long id) {
        repository.delete(id);
    }
}
