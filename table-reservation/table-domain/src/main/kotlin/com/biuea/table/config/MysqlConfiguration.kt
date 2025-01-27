package com.biuea.table.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.Collections
import javax.sql.DataSource


@Configuration
class MysqlConfiguration(
    @Value("\${spring.datasource.master.hikari.driver-class-name}")
    private val masterDriverClassName: String,
    @Value("\${spring.datasource.master.hikari.jdbc-url}")
    private val masterUrl: String,
    @Value("\${spring.datasource.master.hikari.username}")
    private val masterUsername: String,
    @Value("\${spring.datasource.master.hikari.password}")
    private val masterPassword: String,
    @Value("\${spring.datasource.slave.hikari.driver-class-name}")
    private val slaveDriverClassName: String,
    @Value("\${spring.datasource.slave.hikari.jdbc-url}")
    private val slaveUrl: String,
    @Value("\${spring.datasource.slave.hikari.username}")
    private val slaveUsername: String,
    @Value("\${spring.datasource.slave.hikari.password}")
    private val slavePassword: String,
) {
    companion object {
        const val masterDataSource: String = "masterDataSource"
        const val slaveDataSource: String = "slaveDataSource"
    }

    @Bean(masterDataSource)
    fun masterDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName(masterDriverClassName)
            .url(masterUrl)
            .username(masterUsername)
            .password(masterPassword)
            .build()
    }

    @Bean(slaveDataSource)
    fun slaveDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName(slaveDriverClassName)
            .url(slaveUrl)
            .username(slaveUsername)
            .password(slavePassword)
            .build()
    }

    @Bean
    fun routingDataSource(
        @Qualifier(Companion.masterDataSource) masterDataSource: DataSource,
        @Qualifier(Companion.slaveDataSource) slaveDataSource: DataSource
    ): DataSource {
        val routingDataSource: AbstractRoutingDataSource = RoutingDataSource()

        val dataSourceMap: MutableMap<Any, Any> = HashMap()
        dataSourceMap["master"] = masterDataSource
        dataSourceMap["slave"] = slaveDataSource

        val immutableDataSourceMap: Map<Any, Any> = Collections.unmodifiableMap(dataSourceMap)

        routingDataSource.setTargetDataSources(immutableDataSourceMap)
        routingDataSource.setDefaultTargetDataSource(masterDataSource)

        return routingDataSource
    }

    // 라우팅 데이터베이스를 기본 DataSource로 설정하는 빈 설정
    @Primary
    @Bean
    fun dataSource(@Qualifier("routingDataSource") routingDataSource: DataSource): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }
}

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any {
        return when(TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            true -> "slave"
            false -> "master"
        }
    }
}