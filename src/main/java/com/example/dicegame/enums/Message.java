package com.example.dicegame.enums;

public enum Message {

    USER_ALREADY_IN_LIST("Player already registered."),
    GAME_ALREADY_STARTED("Game already started. Please wait."),
    GAME_PLAYERS_FULL("Can't join. Game already full of users."),
    NEW_GAME_STARTED("New game initialized, please add players to start."),
    PLAYER_ADDED("Player added."),
    MINIMUM_REQUIRED_USERS("Minimum one player required."),
    PLAYER_NOT_FOUND("Player not found."),
    START_PLAY_AGAIN("Its 6, your score started, again it your turn."),
    ROLL_SCORE_FOUR("Its 4, you score reduced to:"),
    PLAY_AGAIN("Its 6, again your turn."),
    GAME_WIN("You are winner."),
    GAME_FINISH("Game finished."),
    INVALID_USER_TURN("Its not your turn, its turn for: "),
    INVALID_NUMBER_OF_USERS("Invalid number of users provided");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
