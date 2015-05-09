package knbit.notification.bc.messagewrapper.domain;

import knbit.notification.bc.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class MessageWrapper extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column
    private String payload;

}
