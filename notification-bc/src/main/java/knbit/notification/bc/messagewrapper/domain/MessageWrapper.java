package knbit.notification.bc.messagewrapper.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode
public class MessageWrapper {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column
    private String payload;

    public MessageWrapper(MessageType type, String payload) {
        this.id = generateId();
        this.type = type;
        this.payload = payload;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
