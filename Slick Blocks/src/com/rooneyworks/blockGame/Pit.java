package com.rooneyworks.blockGame;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import com.rooneyworks.Blocks.*;

public class Pit {
	/*the pit coordinate system works as follows:
	x,0 is below the bottom of where the pit appears to be.
	x,1 is the "bottom" row of the pit.
	0,y is the leftmost column of the pit.
	etc.
	9,y is the rightmost column,
	x,20 is the topmost row.
	
	*/
	private int[] [] fallenBlocks = null; //-1= unoccupied, 0, 1, 2, 3 = colors
	//private int[] [] fallenBlocksTwo = null; //a copy of fallenBlocks that exists for the sole purpose of facilitating the row delete animation
	ArrayList<Image> blockImages = null;
	final int PIT_X = 52;	//contains the pixel offset to the first block
    final int PIT_Y = 18;
 
    final int blockSize = 28;

    public TetraBlock fallingBlock = null;
    public TetraType nextBlock = null;
    
    public boolean keepGoing;
    public boolean completedRows;
    
    ArrayList<Integer> deleteRow = null;
    /**
     * renders all of the objects in the pit (incl. blocks, falling and fallen)
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
		
		int[] [] blockInfo = fallingBlock.getXYc();
		int x, y, color;
		
		for(int blocknum = 0; blocknum < 4; blocknum ++) { //render currently falling block
			x = blockInfo[0] [blocknum];
			y = blockInfo[1] [blocknum];
			color = blockInfo[2] [blocknum];
			blockImages.get(color).draw(x*blockSize+PIT_X, PIT_Y + (blockSize* ( 20 - y)));
		}
		
		
		//also render the next block
	}
	/**
	 * Pit constructor. Sets up the pit for play
	 * @param BlockPics An array of the pictures of the blocks to be used
	 */
	Pit(ArrayList<Image> BlockPics) {	//constructs the pit. 
		blockImages = BlockPics;
		
		fallenBlocks = new int[10] [21];
		for(int x = 0; x <10; x++) {
			for(int y = 0; y<21; y++) {
				fallenBlocks[x][y] = -1;		//I'm using -1 as the placeholder for "air"
			}
		}
		nextBlock = TetraType.randomType(); //pre-initializes the first block to fall
		newFallingBlock();
		
		deleteRow = new ArrayList<Integer>(0);
		completedRows = false;
		keepGoing = true;
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
		keepGoing = true;
		
		
		
	}
	/**
	 * Performs an action on the blocks in the pit
	 * (For example - tells the block to drop downward, move sideways, etc.
	 * @param action an OperationType enum containing the action (LEFT, RIGHT, DROP, or ROTATE)
	 */
	public void update(OperationType action) {
		switch(action) {
		case LEFT:
			fallingBlock.left(fallenBlocks);
			break;
		case RIGHT:
			fallingBlock.right(fallenBlocks);
			break;
		case DROP:
			fallenBlocks = fallingBlock.fall(fallenBlocks);
			if(!fallingBlock.isFalling) {
				checkRows();
				keepGoing = newFallingBlock();
			}
			break;
		case ROTATE:
			fallingBlock.rotate(fallenBlocks);
			break;
		}
		
	}
	
	/**
	 * Inserts the block defined in nextBlock to the top of the pit.
	 * @return boolean - whether or not the block was successfully inserted (false = game over)
	 */
	public boolean newFallingBlock() {
		fallingBlock = new TetraBlock(nextBlock);
		nextBlock = TetraType.randomType();
		return fallingBlock.test(fallenBlocks);	
			
	}
	
	/**
	 * Checks to see if any rows have been completed. If they have, the row number is added to the DeleteRow array
	 * and the instance variable completedRows is set to true.
	 */
	public void checkRows() {
		
		boolean fullRow = true;
		
		for(int rowNum = 0; rowNum < 20; rowNum ++) {
			fullRow = true;
			for(int currentCell = 0; currentCell<10; currentCell ++) {
				if(fallenBlocks[currentCell][rowNum] == -1){
					fullRow = false;
					break;
				}			
			}
			if(fullRow) {
				deleteRow.add(rowNum);
				completedRows = true;
			}
		}
	}
	
	public int[][] getPit() {
		return fallenBlocks;
	}
}
