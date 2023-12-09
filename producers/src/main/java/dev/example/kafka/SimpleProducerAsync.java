package dev.example.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class SimpleProducerAsync {

    public static final Logger logger = LoggerFactory.getLogger(SimpleProducerAsync.class.getName());

    public static void main(String[] args) {

        String topicName = "sns-app-topic";


        // KafkaProducer configuration setting
        Properties props = new Properties();
        // bootstrap.servers : where broker ?
        // serialize
        // key.serializer.class
        // value.serializer.class
        // 080027BBD461
//        props.setProperty("bootstrap.servers", "192.168.0.36:9092");
//        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.36:9092");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.36:9092");
        // getName() : all Path print
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // KafkaProducer object create
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        // ProducerRecord object create
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName,"hello world!");
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "id-001", "hello world!");

        // KafkaProducer message send
//        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
//        RecordMetadata recordMetadata = future.get();
        kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if(exception == null) {
                    logger.info("\n ####### record metadata received ##### \n" +
                            "partition:" + metadata.partition() + "\n" +
                            "offset:" + metadata.offset() + "\n" +
                            "timestamp:" + metadata.timestamp());
                } else {
                    logger.error("exception error from broker " + exception.getMessage());
                }

            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // kafkaProducer.flush()  : close have auto flush
        kafkaProducer.close();

    }
}
