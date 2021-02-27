package com.example.dicegame.core;

import com.example.dicegame.beans.Player;
import com.example.dicegame.enums.GameStatus;
import com.example.dicegame.enums.Message;
import com.example.dicegame.enums.PlayerStatus;

import java.util.Map;
import java.util.Optional;

public class DiceBoard {

    private static int MAX_USERS = 4;
    private static Node CURRENT_USER;
    private static GameStatus status;
    private static DiceBoard board;
    private static CircularLinkedList playerList;

    private DiceBoard(int maxUsers) {
        this.MAX_USERS = maxUsers;
        status = GameStatus.PRE_START;
        playerList = new CircularLinkedList(maxUsers);
        board = this;
    }


    public static CircularLinkedList getPlayerList() {
        return playerList;
    }

    public static void resetGame(){
        getPlayerList().deletePlayers();
        status = GameStatus.DONE;
        CURRENT_USER = null;
    }

    public static String startGame(int maxUsers){
        if(maxUsers < 1){
            return Message.MINIMUM_REQUIRED_USERS.getMessage();
        }
        if(GameStatus.RUNNING != status ){
            new DiceBoard(maxUsers);
            return Message.GAME_ALREADY_STARTED.getMessage();
        } else {
            return Message.NEW_GAME_STARTED.getMessage();
        }
    }

    public static Node getCurrentUser(){
        if(CURRENT_USER == null) CURRENT_USER = playerList.getHead();
        return CURRENT_USER;
    }

    public static void setNextUser(){

        if(CURRENT_USER != null && CURRENT_USER.nextNode != null)
            CURRENT_USER = CURRENT_USER.nextNode;
    }

    public static DiceBoard getDiceBoard(){


        return board;
    }

    public static Node getPlayer(String playerName){

        return getPlayerList().findNode(playerName);
    }

    public static String addPlayer(String playerName){
            Player player = new Player(playerName);

        return playerList.addNode(player);
    }

    public String play(String playerName, int rollScore) throws Exception {

        Node playerNode = this.playerList.findNode(playerName);

        if(playerNode == null) return Message.PLAYER_NOT_FOUND.getMessage();

        if(! playerNode.value.getName().equalsIgnoreCase(getCurrentUser().value.getName()))
            return Message.INVALID_USER_TURN.getMessage() + getCurrentUser().value.getName();

        return processRoll(playerNode, rollScore);

    }

    private String processRoll(Node playerNode, int rollScore)
    {
        Player player = playerNode.value;
        if(player.getStatus() == PlayerStatus.IDLE &&
                rollScore == 6){
            player.setStatus(PlayerStatus.PLAYING);
            return Message.START_PLAY_AGAIN.getMessage()
                    +" : "+ player.getName() +" Score: 0";
        }else if(player.getStatus() == PlayerStatus.IDLE){
            setNextUser();
            return getCurrentUser().value.getName();
        } else if(player.getStatus() == PlayerStatus.PLAYING
                && rollScore == 4){
            int userScore = getCurrentUser().value.getScore() - 4;
            userScore = userScore > 0 ? userScore : 0;
            getCurrentUser().value.setScore(userScore);
            setNextUser();
            return Message.ROLL_SCORE_FOUR.getMessage() + userScore;

        } else if(player.getStatus() == PlayerStatus.PLAYING
                && rollScore != 4){
            int userScore = player.getScore() + rollScore;
            if(userScore >= 25){
                String msg = Message.GAME_WIN.getMessage()
                        +", Player: "+ getCurrentUser().value.getName()
                        +", Score: "+ userScore;
                finishGame();
                return msg;
            } else if(rollScore == 6) {
                player.setScore(userScore);
                return Message.START_PLAY_AGAIN.getMessage()
                        +" : "+ player.getName() +" Score: "+ userScore;
            } else if(rollScore != 6) {
                player.setScore(userScore);
                return player.getName() +" Score: "+ userScore;
            }
        }

        return "";
    }

    private void finishGame(){
        getPlayerList().deletePlayers();
        this.resetGame();
    }
}
