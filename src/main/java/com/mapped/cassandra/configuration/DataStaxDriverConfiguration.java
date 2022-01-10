package com.mapped.cassandra.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
public class DataStaxDriverConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStaxDriverConfiguration.class);

    private static final String DATASTAX_ENV_PATH = "DATASTAX_CONFIG_PATH";
    private static final String DATASTAX_CONFIG_PATH = "datastax.config.path";

    @Autowired
    private Environment environment;

    @Bean
    public CqlSession cqlSession() {
        AbstractEnvironment environment = (AbstractEnvironment) this.environment;
        String configPath = environment.getProperty(DATASTAX_CONFIG_PATH,
                environment.getProperty(DATASTAX_ENV_PATH, ""));
        if(configPath.isEmpty()){
            throw new IllegalStateException("Configuration path isn't provided");
        }

        File configFile = new File(configPath);
        if (!configFile.exists()) {
            throw new IllegalStateException("Could not load config file: " + configPath);
        }

        LOGGER.info("Loading configuration from {}", configFile.getAbsolutePath());

        final CqlSessionBuilder builder = CqlSession.builder().withConfigLoader(DriverConfigLoader.fromFile(configFile));

        CqlSession cqlSession = builder.build();

        LOGGER.info("Cql session is opened");

        return cqlSession;
    }

}
