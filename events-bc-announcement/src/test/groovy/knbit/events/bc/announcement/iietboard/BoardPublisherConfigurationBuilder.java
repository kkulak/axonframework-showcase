package knbit.events.bc.announcement.iietboard;

import knbit.events.bc.announcement.iietboard.publisher.BoardPublisherConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.04.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newBoardPublisherConfiguration")
public class BoardPublisherConfigurationBuilder {

    private String boardUsername = "username";
    private String boardPassword = "password";
    private String loginUrl = "loginUrl";
    private String boardUrl = "boardUrl";
    private String boardId = "boardId";

    public BoardPublisherConfiguration build() {
        return new BoardPublisherConfiguration(
                boardUsername, boardPassword, loginUrl, boardUrl, boardId
        );
    }


}
