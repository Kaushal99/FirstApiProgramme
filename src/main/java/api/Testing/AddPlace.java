package api.Testing;


import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



public class AddPlace extends Utils
{       //  Auto- generate method stub
    // validate if Add Place API is working as expected

    public static void main(String[] args)
    {
        //creating object of Add place class
        //Payload payload = new Payload();

        // Api testing or Rest assure from Rahul Shetty video using his own URl
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Rest Assure only supports Given, When, Then format
        given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.addPlace())  // extending body from return value passed in Utils class

                .when().post("maps/api/place/add/json")

                //Assertion
                .then().log().all().assertThat().statusCode(200)

                // adding body now to validate scope = app from our programme
                .body("scope",equalTo("APP")) // need to import hamcrest


                // now for this programme we need to validate the server header, so will pass it to validate that
            .header("Server","Apache/2.4.18 (Ubuntu)");




    }
}
