package knbit.notification.bc.messagewrapper.service;

import knbit.notification.bc.config.Topics;
import knbit.notification.bc.messagewrapper.domain.MessageType;

import java.util.HashMap;
import java.util.Map;

public class TopicMatcher {
    private static final Map<MessageType, String> maps = new HashMap<>();

    static {
        maps.put(MessageType.EVENT_PROPOSED, Topics.EVENT_PROPOSED);
    }

    public static String match(MessageType type) {
        return maps.get(type);
    }

}
