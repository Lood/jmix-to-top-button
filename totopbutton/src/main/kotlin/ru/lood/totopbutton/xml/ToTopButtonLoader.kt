package ru.lood.totopbutton.xml

import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader
import ru.lood.totopbutton.component.ToTopButton

class ToTopButtonLoader : AbstractComponentLoader<ToTopButton>() {
    override fun loadComponent() {
        loadBoolean(element, "autofocus", resultComponent::autofocus::set)
        loadString(element, "scrollableElementId", resultComponent::scrollableElementId::set)

        componentLoader().loadText(resultComponent, element)
        componentLoader().loadTitle(resultComponent, element, context)
        componentLoader().loadTooltip(resultComponent, element)
        componentLoader().loadWhiteSpace(resultComponent, element)
        componentLoader().loadEnabled(resultComponent, element)
        componentLoader().loadClassNames(resultComponent, element)
        componentLoader().loadFocusableAttributes(resultComponent, element)
        componentLoader().loadThemeNames(resultComponent, element)
        componentLoader().loadSizeAttributes(resultComponent, element)

    }

    override fun createComponent(): ToTopButton {
        return factory.create(ToTopButton::class.java)
    }
}