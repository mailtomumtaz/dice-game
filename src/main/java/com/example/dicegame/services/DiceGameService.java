package com.example.dicegame.services;

import com.example.dicegame.beans.Player;
import com.example.dicegame.core.DiceBoard;
import com.example.dicegame.enums.Message;
import com.example.dicegame.enums.PlayerStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiceGameService {

    @Autowired
    DiceWebServiceClient client;

    /**
     * start game
     * @param maxUsers
     * @return
     */
    public String startGame(int maxUsers){

        return DiceBoard.startGame(maxUsers);
    }

    /**
     * Play the game
     * @param playerName
     * @return
     * @throws Exception
     */
    public String play(String playerName) throws Exception {

        return DiceBoard.getDiceBoard().play(playerName, client.getDiceOutcome());
    }

    /**
     * finish game
     * @return
     */
    private String finishGame(){
        DiceBoard.getDiceBoard().resetGame();
        return Message.GAME_FINISH.getMessage();
    }

    /**
     * addPlayer
     * @param playerName
     * @return
     */
    public String addPlayer(String playerName) {
        return DiceBoard.getDiceBoard().addPlayer(playerName);
    }

    /**
     * stopGame
     */
    public void stopGame(){
        if(DiceBoard.getDiceBoard() != null)
            DiceBoard.getDiceBoard().resetGame();
    }
}
