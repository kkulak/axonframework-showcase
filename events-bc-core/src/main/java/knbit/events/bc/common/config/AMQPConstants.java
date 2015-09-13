package knbit.events.bc.common.config;

public class AMQPConstants {

    public static final String RS_INTEGRATION_QUEUE = "rs.integration.in";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String EXCHANGE = "knbit.events.bc";

    public static final int RABBITMQ_SERVER_PORT = 5672;
    public static final String RABBITMQ_ADDRESS_ENVIRONMENT_VARIABLE = "RABBITMQ_PORT_" + RABBITMQ_SERVER_PORT + "_TCP_ADDR";
    public static final String DEFAULT_SERVER_IP = "127.0.0.1";

}