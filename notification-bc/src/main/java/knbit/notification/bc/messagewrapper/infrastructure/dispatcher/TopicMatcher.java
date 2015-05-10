package knbit.notification.bc.messagewrapper.infrastructure.dispatcher;

import knbit.notification.bc.config.Topic;
import knbit.notification.bc.messagewrapper.domain.MessageType;

import java.util.HashMap;
import java.util.Map;

public class TopicMatcher {
    private static final Map<MessageType, String> maps = new HashMap<>();

    static {
        maps.put(MessageType.EVENT_PROPOSED, Topic.INITIAL);
    }

    public static String match(MessageType type) {
        return maps.get(type);
    }

}
