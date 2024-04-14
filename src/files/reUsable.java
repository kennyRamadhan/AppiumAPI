package files;

import io.restassured.path.json.JsonPath;


public class reUsable {
	
	
	public static JsonPath extractJson(String responseBoard) {
		
		JsonPath js = new JsonPath(responseBoard);
		return js;
		
	}

}
