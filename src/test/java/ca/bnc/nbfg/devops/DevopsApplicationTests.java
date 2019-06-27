package ca.bnc.nbfg.devops;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.Guest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
        // TODO: remove comment when assert fixed
       // assertThat(events).containsAnyOf(event1., event2);
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
    public void getEventsByGuest_SUCCESS(){

       //setup
        Event event = postEvent();
    //    Event eventUpdated = inviteGuest(event.getId(), "test@test.com");

        //invite guest
        Guest guest = new Guest();
        guest.setEmail("guest@exemple.com");
        List<Guest> guestList = Arrays.asList(guest);
        event.setGuests(guestList);
       // String path = EVENTS_PATH + "/" + eventUpdated.getGuests().get(0).getEmail()+ "/guests";
        String path = EVENTS_PATH + "/" + event.getGuests().get(0).getEmail()+ "/guests";
         given()
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body(isEmptyOrNullString());
        //assert
        //Assert.assertEquals(listEvents.size() , 1);
        //Assert.assertEquals(listEvents.get(0).getGuests().get(0).getEmail(), event.getGuests().get(0).getEmail());
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

    private Guest buildGuest(String email) {
        Guest guest = new Guest();
        guest.setLastName("last name");
        guest.setFirstName("first name");
        guest.setEmail(email);
        return guest;
    }

    private Event inviteGuest(Long eventId, String email) {
        String path = EVENTS_PATH + "/" + eventId.toString()+ "/guests";
        Guest guest = buildGuest(email);
         return  given()
                .contentType("application/json")
                .body(guest)
                .when()
                .post(path)
                .as(Event.class);

    }
}
