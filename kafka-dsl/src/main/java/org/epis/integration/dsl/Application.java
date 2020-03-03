/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.epis.integration.dsl;

import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.epis.integration.dsl.common.KafkaAppProperties;
import org.epis.integration.dsl.dao.SavePatientDao;
import org.epis.integration.dsl.splitter.PatientDataSplitter;
import org.epis.integration.dsl.transformer.PatientDataTransformer;
import org.epis.integration.dsl.ws.PatientSoapServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@EnableIntegration
@ComponentScan(basePackages = "org.epis.integration.dsl.app")
@EnableAutoConfiguration
@EnableConfigurationProperties(KafkaAppProperties.class)
public class Application {

	@Autowired
	private KafkaAppProperties properties;

	@Autowired
	private IntegrationFlowContext flowContext;

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE).run(args);
		context.getBean(Application.class).runDemo(context);
		// context.close();
	}

	private void runDemo(ConfigurableApplicationContext context) {
		MessageChannel toKafka = context.getBean("toKafka", MessageChannel.class);
		System.out.println("Sending 10 messages...");
		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, this.properties.getTopic());
		for (int patientId = 1000; patientId < 1030; patientId++) {
			// System.out.println("sent message " + "foo" + i);
			// int patientId = i;
			String inputMessage1 = "<PatientID><id>" + patientId++ + "</id></PatientID> ";
			String inputMessage2 = "<PatientID><id>" + patientId++ + "</id></PatientID> ";
			String inputMessage3 = "<PatientID><id>" + patientId + "</id></PatientID> ";

			System.out.println("xml request sent to kafka broker" + inputMessage1 + inputMessage2 + inputMessage3);

			toKafka.send(new GenericMessage<>(inputMessage1 + inputMessage2 + inputMessage3, headers));
		}
		// System.out.println("Sending a null message...");
		// toKafka.send(new GenericMessage<>(KafkaNull.INSTANCE, headers));

		/*
		 * PollableChannel fromKafka = context.getBean("fromKafka",
		 * PollableChannel.class); Message<?> received = fromKafka.receive(10000); int
		 * count = 0; while (received != null) {
		 * System.out.println("recevied"+received); received = fromKafka.receive(++count
		 * < 11 ? 10000 : 1000); System.out.println("recevied"+received); }
		 */

		System.out.println("Adding an adapter for a second topic and sending 10 messages...");
		addAnotherListenerForTopics(this.properties.getNewTopic());
		headers = Collections.singletonMap(KafkaHeaders.TOPIC, this.properties.getNewTopic());
		for (int patientId = 2000; patientId < 10; patientId++) {
			// int patientId = i;

			String inputMessage1 = "<PatientID><id>" + patientId++ + "</id></PatientID> ";
			String inputMessage2 = "<PatientID><id>" + patientId++ + "</id></PatientID> ";
			String inputMessage3 = "<PatientID><id>" + patientId + "</id></PatientID> ";

			System.out.println("xml request sent to kafka broker" + inputMessage1 + inputMessage2 + inputMessage3);

			toKafka.send(new GenericMessage<>(inputMessage1 + inputMessage2 + inputMessage3, headers));
		}
		/*
		 * received = fromKafka.receive(10000); count = 0; while (received != null) {
		 * System.out.println("recevied"+received); received = fromKafka.receive(++count
		 * < 10 ? 10000 : 1000); System.out.println("recevied"+received);
		 * 
		 * }
		 */
	}

	@Bean
	public ProducerFactory<?, ?> kafkaProducerFactory(KafkaProperties properties) {
		Map<String, Object> producerProperties = properties.buildProducerProperties();
		producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		return new DefaultKafkaProducerFactory<>(producerProperties);
	}

	@ServiceActivator(inputChannel = "toKafka")
	@Bean
	public MessageHandler handler(KafkaTemplate<String, String> kafkaTemplate) {
		KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
		handler.setMessageKeyExpression(new LiteralExpression(this.properties.getMessageKey()));
		return handler;
	}

	@Bean
	public NewTopic topic(KafkaAppProperties properties) {
		return new NewTopic(properties.getTopic(), 1, (short) 1);
	}

	@Bean
	public NewTopic newTopic(KafkaAppProperties properties) {
		return new NewTopic(properties.getNewTopic(), 1, (short) 1);
	}

	@Autowired
	private KafkaProperties kafkaProperties;

	public void addAnotherListenerForTopics(String... topics) {
		Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
		// change the group id so we don't revoke the other partitions.
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,
				consumerProperties.get(ConsumerConfig.GROUP_ID_CONFIG) + "x");

		// TODO as per new latest api spring integration flow registration need to be
		// done dynamically
		IntegrationFlow flow = fromKafkaFlow(consumerProperties);

		this.flowContext.registration(flow).register();

	}

	private IntegrationFlow fromKafkaFlow(Map<String, Object> consumerProperties) {
		IntegrationFlow flow = IntegrationFlows
				.from(Kafka.messageDrivenChannelAdapter(
						new DefaultKafkaConsumerFactory<String, String>(consumerProperties), "topic1", "topic2"))
				.channel("requestChannel").split(new PatientDataSplitter()).transform(new PatientDataTransformer())
				.handle(new PatientSoapServiceActivator()).handle(new SavePatientDao()).get();
		return flow;
	}

}