package org.epis.integration.dsl.gateway;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.epis.integration.dsl.app.KafkaAppProperties;
import org.epis.integration.dsl.endpoint.PatientDataSplitter;
import org.epis.integration.dsl.endpoint.PatientDataTransformer;
import org.epis.integration.dsl.endpoint.PatientServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
@SuppressWarnings("rawtypes")
public class InboundGatewayConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Autowired
	private KafkaAppProperties properties;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.topic}")
	private String springIntegrationKafkaTopic;

	@Autowired
	@Qualifier("consumerChannel")
	private PollableChannel consumerChannel;
	
	



	/*
	 * public KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter() {
	 * 
	 * @SuppressWarnings("unchecked") KafkaMessageDrivenChannelAdapter
	 * kafkaMessageDrivenChannelAdapter = new KafkaMessageDrivenChannelAdapter(
	 * kafkaListenerContainer());
	 * kafkaMessageDrivenChannelAdapter.setOutputChannel(consumerChannel);
	 * 
	 * return kafkaMessageDrivenChannelAdapter; }
	 */

	@SuppressWarnings("unchecked")
	//@Bean
	public ConcurrentMessageListenerContainer kafkaListenerContainer() {
		ContainerProperties containerProps = new ContainerProperties(springIntegrationKafkaTopic);

		return (ConcurrentMessageListenerContainer) new ConcurrentMessageListenerContainer(
				new DefaultKafkaConsumerFactory(consumerConfigs()), containerProps);
	}

	@SuppressWarnings("unchecked")
	//@Bean
	public Map consumerConfigs() {
		Map properties = new HashMap();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "dummy");
		return properties;
	}

	// kafka listener
//TODO
	//@Bean(name="kafkaInboundGatewayFlow")
	public IntegrationFlow kafkaInboundGatewayFlow() {
		
		IntegrationFlow integrationFlow=  IntegrationFlows
				.from(Kafka.messageDrivenChannelAdapter(
						new DefaultKafkaConsumerFactory<String, String>(kafkaProperties.buildConsumerProperties()),
						this.properties.getTopic()))
				.channel("requestChannel").transform(new PatientDataTransformer()).split(new PatientDataSplitter())
				.handle(new PatientServiceActivator()).get();

	//	this.flowContext.registration(integrationFlow).id("kafkaInboundGatewayFlow").register();
		return integrationFlow;
	}
	
	
	
	/*@Bean
	public ConcurrentMessageListenerContainer<String, String>(
	        ConcurrentKafkaListenerContainerFactory<String, String> factory) {

	    ConcurrentMessageListenerContainer<String, String> container =
	        factory.createContainer("topic1", "topci2");
	    container.setMessageListener(m -> { ... } );
	    return container;
	}*/
	
/*	@Bean
	public IntegrationFlow kafkaInboundGatewayFlows() {
		IntegrationFlow integrationFlow=  IntegrationFlows
				.from(Kafka.messageDrivenChannelAdapter(
						kafkaConsumerFactory(),
						this.properties.getTopic()))
				.channel("requestChannel").transform(new PatientDataTransformer()).split(new PatientDataSplitter())
				.handle(new PatientServiceActivator()).get();

		this.flowContext.registration(integrationFlow).id("kafkaInboundGatewayFlow").register();
		return integrationFlow;
	}
	
	
	
	public ConsumerFactory<?, ?> kafkaConsumerFactory() {
		Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
		consumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
		return new DefaultKafkaConsumerFactory<>(consumerProperties);
	}*/


}
