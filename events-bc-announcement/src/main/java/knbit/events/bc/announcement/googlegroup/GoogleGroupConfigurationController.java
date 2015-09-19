package knbit.events.bc.announcement.googlegroup;

import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfiguration;
import knbit.events.bc.announcement.googlegroup.configuration.GoogleGroupConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 19.09.15.
 */

@RestController
@RequestMapping("/announcements/config/googlegroup")
public class GoogleGroupConfigurationController {

    private final GoogleGroupConfigurationRepository repository;

    @Autowired
    public GoogleGroupConfigurationController(GoogleGroupConfigurationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GoogleGroupConfiguration configurationFor(@PathVariable("id") Long id) {
        return repository.findOne(id).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<GoogleGroupConfiguration> allConfigurations() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void newConfiguration(@RequestBody @Valid GoogleGroupConfiguration configuration) {
        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateConfiguration(@RequestBody @Valid GoogleGroupConfiguration configuration,
                                    @PathVariable("id") Long id) {

        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConfiguration(@PathVariable("id") Long id) {
        repository.delete(id);
    }
}
