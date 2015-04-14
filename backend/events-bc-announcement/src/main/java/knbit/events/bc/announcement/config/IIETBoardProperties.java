package knbit.events.bc.announcement.config;

import lombok.*;

/**
 * Created by novy on 11.04.15.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class IIETBoardProperties {

    private String username;
    private String password;
    private String loginUrl;
    private String boardUrl;
    private String boardId;

}
