To top button component.
This component is a button for scrolling the content up.

The button appears in the lower right part of the scrollable element.

When clicking on the button, the component remembers the position of the screen, and when clicked again, it returns to the remembered position (provided that the user has not manually scrolled further than the saved position)

## Using

Add the repository and dependency to build.gradle:

```groovy
...
repositories {
    maven {
        url 'https://maven.pkg.github.com/lood/jmix-to-top-button'
    }
}
...
dependencies {
  ...
  imp  
  ...
}
...
```

