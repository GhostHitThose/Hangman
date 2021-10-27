# Hangman

### Target Includes The Latest Successful Developer Build


Build with
```
mvn clean
mvn package
```


# Info

Added shade plugin and removed jar plugins. Shaded jar can run outside of target folder because it includes dependencies in the jar. The unshaded version won't allow for run outside of the target folder.
One issue with the shaded jar is that it is significantly larger due to it incorporating dependency libs within the jar itself.
