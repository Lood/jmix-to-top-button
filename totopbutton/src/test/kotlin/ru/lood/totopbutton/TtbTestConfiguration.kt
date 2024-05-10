package ru.lood.totopbutton

import io.jmix.core.annotation.JmixModule
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

import javax.sql.DataSource

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(TtbConfiguration::class)
@PropertySource("classpath:/ru/lood/totopbutton/test-app.properties")
@JmixModule(id = "ru.lood.totopbutton.test", dependsOn = [TtbConfiguration::class])
open class TtbTestConfiguration {

    @Bean
    @Primary
    open fun dataSource(): DataSource {
        return EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }
}
