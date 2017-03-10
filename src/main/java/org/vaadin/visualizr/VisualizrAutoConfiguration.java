package org.vaadin.visualizr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.vaadin.spring.annotation.SpringUI;

@ConditionalOnClass({SpringUI.class, EndpointAutoConfiguration.class})
public class VisualizrAutoConfiguration {

	private static Logger logger = LoggerFactory.getLogger(VisualizrAutoConfiguration.class);

	@Configuration
	@PropertySource("classpath:visualizr.properties")
	@ComponentScan("org.vaadin.visualizr")
	static class EnableVisualizrConfiguration implements InitializingBean {

		@Override
		public void afterPropertiesSet() throws Exception {
			logger.debug("{} initialized", getClass().getName());
		}
	}
}
