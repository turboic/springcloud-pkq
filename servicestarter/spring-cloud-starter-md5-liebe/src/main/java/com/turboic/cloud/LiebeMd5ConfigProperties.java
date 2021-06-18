package com.turboic.cloud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

/**
 * @author liebe
 */
@ConfigurationProperties(LiebeMd5ConfigProperties.PREFIX)
public class LiebeMd5ConfigProperties {

	/**
	 * Prefix of {@link LiebeMd5ConfigProperties}.
	 */
	public static final String PREFIX = "spring.cloud.liebe.md5.config";

	private static final Logger log = LoggerFactory
			.getLogger(LiebeMd5ConfigProperties.class);

	@Autowired
	@JsonIgnore
	private Environment environment;

	@PostConstruct
	public void init() {
		this.overrideFromEnv();
	}

	private void overrideFromEnv() {
		if (StringUtils.isEmpty(this.getHost())) {
			this.setUsername(
					environment.resolvePlaceholders("${spring.cloud.liebe.md5.config.host:}"));
		}
		if (StringUtils.isEmpty(this.getUsername())) {
			this.setUsername(
					environment.resolvePlaceholders("${spring.cloud.liebe.md5.config.username:}"));
		}
		if (StringUtils.isEmpty(this.getPassword())) {
			this.setPassword(
					environment.resolvePlaceholders("${spring.cloud.liebe.md5.config.password:}"));
		}
	}

	/**
	 * nacos config server address.
	 */
	private String host;

	/**
	 * the nacos authentication username.
	 */
	private String username;

	/**
	 * the nacos authentication password.
	 */
	private String password;

	public static String getPREFIX() {
		return PREFIX;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
