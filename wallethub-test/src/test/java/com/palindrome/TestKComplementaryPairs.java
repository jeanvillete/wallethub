package com.palindrome;

import org.junit.Assert;
import org.junit.Test;

import com.wallethub.KComplementaryPairs;

public class TestKComplementaryPairs {
	
	private static final int K = 4;
	private static final int[][] tests = new int[][]{
		new int[]{2,2,2,3,2,1},
		new int[]{3,3,3,3,3,1,1,1,1,4,0,4,3,5,-1},
		new int[]{1,2,3,4},
		new int[]{2,2,2,2,2,2},
		new int[]{0},
		new int[]{4},
		new int[]{0,4},
		new int[]{2,4,4,2,-2},
		new int[]{3,1,4,0,4,0,4},
		new int[]{5,4,3,2,1,0,-1,-2,-3,-4,-5,3},
		new int[]{5,4,3,2,1,0,-1,-2,-3,-4,-5,3,1,10,-6,2,2,-2,-4},
		new int[]{5,4,3,2,1,0,-1,-2,-3,-4,-5,3,1,10,-6,2,2,-2,-4,5,4,3,2,1,0,-1,-2,-3,-4,-5,3,1,10,-6,2,2,-2,-4,5,4,3,2,1,0,-1,-2,-3,-4,-5,3,1,10,-6,2,2,-2,-4}
	};

	@Test
	public void test() {
		for ( int[] A : tests ) {
			int v1 = KComplementaryPairs.doCheckV1( A, K );
			int v2 = KComplementaryPairs.doCheckV2( A, K );
			int v3 = KComplementaryPairs.doCheckV3( A, K );
			
			System.out.printf( "v1 = [%d] v2 = [%d] v3 = [%d]\n", v1, v2, v3 );
			
			Assert.assertEquals( v1, v2, v3 );
		}
	}
	
}
