package com.example.dicegame.controller;

import com.example.dicegame.beans.Player;
import com.example.dicegame.enums.Message;
import com.example.dicegame.services.DiceGameService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dice-game")
public class DiceController {

    @Autowired
    private DiceGameService diceGameService;

    /**
     * Welcome note
     * @return
     */
    @GetMapping("/")
    public String hello(){

        return "Welcome to Dice Game!";
    }

    /**
     * start-game
     * @param minPlayers
     * @return
     */
    @PostMapping("/start-game")
    public ResponseEntity<String> startGame(@RequestParam String minPlayers){
        try {
            return ResponseEntity.ok(diceGameService.startGame(Integer.parseInt(minPlayers)));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(Message.INVALID_NUMBER_OF_USERS.getMessage());
        }
    }

    /**
     * add player in game
     * @param playerName
     * @return
     */
    @PostMapping("/add-player")
    public ResponseEntity<String> addPlayer(@RequestParam String playerName){
        return ResponseEntity.ok(diceGameService.addPlayer(playerName));
    }

    /**
     * roll dice
     * @param playerName
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/roll", produces = "application/json")
    public ResponseEntity<String> roll(@RequestParam String playerName) throws Exception {
        return ResponseEntity.ok( diceGameService.play(playerName));
    }

    /**
     * stop-game
     * @return
     */
    @GetMapping("/stop-game")
    public ResponseEntity<String> stopGame(){
        diceGameService.stopGame();
        return ResponseEntity.ok("");
    }
}
