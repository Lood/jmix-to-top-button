package ru.lood.autoconfigure.totopbutton

import ru.lood.totopbutton.TtbConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Import

@AutoConfiguration
@Import(TtbConfiguration::class)
open class TtbAutoConfiguration {
}

