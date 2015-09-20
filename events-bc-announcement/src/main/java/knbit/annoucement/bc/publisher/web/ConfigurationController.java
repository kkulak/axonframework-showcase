package knbit.annoucement.bc.publisher.web;

import knbit.annoucement.bc.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 07.04.15.
 */

@RestController
@RequestMapping("/announcements/config")
public class ConfigurationController {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationController(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public FacebookProperties facebookConfiguration() {
        return configurationRepository.facebookProperties();
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void facebookConfiguration(@RequestBody @Valid FacebookProperties facebookProperties) {
        configurationRepository.facebookProperties(facebookProperties);
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public TwitterProperties twitterConfiguration() {
        return configurationRepository.twitterProperties();
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void twitterConfiguration(@RequestBody @Valid TwitterProperties twitterProperties) {
        configurationRepository.twitterProperties(twitterProperties);
    }

    @RequestMapping(value = "/googlegroup", method = RequestMethod.GET)
    public GoogleGroupProperties googleGroupConfiguration() {
        return configurationRepository.googleGroupProperties();
    }

    @RequestMapping(value = "/googlegroup", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void googleGroupConfiguration(@RequestBody @Valid GoogleGroupProperties googleGroupProperties) {
        configurationRepository.googleGroupProperties(googleGroupProperties);
    }

    @RequestMapping(value = "/iietboard", method = RequestMethod.GET)
    public IIETBoardProperties iietBoardConfiguration() {
        return configurationRepository.iietBoardProperties();
    }

    @RequestMapping(value = "/iietboard", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iietBoardConfiguration(@RequestBody @Valid IIETBoardProperties iietBoardProperties) {
        configurationRepository.iietBoardProperties(iietBoardProperties);
    }

}
