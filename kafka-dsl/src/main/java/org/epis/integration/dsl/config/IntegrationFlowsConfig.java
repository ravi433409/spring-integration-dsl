package org.epis.integration.dsl.config;

import org.epis.integration.dsl.KafkaAppProperties;
import org.epis.integration.dsl.splitter.PatientDataSplitter;
import org.epis.integration.dsl.transformer.PatientDataTransformer;
import org.epis.integration.dsl.ws.PatientServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
@ComponentScan
public class IntegrationFlowsConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	@Description("Entry to the messaging system through the gateway.")
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("Entry to the messaging system through the gateway.")
	public MessageChannel fromKafka() {
		return new DirectChannel();
	}

	@Bean
	@Description("Sends request messages to the web service outbound gateway")
	public MessageChannel invocationChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("Sends web service responses to both the client and a database")
	public MessageChannel responseChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean
	@Description("Stores non filtered messages to the database")
	public MessageChannel dabaseStoreChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("Stores inbound kafka consumer messages")
	public PollableChannel consumerChannel() {
		return new QueueChannel();
	}

	// @Bean
	public IntegrationFlow fromKafkaFlow(ConsumerFactory<?, ?> consumerFactory) {
		return IntegrationFlows
				.from(Kafka.messageDrivenChannelAdapter(
						new DefaultKafkaConsumerFactory<String, String>(kafkaProperties.buildConsumerProperties()),
						"topic1"))
				.channel("fromKafka").transform(new PatientDataTransformer()).split(new PatientDataSplitter())
				.handle(new PatientServiceActivator()).get();
	}

}
