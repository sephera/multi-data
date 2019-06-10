package com.nduyhai.multidata.infrastructure.config;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.config.Configuration.Builder;
import com.vladmihalcea.flexypool.connection.ConnectionDecoratorFactoryResolver;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeoutEvent;
import com.vladmihalcea.flexypool.event.ConnectionLeaseTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.EventListener;
import com.vladmihalcea.flexypool.metric.MetricsFactoryResolver;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FlexyPoolConfiguration {

  public static class ConnectionAcquireTimeThresholdExceededEventListener
      extends EventListener<ConnectionAcquireTimeThresholdExceededEvent> {

    public ConnectionAcquireTimeThresholdExceededEventListener() {
      super(ConnectionAcquireTimeThresholdExceededEvent.class);
    }

    @Override
    public void on(ConnectionAcquireTimeThresholdExceededEvent event) {
      log.info("Caught event {}", event);
    }
  }

  public static class ConnectionLeaseTimeThresholdExceededEventListener
      extends EventListener<ConnectionLeaseTimeThresholdExceededEvent> {

    public ConnectionLeaseTimeThresholdExceededEventListener() {
      super(ConnectionLeaseTimeThresholdExceededEvent.class);
    }

    @Override
    public void on(ConnectionLeaseTimeThresholdExceededEvent event) {
      log.info("Caught event {}", event);
    }
  }

  public static class ConnectionAcquireTimeoutEventListener
      extends EventListener<ConnectionAcquireTimeoutEvent> {

    public ConnectionAcquireTimeoutEventListener() {
      super(ConnectionAcquireTimeoutEvent.class);
    }

    @Override
    public void on(ConnectionAcquireTimeoutEvent event) {
      log.info("Caught event {}", event);
    }
  }

  public static FlexyPoolDataSource datasource(String uniqueId,
      HikariDataSource poolingDataSource) {
    final Configuration<HikariDataSource> config = new Builder<>(
        uniqueId,
        poolingDataSource,
        HikariCPPoolAdapter.FACTORY
    )
        .setMetricsFactory(MetricsFactoryResolver.INSTANCE.resolve())
        .setConnectionProxyFactory(ConnectionDecoratorFactoryResolver.INSTANCE.resolve())
        .setMetricLogReporterMillis(TimeUnit.SECONDS.toMillis(5))
        .setJmxEnabled(true)
        .setJmxAutoStart(true)
        .setConnectionAcquireTimeThresholdMillis(50L)
        .setConnectionLeaseTimeThresholdMillis(250L)
        .setEventListenerResolver(() -> Arrays.asList(
            new ConnectionAcquireTimeoutEventListener(),
            new ConnectionAcquireTimeThresholdExceededEventListener(),
            new ConnectionLeaseTimeThresholdExceededEventListener()
        ))
        .build();

    return new FlexyPoolDataSource<HikariDataSource>(
        config,
        new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
        new RetryConnectionAcquiringStrategy.Factory(2)
    );
  }


}
