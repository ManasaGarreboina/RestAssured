package FilesForBasicRestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReUsableMethods {
    public static JsonPath rawToJson(String response)
    {
        JsonPath js =new JsonPath(response);
        return js;
    }
    public static JsonPath rawToJson1(Response r)
	{ 
		String respon=r.asString();
		JsonPath x=new JsonPath(respon);
		System.out.println(respon);
		return x;
	}
}
