package org.example.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Sends a single message via Client channel to RabbitMQ's queue.
 *
 * @author sukh
 */
public class Send {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // Connection abstracts the socket connection; takes care of protocol version negotiation and authentication
        // Create a channel to get things done
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // declare a queue; idempotent
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World";
            // publish the message
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
