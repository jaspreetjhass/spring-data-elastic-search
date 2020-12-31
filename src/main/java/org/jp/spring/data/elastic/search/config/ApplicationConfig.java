package org.jp.spring.data.elastic.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ApplicationConfig {

	//@Bean
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9400").build();
		final ElasticsearchRestTemplate elasticsearchRestTemplate = new ElasticsearchRestTemplate(
				RestClients.create(clientConfiguration).rest());
		return elasticsearchRestTemplate;
	}

}
