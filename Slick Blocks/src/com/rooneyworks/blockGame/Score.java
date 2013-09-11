package com.rooneyworks.blockGame;

import java.io.Serializable;

public class Score implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5940306174802720347L;
	private int score;
	private String player;
	
	/**
	 * Constructs an object of type Score containing the name of the player and their score.
	 * @param name The name of the player who earned the score.
	 * @param score The score of the player.
	 */
	
	public Score(String name, int score) {
		this.player = name;
		this.score = score;
		
		
	}
	
	/**
	 * Gets the score stored in this Score object.
	 * @return an int containing the score
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the player who holds the score stored in this Score object
	 * @return a String containing the player's name
	 */
	public String getPlayer() {
		return player;
		
	}
	
	/**
	 * Sets the score stored in this object
	 * @param score the score of the player
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Sets the player name stored in this object
	 * @param name the name of the player
	 */
	public void setName(String name) {
		this.player = name;
	}
	
	/**
	 * @return a string representation of the score in the form "Billy\t52"
	 */
	public String toString() {
		
		return player + "\t" + score;
	}
	
	
}
