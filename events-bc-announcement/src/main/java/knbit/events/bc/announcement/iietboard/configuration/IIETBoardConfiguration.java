package knbit.events.bc.announcement.iietboard.configuration;

import knbit.events.bc.announcement.AbstractPublisherConfiguration;
import knbit.events.bc.announcement.PublisherConfiguration;
import knbit.events.bc.announcement.PublisherVendor;
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

    @Override
    public PublisherVendor getVendor() {
        return PublisherVendor.IIET_BOARD;
    }
}
