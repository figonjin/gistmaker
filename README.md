### The test comes with a blank account provided

---
Dependencies:
- Java 11
- Maven

---
To change the account variables, modify the following file:
>src/test/resources/gh.properites

Where:\
`gh.username`= git username\
`gh.password` = personal access token ID (make sure to give `gist` permissions when creating the token)

---
To run the tests simply run `maven  test` from the projects folder or run the tests from `src/test/java/org.local.gistman/GistTests.java` using the IDE of your choice
