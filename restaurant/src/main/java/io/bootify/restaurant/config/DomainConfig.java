package io.bootify.restaurant.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.restaurant.domain")
@EnableJpaRepositories("io.bootify.restaurant.repos")
@EnableTransactionManagement
public class DomainConfig {
}
