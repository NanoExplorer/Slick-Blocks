package com.rooneyworks.blockGame;

import java.util.ArrayList;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.rooneyworks.blockGame.AnimationStates;
//import com.rooneyworks.Blocks.*;

public class GameplayState extends BasicGameState {

	
	int stateID = -1;				//The identification number for the state
	
	Image background;		
	Image transparentThingy; //I'm pretty sure this is the hud overlay
	Image master;			//holds misc gameplay textures
	Image ready;				//holds image that says "ready"	
	Image set;				//"set" (you get the idea)
	Image go;
	
	ArrayList<Image> blockImages;
	Pit pit;
	DisplayPit pitTwo;
	
	int timeToFall;
	int fallInterval;
	final int KEY_REPEAT_INTERVAL = 80;
	final int FIRST_KEY_REPEAT_INTERVAL = 160;

	
	int keyRepeat;
	int lastKey;
	
	int animationTimer;
	//int pauseDelayTimer; //makes sure that you can't just hold down space and play the game at 1/2 speed
	final int PAUSE_DELAY_TIME = 1000; //How long the game will delay before letting you pause or unpause again. (This is basically an animation timer)
	final int PAUSE_ANIM_DELAY =20;
	
	int blockAnimationX, blockAnimationY;
	
	float scale;
	
	String readySetText;
	AngelCodeFont theFont = null;		//load the font
	Color myOrange = null;			//set the color of myOrange	
	AnimationStates animationState;

	
	GameplayState(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("res/gameBackground.jpg");
		master = new Image("res/gamePlayTextures.png");
		ready = new Image("res/Ready.png");
		set = new Image("res/Set.png");
		go = new Image("res/Go.png");
		transparentThingy = master.getSubImage(29, 0, 287, 573);
		scale = 1;
		//pauseDelayTimer = 0;
		
		ArrayList<Image> blockImages = new ArrayList<Image>();
		 
		for(int i = 0; i < 4; i++)
		   blockImages.add(master.getSubImage(0, i*28, 28, 28));
				
		pit = new Pit(blockImages);
		pitTwo = new DisplayPit(blockImages);
		timeToFall = 0;
		fallInterval = 240;
		keyRepeat = FIRST_KEY_REPEAT_INTERVAL;
		animationTimer = 2000; //animationtimer will start out counting down to zero and will be used to calculate when to display the "ready set go" animation
		animationState = AnimationStates.READY;
		theFont = new AngelCodeFont("res/fnt/GameGui.fnt", "res/fnt/GameGui_0.png");	//load the font (Used for scores and other stuff)
		myOrange = new Color(0xF24C00);			//set the color of myOrange
		
		blockAnimationX = 0;
		blockAnimationY = 1;
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		transparentThingy.draw(48,15);
		
		switch(animationState){
		case READY:
			ready.draw(100, 300, scale, myOrange);
			
			break;
		case SET:
			set.draw(100, 300, scale, myOrange);
			break;
		case GO:
			go.draw(100, 300, scale, myOrange);
			break;
		case PLAYING:
			pit.render(g);
			break;
		case DELETING_ROW:
			pit.render(g);
			break;
		case PAUSED:
			pitTwo.render(g);
			theFont.drawString(100, 300, "Paused", new Color(0x000000));
			break;
		case GAME_OVER:
			pit.render(g);
			theFont.drawString(400, 200, "GAME OVER", myOrange);
			break;
		case PAUSE_IN:
			pitTwo.render(g);
			theFont.drawString(100, 300, "Paused", new Color(0x000000));
			break;
		case PAUSE_OUT:
			pitTwo.render(g);
			theFont.drawString(100, 300, "Paused", new Color(0x000000));
			break;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		//if(pauseDelayTimer > 0) {
		//	pauseDelayTimer -= delta;
		//}
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			reset();
			game.enterState(SlickBlocksGame.MAINMENUSTATE);
			
		}
		
		switch(animationState) {
		case READY:
			//will use the int timeToFall as the counter to determine how much time is left before the game starts (Basically time before the first fall)
			animationTimer -= delta;
			if(animationTimer <= 0) {
				animationState = AnimationStates.SET;
				animationTimer = 2000;
				myOrange.a = 1;
			} else {
				readyAnimation(delta);
			}
			break;
		case SET:
			animationTimer -= delta;
			if(animationTimer <= 0) {
				animationState = AnimationStates.GO;
				animationTimer = 2000;
				myOrange.a = 1;
			} else {
				readyAnimation(delta);
			}
			break;
		case GO:
			animationTimer -= delta;
			if(animationTimer <= 0) {
				animationState = AnimationStates.PLAYING;
				myOrange.a = 1;
			} else {
				readyAnimation(delta);
			}
			break;
		case PLAYING:
			updatePlaying(input, delta);
			break;
		case DELETING_ROW:
			rowAnimation();
			break;
		case PAUSED:
			if(input.isKeyDown(Input.KEY_SPACE)){
				animationState = AnimationStates.PAUSE_OUT;
				
			}
			//display pause menu....
			break;
		case GAME_OVER:
			//play game over animation
			break;
		case PAUSE_IN:
			
			myOrange.a = 1;
			
			//increment/decrement the x coord
			if(blockAnimationY % 2 !=0) {
				blockAnimationX++;
			} else {
				blockAnimationX--;
			}
			
			//calculate whether to increment y
			if(blockAnimationX >= 10) {
				blockAnimationY++;
				blockAnimationX -= 1;
			} else if (blockAnimationX < 0) {
				blockAnimationY++;
				blockAnimationX += 1;
			}
			
			//check if we need to go to pause state
			if(blockAnimationY > 20) {
				animationState = AnimationStates.PAUSED;
				break;
				
			}
			pitTwo.setBlock(blockAnimationX, blockAnimationY, 0);
			break;
		case PAUSE_OUT:
			
			//increment/decrement the x coord
			if(blockAnimationY % 2 ==0) {
				blockAnimationX++;
			} else {
				blockAnimationX--;
			}
			
			//calculate whether to increment y
			if(blockAnimationX >= 10) {
				blockAnimationY--;
				blockAnimationX -= 1;
			} else if (blockAnimationX < 0) {
				blockAnimationY--;
				blockAnimationX += 1;
			}
			
			//check if we need to go to pause state
			if(blockAnimationY < 1) {
				animationState = AnimationStates.PLAYING;
				break;
				
			}
			pitTwo.setBlock(blockAnimationX, blockAnimationY, pit.getPit()[blockAnimationX][blockAnimationY]);
			break;
		}
		
	}

	public void rowAnimation() {
		
	}
	
	public void readyAnimation(int delta) {
		
		//The animations for ready, set and go are the same except with a different image. The different image
		//part will be handled in the render method.
		if(animationTimer >1000) {
			//scale increases slightly (or not...)
		} else if (animationTimer > 0) {
			//opacity decreases linearly to zero
			//scale continues to increase (or not...)
			myOrange.a -= 0.0015f * delta;
		}
	}

	
	@Override
	public int getID() {
		return stateID;
	}
	
	
	/**
	 * Manages the gameplay from a high level. Responds to key presses and framerate changes to keep gameplay going.
	 * Makes pit update calls to refresh the current block positions
	 * @param input an Input object (from Slick)
	 * @param delta an int containing the time it took from the last update to this update
	 */
	public void updatePlaying(Input input, int delta) {
		if(timeToFall <1) {						//So apparently timeToFall keeps track of when the blocks fall and the block falls when it reaches zero
			pit.update(OperationType.DROP);		//tells the block to drop (Pit keeps track of blocks and locations
			timeToFall = fallInterval;			//timer resets
		} else {
			timeToFall -= delta;				//timer tick
		}
	
		if(input.isKeyDown(Input.KEY_UP)) {	//Rotates block on press of the up-arrow
			if(lastKey == Input.KEY_UP){		//makes sure there's a comprehensible delay between rotate calls
				keyRepeat -= delta;				//cont'd
				if(keyRepeat <= 0) {					//once that delay has passed, rotate it and set the timer
					pit.update(OperationType.ROTATE);
					keyRepeat = KEY_REPEAT_INTERVAL;		//sets the timer for the smaller amount of time (made sense at the time)
				}
			} else {
				keyRepeat = FIRST_KEY_REPEAT_INTERVAL;		//the first time that up is pressed, sets timer to the initial delay
				pit.update(OperationType.ROTATE);		//and rotates the block
			}
		
			lastKey = Input.KEY_UP;
		
		} else if(input.isKeyDown(Input.KEY_LEFT)){		//The same statements from the "up" key handler are true for all of these handlers
			if(lastKey == Input.KEY_LEFT){
				keyRepeat -= delta;
				if(keyRepeat<= 0) {
					pit.update(OperationType.LEFT);
					keyRepeat= KEY_REPEAT_INTERVAL;
				}
			} else {
				keyRepeat = FIRST_KEY_REPEAT_INTERVAL;
				pit.update(OperationType.LEFT);
			}
		
			lastKey = Input.KEY_LEFT;
		
		} else if(input.isKeyDown(Input.KEY_RIGHT)) {
			if(lastKey == Input.KEY_RIGHT){
				keyRepeat -= delta;
				if(keyRepeat<=0) {
					pit.update(OperationType.RIGHT);
					keyRepeat = KEY_REPEAT_INTERVAL;
				}
			} else {
				keyRepeat = FIRST_KEY_REPEAT_INTERVAL;
				pit.update(OperationType.RIGHT);
			}
	
			lastKey = Input.KEY_RIGHT;
		
		} else if(input.isKeyDown(Input.KEY_DOWN)){
			timeToFall -= 4*delta;
			lastKey = Input.KEY_DOWN;
			
		} else if(input.isKeyDown(Input.KEY_SPACE)){ //PAUSE MENU!!!!!!!!!!!!!!
			animationState = AnimationStates.PAUSE_IN;
			pitTwo.copyArray(pit.getPit());
			pitTwo.setBlock(0, 1, 0);
		
		
		} else {
			lastKey = -1337;
		}
	
		if(!pit.keepGoing) {
			animationState = AnimationStates.GAME_OVER;	
			myOrange.a = 1;
		}
		
		if(pit.completedRows) {
			animationState = AnimationStates.DELETING_ROW;		//doesn't do anything yet...
		}
	}

	public void reset() {
		this.pit.reset();
		this.pitTwo.reset();
		this.animationTimer = 2000;
		this.animationState = AnimationStates.READY;
		
	}
}
