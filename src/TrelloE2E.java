import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import files.data;


@Listeners(files.Listeners.class)
public class TrelloE2E extends files.Listeners {

	private static String boardID;
	private static String cardsAPI_ID;
	private static String listToDoId;
	private static String listInProgressId;
	private static String listDeployedId;
	private static String listDoneId;
	private static String listInTestingId;
	private static String listCompletedId;

	@BeforeClass
	public void setUp() {

		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RequestSpecification requestSpec = new RequestSpecBuilder()
				.setBaseUri("https://api.trello.com/1/")
				.addQueryParam("key", data.key())
				.addQueryParam("token", data.token())
				.setContentType("application/json").
				setAccept("*/*").build().log().all();
		
		RestAssured.requestSpecification = requestSpec;
	}

	@Test(priority = 0)
	public static void CreateBoards() throws InterruptedException {

//		 Base URL

//		 A. Create Board and verify response status code should be 200
//		 OK, assert board name matching with input data, permissionLevel should be
//		 ‘private’, viewType should be “Board” and enabled should be ‘true’
		
		Response response = given().queryParam("name", "Flip Test Engineer")
				.when().post("boards/")
				.then()
				.assertThat().statusCode(200)// verify response status code should be 200
				.body("name", equalTo("Flip Test Engineer"))
				.extract().response();
	
		JsonPath js = response.jsonPath();
		

		boardID = js.getString("id");
		String permissionLevel = js.getString("prefs.permissionLevel");
		String name	=	js.getString("name");
		String viewType = js.getString("prefs.switcherViews.viewType[0]");
		String enabled = js.getString("prefs.switcherViews.enabled[0]");

		// assert board name matching with input data, permissionLevel should be
		// ‘private’,
		// viewType should be “Board” and enabled should be ‘true’
		Assert.assertEquals(permissionLevel, "private");
		Assert.assertEquals(name, "Flip Test Engineer");
		Assert.assertEquals(viewType, "Board");
		Assert.assertEquals(enabled, "true");

//		Print 
		
		
		ExtentTest node4 = test.createNode("Create Boards");
		
		ExtentTest node3 = node4.createNode("Status Code");
		node3.log(Status.INFO, ""+response.getStatusCode());
		
		ExtentTest node1 = node4.createNode("Response Body");
		node1.log(Status.INFO,""+response.getBody().asPrettyString());

		ExtentTest node2 = node4.createNode("Assertion");
		node2.log(Status.INFO, "Board Name Is : " + name);
		node2.log(Status.INFO, "View Type Is : " + viewType);
		node2.log(Status.INFO, "Permission Level Is : " + permissionLevel);
		node2.log(Status.INFO, "Enabled Is : " + enabled);

	}

	@Test(priority = 1)
	public static void CreateList() {

//		B. Create the List on the Board created in previous test ,named
//		   ‘To-Do’, ‘In progress’, ‘Completed’, ‘In testing’, ‘Done’, ‘Deployed’ and verify name
//		   matching with input data, boardId, list is opened [“closed” : “false”]

		Response responseListToDo = given().queryParam("name", "To-Do")
				.queryParam("idBoard", boardID)
				.when().post("lists/").then()
				.log().all()
				.assertThat().statusCode(200)
				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name", equalTo("To-Do"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();
		
		//Parsing Json
		JsonPath jsListToDo = responseListToDo.jsonPath();
		listToDoId = jsListToDo.getString("id");
		String listNameToDoId = jsListToDo.getString("name");
		String listStatusClosedToDo = jsListToDo.getString("closed");

		// print name in Extent Report HTML matching with input data, boardId, list is opened [“closed” : “false”]
		ExtentTest nodeListTodo = test.createNode("Create List To Do");
		ExtentTest nodeToDo1 = nodeListTodo.createNode("Status Code");
		nodeToDo1.log(Status.INFO, ""+responseListToDo.getStatusCode());
		ExtentTest nodeToDo2 = nodeListTodo.createNode("Response Body");
		nodeToDo2.log(Status.INFO,""+responseListToDo.getBody().asPrettyString());

		ExtentTest nodeToDo3 = nodeListTodo.createNode("Assertion");
		nodeToDo3.log(Status.INFO, "List Id :" +listToDoId);
		nodeToDo3.log(Status.INFO, "List Name Is : " +listNameToDoId);
		nodeToDo3.log(Status.INFO, "View Type Is : " +listStatusClosedToDo);
	

		Response responseInProgress = given().queryParam("name", "InProgress")
				.queryParam("idBoard", boardID)
				.when().post("lists/")
				.then()
				.log().all()
				.assertThat().statusCode(200)
				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name", equalTo("InProgress"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();

		JsonPath jsListInProgress = responseInProgress.jsonPath();
		listInProgressId = jsListInProgress.getString("id");
		String listNameInProgress = jsListInProgress.getString("name");
		String listStatusClosedInProgress = jsListInProgress.getString("closed");

//		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		ExtentTest nodeInprog = test.createNode("Create List In Progress");
		
		ExtentTest nodeInprog1 = nodeInprog.createNode("Status Code");
		nodeInprog1.log(Status.INFO, ""+responseInProgress.getStatusCode());
		
		ExtentTest nodeInprog2 = nodeInprog.createNode("Response Body");
		nodeInprog2.log(Status.INFO,""+responseInProgress.getBody().asPrettyString());

		ExtentTest nodeInProg3 = nodeInprog.createNode("Assertion");
		nodeInProg3.log(Status.INFO, "List Id :" +listToDoId);
		nodeInProg3.log(Status.INFO, "List Name Is : " +listNameInProgress);
		nodeInProg3.log(Status.INFO, "View Type Is : " +listStatusClosedInProgress);
	
//		
		Response responseCompleted = given().queryParam("name", "Completed")
				.queryParam("idBoard", boardID)
				.when().post("lists/")
				.then()
				.log().all()
				.assertThat().statusCode(200)

//				// verify name matching with input data, boardId, list is opened [“closed” : “false”]
				.body("name", equalTo("Completed"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();
//		
		JsonPath jsListCompleted = responseCompleted.jsonPath();
		listCompletedId = jsListCompleted.getString("id");
		String listNameCompleted = jsListCompleted.getString("name");
		String listStatusClosedCompleted = jsListCompleted.getString("closed");

//		// print name matching with input data, boardId, list is opened [“closed” : “false”]
		ExtentTest nodeCmpltd = test.createNode("Create List Completed");
		ExtentTest nodeCmpltd1 = nodeCmpltd.createNode("Status Code");
		nodeCmpltd1.log(Status.INFO, ""+responseCompleted.getStatusCode());
		ExtentTest nodeCmpltd2 = nodeCmpltd.createNode("Response Body");
		nodeCmpltd2.log(Status.INFO,""+responseCompleted.getBody().asPrettyString());

		ExtentTest nodeCmpltd3 = nodeCmpltd.createNode("Assertion");
		nodeCmpltd3.log(Status.INFO, "List Id :" +listToDoId);
		nodeCmpltd3.log(Status.INFO, "List Name Is : " +listNameCompleted);
		nodeCmpltd3.log(Status.INFO, "View Type Is : " +listStatusClosedCompleted);

		Response responseInTesting = given().queryParam("name", "In Testing")
				.queryParam("idBoard", boardID)
				.when().post("lists/")
				.then()
				.log().all()
				.assertThat().statusCode(200)

				// verify name matching with input data, boardId, list is opened [“closed” :
				// “false”]
				.body("name", equalTo("In Testing"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();

		JsonPath jsListInTesting = responseInTesting.jsonPath();
		listInTestingId = jsListInTesting.getString("id");
		String listNameInTesting = jsListInTesting.getString("name");
		String listStatusClosedInTesting = jsListInTesting.getString("closed");

		// print name matching with input data, boardId, list is opened [“closed” :
		// “false”]
		ExtentTest nodeInTesting = test.createNode("Create List In Testing");
		
		ExtentTest nodeInTesting1 = nodeInTesting.createNode("Status Code");
		nodeInTesting1.log(Status.INFO, ""+responseInTesting.getStatusCode());
		
		ExtentTest nodeInTesting2 = nodeInTesting.createNode("Response Body");
		nodeInTesting2.log(Status.INFO,""+responseInTesting.getBody().asPrettyString());

		ExtentTest nodeInTesting3 = nodeInTesting.createNode("Assertion");
		nodeInTesting3.log(Status.INFO, "List Id :" +listToDoId);
		nodeInTesting3.log(Status.INFO, "List Name Is : " +listNameInTesting);
		nodeInTesting3.log(Status.INFO, "View Type Is : " +listStatusClosedInTesting);

		
		
		Response responseDone = given().queryParam("name", "Done")
				.queryParam("idBoard", boardID)
				.when().post("lists/")
				.then()
				.log().all()
				.assertThat().statusCode(200)

				// verify name matching with input data, boardId, list is opened [“closed” :
				// “false”]
				.body("name", equalTo("Done"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();

		JsonPath jsListDone = responseDone.jsonPath();
		listDoneId = jsListDone.getString("id");
		String listNameDone = jsListDone.getString("name");
		String listStatusClosedDone = jsListDone.getString("closed");

		// print name matching with input data, boardId, list is opened [“closed” :
		// “false”]
		ExtentTest nodeDone = test.createNode("Create List Done");
		
		ExtentTest nodeDone1 = nodeDone.createNode("Status Code");
		nodeDone1.log(Status.INFO, ""+responseDone.getStatusCode());
		
		ExtentTest nodeDone2 = nodeDone.createNode("Response Body");
		nodeDone2.log(Status.INFO,""+responseDone.getBody().asPrettyString());

		ExtentTest nodeDone3 = nodeDone.createNode("Assertion");
		nodeDone3.log(Status.INFO, "List Id :" +listToDoId);
		nodeDone3.log(Status.INFO, "List Name Is : " +listNameDone);
		nodeDone3.log(Status.INFO, "View Type Is : " +listStatusClosedDone);


		Response responseDeployed = given().queryParam("name","Deployed")
				.queryParam("idBoard", boardID)
				.when().post("lists/")
				.then()
				.log().all()
				.assertThat().statusCode(200)

				// verify name matching with input data, boardId, list is opened [“closed” :
				// “false”]
				.body("name", equalTo("Deployed"))
				.body("idBoard", equalTo(boardID))
				.body("closed", equalTo(false))
				.extract().response();

		JsonPath jsListDeployed = responseDeployed.jsonPath();
		listDeployedId = jsListDeployed.getString("id");
		String listNameDeployed = jsListDeployed.getString("name");
		String listStatusClosedDeployed = jsListDeployed.getString("closed");

		// Print
		ExtentTest nodeDplyd = test.createNode("Create List Deployed");
		
		ExtentTest nodeDplyd1 = nodeDplyd.createNode("Status Code");
		nodeDplyd1.log(Status.INFO, ""+responseDeployed.getStatusCode());
		
		ExtentTest nodeDplyd2 = nodeDplyd.createNode("Response Body");
		nodeDplyd2.log(Status.INFO,""+responseDeployed.getBody().asPrettyString());

		ExtentTest nodeDplyd3 = nodeDplyd.createNode("Assertion");
		nodeDplyd3.log(Status.INFO, "List Id :" +listToDoId);
		nodeDplyd3.log(Status.INFO, "List Name Is : " +listNameDeployed);
		nodeDplyd3.log(Status.INFO, "View Type Is : " +listStatusClosedDeployed);
	}

	@Test(priority = 2)
	public void createCards() {
		
////	C. Write a test to create a Card in the list ‘To-Do’ created in the last test and verify
////	   list name matching the input data, assert ListId and BoardId in response.

		Response responseNewCards = given().queryParam("name", "Automation API")
				.queryParam("idList", listToDoId)
				.when().post("cards/")
				.then()
				.log().all()
				.assertThat().statusCode(200)
				.body("name", equalTo("Automation API")) // verify list name matching the input data response
				.body("idList", equalTo(listToDoId)) // assert ListId in response
				.body("idBoard", equalTo(boardID))// assert BoardId in response
				.extract().response();

		JsonPath jsCardsAPI = responseNewCards.jsonPath();
		cardsAPI_ID = jsCardsAPI.getString("id");
		String cardsAPI = jsCardsAPI.getString("name");
		String cardsStatusCloseAPI = jsCardsAPI.getString("closed");

//	// Print
		ExtentTest nodeCards = test.createNode("Create Cards");
		
		ExtentTest nodeCards1 = nodeCards.createNode("Status Code");
		nodeCards1.log(Status.INFO, ""+responseNewCards.getStatusCode());
		
		ExtentTest nodeCards2 = nodeCards.createNode("Response Body");
		nodeCards2.log(Status.INFO,""+responseNewCards.getBody().asPrettyString());

		ExtentTest nodeCards3 = nodeCards.createNode("Assertion");
		nodeCards3.log(Status.INFO, "List Id :" +listToDoId);
		nodeCards3.log(Status.INFO, "Cards Name : " +cardsAPI);
		nodeCards3.log(Status.INFO, "Status Closed : " +cardsStatusCloseAPI);

	}

	@Test(priority = 3)
	public void movingCardsBetweenCreatedList() {
////	 D. Write a test to move the card created in the last test to the ‘In-progress’,
////    ‘Completed’, ‘In testing’, ‘Done’, ‘Deployed’ List, assert cardId and listId when
////     moving to the next list within the same board.

		Response responseMoveCardsToInProgress = given().pathParam("id", cardsAPI_ID)
				.queryParam("boardId", boardID)
				.queryParam("idList", listInProgressId)
				.when().put("cards/{id}")
				.then()
				.log().all().extract().response();
		
		JsonPath jsMoveCardsToInProgress = responseMoveCardsToInProgress.jsonPath();
		String cardsId1 = jsMoveCardsToInProgress.getString("id");
		String actualBoardId1 = jsMoveCardsToInProgress.getString("idBoard");
		String currentListId1 = jsMoveCardsToInProgress.getString("idList");

////assert cardId and listId when moving to the next list within the same board.
		Assert.assertEquals(cardsId1, cardsAPI_ID);
		Assert.assertEquals(actualBoardId1, boardID);
		Assert.assertEquals(currentListId1, listInProgressId);

////print cardId and listId when moving to the next list within the same board.
		ExtentTest nodeCardsToIP = test.createNode("Move Cards To In Progress");
		
		ExtentTest  nodeCardsToIP1 =  nodeCardsToIP.createNode("Status Code");
		nodeCardsToIP1.log(Status.INFO, ""+responseMoveCardsToInProgress.getStatusCode());
		
		ExtentTest  nodeCardsToIP2 =  nodeCardsToIP.createNode("Response Body");
		nodeCardsToIP2.log(Status.INFO,""+responseMoveCardsToInProgress.getBody().asPrettyString());

		ExtentTest  nodeCardsToIP3 =  nodeCardsToIP.createNode("Assertion");
		nodeCardsToIP3.log(Status.INFO, "Board Id :" +actualBoardId1);
		nodeCardsToIP3.log(Status.INFO, "Cards Id : " +cardsId1);
		nodeCardsToIP3.log(Status.INFO, "Current List : " +currentListId1);
		
		
		Response responseMoveCardsToInTesting = given().pathParam("id", cardsAPI_ID)
				.queryParam("boardId", boardID)
				.queryParam("idList", listInTestingId)
				.when().put("cards/{id}")
				.then()
				.log().all()
				.extract().response();

		JsonPath jsMoveCardsToInTesting = responseMoveCardsToInTesting.jsonPath();
		String cardsId2 = jsMoveCardsToInTesting.getString("id");
		String actualBoardId2 = jsMoveCardsToInTesting.getString("idBoard");
		String currentListId2 = jsMoveCardsToInTesting.getString("idList");
//
////assert cardId and listId when moving to the next list within the same board.
		Assert.assertEquals(cardsId2, cardsAPI_ID);
		Assert.assertEquals(actualBoardId2, boardID);
		Assert.assertEquals(currentListId2, listInTestingId);
//
////print cardId and listId when moving to the next list within the same board.
		ExtentTest nodeCardsToIT = test.createNode("Move Cards To In Testing");
		
		ExtentTest  nodeCardsToIT1 =  nodeCardsToIT.createNode("Status Code");
		nodeCardsToIT1.log(Status.INFO, ""+responseMoveCardsToInTesting.getStatusCode());
		
		ExtentTest  nodeCardsToIT2 =  nodeCardsToIT.createNode("Response Body");
		nodeCardsToIT2.log(Status.INFO,""+responseMoveCardsToInTesting.getBody().asPrettyString());
		
		ExtentTest  nodeCardsToIT3 =  nodeCardsToIT.createNode("Assertion");
		nodeCardsToIT3.log(Status.INFO, "Board Id :" +actualBoardId2);
		nodeCardsToIT3.log(Status.INFO, "Cards Id : " +cardsId2);
		nodeCardsToIT3.log(Status.INFO, "Current List : " +currentListId2);

		Response responseMoveCardsToDone = given().pathParam("id", cardsAPI_ID)
				.queryParam("boardId", boardID)
				.queryParam("idList", listDoneId)
				.when().put("cards/{id}").then()
				.log().all()
				.extract().response();

		JsonPath jsMoveCardsToDone = responseMoveCardsToDone.jsonPath();
		String cardsId3 = jsMoveCardsToDone.getString("id");
		String actualBoardId3 = jsMoveCardsToDone.getString("idBoard");
		String currentListId3 = jsMoveCardsToDone.getString("idList");

//assert cardId and listId when moving to the next list within the same board.
		Assert.assertEquals(cardsId3, cardsAPI_ID);
		Assert.assertEquals(actualBoardId3, boardID);
		Assert.assertEquals(currentListId3, listDoneId);

//print cardId and listId when moving to the next list within the same board.
		ExtentTest nodeCardsToDN = test.createNode("Move Cards To Done");
		
		ExtentTest  nodeCardsToDN1 =  nodeCardsToDN.createNode("Status Code");
		nodeCardsToDN1.log(Status.INFO, ""+responseMoveCardsToDone.getStatusCode());
		
		ExtentTest  nodeCardsToDN2 =  nodeCardsToIT.createNode("Response Body");
		nodeCardsToDN2.log(Status.INFO,""+responseMoveCardsToDone.getBody().asPrettyString());
		
		ExtentTest  nodeCardsToDN3 =  nodeCardsToIT.createNode("Assertion");
		nodeCardsToDN3.log(Status.INFO, "Board Id :" +actualBoardId3);
		nodeCardsToDN3.log(Status.INFO, "Cards Id : " +cardsId3);
		nodeCardsToDN3.log(Status.INFO, "Current List : " +currentListId3);

		
		Response responseMoveCardsToDeployed = given().pathParam("id", cardsAPI_ID)
				.queryParam("boardId", boardID)
				.queryParam("idList", listDeployedId)
				.when().put("cards/{id}")
				.then()
				.log().all()
				.extract().response();

		JsonPath jsMoveCardsToDeployed = responseMoveCardsToDeployed.jsonPath();
		String cardsId4 = jsMoveCardsToDeployed.getString("id");
		String actualBoardId4 = jsMoveCardsToDeployed.getString("idBoard");
		String currentListId4 = jsMoveCardsToDeployed.getString("idList");

//assert cardId and listId when moving to the next list within the same board.
		Assert.assertEquals(cardsId4, cardsAPI_ID);
		Assert.assertEquals(actualBoardId4, boardID);
		Assert.assertEquals(currentListId4, listDeployedId);

//print cardId and listId when moving to the next list within the same board.
		ExtentTest nodeCardsToDplyd = test.createNode("Move Cards To Deployed");
		
		ExtentTest  nodeCardsToDplyd1 =  nodeCardsToDplyd.createNode("Status Code");
		nodeCardsToDplyd1.log(Status.INFO, ""+responseMoveCardsToDeployed.getStatusCode());
		
		ExtentTest  nodeCardsToDplyd2 =  nodeCardsToDplyd.createNode("Response Body");
		nodeCardsToDplyd2.log(Status.INFO,""+responseMoveCardsToDeployed.getBody().asPrettyString());
		
		ExtentTest  nodeCardsToDplyd3 =  nodeCardsToDplyd.createNode("Assertion");
		nodeCardsToDplyd3.log(Status.INFO, "Board Id :" +actualBoardId4);
		nodeCardsToDplyd3.log(Status.INFO, "Cards Id : " +cardsId4);
		nodeCardsToDplyd3.log(Status.INFO, "Current List : " +currentListId4);
		

		Response responseMoveCardsToCompleted = given().pathParam("id", cardsAPI_ID)
				.queryParam("boardId", boardID)
				.queryParam("idList", listCompletedId)
				.when().put("cards/{id}").then()
				.log().all().extract().response();

		JsonPath jsMoveCardsToCompleted = responseMoveCardsToCompleted.jsonPath();
		String cardsId5 = jsMoveCardsToCompleted.getString("id");
		String actualBoardId5 = jsMoveCardsToCompleted.getString("idBoard");
		String currentListId5 = jsMoveCardsToCompleted.getString("idList");

//assert cardId and listId when moving to the next list within the same board.
		Assert.assertEquals(cardsId5, cardsAPI_ID);
		Assert.assertEquals(actualBoardId5, boardID);
		Assert.assertEquals(currentListId5, listCompletedId);

//Print cardId and listId when moving to the next list within the same board.
		ExtentTest nodeCardsToCmpltd = test.createNode("Move Cards To Completed");
		
		ExtentTest  nodeCardsToCmpltd1 =  nodeCardsToCmpltd.createNode("Status Code");
		nodeCardsToCmpltd1.log(Status.INFO, ""+responseMoveCardsToCompleted.getStatusCode());
		
		ExtentTest  nodeCardsToCmpltd2 =  nodeCardsToCmpltd.createNode("Response Body");
		nodeCardsToCmpltd2.log(Status.INFO,""+responseMoveCardsToCompleted.getBody().asPrettyString());
		
		ExtentTest  nodeCardsToCmpltd3 =  nodeCardsToCmpltd.createNode("Assertion");
		nodeCardsToCmpltd3.log(Status.INFO, "Board Id :" +actualBoardId5);
		nodeCardsToCmpltd3.log(Status.INFO, "Cards Id : " +cardsId5);
		nodeCardsToCmpltd3.log(Status.INFO, "Current List : " +currentListId5);
	}

//// Delete Boards

	@Test(priority = 4)
	public void DeleteBoards() {
		
		Response delete = given().pathParam("id", boardID)
				.when().delete("boards/{id}")
				.then()
				.log().all()
				.assertThat().statusCode(200).extract().response();
		

		
		ExtentTest nodeDelete = test.createNode("Delete");
		ExtentTest  nodeDltd1 =  nodeDelete.createNode("Status Code");
		nodeDltd1.log(Status.INFO, ""+delete.getStatusCode());
		

	}

}
