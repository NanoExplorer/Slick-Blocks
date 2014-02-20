package com.rooneyworks.Blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TetraType {
	
	I(new int[] [] []{ 
		{
			{-1,0,1,2},
			{ 0,0,0,0}
		},
		{
			{0,0,0,0},
			{1,0,-1,-2},
		},
		{
			{1,0,-1,-2},
			{0,0, 0, 0},
		},
		{
			{ 0,0,0,0},
			{-1,0,1,2}
		}
	} ),
	J(new int[] [] []{ 
		{
			{ 0,0,0,1},
			{-1,0,1,1}
		},
		{
			{-1,0,1,1},
			{ 0,0,0,-1}
		},
		{
			{0,0, 0,-1},
			{1,0,-1,-1}
		},
		{
			{1,0,-1,-1},
			{0,0, 0, 1}
		}
	} ),
	L(new int[] [] []{ 
		{
			{ 1, 0,0,0},
			{-1,-1,0,1}
		},
		{
			{-1,-1,0,1},
			{-1, 0,0,0}
		},
		{
			{-1,0,0, 0},
			{ 1,1,0,-1}
		},
		{
			{1,1,0,-1},
			{1,0,0,0}
		},
	} ),
	O(new int[] [] []{ 
		{
			{0,1,1,0},
			{1,1,0,0}
		},
		{
			{1,1,0,0},
			{1,0,0,1}
		},
		{
			{1,0,0,1},
			{0,0,1,1}
		},
		{
			{0,0,1,1},
			{0,1,1,0}
		}

	} ),
	S(new int[] [] []{ 
		{
			{-1,0,0,1},
			{0,0,1,1}
		},
		{
			{0,0,1,1},
			{1,0,0,-1}
		},
		{
			{1,0, 0,-1},
			{0,0,-1,-1}
		},
		{
			{0,0,-1,-1},
			{-1,0,0,1}
		}
	} ),
	T(new int[] [] []{ 
		{
			{0,1, 0,-1},
			{0,0,-1,0}
		},
		{
			{0,0,-1,0},
			{0,-1,0,1}
		},
		{
			{0,-1,0,1},
			{0, 0,1,0}
		},
		{
			{0,0,1,0},
			{0,1,0,-1}
		}
	} ),
	Z(new int[] [] []{ 
			{
				{-1, 0, 0, 1},
				{ 1, 1, 0, 0}
			},
			{
				{ 1, 1, 0, 0},
				{ 1, 0, 0,-1}
			},
			{
				{ 1, 0,0,-1},
				{-1,-1,0, 0}
			},
			{
				{-1,-1,0,0},
				{-1, 0,0,1}
			}
	} );
	
	public final int[] [] [] blockMap;
	
	private TetraType(int [] [] [] map) {
		blockMap = map;
	}
	
	
	
	
	
	private static final List<TetraType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));	
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	
	public static TetraType randomType()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	 }

	
	

}
