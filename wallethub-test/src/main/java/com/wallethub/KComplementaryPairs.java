package com.wallethub;

import java.util.HashMap;

public class KComplementaryPairs {
	
	
	/**
	 * 1 - a very basic solution would be iterate over the Array A in a linear manner which each index could be called as 'i', 
	 * and for each index 'i' we could apply a second iterating also on a linear way which each index could be called as 'j' and
	 * check each pair 'i' and 'j' if a sum on them equals the K passed as parameter.
	 * 
	 * notes: this is a O( n^2 ) solution
	 * 
	 * @param A
	 * @param K
	 * @return
	 */
	public static int doCheckV1( int[] A, int K ) {
		int count = 0;
		
		for ( int i = 0; i < A.length; i++ )
			for ( int j = 0; j < A.length; j++ )
				if ( K == A[ i ] + A[ j ] )
					count++;
		
		return count;
	}

	/**
	 * 2 - in order to decrease the complexity, we could assume a mathematical equivalence of 'x+y=y+x' and do not iterate over
	 * the whole index 'j' for each 'i', but always start 'j' from the current 'i'.
	 * 
	 * notes: this is a O( n(n+1)/2 ) solution, which for a really big Array, it reaches about 45% (and almost 50%) less than the previous one
	 * 
	 * @param A
	 * @param K
	 * @return
	 */
	public static int doCheckV2( int[] A, int K ) {
		int count = 0;
		
		for ( int i = 0; i < A.length; i++ )
			for ( int j = i; j < A.length; j++ )
				if ( K == A[ i ] + A[ j ] )
					count += i != j ? 2 : 1;
		
		return count;
	}
	
	/**
	 * 3 - another great mathematic equivalence we might consider in order to decrease even more the time consuming/complexity
	 * is 'K=A[i]+A[j] <=> A[i]-K=A[j]' which allows us instead of check every possibility for 'K = A[i]+A[j]', check for each
	 * element index 'i' minus K if exists the correspondent element on index 'j', in an assertive fashion.
	 * 
	 * notes: this is a O( n ) solution
	 * 
	 * @param A
	 * @param K
	 * @return
	 */
	public static int doCheckV3( int[] A, int K ) {
		Integer count = 0, aux = 0;
		Operand op = null;
		
		HashMap< Operand, Operand > computed = new HashMap< Operand, Operand >();
		
		for ( int i = 0; i < A.length; i++ ) {
			op = new Operand( A[ i ] );
			if ( computed.containsKey( op ) )
				computed.get( op ).incrementOcurrence();
			else computed.put( op, op );
		}
		
		for ( Operand operand : computed.values() ) {
			aux = K - operand.getValue();
			
			Operand related = computed.get( new Operand( aux ) );
			if ( related != null ) {
				count += operand.getOccurence() * related.getOccurence() * ( operand.getValue() == related.getValue() ? 1 : 2 );
				
				operand.resetOcorrence();
				related.resetOcorrence();
			}
		}
		
		return count;
	}
	
}

class Operand {
	
	private Integer value;
	private Integer occurence;
	
	Operand( Integer value ) {
		if ( value == null ) throw new IllegalArgumentException( "Argument 'value' cannot be null." );
		this.value = value;
		this.occurence = 1;
	}
	
	void incrementOcurrence(){
		this.occurence++;
	}
	
	void resetOcorrence() {
		this.occurence = 0;
	}
	
	Integer getOccurence() {
		return this.occurence;
	}
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Operand && this.value.equals( ( ( Operand ) obj).value );
	}

	public Integer getValue() {
		return value;
	}
	
}