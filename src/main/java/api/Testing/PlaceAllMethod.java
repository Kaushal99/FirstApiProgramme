package api.Testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaceAllMethod extends Utils
{    //  Auto- generate method stub
    // validate if Add Place API is working as expected
    //Add Place-> Update place with New Address-> Get place to validate if New address is present in response

    public static void main(String[] args)
    {
        //creating object of Add place class
       // PlaceAllMethod placeMethod = new PlaceAllMethod();

        // Api testing or Rest assure from Rahul Shetty video using his own URl
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Rest Assure only supports Given, When, Then format

        //making given into String variable for reusability of or to get the code of other values from this responce
        String getResponse = given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.addPlace()).  // extending body from return value passed in Utils class

                when().post("maps/api/place/add/json").

                //Assertion
                then().log().all().assertThat().statusCode(200).

                // adding body now to validate scope = app from our programme
                body("scope",equalTo("APP")). // need to import hamcrest

                // now for this programme we need to validate the server header, so will pass it to validate that
                header("Server","Apache/2.4.18 (Ubuntu)").

                //here we will extract json responce from console and store it in one String varialbe in string
                extract().response().asString();

                // printing the response
                System.out.println(getResponse);

                //Now we will create a JSon path class which is already in built
                JsonPath jsonPath = Utils.rawJson(getResponse); //for passing the Json

        // now we need to copy Place Id values and make it reusable by passing it in string so can call it anytime
                String placeID = jsonPath.getString("place_id");

                System.out.println(placeID); // this will abstract only value of Place Id  and print


        // Update Place -> again will write Given,When and THen

        String newAddress = "7,Kensingonw willa, watford,UK"; // making variable for new address for usability

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").
                body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").
                when().put("maps/api/place/update/json").
                then().assertThat().log().all().statusCode(200).
                body("msg",equalTo("Address successfully updated"));

        //Get Method
        String getPlaceResponce = given().log().all().queryParam("key","qaclick123").
                queryParam("place_id",placeID).

                when().get("maps/api/place/get/json").
                then().assertThat().log().all().statusCode(200). // using another method for assertation
                extract().response().asString();

                JsonPath jsonPath1 = Utils.rawJson(getPlaceResponce);

                String actualAddress = jsonPath1.getString("address");
                System.out.println(actualAddress);

                //so now we need to have one more assertion to compare address of update place and get place
                //so now to write assertion we need to relay on java as APi given when then noe we need to have junit,Testng
                //cucumberJunit, TestNg
                //need to add TestNg repository to our project

                Assert.assertEquals(actualAddress,newAddress);

    }

}
