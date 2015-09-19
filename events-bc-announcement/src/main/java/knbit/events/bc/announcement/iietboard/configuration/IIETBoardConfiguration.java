package knbit.events.bc.announcement.iietboard.configuration;

import knbit.events.bc.announcement.AbstractNamedConfiguration;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by novy on 11.04.15.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class IIETBoardConfiguration extends AbstractNamedConfiguration {

    private String username;
    private String password;
    private String loginUrl;
    private String boardUrl;
    private String boardId;
}
