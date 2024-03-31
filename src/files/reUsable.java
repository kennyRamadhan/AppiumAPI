package files;

import io.restassured.path.json.JsonPath;

public class reUsable {
	
	
	public static JsonPath extractJson(String response) {
		
		JsonPath js = new JsonPath(response);
		return js;
		
	}

}
