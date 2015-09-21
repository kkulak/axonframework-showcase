package knbit.events.bc.announcement.configuration.iietboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 19.09.15.
 */

@RestController
@RequestMapping("/announcements/config/iietboard")
public class IIETBoardConfigurationController {

    private final IIETBoardConfigurationRepository repository;

    @Autowired
    public IIETBoardConfigurationController(IIETBoardConfigurationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public IIETBoardConfiguration configurationFor(@PathVariable("id") Long id) {
        return repository.findOne(id).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<IIETBoardConfiguration> allConfigurations() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void newConfiguration(@RequestBody @Valid IIETBoardConfiguration configuration) {
        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateConfiguration(@RequestBody @Valid IIETBoardConfiguration configuration,
                                    @PathVariable("id") Long id) {

        repository.save(configuration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConfiguration(@PathVariable("id") Long id) {
        repository.delete(id);
    }
}
