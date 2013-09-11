package com.rooneyworks.Blocks;




public class Block {


	private int blockyX;
	private int blockyY;
	private int color; //color is between 1 and 4
	/**
	 * creates a new block object at the specified position.
	 * 
	 * @param blockX - the BLOCK x value of the block (in blocks, not pixels)
	 * @param blockY - the Block y value of the block (in blocks, not pixels)
	 */
	public Block(int blockX, int blockY, int color ) {

		blockyX = blockX;
		blockyY = blockY;
		this.color = color;
		
	}
	/**
	 * Sets the position (in blocks) of the block
	 * @param newX
	 * @param newY
	 */
	public void setXY(int newX, int newY) {
		blockyY = newY;
		blockyX = newX;
	}
	/**
	 * 
	 * @return the x-coordinate (in blocks) of the block
	 */
	public int getBlockyX() {
		return blockyX;
	}
	/**
	 * 
	 * @return the y-coordinate (in blocks) of the block
	 */
	public int getBlockyY() {
		return blockyY;
	}
	/**
	 * 
	 * @return the color of the block
	 */
	public int getcolor() {
		return color;
	}
	

}
