package knbit.events.bc.announcement.config;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by novy on 11.04.15.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class IIETBoardProperties {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String loginUrl;
    @NotBlank
    private String boardUrl;
    @NotBlank
    private String boardId;

}
