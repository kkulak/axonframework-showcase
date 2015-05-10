package knbit.notification.bc.messagewrapper.domain;

import com.google.common.base.Preconditions;
import knbit.notification.bc.messagewrapper.infrastructure.persistence.LocalDateTimePersistenceConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode
public class MessageWrapper {

    @Id
    private String id;
    @Column
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column
    private boolean read;
    @Column
    private String payload;

    public MessageWrapper(MessageType type, String payload) {
        this.id = generateId();
        this.createdOn = LocalDateTime.now();
        this.read = false;
        this.type = type;
        this.payload = payload;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public void markRead() {
        Preconditions.checkState(!read, "Message already marked as read!");
        read = true;
    }

}
