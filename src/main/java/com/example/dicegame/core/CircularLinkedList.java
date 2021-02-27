package com.example.dicegame.core;

import com.example.dicegame.beans.Player;
import com.example.dicegame.enums.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Mumtaz Ali Sarki
 * CurcularLinkedList.java
 */
public class CircularLinkedList {
    final Logger LOGGER = LoggerFactory.getLogger(CircularLinkedList.class);

    private Node head = null;
    private Node tail = null;
    private int count;
    private int maxPlayerAllowed;

    /**
     *
     * @param maxPlayers
     */
    public CircularLinkedList(int maxPlayers){
        this.maxPlayerAllowed = maxPlayers;
    }

    /**
     *
     * @return head node
     */
    public Node getHead(){
        return head;
    }

    /**
     *
     * @param player
     * @return
     */
    public String addNode(Player player) {
        if(count == maxPlayerAllowed) return Message.GAME_PLAYERS_FULL.getMessage();
        if(this.findNode(player.getName()) != null)
            return Message.USER_ALREADY_IN_LIST.getMessage();
        Node newNode = new Node(player);

        if (head == null) {
            head = newNode;
        } else {
            tail.nextNode = newNode;
        }

        tail = newNode;
        tail.nextNode = head;

        return Message.PLAYER_ADDED.getMessage();
    }

    /**
     *
     * @param playerName
     * @return
     */
    public boolean containsNode(String playerName) {

        Node currentNode = head;

        if (head == null) {
            return false;
        } else {
            do {
                if (currentNode.value.getName().equalsIgnoreCase(playerName)) {
                    return true;
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
            return false;
        }
    }

    /**
     *
     * @param playerName
     * @return
     */
    public Node findNode(String playerName) {

        Node currentNode = head;

        if (head == null) {
            return null;
        } else {
            do {
                if (currentNode.value.getName().equalsIgnoreCase(playerName)) {
                    return currentNode;
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
            return null;
        }
    }

    /**
     * delete all players of game
     */
    public void deletePlayers() {

        head = null;
        tail = null;
    }

    /**
     *
     */
    public void traverseList() {

        Node currentNode = head;

        if (head != null) {
            do {
                LOGGER.info(currentNode.value.getName() + " ");
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }
    }

}

/**
 * class: Node
 */
class Node {

    Player value;
    Node nextNode;

    public Node(Player value) {
        this.value = value;
    }

}