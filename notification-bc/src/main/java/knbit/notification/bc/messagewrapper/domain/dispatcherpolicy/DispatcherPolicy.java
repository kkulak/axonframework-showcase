package knbit.notification.bc.messagewrapper.domain.dispatcherpolicy;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;

import java.util.List;

public interface DispatcherPolicy {

    List<MessageWrapper> oldMessages();

}
