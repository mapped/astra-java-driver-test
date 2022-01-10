package com.mapped.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "com.mapped.cassandra")
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Autowired
    private CqlSession cqlSession;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void startApplication(){

        LOGGER.info("Application is started");

        ResultSet resultSet = cqlSession.execute("select * from janusgraph.graphindex;");

        if(!resultSet.wasApplied()){
            LOGGER.error("The query was not applied");
            throw new RuntimeException("Couldn't execute query successfully");
        }

        LOGGER.info("The first row: "+resultSet.one().toString());

        LOGGER.info("The test case is finished");
    }

}
