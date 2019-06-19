package ca.bnc.nbfg.devops;

import ca.bnc.nbfg.devops.model.Event;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DevopsApplicationTests {

    private static final String EVENTS_PATH = "/events";

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createEvents_success() {
        //setup
        Event event = buildEvent();

        //test and assert
        given()
                .contentType("application/json")
                .body(event)
                .when()
                .post(EVENTS_PATH)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("description", equalTo("description of event"));
    }

    @Test
    public void getEvents_success() {
        //setup
        Event event1 = postEvent();
        Event event2 = postEvent();

        //test
        Event[] events =
                given()
                        .when()
                        .get(EVENTS_PATH)
                        .as(Event[].class);

        //assert
        assertThat(events).hasSize(2);
        assertThat(events).containsAnyOf(event1, event2);
    }

    @Test
    public void cancelEvent_success() {
        //setup
        Event eventToCancel = postEvent();

        //test and assert
        given()
                .pathParam("id", eventToCancel.getId())
                .when()
                .put(EVENTS_PATH + "/{id}/cancel")
                .then()
                .statusCode(200).body(isEmptyOrNullString());
    }

    @Test
    public void deleteCanceledEvent_success() {
        //setup
        Event eventToDelete = postEvent();

        RequestSpecification requestSpecification =
                given().pathParam("id", eventToDelete.getId());

        //cancel the event
        requestSpecification
                .when()
                .put(EVENTS_PATH + "/{id}/cancel");

        //test and assert
        requestSpecification
                .when()
                .delete(EVENTS_PATH + "/{id}")
                .then()
                .statusCode(200).body(isEmptyOrNullString());

        given()
                .when()
                .get(EVENTS_PATH)
                .then()
                .statusCode(200)
                .and()
                .body("", hasSize(0));
    }

    @Test
    public void event_not_deleted_when_not_canceled() {
        //setup
        Event eventToDelete = postEvent();

        RequestSpecification requestSpecification =
                given().pathParam("id", eventToDelete.getId());

        //test and assert
        requestSpecification
                .when()
                .delete(EVENTS_PATH + "/{id}")
                .then()
                .statusCode(200).body(isEmptyOrNullString());

        given()
                .when()
                .get(EVENTS_PATH)
                .then()
                .statusCode(200)
                .and()
                .body("", hasSize(1));
    }

    private Event postEvent() {
        Event event = buildEvent();
        return given()
                .contentType("application/json")
                .body(event)
                .when()
                .post(EVENTS_PATH)
                .as(Event.class);
    }

    private Event buildEvent() {
        Event event = new Event();
        event.setDescription("description of event");
        event.setStartDate(LocalDateTime.now());
        event.setEndDate(LocalDateTime.now().plusDays(3));
        return event;
    }

}
