package dev.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class SimpleProducer {

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

        // acks , defallt = -a
//        props.setProperty(ProducerConfig.ACKS_CONFIG, "0")


        // KafkaProducer object create
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        // ProducerRecord object create
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName,"hello world!");
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "id-001", "hello world!");

        // KafkaProducer message send
        kafkaProducer.send(producerRecord);
//        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);

        // kafkaProducer.flush()  : close have auto flush
        kafkaProducer.close();
    }
}
