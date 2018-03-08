package com.blandon.test.workqueue;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {
	private final static String QUEUE_NAME = "task_queue";

	  public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    
	    //This tells RabbitMQ not to give more than one message to a worker at a time. Or, in other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one. Instead, it will dispatch it to the next worker that is not still busy.
	    channel.basicQos(1);

	    Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	        String message = new String(body, "UTF-8");
	        System.out.println(" [x] Received '" + message + "'");
	        try {
	            doWork(message);
	          } catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
	            System.out.println(" [x] Done");
	            //Tell RabbitMQ server to delete the message as it is already successfully acknowledged.
	            channel.basicAck(envelope.getDeliveryTag(), false);
	          }
	      }
	    };
	    boolean autoAck = false;
	    channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	  }
	  
	  private static void doWork(String task) throws InterruptedException {
		  System.out.println(" Checking message");
		    for (char ch: task.toCharArray()) {
		        if (ch == '.') {
		        	Thread.sleep(10000);
		        	}
		    }
		}  
}
