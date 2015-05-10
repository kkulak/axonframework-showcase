package knbit.notification.bc.messagewrapper.domain.dispatcherpolicy;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import knbit.notification.bc.messagewrapper.infrastructure.persistence.MessageWrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeAndAmountBasedDispatcherPolicy implements DispatcherPolicy {
    private final MessageWrapperRepository repository;
    private static final int MAX_MESSAGES_COUNT = 10;

    @Autowired
    public TimeAndAmountBasedDispatcherPolicy(MessageWrapperRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MessageWrapper> oldMessages() {
        return repository.findOldMessages(
                new PageRequest(0, MAX_MESSAGES_COUNT)
        );
    }

}
