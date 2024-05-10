import {html} from "@polymer/polymer"
import {Button} from "@vaadin/button";
import {css, registerStyles} from "@vaadin/vaadin-themable-mixin";

/**
 * Use registerStyles instead of the `<style>` tag to make sure
 * that this CSS will override core styles of `vaadin-button`.
 */
registerStyles(
    'to-top-button',
    css`
        :host {
            background: transparent;
            color: var(--lumo-text-color);
            position: fixed;
            bottom: 50px;
            right: 20px;
            opacity: .2;
            cursor: pointer;
            line-height: 32px;
            text-decoration: none;
            box-sizing: border-box;
        }

        :host(:hover) {
            opacity: 0.7;
        }

        :host(.to-bottom) {
            transform: rotateX(180deg);
        }


        @media (max-width: 1309px) {
            :host {
                right: 10px;
            }
        }

        @media (max-width: 991px) {
            :host {
                opacity: .2;
                right: 5px;
            }
        }
    `,
    {moduleId: 'to-top-button-styles'},
);

class ToTopButton extends Button {

    private scrollableElement: Element | null | undefined = null;
    private topPositionToBack: number | undefined = undefined;

    static get is() {
        return "to-top-button"
    }

    static get template() {
        return html`
            <div class="vaadin-button-container">
                <span part="prefix" aria-hidden="true">
                    <slot name="prefix"></slot>
                </span>
                <span part="label">
                    <slot></slot>
                </span>
            </div>

            <slot name="tooltip"></slot>
        `;
    }

    static get properties() {
        return {
            ariaLabel: {
                type: String,
                value: 'To Top',
                reflectToAttribute: true,
            },
            scrollableElementId: {
                type: String,
                notify: true,
                observer: '_onScrollableElementIdChange'
            }
        }
    }

    constructor() {
        super();
        this.addEventListener('click', () => this.toTop())
        this.addEventListener("click", () => {
            const customEvent = new CustomEvent("to-top-scroll", {detail: {value: "test"}});
            this.dispatchEvent(customEvent)
        })
    }

    /**@protected */
    ready() {
        // @ts-ignore
        super.ready()
        let seId = this.getAttribute("scrollableElementId");
        if (!this.scrollableElement) {
            if (seId) {
                this.scrollableElement = document.getElementById(seId)
            } else {
                this.scrollableElement = document.getElementById("MainView")?.shadowRoot?.querySelector("[content]")
            }
            this.attacheScrollendEvent();
            console.log("to-top-button attache to ", this.scrollableElement)
        }
    }

    private attacheScrollendEvent() {
        this.scrollableElement?.addEventListener("scrollend", (event) => this.onScroll(event))

        this.hidden = true;
    }

    /**
     * @protected
     */
    toTop() {
        if (this.topPositionToBack == undefined) {
            this.topPositionToBack = this.scrollableElement?.scrollTop;
            this.scrollableElement?.scroll(0, 0);
            this.classList.add("to-bottom")
        } else {
            this.scrollableElement?.scroll(0, this.topPositionToBack | 0);
            this.topPositionToBack = undefined;
            this.classList.remove("to-bottom")
        }
    }

    /**
     * @protected
     * @param event
     */
    onScroll(event: Event) {

        if (this.scrollableElement?.scrollTop && this.scrollableElement?.scrollTop > 10) {
            //Показываем кнопку если пролистали вниз
            this.hidden = false;
            if (this.scrollableElement?.scrollTop && this.topPositionToBack && this.scrollableElement?.scrollTop >= this.topPositionToBack) {
                //сбрасываем сохраненную позицию и убираем класс-вращатель, если перелистали сохраненную позицию
                this.topPositionToBack = undefined
                this.classList.remove("to-bottom")
            }
        } else if (!this.topPositionToBack) {
            //Прячем кнопку если пролистали вверх и нет сохраненной позиции
            this.hidden = true
            this.classList.remove("to-bottom")
        }
    }

    /**
     * @protected
     */
    _onScrollableElementIdChange(idElement: string | undefined) {
        console.log("call _onScrollableElementIdChange ", idElement);
        if (idElement) {
            //this.setAttribute("scrollableElementId", idElement)
            this.scrollableElement = document.getElementById(idElement);
            this.topPositionToBack = undefined;
            this.attacheScrollendEvent();
        }
    }
}

customElements.define(ToTopButton.is, ToTopButton)

export {ToTopButton}
