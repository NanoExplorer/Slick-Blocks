package com.rooneyworks.Blocks;



import java.util.Random;

import com.rooneyworks.Blocks.Block;
import com.rooneyworks.Blocks.TetraType;

public class TetraBlock {
	
	private int blockyX;
	private int blockyY;
	private int rotation = 0;

	private TetraType blockType;
	
	public boolean isFalling;

	int[] [] tetraMap;

	Block[] fourBlocks = {null, null, null, null};
	
	public TetraBlock(TetraType blockType) { 
		tetraMap = blockType.blockMap [0];
		Random randomGen = new Random();
		for(int i = 0; i < 4; i++) {
			fourBlocks[i] = new Block(5 + tetraMap[0][i], 19+tetraMap[1][i], randomGen.nextInt(4));
		}
		
		blockyX = 5;
		blockyY = 19;
		isFalling = true;
		this.blockType = blockType;
		
	}

	public boolean test(int[] [] blockMap) {
		boolean canFit = true;
		for(Block currentBlock : fourBlocks) {
			if(blockMap[currentBlock.getBlockyX()][currentBlock.getBlockyY()] > 0)
				canFit = false;
		}
		return canFit;
	}
	
	public void rotate(int[] [] blockMap) {
		rotation += 1;
		if(rotation >= 4) 
			rotation = 0;
		boolean canFit= true;
		do {
			canFit = true;
			tetraMap = blockType.blockMap[rotation];
			update();
			for(Block currentBlock:fourBlocks) {
				if(currentBlock.getBlockyX() < 0 || currentBlock.getBlockyX() > 9) {
					canFit = false;
				} else if(currentBlock.getBlockyY() < 0 || currentBlock.getBlockyY() > 20) {
					canFit = false;
				} else if(blockMap[currentBlock.getBlockyX()][currentBlock.getBlockyY()]>0) {
					canFit = false;
				}

			}
			if(!canFit){
				rotation += 1;
				if(rotation >= 4) 
					rotation = 0;
			}
		}while(!canFit);	//This looks reeeeally sketchy.
		update();
	}
	
	public int[] [] fall(int[] [] blockMap) {

		
		boolean move = true;
		int currentBlockX, currentBlockY;
		
		
		for( Block currentBlock: fourBlocks){
			currentBlockX = currentBlock.getBlockyX();
			currentBlockY = currentBlock.getBlockyY();
				
				if(blockMap[currentBlockX][currentBlockY-1] != -1) {
					move = false;
				}
				if(currentBlockY - 1 <= 0){
					move = false;
				}
		}
		if(move) {
			blockyY -= 1;
			update();
		} else {
			//this is where the blocks hit the ground
			isFalling = false;
			for(Block thisBlock : fourBlocks) {
				blockMap[thisBlock.getBlockyX()] [thisBlock.getBlockyY()] = thisBlock.getcolor();
				
			}
		}
		
		return blockMap;
	}
	
	public void left(int[] [] blockMap) {
		boolean move = true;
		
		for( Block currentBlock: fourBlocks){
			if (currentBlock.getBlockyX() - 1 < 0) {
				move = false;
			} else if (blockMap[currentBlock.getBlockyX() - 1][currentBlock.getBlockyY()]>0) {
				move = false;
			}
		}
		if(move) {
			blockyX -= 1;
			update();
		}
		
	}
	
	public void right(int[] [] blockMap) {
		boolean move = true;
		for( Block currentBlock: fourBlocks){
			if (currentBlock.getBlockyX() + 1 > 9){
				move = false;
			}else if (blockMap[currentBlock.getBlockyX() + 1][currentBlock.getBlockyY()]>0) {
				move = false;
			}
		}
		if(move) {
			blockyX += 1;
			update();
		}
		
	}
	
	public int[] [] getXYc() {
		int[] [] coordinates = new int[3] [4];
		int num = 0;
		for(Block currentBlock : fourBlocks) {
			coordinates[0] [num] = currentBlock.getBlockyX();
			coordinates[1] [num] = currentBlock.getBlockyY();
			coordinates[2] [num] = currentBlock.getcolor();
			num++;
		}
		return coordinates;
	}
	
	private void update() {
		for(int i = 0; i < 4; i++) {
			fourBlocks[i].setXY(blockyX + tetraMap[0][i], blockyY+tetraMap[1][i]);
		}
	}
}
