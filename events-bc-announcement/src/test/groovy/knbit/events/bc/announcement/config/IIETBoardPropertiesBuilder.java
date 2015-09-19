package knbit.events.bc.announcement.config;

import knbit.events.bc.announcement.iietboard.configuration.IIETBoardConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newIIETBoardProperties")
class IIETBoardPropertiesBuilder {

    private String username = "username";
    private String password = "password";
    private String loginUrl = "loginUrl";
    private String boardUrl = "boardUrl";
    private String boardId = "id";


    public IIETBoardConfiguration build() {
        return new IIETBoardConfiguration(
                username, password, loginUrl, boardUrl, boardId
        );
    }
}
