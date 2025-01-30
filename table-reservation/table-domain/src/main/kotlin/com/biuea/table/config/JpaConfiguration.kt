package com.biuea.table.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@EnableJpaRepositories(basePackages = ["com.biuea.table.infrastructure.mysql"])
@Configuration
@EnableTransactionManagement
class JpaConfiguration {
    @Bean
    fun entityManagerFactory(
        @Qualifier("dataSource") dataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()

        entityManagerFactory.apply {
            this.dataSource = dataSource
            this.setPackagesToScan("com.biuea.table.domain")
            this.jpaVendorAdapter = jpaVendorAdapter()
            this.persistenceUnitName = "entityManager"
            this.setJpaProperties(hibernateProperties())
        }

        return entityManagerFactory
    }

    private fun jpaVendorAdapter(): JpaVendorAdapter {
        val hibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        // DDL 생성 기능을 활성화
//        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setShowSql(true)

        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL)

        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect")

        return hibernateJpaVendorAdapter
    }

    private fun hibernateProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "validate")
        properties.setProperty("hibernate.format_sql", "true")
        return properties
    }

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        val jpaTransactionManager = JpaTransactionManager().apply {
            this.entityManagerFactory = entityManagerFactory.getObject()
        }
        return jpaTransactionManager
    }
}