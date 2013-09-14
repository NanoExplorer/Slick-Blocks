/*This is basically a watered-down version of the Pit class that
  only contains the ability to display blocks in a render setting.
  It has an array of blocks that it will render, and can be changed
  with the addBlock, removeBlock and setBlock methods.
*/
package com.rooneyworks.blockGame;

import java.util.ArrayList;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class DisplayPit {

	private int[] [] fallenBlocks = null; //-1= unoccupied, 0, 1, 2, 3 = colors
	ArrayList<Image> blockImages = null;
	final int PIT_X = 52;	//contains the pixel offset to the first block
    final int PIT_Y = 18;
 
    final int blockSize = 28;

   
    /**
     * renders blocks
     * @param g the graphics object
     */
	public void render(Graphics g) {
		
		for(int x = 0; x <10; x++) {	//render fallen blocks
			for(int y = 0; y<21; y++) {
				int thisBlockCode = fallenBlocks[x][y];
				if(thisBlockCode >= 0) {
					blockImages.get(thisBlockCode).draw(x*blockSize+PIT_X, PIT_Y + (blockSize* ( 20 - y)));
				}
			}
		}
		
		
		
	
	}
	/**
	 * Pit constructor. Sets up the pit for play
	 * @param BlockPics An array of the pictures of the blocks to be used
	 */
	DisplayPit(ArrayList<Image> BlockPics) {	//constructs the pit. 
		blockImages = BlockPics;
		
		fallenBlocks = new int[10] [21];
		for(int x = 0; x <10; x++) {
			for(int y = 0; y<21; y++) {
				fallenBlocks[x][y] = -1;		//I'm using -1 to denote "air"
			}
		}
	}
	
	/**
	 * resets the pit by setting all blocks to air
	 */
	public void reset() {
		for(int x = 0; x <10; x++) {
			for(int y = 0; y<21; y++) {
				fallenBlocks[x][y] = -1;
			}
		}
	}
	/**
	 * Adds a block to the "showy" pit
	 * @param x blocky x coordinate of the block to add
	 * @param y blocky y coordinate of the block to add
	 * @param color an int between 0 and 3 representing the color of the block
	 * @return true if the add operation succeded.
	 */
	public boolean addBlock(int x, int y, int color) {
		if(fallenBlocks[x][y] == -1) {
			fallenBlocks[x][y] = color;
			return true;
		} else {
			return false;
		}
	}
	
	public void removeBlock(int x, int y) {
		fallenBlocks[x][y] = -1;
	}
	
	public void setBlock(int x, int y, int color) {
		fallenBlocks[x][y] = color;
	}
	/**
	 * Copies the fallenBlocks array of a Pit using the output of Pit.getPit().
	 * Does NOT change the array passed to it.
	 *
	 * @param anotherPit a 10x21 array containing numbers representing blocks
	 */
	public void copyArray(int[][] anotherPit) {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y<21; y++) {
				fallenBlocks[x][y] = anotherPit[x][y];
			}
		
		}
	}
}