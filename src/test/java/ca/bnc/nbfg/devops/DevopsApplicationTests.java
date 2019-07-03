package ca.bnc.nbfg.devops;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.EventRepository;
import ca.bnc.nbfg.devops.service.EventService;
import io.restassured.RestAssured;
import io.restassured.internal.http.URIBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.Socket;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DevopsApplicationTests {

    private static final String EVENTS_PATH = "/events";
    private static final String GUESTS_PATH = "/guests";

    private static final LocalDateTime FROM_DATE = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime TO_DATE = LocalDateTime.now().plusDays(2);

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

    @Test
    public void getEventsByPeriod() {
        Event event = postEvent();
        RequestSpecification requestSpecification =
                given().pathParam("fromDate", "2019-07-02T12:02:00").pathParam("toDate", "2019-07-04T12:02:00");

        requestSpecification.contentType("application/json" + "/" + FROM_DATE + "/" + TO_DATE).then().statusCode(200).and().body(notNullValue());
    }

    @Test
    public void getMyEventsByPeriod() {
        Event event = postEvent();
        Guest guest = new Guest();
        guest.setEmail("test@test.com");
        guest.setFirstName("toto");
        guest.setLastName("tata");
        inviteGuest(event ,guest);

        RequestSpecification requestSpecification =
                given().pathParam("email" , "test@test.com").pathParam("fromDate", "2019-07-02T12:02:00").pathParam("toDate", "2019-07-04T12:02:00");

        requestSpecification.when().get(EVENTS_PATH + "/{email}/{fromDate}/{toDate}").then().statusCode(200).body(notNullValue());

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
        return new Event(LocalDateTime.now(),LocalDateTime.now().plusDays(3),"title", "description of event");
    }

    private void inviteGuest(Event event, Guest guest) {
        Long id = event.getId();
        List<Guest> guestList = Arrays.asList(guest);

        RequestSpecification requestSpecification =
                given().contentType("application/json").pathParam("id" ,event.getId()).body(guestList);

        requestSpecification.when().post( EVENTS_PATH + "/{id}/guests");

    }
}
