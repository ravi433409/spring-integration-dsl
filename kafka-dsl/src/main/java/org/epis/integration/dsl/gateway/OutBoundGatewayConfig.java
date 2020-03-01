/*package org.epis.integration.dsl.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.messaging.MessageHandler;

import com.mongodb.MongoClient;

@Configuration
public class OutBoundGatewayConfig {
  
	
	//mongo outbound call
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "si4Db");
	}

	@Bean
	@ServiceActivator(inputChannel = "storeChannel")
	public MessageHandler mongodbAdapter() throws Exception {
		MongoDbStoringMessageHandler adapter = new MongoDbStoringMessageHandler(mongoDbFactory());
		adapter.setCollectionNameExpression(new LiteralExpression("courses"));

		return adapter;
	}

	// SOAP Outbound call
	@Bean
	public org.springframework.integration.dsl.IntegrationFlow kafkaInbounGatewayFlow() {
		return IntegrationFlows.from("soap-outbound-gateway-request-channel")
				.gateway("soap-outbound-gateway-request-channel", c -> c.replyChannel("reply channel")).get();
	}

}
*/