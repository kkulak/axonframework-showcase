package knbit.events.bc.announcement.config;

import com.gargoylesoftware.htmlunit.WebClient;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.iietboard.BoardPublisherConfiguration;
import knbit.events.bc.announcement.iietboard.IIETBoardPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by novy on 03.04.15.
 */

@Configuration
@PropertySource("classpath:iietboard.properties")
class IIETBoardConfig {

    @Value("${board.username}")
    private String username;

    @Value("${board.password}")
    private String password;

    @Value("${board.uri}")
    private String boardUri;

    @Value("${board.boardid}")
    private String boardId;

    @Value("${accounts.loginuri}")
    private String loginUri;


    @Bean(name = Publishers.IIET_BOARD)
    public Publisher iietForumPublisher() {
        return new IIETBoardPublisher(
                new BoardPublisherConfiguration(username, password, loginUri, boardUri, boardId),
                new WebClient()
        );
    }

}
