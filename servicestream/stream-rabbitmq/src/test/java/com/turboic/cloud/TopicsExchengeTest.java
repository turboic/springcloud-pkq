package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Unit test for simple App.
 */
public class TopicsExchengeTest
{
    private static final String EXCHANGE_NAME = "topic_logs";
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            //String routingKey = getRouting(argv);
            //String message = getMessage(argv);
            //channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            //System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
