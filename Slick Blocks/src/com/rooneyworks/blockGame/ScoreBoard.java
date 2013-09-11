package com.rooneyworks.blockGame;

import java.io.*;
import java.util.ArrayList;
import com.rooneyworks.blockGame.Score;

public class ScoreBoard {

	private ArrayList<Score> highScores = null;	//element zero is the top score
	private String fileName = "HighScores.score";
	/**
	 * Creates a new ScoreBoard at the location unless one exists at the specified file name, in which case it loads that ScoreBoard
	 * @param size is the number of scores to be contained in the highScore list
	 * @param filename is the name of the file to store the scoreboard in.
	 */
	public ScoreBoard(int size, String fileName) {
		highScores = new ArrayList<Score>();
		this.fileName = fileName;
		
		try {
			load();
		} catch(IOException e) {
			for(int i = 0; i < size; i++)
				highScores.add(new Score("", 0));
			save();
			e.printStackTrace();
		}
		

	}
	
	/**
	 * Constructor - loads an existing scoreboard.
	 * 
	 * @param fileName The filename of the scoreboard to be loaded
	 */
	public ScoreBoard(String fileName) throws IOException{
		this.fileName = fileName;
		this.load();
	}
	
	/**
	 * Constructor - loads an existing scoreboard from the default file name (HighScores.score)
	 * 
	 */
	public ScoreBoard() throws IOException{
		this.load();
	}
	
	/**
	 * Sets the file name that this scoreboard will save to
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	/**
	 * Tests the score passed to see if it is higher than the lowest score on the board
	 * @param score The score to be tested
	 * @return boolean whether or not the score would be listed on the board were it submitted.
	 */
	
	public boolean testScore(int score) {
		
		if(score >= highScores.get(highScores.size() - 1).getScore()) {
			return true;
		} else {
		
			
		return false;
		}
	}
	/**
	 * Adds a score and player name to the score board.
	 * @param name String, the name of the player.
	 * @param score int, the player's score.
	 * @return Returns false if the score wasn't added to the board, true if it was added.
	 */
	public boolean addScore(String name, int score) {
		
		boolean returns = false;
		
		for(int i = 0; i<highScores.size(); i++){
			if(score >= highScores.get(i).getScore()) {
				highScores.add(i, new Score(name, score));
				highScores.remove(highScores.size() - 1);
				returns = true;
				this.organize();
				this.save();
				break;
			}
		}
		
		return returns;
		
		
	}
	/**
	 * Adds a score to the scoreboard, if the score is stored in the form of a pre-existing "Score" object
	 * @param score Score to be added
	 * @return boolean representing whether the score was actually added to the board or not.
	 */
	public boolean addScore(Score score) {
		
		boolean returns = false;
		
		int scoreNum = score.getScore();
		
		for(int i = 0; i<highScores.size(); i++){
			if(scoreNum >= highScores.get(i).getScore()) {
				highScores.add(i, score);
				highScores.remove(highScores.size() - 1);
				returns = true;
				this.organize();
				this.save();
				break;
			}
		}
		
		return returns;
		
		
	}
	/**
	 * Saves the scoreboard to file. The file it saves to is specified by the setFileName method or is set when the scoreboard is constructed.
	 * 
	 */
	private void save() 	{
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			OutputStream buffer = new BufferedOutputStream(out);
			ObjectOutput output = new ObjectOutputStream(buffer);
		
			try {
				output.writeObject(highScores);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				output.close();
			}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Reads an existing scoreboard from file. The file is defined by the setFileName method or the constructor.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void load() throws IOException {
		FileInputStream in = null;
		
			in = new FileInputStream(fileName);
			InputStream inBuffer = new BufferedInputStream(in);
			ObjectInput input = new ObjectInputStream(inBuffer);
			try {
				highScores = (ArrayList<Score>)input.readObject();
			
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
				
			} finally {
				input.close();
			}
		
	}
	/**
	 * This method will (**SHOULD**) order the scores if for some reason they fall out of order
	 * 
	 * Right now, it just prints "Gaack..." if the scores are disorganized, because at the time I wrote this I lacked the skill to
	 * actually implement a way to organize the scores. The scores are added in the correct places, and so the organize method *Should* not
	 * be needed.
	 */
	public void organize() {
		int currentNum;
		//int insertAt = -1;
		boolean reOrganize = false;
		for(int i = 0; i< highScores.size(); i++){
			currentNum = highScores.get(i).getScore();
			
			for(int j = i + 1; j < highScores.size(); j++) {
				if(currentNum < highScores.get(j).getScore()) {
					//the scores are out of order...
					//insertAt = j;
					reOrganize = true;
				}
			}
			if(reOrganize) {
				System.out.println("Gaack...");
			}
			
			
			
		}
	}
	/**
	 * returns a string representation of the scoreboard. Scores and names are separated by a \t
	 */
	public String toString() {
		String output = "";
		for(int i = 0; i < highScores.size(); i++ ){
			output = output + highScores.get(i).toString() + "\t";
		}
		return output;
	}
	
	
}
