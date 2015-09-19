package knbit.events.bc.announcement.iietboard.publisher;

import com.gargoylesoftware.htmlunit.WebClient;
import knbit.events.bc.announcement.iietboard.configuration.IIETBoardConfiguration;

/**
 * Created by novy on 19.09.15.
 */
public class IIETBoardPublisherFactory {

    public IIETBoardPublisher fromConfiguration(IIETBoardConfiguration configuration) {

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
}
