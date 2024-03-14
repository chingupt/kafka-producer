package com.kafka.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class MainApplication {

	private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Change to your Kafka broker address

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new RandomCarGenerator(), 0, 2000); // Schedule task every 30 seconds
	}

	static class RandomCarGenerator extends TimerTask {
		private final KafkaProducer<String, String> producer;

		public RandomCarGenerator() {
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

			producer = new KafkaProducer<>(props);
		}

		@Override
		public void run() {
			Car randomCar = new Car();

			// Convert Car object to JSON (you can use any serialization method you prefer)
			String carJson = carToJson(randomCar);

			// Send the car data to Kafka topic "CarTopic"
			ProducerRecord<String, String> record = new ProducerRecord<>("carsData", carJson);
			producer.send(record, (metadata, exception) -> {
				if (exception == null) {
					System.out.println("Car data sent successfully to Kafka topic!");
				} else {
					System.err.println("Error sending car data: " + exception.getMessage());
				}
			});
		}

		private String carToJson(Car car) {
			// Convert Car object to JSON (you can use a library like Jackson or Gson)
			// Example: {"make":"Ford","model":"Focus","year":2016,"saleTimestamp":"02/10/2024","dealerName":"A","state":"MO","price":14590}
			return "{\"make\":\"" + car.make + "\",\"model\":\"" + car.model + "\",\"year\":" + car.year +
					",\"saleTimestamp\":\"" + formatDate(car.saleTimestamp) + "\",\"dealerName\":\"" + car.dealerName +
					"\",\"state\":\"" + car.state + "\",\"price\":" + car.price + "}";
		}

		private String formatDate(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}

		@Override
		public void finalize() {
			producer.close();
		}
	}

}
