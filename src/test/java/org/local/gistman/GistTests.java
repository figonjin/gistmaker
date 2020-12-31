package org.local.gistman;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.local.gistman.helpers.ArrayPopulator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(OrderAnnotation.class)
public class GistTests {

    private static final File jsonBody = new File("src/test/resources/body.json");
    private static String gistID;

    @BeforeAll
    public static void credentialsSetter() throws IOException {
        FileReader reader = new FileReader("src/test/resources/gh.properties");

        Properties p = new Properties();
        p.load(reader);

        RestAssured.baseURI = p.getProperty("gh.url");

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(p.getProperty("gh.username"));
        authScheme.setPassword(p.getProperty("gh.password"));
        authentication = authScheme;

    }

    @Order(1)
    @Test
    public void makeGist() {
        gistID =
            given().
                    accept("application/vnd.github.v3+json").
                    contentType(JSON).
                    body(jsonBody).
                    when().
                    post(baseURI+"/gists").
                    then().
                    statusCode(201).
                    extract().
                    path("id");
    }

    @Order(2)
    @Test
    public void checkGist() throws IOException {
        ArrayList<String> jsonFieldValidation = ArrayPopulator.populateArray("src/test/resources/jsonFieldsValidation.txt");
        Response response =
        get(baseURI+"/gists").
                then().
                statusCode(200).
                extract().
                response();

        List<Map<String, String>> jsonResponse = response.jsonPath().getList("$");
        if (jsonResponse.isEmpty()) {
            Assertions.fail("No gists found on target account");
        }
        for (int i=0; i < jsonResponse.size(); i++) {
            for (int j=0; j < jsonFieldValidation.size(); j++) {
                assertThat(jsonResponse.get(i), hasKey(jsonFieldValidation.get(j)));
                assertThat(String.valueOf(jsonResponse.get(i).get(jsonFieldValidation.get(j))), notNullValue());
            }
        }
    }

    @Order(3)
    @Test
    public void removeGist() {
        given().
                pathParam("gistID", gistID).
                when().
                delete(baseURI+"/gists/{gistID}").
                then().
                statusCode(204);
    }
}
