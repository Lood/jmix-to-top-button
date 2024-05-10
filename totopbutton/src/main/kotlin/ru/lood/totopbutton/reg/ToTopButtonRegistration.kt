package ru.lood.totopbutton.reg

import io.jmix.flowui.sys.registration.ComponentRegistration
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.lood.totopbutton.component.ToTopButton
import ru.lood.totopbutton.xml.ToTopButtonLoader

@Configuration
open class ToTopButtonRegistration {

    @Bean
    open fun toTopButton(): ComponentRegistration {
        return ComponentRegistrationBuilder.create(ToTopButton::class.java)
            .withComponentLoader("toTopButton", ToTopButtonLoader::class.java)
            .build()
    }
}