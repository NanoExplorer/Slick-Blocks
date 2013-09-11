package com.rooneyworks.blockGame;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.AngelCodeFont;

public class MainMenuState extends BasicGameState {
	//INITIAIZING VARIABLES! __________________________________________________________________________________________
	int stateID = -1;			//contains the state identifier
	Image background = null;	//background image
	Image startGame = null;		//start game menu button
	Image exitGame = null;		//exit menu button

	
	private static int menuX = 410;	//x position of the menu buttons
    private static int menuY = 160;	//y position of the top menu button
 
    float startGameScale = 1;	//scale of the start game button, used for the button to grow when moused over
    float exitScale = 1;		//same as above, for exit button
    float scaleStep = 0.0001f;	//how fast the scale changes
	
	String scoreBoard = "";		//contains the scoreboard to be written to the screen
	String scoreBoardNames = "";
	String scoreBoardScores = "";
	
	ScoreBoard board = null;	//contains the scoreboard for the game
	
	Music music = null;
	
	AngelCodeFont highScoreFont = null;	//font to write the scoreboard on the screen with
	Color myOrange = null;				//color of highScoreFont
	
	boolean initialized = false;		//Makes sure certain unstable statements are executed only once
    
    
	public MainMenuState(int stateID) {
		this.stateID = stateID;// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		
		
		background = new Image("res/background.jpg");			//load background image
		Image menuOptions = new Image("res/menuOptions.png");	//load menu options
		
		startGame = menuOptions.getSubImage(0, 0, 377, 71);		//get the start game part of the menu options image
		exitGame = menuOptions.getSubImage(0, 71, 377, 71);		//get the exit game part of the menu options image
		board = new ScoreBoard(10, "res/scoreboard.board");		//make a new scoreboard to hold the high scores

		highScoreFont = new AngelCodeFont("res/fnt/NewFutura.fnt", "res/fnt/NewFutura_0.png");		//load the font
		myOrange = new Color(0xF24C00);			//set the color of myOrange
		
		
		scoreBoard = board.toString();		//make the scoreboard string file
		String[] scoreBoardArray = scoreBoard.split("\t", 20);
		

		
		if(!initialized) {			//when these statements haven't executed yet, execute them
			initialized = true;		//then make sure they don't execute again
			
			for(int i = 0; i < 20; i++) {	 //MAKE SURE THIS IS ONLY EXECUTED ONCE!!! (or else it becomes a memory leak) 
											// (Solved by putting it in with the sound)
				
				if( i%2 != 0) {
					scoreBoardScores = scoreBoardScores + scoreBoardArray[i] + "\n";
				} else {
					scoreBoardNames = scoreBoardNames + scoreBoardArray[i] + "\n";
				}
			}
			
			
		
			music = new Music("res/themeSong.ogg");
		
			music.loop();
		
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		background.draw(0, 0);							//draw the background image to the screen
		startGame.draw(menuX, menuY, startGameScale);	//draw the start game button to the screen, at the specified x and y and scale
		exitGame.draw(menuX, menuY + 80, exitScale);	//same as above for exit game button
		
		highScoreFont.drawString(25, 300, scoreBoardNames, myOrange);	//draw scoreBoard to screen
		highScoreFont.drawString(250, 300, scoreBoardScores, myOrange);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) //This method runs once every "tick"
			throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();		//load the input object
		
		int mouseX = input.getMouseX();			//mouse's x value
		int mouseY = input.getMouseY();			//mouse's y value
		
		boolean insideStart = false;	//is the mouse inside the start button?
		boolean insideExit = false;		//is it in the exit button?
		
		if( (mouseX >= menuX && mouseX <= menuX + startGame.getWidth()) && (mouseY >= menuY && mouseY <= menuY + startGame.getHeight()) ) {
			insideStart = true;		//if the mouse is inside the start button, insideStart is true
		}else if(( mouseX >= menuX && mouseX <= menuX+ exitGame.getWidth()) && ( mouseY >= menuY+80 && mouseY <= menuY+80 + exitGame.getHeight())) {
			insideExit = true;		//if the mouse is inside the exit button, insideExit is true
		}
		
		if(insideStart) {
			if(startGameScale < 1.05f) {				
				startGameScale += (scaleStep * delta);		//scale the start button up if the mouse is inside it and if it isn't the max size
			}
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) {
				game.enterState(SlickBlocksGame.GAMEPLAYSTATE);		//enter gameplay state if you click the start button
			}
			
		} else {
			if(startGameScale > 1.00f) {
				startGameScale -= (scaleStep * delta);		//scale the start button down if it isn't already 
			}												//at scale = 1 and the mouse isn't inside it
		}
		if(insideExit) {
			if(exitScale < 1.05f) {
				exitScale += (scaleStep * delta);		//scale up the exit button if mouse is in it and it isn't the max size yet
			}
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) {
				container.exit();					//exit game if you click exit
			}
			
		} else {
			if(exitScale > 1.00f) {
				exitScale -= (scaleStep * delta);	//scale down exit when mouse isn't in it
			}
		}

		

		
		
		//System.out.println(delta);
	}

	@Override
	public int getID() {
		
		return stateID;
	}


}
