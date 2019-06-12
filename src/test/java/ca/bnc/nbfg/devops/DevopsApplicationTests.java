package ca.bnc.nbfg.devops;

import ca.bnc.nbfg.devops.model.Event;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevopsApplicationTests {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createEvents_success() {
        //setup
        Event event = new Event();
        event.setDescription("description of event");
        event.setStartDate(LocalDateTime.now());
        event.setEndDate(LocalDateTime.now().plusDays(3));

        //test and assert
        with()
            .contentType("application/json")
            .body(event)
        .when()
            .post("/events")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("description", equalTo("description of event"));
    }

}
