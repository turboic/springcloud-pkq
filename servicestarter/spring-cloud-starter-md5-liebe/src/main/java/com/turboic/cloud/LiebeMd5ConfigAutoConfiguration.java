package com.turboic.cloud;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author liibe
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class LiebeMd5ConfigAutoConfiguration {
	@Bean
	public LiebeMd5ConfigProperties liebeMd5ConfigProperties(ApplicationContext context) {
		if (context.getParent() != null
				&& BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
						context.getParent(), LiebeMd5ConfigProperties.class).length > 0) {
			return BeanFactoryUtils.beanOfTypeIncludingAncestors(context.getParent(),
					LiebeMd5ConfigProperties.class);
		}
		return new LiebeMd5ConfigProperties();
	}
}
