package org.mintcode.errabbit.log4j.send;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.errabbit.model.Report;

import javax.jms.*;


/**
 * Created by soleaf on 2015. 2. 10..
 */
public class ActiveMQSender {

    private static ActiveMQSender single = new ActiveMQSender();

    //created ConnectionFactory object for creating connection
    String userName;
    String password;
    String uri;
    String queueName = "errabbit.report";
    String rabbitID;

    Connection connection = null;
    Session session = null;
    MessageProducer producer = null;

    private ActiveMQSender(){}

    public static ActiveMQSender getInstance(){
        return single;
    }

    public boolean connect(String uri, String userName, String password, String rabbitID){

        this.uri = uri;
        this.userName = userName;
        this.password = password;
        this.rabbitID = rabbitID;

        if (getSession()){
            System.out.println("Connection Success");
            return true;
        }
        else{
            System.out.println("Connection Fail");
            return false;
        }

    }

    public boolean getSession() {

        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, uri);
            // Create a Connection
            Connection connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            return true;

        } catch (JMSException e) {
            e.printStackTrace();

            return false;
        }
    }

    public  void send(LoggingEvent loggingEvent){
        try {
            ObjectMessage message = session.createObjectMessage(new Report(rabbitID, loggingEvent));
            send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    protected void send(ObjectMessage message) {
        try {

            // Tell the producer to send the message
            System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public void close() throws JMSException {
        // Clean up
        producer.close();
        session.close();
        connection.close();

    }

}

