package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.config.ConfigurationRepository;
import knbit.events.bc.announcement.config.Publishers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Map<String, String> facebookConfiguration() {
        return configurationRepository.findBy(Publishers.FACEBOOK);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void facebookConfiguration(@RequestBody Map<String, String> facebookConfiguration) {
        configurationRepository.update(Publishers.FACEBOOK, facebookConfiguration);
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public Map<String, String> twitterConfiguration() {
        return configurationRepository.findBy(Publishers.TWITTER);
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void twitterCOnfiguration(@RequestBody Map<String, String> twitterConfiguration) {
        configurationRepository.update(Publishers.TWITTER, twitterConfiguration);
    }

    @RequestMapping(value = "/googlegroup", method = RequestMethod.GET)
    public Map<String, String> googleGroupConfiguration() {
        return configurationRepository.findBy(Publishers.GOOGLE_GROUP);
    }

    @RequestMapping(value = "/googlegroup", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void googleGroupConfiguration(@RequestBody Map<String, String> googleGroupConfiguration) {
        configurationRepository.update(Publishers.GOOGLE_GROUP, googleGroupConfiguration);
    }

    @RequestMapping(value = "/iietboard", method = RequestMethod.GET)
    public Map<String, String> iietBoardConfiguration() {
        return configurationRepository.findBy(Publishers.IIET_BOARD);
    }

    @RequestMapping(value = "/iietboard", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iietBoardConfiguration(@RequestBody Map<String, String> iietBoardConfiguration) {
        configurationRepository.update(Publishers.IIET_BOARD, iietBoardConfiguration);
    }

}
