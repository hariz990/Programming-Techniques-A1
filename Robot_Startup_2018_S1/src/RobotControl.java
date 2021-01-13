import javax.swing.JOptionPane;

class RobotControl
{
	private Robot r;
	public static StringBuilder sb;

	// Examples of constants
	private final int SOURCE_LOCATION = 10; // Blocks placed
	private final int TARGET_1 = 1; // For block size 1
	private final int TARGET_2 = 2; // For block size 2
	private final int FIRST_BAR_POSITION = 3; // First bar location of block size 3

	public RobotControl(Robot r)
	{
		this.r = r;
	}

	public void control(int barHeights[], int blockHeights[])
	{
		//sampleControlMechanism(barHeights, blockHeights);
		run(barHeights, blockHeights);
	}

	public void sampleControlMechanism(int barHeights[], int blockHeights[])
	{
		// Internally the Robot object maintains the value for Robot height(h),
		// arm-width (w) and picker-depth (d).

		// These values are displayed for your convenience
		// These values are initialised as h=2 w=1 and d=0

		// When you call the methods up() or down() h will be changed
		// When you call the methods extend() or contract() w will be changed
		// When you call the methods lower() or raise() d will be changed

		// sample code to get you started
		// Try running this program with obstacle 555555 and blocks of height
		// 2222 (default)
		// It will work for first block only
		// You are free to introduce any other variables

		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		int sourceHt = 12;

		// For Parts (a) and (b) assume all four blocks are of the same height
		// For Part (c) you need to compute this from the values stored in the
		// array blockHeights
		// i.e. sourceHt = blockHeights[0] + blockHeights[1] + ... use a loop!

		int targetCol1Ht = 0; // Applicable only for part (c) - Initially empty
		int targetCol2Ht = 0; // Applicable only for part (c) - Initially empty

		// height of block just picked will be 3 for parts A and B
		// For part (c) this value must be extracted from the topmost unused value
		// from the array blockHeights

		int blockHt = 3;

		// clearance should be based on the bars, the blocks placed on them,
		// the height of source blocks and the height of current block

		// Initially clearance will be determined by the blocks at source
		// (3+3+3+3=12)
		// as they are higher than any bar and block-height combined

		int clearence = 12;

		// Raise it high enough - assumed max obstacle = 4 < sourceHt

		// this makes sure robot goes high enough to clear any obstacles
		while (h < clearence + 1)
		{
			// Raising 1
			r.up();

			// Current height of arm1 being incremented by 1
			h++;
		}

		System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// this will need to be updated each time a block is dropped off
		int extendAmt = 10;

		// Bring arm 2 to column 10
		while (w < extendAmt)
		{
			// moving 1 step horizontally
			r.extend();

			// Current width of arm2 being incremented by 1
			w++;
		}

		System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// lowering third arm - the amount to lower is based on current height
		// and the top of source blocks

		// the position of the picker (bottom of third arm) is determined by h
		// and d
		while (h - d > sourceHt + 1)
		{
			// lowering third arm
			r.lower();

			// current depth of arm 3 being incremented
			d++;
		}

		// picking the topmost block
		r.pick();

		// topmost block is assumed to be 3 for parts (a) and (b)
		blockHt = 3;

		// When you pick the top block height of source decreases
		sourceHt -= blockHt;

		// raising third arm all the way until d becomes 0
		while (d > 0)
		{
			r.raise();
			d--;
		}

		System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// why not see the effect of changing contractAmt to 6 ?
		int contractAmt = 7;

		// Must be a variable. Initially contract by 3 units to get to column 3
		// where the first bar is placed (from column 10)

		while (contractAmt > 0)
		{
			r.contract();
			contractAmt--;
		}

		System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// You need to lower the third arm so that the block sits just above the
		// bar
		// For part (a) all bars are initially set to 7
		// For Parts (b) and (c) you must extract this value from the array
		// barHeights

		int currentBar = 0;

		// lowering third arm
		while ((h - 1) - d - blockHt > barHeights[currentBar])
		{
			r.lower();
			d++;
		}

		System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// dropping the block
		r.drop();

		// The height of currentBar increases by block just placed
		barHeights[currentBar] += blockHt;

		// raising the third arm all the way
		while (d > 0)
		{
			r.raise();
			d--;
		}
		System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// This just shows the message at the end of the sample robot run -
		// you don't need to duplicate (or even use) this code in your program.

		JOptionPane.showMessageDialog(null,
				"You have moved one block from source "
						+ "to the first bar position.\n"
						+ "Now you may modify this code or "
						+ "redesign the program and come up with "
						+ "your own method of controlling the robot.",
				"Helper Code Execution", JOptionPane.INFORMATION_MESSAGE);
		// You have moved one block from source to the first bar position.
		// You should be able to get started now.
	}

	public void run(int barHeights[], int blockHeights[])
	{
		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3
		int sumOfBlockHeights = 0; // BlockHeights total
		int currentBlock = 0; // Current Block 
		int blockPos = 3; // First location of Block 3
		
		int heightBlock[] = new int[blockHeights.length]; // New Array for blockHeights
		int heightArray[] = new int [10]; // Storing all heights from column 1-10
		int arrayBar = 2; // Starting point of storing barHeights in heightArray
		int endPos = 0; // Storing end position of block destination from column 10
		
		
		//CALCULATE total blocks in SOURCE_LOCATION
		for (int i : blockHeights)
		{
		    sumOfBlockHeights += i;
		}
		
		heightArray[9] += sumOfBlockHeights;
		
		//REVERSE blockHeights array and store in newArray
		for(int i = 0; i < heightBlock.length; i++)
		{
			heightBlock[i] = blockHeights[blockHeights.length - 1 - i];
		}
		
		//STORING bar heights into heightArray
		for (int i = 2; i < barHeights.length + 2; i++)
        {
        	heightArray[i] = barHeights[i-2];
        }
		
		//START
		//RAISE robot 1st arm
		while (h < sumOfBlockHeights + 1)
		{
			r.up();
			h++;	
		}
		
		//LOOP PROCESS
		while(sumOfBlockHeights != 0)
		{
			
		while(w < SOURCE_LOCATION)//Pushing 2nd arm to location of blocks stored
		{
			r.extend();
			w++;
		}
		
		while (sumOfBlockHeights + d != h - 1)//Pushing 3rd arm downwards 
		{
			r.lower();
			d++;
		}
		
		r.pick();
		sumOfBlockHeights -= heightBlock[currentBlock];
		heightArray[9] -= heightBlock[currentBlock];
		
		//CHECKING block size
		if(blockHeights[currentBlock] == 1)
		{
			endPos = 1;
		}
		else if(blockHeights[currentBlock] == 2)
		{
			endPos = 2;
		}
		else if(blockHeights[currentBlock] == 3)
		{
			endPos = blockPos;
		}
		
		//RAISE robot 3rd arm
		int maxFinal = maxHeight(endPos, heightArray, w, sumOfBlockHeights);
		if(maxFinal > sumOfBlockHeights)
		{
			int difference1 = maxFinal - (h - 1 - d - heightBlock[currentBlock]);
			for(int p = 0; p < difference1; p++)
			{
				r.raise();
				d--;
			}	
		}
		
		//FOR BLOCK SIZE 1
		if(heightBlock[currentBlock] == 1)
		{
			while ((w-TARGET_1) > 0)//Pushing robot 2nd arm to block location
			{
				r.contract();
				w--;
			}
			
			while (h - d != heightArray[0] + 2)//Placing the block to location
			{
				r.lower();
				d++;
			}
			
			r.drop();
			heightArray[0] += heightBlock[currentBlock];
			
			currentBlock++;
		}
		
		//FOR BLOCK SIZE 2
		else if(heightBlock[currentBlock] == 2)
		{
			while ((w-TARGET_2) > 0)//Pushing robot 2nd arm to block location
			{
				r.contract();
				w--;
			}
			
			while (h - d != heightArray[1] + 3)//Placing the block to location
			{
				r.lower();
				d++;
			}
			
			r.drop();
			heightArray[1] += heightBlock[currentBlock];
			
			currentBlock++;
		}
		
		//FOR BLOCK SIZE 3
		else if(heightBlock[currentBlock] == 3)
		{
			while ((w-blockPos) > 0)//Placing each block on next available bar
			{
				r.contract();
				w--;
			}
			
			while ((h - 1) - d - heightBlock[currentBlock] > heightArray[arrayBar])//Placing the block to location
			{
				r.lower();
				d++;
			}
			
			r.drop();
			heightArray[arrayBar] += heightBlock[currentBlock];
		
			currentBlock++;
			blockPos++;
			arrayBar++;
		}
		
		//RAISE robot 3rd arm
		int maxBarHeight = maxHeight(10, heightArray, w, sumOfBlockHeights);
		if(maxBarHeight >= sumOfBlockHeights && sumOfBlockHeights > 0)
		{
			int difference2 = maxBarHeight - (h - 1 - d);
			for(int p = 0; p < difference2; p++)
			{
				r.raise();
				d--;
			}	
		}
		}
	}
	
	public int maxHeight(int endPos, int[] heightArray, int w, int sumOfBlockHeights)//METHOD for maxHeight value
	{
		int value = 0; 
		
		//FIND maximum value in heightArray from starting point of robot 2nd arm to end point of its destination
		if(w < endPos)
		{
			for (int i = w - 1; i < endPos - 1; i++)
			{
				if (heightArray[i] > value)
				{
					value = heightArray[i];
				}
			}
		}
		else
		{
			for (int i = endPos - 1; i < w - 1; i++)
			{
				if (heightArray[i] > value)
				{
					value = heightArray[i];
				}
			}
		}
		
		if(sumOfBlockHeights > value && endPos == 10)
		{
			value = sumOfBlockHeights;
		}
	
		return value;
	}
	
	public void Pseudocode()
	{
	//Method run
	/*BEGIN 
		CALCULATE total blocks that are placed on SOURCE_LOCATION
		STORE value of sumOfBlockheights into heightArray[9] 
		STORE elements in blockHeights from reverse into a new array (heightBlock[])
		STORE elements in barHeights into a new array (heightArray[]) this way the program can check for maximum height from column 1-10
		RAISE robot 1st arm to height of total block heights + 1
			INCREMENT h
		
		WHILE total block heights is not equal to 0
		
			EXTEND robot 2nd arm to where blocks are placed (SOURCE_LOCATION)
				INCREMENT w
			LOWER robot 3rd arm to reach the top block 
				INCREMENT d
			PICK block 
			CALCULATE new value of sumOfBlockHeights minus height of block picked
			CALCULATE new value of heightArray[9] minus height of block picked
			
			CHECK for the size of block picked
			IF block size is 1 
				endPos is 1 
			ELSE IF block size is 2 
				endPos is 2 
			ELSE IF block size is 3 
				endPos is blockPos
			CALCULATE maximum height in heightArray
			RAISE robot 3rd arm according to difference1
				DECREMENT d
			
			IF block size is 1 
				CONTRACT robot 2nd arm to location TARGET_1
					DECREMENT w
				LOWER robot 3rd arm to place block at location 
					INCREMENT d
				DROP block
				CALCULATE new value of heightArray[0] plus height of block dropped
				
			ELSE IF block size is 2
				CONTRACT robot 2nd arm to location TARGET_2
					DECREMENT w
				LOWER robot 3rd arm to place block at location 
					INCREMENT d
				DROP block
				CALCULATE new value of heightArray[1] plus height of block dropped
				
			ELSE IF block size 3
				FIND available bar to place the block
				CONTRACT robot 2nd arm to location of bar
				 	DECREMENT w
				LOWER robot 3rd arm to place block at location
					INCREMENT d
				DROP block
				CALCULATE new value of heightArray plus height of block dropped
			
			IF maximum bar height is more or equal than total height of blocks and total height of blocks is more than 0
			CALCULATE difference2 between maximum bar height and h - 1 - d 
			FOR LOOP using difference2 value
				RAISE robot 3rd arm
					DECREMENT d
				
		REPEAT until condition of WHILE loop statement is true	
	END*/
	
	//Method maxHeights 
	/*BEGIN 
	 * 
	 * 	ASSIGN value to 0 
	 * 
	 * 	IF endPos is more than robot 2nd arm (w)
	 * 		FOR (int i = w - 1; i < endPos - 1; i++)
	 * 		STORE value to maximum number in heightArray 
	 * 	ELSE 
	 * 		FOR (int i = endPos - 1; i < w - 1; i++)
	 * 		STORE value to maximum number in heightArray
	 * 
	 * 	IF (sumOfBlockHeights > value && endPos == 10)
	 * 		STORE value to sumOfBlockHeights
	 * 
	 * RETURN value 
	 * 
	 *///END
		
	}
}

		
		
	
	


