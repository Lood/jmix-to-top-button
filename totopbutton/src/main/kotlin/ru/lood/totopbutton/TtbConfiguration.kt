package ru.lood.totopbutton

import io.jmix.core.annotation.JmixModule
import io.jmix.core.impl.scanning.AnnotationScanMetadataReaderFactory
import io.jmix.eclipselink.EclipselinkConfiguration
import io.jmix.flowui.FlowuiConfiguration
import io.jmix.flowui.sys.ActionsConfiguration
import io.jmix.flowui.sys.ViewControllersConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import ru.lood.totopbutton.reg.ToTopButtonRegistration

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@JmixModule(dependsOn = [EclipselinkConfiguration::class, FlowuiConfiguration::class])
@PropertySource(name = "ru.lood.totopbutton", value = ["classpath:/ru/lood/totopbutton/module.properties"])
@Import(ToTopButtonRegistration::class)
open class TtbConfiguration {

    @Bean("ttb_TtbViewControllers")
    open fun screens(applicationContext: ApplicationContext,
                     metadataReaderFactory: AnnotationScanMetadataReaderFactory
    ): ViewControllersConfiguration {
        return ViewControllersConfiguration(applicationContext, metadataReaderFactory).apply {
            basePackages = listOf("ru.lood.totopbutton")
        }
    }

    @Bean("ttb_TtbActions")
    open fun actions(applicationContext: ApplicationContext,
                     metadataReaderFactory: AnnotationScanMetadataReaderFactory
    ): ActionsConfiguration {
        return ActionsConfiguration(applicationContext, metadataReaderFactory).apply {
            basePackages = listOf("ru.lood.totopbutton")
        }
    }
}
