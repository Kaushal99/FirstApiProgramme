package api.Testing;

import io.restassured.path.json.JsonPath;

public class Utils
{


    // reusable method for Json path  for response
    public static JsonPath rawJson(String response)
    {
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath;


    }

}
