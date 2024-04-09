import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import files.data;
import files.reUsable;

@Listeners(files.Listeners.class)
public class TrelloE2E {
	
	private static String boardID;
	private static String cardsAPI_ID;
	private static String listToDoId;
	private static String listInProgressId; 
	private static String listDeployedId;
	private static String listDoneId;
	private static String listInTestingId;
	private static String listCompletedId;
	
	@Test(priority=0)
	public static void CreateBoards() throws InterruptedException
	{
		
//		 
//		 Base URL
		RestAssured.baseURI = "https://api.trello.com/1/";
		 
		 
//		 A. Create Board and verify response status code should be 200
//		 OK, assert board name matching with input data, permissionLevel should be
//		 ‘private’, viewType should be “Board” and enabled should be ‘true’
		 
		 String responseBoard = given().queryParam("name","Flip Test Engineer").queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().post("boards/")
		.then().log().all().log().all().assertThat().statusCode(200)
		.body("name",equalTo("Flip Test Engineer"))//	verify response status code should be 200
		.extract().asString();
		

	
		JsonPath js = reUsable.extractJson(responseBoard);
		

		boardID = js.getString("id");
		String permissionLevel = js.getString("prefs.permissionLevel");
		String name	=	js.getString("name");
		String viewType = js.getString("prefs.switcherViews.viewType[0]");
		String enabled = js.getString("prefs.switcherViews.enabled[0]");
		
		//assert board name matching with input data, permissionLevel should be ‘private’, 
		//viewType should be “Board” and enabled should be ‘true’
		Assert.assertEquals(permissionLevel, "private");
		Assert.assertEquals(name,"Flip Test Engineer");
		Assert.assertEquals(viewType,"Board");
		Assert.assertEquals(enabled,"true");
		
//		Print 
		Reporter.log("Permission Level : "+permissionLevel);
		Reporter.log("Board Id : "+boardID);
		Reporter.log("Board Name : "+name);
		Reporter.log("View Type Is : "+viewType);
		Reporter.log("Enabled : "+enabled);
		
		



	
	}
	
	
	@Test(priority=1)
	public static void CreateList() {
		
//		B. Create the List on the Board created in previous test ,named
//		   ‘To-Do’, ‘In progress’, ‘Completed’, ‘In testing’, ‘Done’, ‘Deployed’ and verify name
//		   matching with input data, boardId, list is opened [“closed” : “false”]
		
		String responseListToDo = given().queryParam("name","To-Do").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
				
//				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("To-Do"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
//		
		JsonPath jsListToDo = reUsable.extractJson(responseListToDo);
		 listToDoId = jsListToDo.getString("id");
		String listNameToDoId= jsListToDo.getString("name");
		String listStatusClosedToDo = jsListToDo.getString("closed");
		
//		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		Reporter.log("List Id To Do : "+listToDoId);
		Reporter.log("List Name : " +listNameToDoId);
		Reporter.log("List Status Closed :"+listStatusClosedToDo);
		
		
		String responseInProgress = given().queryParam("name","InProgress").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
				
//				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("InProgress"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
	
		JsonPath jsListInProgress = reUsable.extractJson(responseInProgress);
		 listInProgressId = jsListInProgress.getString("id");
		String listNameInProgress = jsListInProgress.getString("name");
		String listStatusClosedInProgress= jsListInProgress.getString("closed");
		
//		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		Reporter.log("List Id In Progress : "+listInProgressId);
		Reporter.log("List Name : "+listNameInProgress);
		Reporter.log("List Status Closed : "+listStatusClosedInProgress);
//		
		String responseCompleted = given().queryParam("name","Completed").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
				
//				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("Completed"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
//		
		JsonPath jsListCompleted = reUsable.extractJson(responseCompleted);
		listCompletedId = jsListCompleted.getString("id");
		String listNameCompleted = jsListCompleted.getString("name");
		String listStatusClosedCompleted = jsListCompleted.getString("closed");
		
//		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		Reporter.log("List Id Completed : "+listCompletedId);
		Reporter.log("List Name :"+listNameCompleted);
		Reporter.log("List Status Closed : "+listStatusClosedCompleted);
		
		String responseInTesting = given().queryParam("name","In Testing").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
		
				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("In Testing"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
		
		JsonPath jsListInTesting = reUsable.extractJson(responseInTesting);
	 listInTestingId = jsListInTesting.getString("id");
		String listNameInTesting = jsListInTesting.getString("name");
		String listStatusClosedInTesting = jsListInTesting.getString("closed");
	
		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		Reporter.log("List Id In Testing :"+listInTestingId);
		Reporter.log("List Name : "+listNameInTesting);
		Reporter.log("List Status Closed : "+listStatusClosedInTesting);
		
		String responseDone = given().queryParam("name","Done").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
				
				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("Done"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
		
		JsonPath jsListDone = reUsable.extractJson(responseDone);
		listDoneId = jsListDone.getString("id");
		String listNameDone = jsListDone.getString("name");
		String listStatusClosedDone = jsListDone.getString("closed");
		
		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		Reporter.log("List Id Done : "+listDoneId);
		Reporter.log("List Name : " +listNameDone);
		Reporter.log("List Status Closed : "+listStatusClosedDone);
		
		String responseDeployed = given().queryParam("name","Deployed").queryParam("key",data.key())
				.queryParam("token",data.token()).queryParam("idBoard",boardID)
				.header("Content-Type", "application/json")
				.header("Accept","*/*")
				.when().post("lists/")
				.then().log().all()
				.assertThat().statusCode(200)
				
				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name",equalTo("Deployed"))
				.body("idBoard",equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().asString();
		
		
		JsonPath jsListDeployed = reUsable.extractJson(responseDeployed);
	 listDeployedId = jsListDeployed .getString("id");
		 String listNameDeployed = jsListDeployed .getString("name");
		String listStatusClosedDeployed = jsListDeployed .getString("closed");
		
		// Print
		Reporter.log("List Id Deployed : "+listDeployedId);
		Reporter.log("List Name : "+listNameDeployed);
		Reporter.log("List Status Closed : "+listStatusClosedDeployed);
		
		
	}
	
	
	@Test(priority=2)
	public void createCards() {
////	C. Write a test to create a Card in the list ‘To-Do’ created in the last test and verify
////	   list name matching the input data, assert ListId and BoardId in response.

	String responseNewCards = given().queryParam("name","Automation API").queryParam("key",data.key())
			.queryParam("token",data.token()).queryParam("idList",listToDoId)
			.header("Content-Type", "application/json")
			.header("Accept","*/*")
			.when().post("cards/")
			.then().log().all()
			.assertThat().statusCode(200)
			.body("name",equalTo("Automation API")) // verify list name matching the input data in response
			.body("idList",equalTo(listToDoId)) // assert ListId in response
			.body("idBoard",equalTo(boardID))// assert BoardId in response
			.extract().asString();

	JsonPath jsCardsAPI = reUsable.extractJson(responseNewCards);
	cardsAPI_ID = jsCardsAPI.getString("id");
	String cardsAPI= jsCardsAPI.getString("name");
	String cardsStatusCloseAPI = jsCardsAPI.getString("closed");
	
//	// Print
	Reporter.log("Cards Id Automation API : "+cardsAPI_ID);
	Reporter.log("Cards Name : "+cardsAPI);
	Reporter.log("Cards Status Closed : "+cardsStatusCloseAPI);


	}
	
	
	
	@Test(priority=3)
	public void movingCardsBetweenCreatedList() {
////	 D. Write a test to move the card created in the last test to the ‘In-progress’,
////    ‘Completed’, ‘In testing’, ‘Done’, ‘Deployed’ List, assert cardId and listId when
////     moving to the next list within the same board.

String responseMoveCardsToInProgress = given().pathParam("id", cardsAPI_ID)
		.queryParam("boardId", boardID)
		.queryParam("idList", listInProgressId)
		.queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().put("cards/{id}")
		.then().log().all()
		.extract().asString();
JsonPath jsMoveCardsToInProgress =reUsable.extractJson(responseMoveCardsToInProgress);
String cardsId1 = jsMoveCardsToInProgress.getString("id");
String actualBoardId1 = jsMoveCardsToInProgress.getString("idBoard");
String currentListId1 = jsMoveCardsToInProgress.getString("idList");

////assert cardId and listId when moving to the next list within the same board.
Assert.assertEquals(cardsId1, cardsAPI_ID);
Assert.assertEquals(actualBoardId1,boardID);
Assert.assertEquals(currentListId1,listInProgressId);

////print cardId and listId when moving to the next list within the same board.
Reporter.log("Cards Id Automation API : "+cardsId1);
Reporter.log("Actual Board Id : "+actualBoardId1);
Reporter.log("Current List Id : "+currentListId1);

String responseMoveCardsToInTesting = given().pathParam("id", cardsAPI_ID)
		.queryParam("boardId", boardID)
		.queryParam("idList", listInTestingId)
		.queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().put("cards/{id}")
		.then().log().all()
		.extract().asString();

JsonPath jsMoveCardsToInTesting = reUsable.extractJson(responseMoveCardsToInTesting);
String cardsId2 = jsMoveCardsToInTesting.getString("id");
String actualBoardId2 = jsMoveCardsToInTesting.getString("idBoard");
String currentListId2 = jsMoveCardsToInTesting.getString("idList");
//
////assert cardId and listId when moving to the next list within the same board.
Assert.assertEquals(cardsId2, cardsAPI_ID);
Assert.assertEquals(actualBoardId2,boardID);
Assert.assertEquals(currentListId2,listInTestingId);
//
////print cardId and listId when moving to the next list within the same board.
Reporter.log("Cards Id Automation API : "+cardsId2);
Reporter.log("Actual Board Id : "+actualBoardId2);
Reporter.log("Current List Id : "+currentListId2);

String responseMoveCardsToDone = given().pathParam("id", cardsAPI_ID)
		.queryParam("boardId", boardID)
		.queryParam("idList", listDoneId)
		.queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().put("cards/{id}")
		.then().log().all()
		.extract().asString();

JsonPath jsMoveCardsToDone = reUsable.extractJson(responseMoveCardsToDone);
String cardsId3 = jsMoveCardsToDone.getString("id");
String actualBoardId3 = jsMoveCardsToDone.getString("idBoard");
String currentListId3 = jsMoveCardsToDone.getString("idList");

//assert cardId and listId when moving to the next list within the same board.
Assert.assertEquals(cardsId3, cardsAPI_ID);
Assert.assertEquals(actualBoardId3,boardID);
Assert.assertEquals(currentListId3,listDoneId);

//print cardId and listId when moving to the next list within the same board.
Reporter.log("Cards Id Automation API : "+cardsId3);
Reporter.log("Actual Board Id : "+actualBoardId3);
Reporter.log("Current List Id : "+currentListId3);


String responseMoveCardsToDeployed = given().pathParam("id", cardsAPI_ID)
		.queryParam("boardId", boardID)
		.queryParam("idList", listDeployedId)
		.queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().put("cards/{id}")
		.then().log().all()
		.extract().asString();

JsonPath jsMoveCardsToDeployed = reUsable.extractJson(responseMoveCardsToDeployed);
String cardsId4 = jsMoveCardsToDeployed.getString("id");
String actualBoardId4 = jsMoveCardsToDeployed.getString("idBoard");
String currentListId4 = jsMoveCardsToDeployed.getString("idList");


//assert cardId and listId when moving to the next list within the same board.
Assert.assertEquals(cardsId4, cardsAPI_ID);
Assert.assertEquals(actualBoardId4,boardID);
Assert.assertEquals(currentListId4,listDeployedId);


//print cardId and listId when moving to the next list within the same board.
Reporter.log("Cards Id Automation API : "+cardsId4);
Reporter.log("Actual Board Id : "+actualBoardId4);
Reporter.log("Current List Id : "+currentListId4);

String responseMoveCardsToCompleted = given().pathParam("id", cardsAPI_ID)
		.queryParam("boardId", boardID)
		.queryParam("idList", listCompletedId)
		.queryParam("key",data.key())
		.queryParam("token",data.token())
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().put("cards/{id}")
		.then().log().all()
		.extract().asString();

JsonPath jsMoveCardsToCompleted = reUsable.extractJson(responseMoveCardsToCompleted);
String cardsId5 = jsMoveCardsToCompleted.getString("id");
String actualBoardId5 = jsMoveCardsToCompleted.getString("idBoard");
String currentListId5 = jsMoveCardsToCompleted.getString("idList");


//assert cardId and listId when moving to the next list within the same board.
Assert.assertEquals(cardsId5, cardsAPI_ID);
Assert.assertEquals(actualBoardId5,boardID);
Assert.assertEquals(currentListId5,listCompletedId);

//Print cardId and listId when moving to the next list within the same board.
Reporter.log("Cards Id Automation API : "+cardsId5);
Reporter.log("Actual Board Id : "+actualBoardId5);
Reporter.log("Current List Id : "+currentListId5);
	}



//// Delete Boards

	@Test(priority=4)
	public void DeleteBoards() {
		given().pathParam("id", boardID)
		.queryParam("listId", listInProgressId)
		.queryParam("key",data.key())
		.queryParam("token",data.token()).queryParam("idList",listToDoId)
		.header("Content-Type", "application/json")
		.header("Accept","*/*")
		.when().delete("boards/{id}")
		.then().log().all()
		.assertThat().statusCode(200);
			
	}

	
	
	

}
