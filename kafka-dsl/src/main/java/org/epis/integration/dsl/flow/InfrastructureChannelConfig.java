package org.epis.integration.dsl.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
@ComponentScan(basePackageClasses = { InfrastructureChannelConfig.class })
public class InfrastructureChannelConfig {

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

	/*
	 * @Bean
	 * 
	 * @Description("Stores inbound kafka consumer messages") public DirectChannel
	 * consumerChannel() { return new DirectChannel(); }
	 */

}
