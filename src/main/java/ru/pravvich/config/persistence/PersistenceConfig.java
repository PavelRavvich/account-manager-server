package ru.pravvich.config.persistence;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Pavel Ravvich.
 */
@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages="ru.pravvich.repository")
@EnableTransactionManagement
public class PersistenceConfig {
}
