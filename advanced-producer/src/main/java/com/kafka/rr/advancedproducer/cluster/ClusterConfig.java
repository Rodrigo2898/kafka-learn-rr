package com.kafka.rr.advancedproducer.cluster;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class ClusterConfig {

    private final Environment env;
    private final Logger log = LoggerFactory.getLogger(ClusterConfig.class);

    public ClusterConfig(Environment env) {
        this.env = env;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void getClusterConfigs() {
        Properties properties = getProperties();

        try (AdminClient adminClient = AdminClient.create(properties)) {

            DescribeClusterResult cluster = adminClient.describeCluster();
            log.info("Connected to cluster: {}", cluster.clusterId().get());
            log.info("The brokers in the cluster are:");
            cluster.nodes().get().forEach(node -> log.info(" * {}", node));
            log.info("The controller is: {}", cluster.controller().get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap-servers"));
        properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, env.getProperty("spring.kafka.properties.security.protocol"));
//        properties.put("sasl.mechanism", env.getProperty("spring.kafka.properties.sasl.mechanism"));
//        properties.put("sasl.jaas.config", env.getProperty("spring.kafka.properties.sasl.jaas.config"));
        return properties;
    }
}
