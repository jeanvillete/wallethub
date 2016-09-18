package com.wallethub;

/**
 * A palindrome is a word, phrase, number, or other sequence of characters which reads the same backward or forward.
 * Allowances may be made for adjustments to capital letters, punctuation, and word dividers.
 * Examples in English include "A man, a plan, a canal, Panama!", "Amor, Roma", "race car", "stack cats",
 * 	"step on no pets", "taco cat", "put it up", "Was it a car or a cat I saw?" and "No 'x' in Nixon".
 * 
 * source: https://en.wikipedia.org/wiki/Palindrome
 */
public class Palindrome {

	public static final boolean check( final String str ) {
		return check( str, null );
	}
	
	public static final boolean check( String str, String adjustments ) {
		if ( str == null || str.isEmpty() ) {
			throw new IllegalArgumentException( "A valid neither null nor empty string has to be passed to be checked." );
		} else if ( str.length() == 1 ) {
			return true;
		}
		
		if ( adjustments != null ) {
			str = str.toLowerCase().replaceAll( "[" + adjustments + "]", "" );
		}
		
		int backwardAt = str.length() / 2;
		int forwardAt = str.length() % 2 == 0 ? backwardAt : backwardAt + 1;
		
		char v1[] = str.substring( 0, backwardAt ).toCharArray();
		char v2[] = str.substring( forwardAt, str.length() ).toCharArray();
		
		for ( int i = 0; i < backwardAt; i ++ ) {
			if ( v1[ i ] != v2[ v2.length - 1 - i ] ) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main( String[] args ) {
		if ( args == null || !( args.length == 1 || args.length == 2 ) ) {
			throw new IllegalArgumentException( "Usage 'palindrome word or phrase' 'optional regex ajustment'" );
		}
		
		System.out.println( ( args.length == 2 ? Palindrome.check( args[0], args[ 1 ] ) : Palindrome.check( args[0] ) ) ? "palindrome" : "not a palindrome" );
	}
	
}
