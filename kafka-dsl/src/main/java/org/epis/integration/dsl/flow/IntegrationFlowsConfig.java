package org.epis.integration.dsl.flow;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
public class IntegrationFlowsConfig {

	/*
	 * @Autowired private IntegrationFlow kafkaInboundGatewayFlow;
	 * 
	 * @Autowired private IntegrationFlowContext flowContext;
	 */

	// Integration flow:- Inbound kafka to soap call

	// @Bean
	/*
	 * public org.springframework.integration.dsl.IntegrationFlow rxIntegationFlow()
	 * { return
	 * IntegrationFlows.from("consumerChannel").gateway(IntegrationFlowBuilder)
	 * .transform(String.class, String::toUpperCase) .split(new
	 * PatientServiceActivator(), "getPatientData") // .transform(new
	 * PatientDataTransformer()) .channel("soap-outbound-gateway-request-channel")
	 * .get(); }
	 */

	// Transformers

	/*
	 * @PostConstruct public void registerFlow() {
	 * 
	 * this.flowContext.registration((org.springframework.integration.dsl.
	 * IntegrationFlow) this.kafkaInboundGatewayFlow).register(); }
	 */

}
