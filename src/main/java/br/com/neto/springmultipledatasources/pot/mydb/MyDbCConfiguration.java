package br.com.neto.springmultipledatasources.pot.mydb;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "br.com.neto.springmultipledatasources.pot.mydb")
public class MyDbCConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.mydb")
    public DataSource mydbDataSource() {
        return DataSourceBuilder.create().build();
    }

}
