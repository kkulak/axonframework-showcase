package knbit.events.bc.announcement.configuration.iietboard;

import knbit.events.bc.announcement.configuration.AbstractPublisherConfiguration;
import knbit.events.bc.announcement.configuration.PublisherConfiguration;
import knbit.events.bc.announcement.publishers.PublisherVendor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by novy on 11.04.15.
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class IIETBoardConfiguration extends AbstractPublisherConfiguration implements PublisherConfiguration {

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

    public IIETBoardConfiguration(Long id,
                                  String name,
                                  boolean isDefault,
                                  String username,
                                  String password,
                                  String loginUrl,
                                  String boardUrl,
                                  String boardId) {

        super(id, name, isDefault);
        this.username = username;
        this.password = password;
        this.loginUrl = loginUrl;
        this.boardUrl = boardUrl;
        this.boardId = boardId;
    }

    @Override
    public PublisherVendor getVendor() {
        return PublisherVendor.IIET_BOARD;
    }
}
