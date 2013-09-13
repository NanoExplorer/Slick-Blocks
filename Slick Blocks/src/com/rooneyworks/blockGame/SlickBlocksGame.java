package com.rooneyworks.blockGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SlickBlocksGame extends StateBasedGame {

	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	/**
	 * Constructor for the game object
	 * defines states MainMenu and GamePlay
	 */
	public SlickBlocksGame() {
		super("SlickBlocks");

		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.enterState(MAINMENUSTATE);
		
		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {

		this.getState(MAINMENUSTATE).init(container, this);
		this.getState(GAMEPLAYSTATE).init(container, this);
		
		
	}

	/**
	 * @param args
	 * @throws SlickException 
	 * This is the main method for Slick Blocks Game - it runs an instance of the game in an 800x600 window
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer app = new AppGameContainer(new SlickBlocksGame());
		app.setDisplayMode(800, 600, false);
		app.start();
	}


}
