package knbit.events.bc.announcement;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by novy on 03.04.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newMessage")
public class MessageBuilder {

    private String recipient = "default@recipient.com";
    private String subject = "defaultSubject";
    private String content = "defaultContent";

    public SimpleMailMessage build() {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);

        return message;
    }
}
