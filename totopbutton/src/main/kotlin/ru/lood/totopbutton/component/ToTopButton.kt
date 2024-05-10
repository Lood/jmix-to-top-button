package ru.lood.totopbutton.component


import com.vaadin.flow.component.*
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.shared.HasTooltip
import com.vaadin.flow.dom.Element
import io.jmix.flowui.kit.component.HasTitle
import java.util.function.Predicate
import kotlin.jvm.optionals.getOrNull

@Tag("to-top-button")
@JsModule("./src/toTopButton/to-top-button.ts")
class ToTopButton(
    icon: Component? = VaadinIcon.ARROW_UP.create(),
    text: String? = null
) : Component(), ClickNotifier<ToTopButton>,
    Focusable<ToTopButton>, HasTheme, HasEnabled, HasSize, HasStyle, HasText, HasTooltip, HasTitle {
    companion object {
        const val TO_TOP_SCROLL_EVENT = "to-top-scroll"
        const val SCROLLABLE_ELEMENT_ID_PROPERTY = "scrollableElementId"
    }

    override fun getText() = element.children.filter { it.isTextNode }.findFirst().getOrNull()?.text
    override fun setText(value: String?) {
        removeAll(*getNonTextNodes())
        if (!value.isNullOrEmpty()) {
            element.appendChild(Element.createText(value))
        }
        updateThemeAttribute()
    }

    private var iconComponent: Component? = null

    val icon get() = iconComponent
    fun setIcon(icon: Component?) {
        if (icon != null && icon.element.isTextNode) throw IllegalArgumentException("Text node can't be used as an icon.")
        val oldIconComponent = iconComponent
        if (oldIconComponent != null) remove(oldIconComponent)
        iconComponent = icon
        if (icon != null) {
            add(icon)
            updateIconSlot()
        }
        updateThemeAttribute()
    }

    var scrollableElementId: String?
        get() = element.getProperty(SCROLLABLE_ELEMENT_ID_PROPERTY)
        set(value) {
            element.setProperty(SCROLLABLE_ELEMENT_ID_PROPERTY, value)
        }

    init {
        setIcon(icon)
        if (!text.isNullOrEmpty())
            this.text = text
    }

    private fun updateIconSlot() {
        iconComponent?.element?.setAttribute("slot", "prefix")
    }

    /**
     * Adds the given components as children of this component.
     * <p>
     * Note that using this method together with convenience methods, such as
     * {@link #setText(String)} and {@link #setIcon(Component)}, may have
     * unexpected results, e.g. in the order of child elements and the result of
     * {@link #getText()}.
     *
     * @param components the components to add
     */
    private fun add(vararg components: Component) {
        components.forEach { component: Component ->
            element.appendChild(component.element)
        }
    }

    /**
     * Removes the given child components from this component.
     *
     * @param components The components to remove.
     * @throws IllegalArgumentException if any of the components is not a child of this component.
     */
    private fun remove(vararg components: Component) {
        components.forEach { component: Component ->
            if (element.equals(component.element.parent)) {
                component.element.removeAttribute("slot")
                element.removeChild(component.element)
            } else {
                throw IllegalArgumentException("The given component ($component) is not a child of this component")
            }
        }
    }

    /**
     *  Removes all contents from this component except elements in
     *  {@code exclusion} array. This includes child components, text content as
     *  well as child elements that have been added directly to this component
     *  using the {@link Element} API.
     */
    private fun removeAll(vararg exclusion: Element?) {
        val toExclude = exclusion.toSet()
        val filter = toExclude::contains
        element.children.filter(Predicate.not(filter)).forEach {
            it.removeAttribute("slot")
        }
        element.removeAllChildren()
        element.appendChild(*exclusion)
    }

    private fun getNonTextNodes() =
        element.children.filter { el -> !el.isTextNode }.toArray { arrayOfNulls<Element>(it) }

    private fun updateThemeAttribute() {
        val childCount = element.children.filter { it.isTextNode || it.tag != "vaadin-tooltip" }.count()
        if (childCount == 1L && iconComponent != null) {
            themeNames.add("icon")
        } else {
            themeNames.remove("icon")
        }
    }

    var autofocus: Boolean
        get() = element.getProperty("autofocus", false)
        set(value) {
            element.setProperty("autofocus", value)
        }

    fun addToTopScrollEvent(listener: ComponentEventListener<ToTopScrollEvent>) =
        addListener(ToTopScrollEvent::class.java, listener)

    @DomEvent(TO_TOP_SCROLL_EVENT)
    class ToTopScrollEvent(
        source: ToTopButton,
        fromClient: Boolean,
        @EventData("event.detail.value") val value: String
    ) : ComponentEvent<ToTopButton>(source, fromClient)
}