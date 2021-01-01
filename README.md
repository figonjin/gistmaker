### The test comes with a blank account provided

---
Dependencies:
- Java 11
- Maven
---

Coverage
The tests are configured to, in a sequential order, create a gist, validate it contains all the necessary fields and promptly remove it.

---
To change the account variables, modify the following file:
>src/test/resources/gh.properites

Where:\
`gh.username`= git username\
`gh.password` = personal access token ID (make sure to give `gist` permissions when creating the token)

However sample git credentials are included in the test

---
To run the tests simply run `maven  test` from the projects folder or run the tests from `src/test/java/org.local.gistman/GistTests.java` using the IDE of your choice

---
Known issues:
- The tests are hardcoded to run sequentially due to the nature of the given task, and therefore will break if ran out of order.
However if the creation test was used as a base to prepare for every other, and the removal test grabbed gist ID's from server-side instead, it would be perfectly possible to reconfigure the entire suite to be modular instead.
