package com.example.dicegame;

import com.example.dicegame.controller.DiceController;
import com.example.dicegame.enums.Message;
import com.example.dicegame.services.DiceGameService;
import com.example.dicegame.services.DiceWebServiceClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class DiceGameApplicationTests {

	@Autowired
	DiceController diceController;

	@MockBean
	DiceWebServiceClient diceWebServiceClient;

	@Before
	void setup(){
		diceWebServiceClient = new DiceWebServiceClient();
	}
	@Test
	void contextLoads() {
	}

	/**
	 * test to start game with valid number of players
	 */
	@Test
	void testStartGameWithValidPlayers(){
		diceController.stopGame();
		ResponseEntity<String> response = diceController.startGame("-1");
		Assert.assertEquals(Message.MINIMUM_REQUIRED_USERS.getMessage(), response.getBody());
	}

	/**
	 * test player can join game.
	 */
	@Test
	void testAddPlayer(){
		diceController.stopGame();
		diceController.startGame("4");
		ResponseEntity<String> response = diceController.addPlayer("Mumtaz");
		Assert.assertEquals(Message.PLAYER_ADDED.getMessage(), response.getBody());
	}

	/**
	 * Test: user asked to play again if he got 6
	 * Test: once user hit six only then he is in Running state
	 * @throws Exception
	 */
	@Test
	void testPlayerStartScore() throws Exception{
		diceController.stopGame();
		diceController.startGame("1");
		diceController.addPlayer("Mumtaz");
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("6"));
		ResponseEntity<String> response = diceController.roll("Mumtaz");
		String expected = Message.START_PLAY_AGAIN.getMessage()
				+" : Mumtaz Score: 0";
		Assert.assertEquals(expected, response.getBody());
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("5"));
		response = diceController.roll("Mumtaz");
		expected = "Mumtaz Score: 5";
		Assert.assertEquals(expected, response.getBody());
	}


	/**
	 * test valid right user playing
	 * @throws Exception
	 */
	@Test
	void testValidPlayerTurn() throws Exception{
		diceController.stopGame();
		diceController.startGame("2");
		diceController.addPlayer("Michael");
		diceController.addPlayer("Mumtaz");
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("6"));
		ResponseEntity<String> response = diceController.roll("Michael");
		String expected = Message.START_PLAY_AGAIN.getMessage()
				+" : Michael Score: 0";
		Assert.assertEquals(expected, response.getBody());
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("5"));
		response = diceController.roll("Mumtaz");
		expected = Message.INVALID_USER_TURN.getMessage()
				+"Michael";
		Assert.assertEquals(expected, response.getBody());
	}


	/**
	 * Test the game win scenario
	 * @throws Exception
	 */
	@Test
	void testPlayerWin() throws Exception{
		diceController.stopGame();
		diceController.startGame("2");
		diceController.addPlayer("Michael");
		diceController.addPlayer("Mumtaz");
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("6"));
		ResponseEntity<String> response = diceController.roll("Michael");

		diceController.roll("Michael");
		diceController.roll("Michael");
		diceController.roll("Michael");
		diceController.roll("Michael");
		response = diceController.roll("Michael");
		String expected = Message.GAME_WIN.getMessage()
				+", Player: Michael"
				+", Score: 30";
		Assert.assertEquals(expected, response.getBody());
	}

	/**
	 * Test the game for score 4 scenario
	 * @throws Exception
	 */
	@Test
	void testPlayerRollScoreFour() throws Exception{
		diceController.stopGame();
		diceController.startGame("2");
		diceController.addPlayer("Michael");
		diceController.addPlayer("Mumtaz");
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("6"));
		ResponseEntity<String> response = diceController.roll("Michael");

		diceController.roll("Michael");
		Mockito.when(
				diceWebServiceClient.getDiceOutcome())
				.thenReturn(new Integer("4"));

		response = diceController.roll("Michael");
		String expected = Message.ROLL_SCORE_FOUR.getMessage() + "2";
		Assert.assertEquals(expected, response.getBody());
	}

	/**
	 * Test duplicate users should not be allowed
	 */
	@Test
	void testDuplicatePlayer(){
		diceController.stopGame();
		diceController.startGame("4");
		ResponseEntity<String> response = diceController.addPlayer("Mumtaz");
		ResponseEntity<String> response1 = diceController.addPlayer("Mumtaz");
		Assert.assertEquals(Message.USER_ALREADY_IN_LIST.getMessage(),
				response1.getBody());
	}

	/**
	 * test case to add multiple users to the game
	 */
	@Test
	void testManyAddPlayer(){
		diceController.stopGame();
		diceController.startGame("1");
		ResponseEntity<String> response = diceController.addPlayer("Mumtaz");
		ResponseEntity<String> response1 = diceController.addPlayer("Michael");
		Assert.assertEquals(Message.PLAYER_ADDED.getMessage(), response1.getBody());

	}

}
