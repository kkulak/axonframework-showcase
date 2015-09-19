package knbit.events.bc.announcement.builders;

import knbit.events.bc.announcement.configuration.iietboard.IIETBoardConfiguration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 12.04.15.
 */
@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newIIETBoardProperties")
public class IIETBoardConfigurationBuilder {

    private Long id = 666L;
    private String name = "name";
    private boolean isDefault = true;
    private String username = "username";
    private String password = "password";
    private String loginUrl = "loginUrl";
    private String boardUrl = "boardUrl";
    private String boardId = "id";


    public IIETBoardConfiguration build() {
        return new IIETBoardConfiguration(
                id, name, isDefault, username, password, loginUrl, boardUrl, boardId
        );
    }
}
