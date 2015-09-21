package knbit.events.bc.announcement.publishers.iietboard;

import com.gargoylesoftware.htmlunit.WebClient;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.configuration.iietboard.IIETBoardConfiguration;
import knbit.events.bc.announcement.configuration.iietboard.IIETBoardConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 19.09.15.
 */

@Component
public class IIETBoardPublisherFactory {

    private final IIETBoardConfigurationRepository repository;

    @Autowired
    public IIETBoardPublisherFactory(IIETBoardConfigurationRepository repository) {
        this.repository = repository;
    }

    public IIETBoardPublisher fromConfigurationBasedOn(Long id) {
        final IIETBoardConfiguration configuration =
                repository.findOne(id).orElseThrow(this::noSuchIIETBoardConfiguration);

        final BoardPublisherConfiguration publisherConfiguration = new BoardPublisherConfiguration(
                configuration.getUsername(),
                configuration.getPassword(),
                configuration.getLoginUrl(),
                configuration.getBoardUrl(),
                configuration.getBoardId()
        );

        final WebClient webClient = new WebClient();

        return new IIETBoardPublisher(
                publisherConfiguration, webClient
        );
    }

    private AnnouncementException noSuchIIETBoardConfiguration() {
        return new AnnouncementException("No such iiet board configuration");
    }
}
