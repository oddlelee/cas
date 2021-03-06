package org.apereo.cas.monitor.config;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.monitor.MonitorProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.monitor.JdbcDataSourceHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;

/**
 * This is {@link CasJdbcMonitorConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("casJdbcMonitorConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@Slf4j
public class CasJdbcMonitorConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Lazy
    @Bean
    public ThreadPoolExecutorFactoryBean pooledJdbcMonitorExecutorService() {
        return Beans.newThreadPoolExecutorFactoryBean(casProperties.getMonitor().getJdbc().getPool());
    }

    @Autowired
    @Bean
    @RefreshScope
    public HealthIndicator dataSourceHealthIndicator(@Qualifier("pooledJdbcMonitorExecutorService") final ExecutorService executor) {
        final MonitorProperties.Jdbc jdbc = casProperties.getMonitor().getJdbc();
        return new JdbcDataSourceHealthIndicator((int) Beans.newDuration(jdbc.getMaxWait()).toMillis(),
            monitorDataSource(), executor, jdbc.getValidationQuery());
    }

    @ConditionalOnMissingBean(name = "monitorDataSource")
    @Bean
    public DataSource monitorDataSource() {
        return JpaBeans.newDataSource(casProperties.getMonitor().getJdbc());
    }
}
