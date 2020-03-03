package org.epis.integration.dsl.config;

import org.epis.integration.dsl.common.KafkaAppProperties;
import org.epis.integration.dsl.converter.PatientDataTransformer;
import org.epis.integration.dsl.splitter.PatientDataSplitter;
import org.epis.integration.dsl.ws.PatientSoapServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.PollableChannel;

@Configuration
@ComponentScan
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
	
	

	
	
	
	
	



}
