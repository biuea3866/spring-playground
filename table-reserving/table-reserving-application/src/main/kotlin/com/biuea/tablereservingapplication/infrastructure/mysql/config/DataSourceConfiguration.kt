package com.biuea.tablereservingapplication.infrastructure.mysql.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.*
import javax.sql.DataSource


@Configuration
class DataSourceConfiguration(
    private val masterDriver: String,
    private val masterJdbcUrl: String,
    private val masterUsername: String,
    private val masterPassword: String,
    private val slaveDriver: String,
    private val slaveJdbcUrl: String,
    private val slaveUsername: String,
    private val slavePassword: String,
) {
    companion object {
        const val MASTER_DATASOURCE = "masterDataSource"
        const val SLAVE_DATASOURCE = "slaveDataSource"
    }

    // Master 데이터베이스의 DataSource를 생성하는 빈 설정
    @Bean(MASTER_DATASOURCE)
    fun masterDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    // Slave 데이터베이스의 DataSource를 생성하는 빈 설정
    @Bean(SLAVE_DATASOURCE)
    fun slaveDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    // 라우팅 데이터베이스를 생성하는 빈 설정
    @Bean
    fun routingDataSource(
        @Qualifier(MASTER_DATASOURCE) masterDataSource: DataSource,
        @Qualifier(SLAVE_DATASOURCE) slaveDataSource: DataSource
    ): DataSource {
        val routingDataSource = RoutingDataSource()

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

    enum class RoutingSource {
        MASTER,
        SLAVE
    }

    class RoutingDataSource : AbstractRoutingDataSource() {
        override fun determineCurrentLookupKey(): Any {
            return when(TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
                true -> RoutingSource.SLAVE
                false -> RoutingSource.MASTER
            }
        }
    }
}