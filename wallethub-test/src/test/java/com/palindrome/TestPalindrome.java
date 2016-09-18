package com.palindrome;

import org.junit.Assert;
import org.junit.Test;

import com.wallethub.Palindrome;

public class TestPalindrome {

	@Test
	public void test() {
		Assert.assertTrue( Palindrome.check( "1221" ) );
		Assert.assertTrue( Palindrome.check( "1" ) );
		Assert.assertTrue( Palindrome.check( "11" ) );
		Assert.assertTrue( Palindrome.check( "121" ) );
		Assert.assertTrue( Palindrome.check( "12321" ) );
		Assert.assertTrue( Palindrome.check( "123321" ) );
		Assert.assertTrue( Palindrome.check( "1234321" ) );
		Assert.assertTrue( Palindrome.check( "put it up", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "A man, a plan, a canal, Panama!", "\\s\\,\\!" ) );
		Assert.assertTrue( Palindrome.check( "Amor, Roma", "\\s\\," ) );
		Assert.assertTrue( Palindrome.check( "race car", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "stack cats", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "step on no pets", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "Step On No Pets", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "taco cat", "\\s" ) );
		Assert.assertTrue( Palindrome.check( "Was it a car or a cat I saw?", "\\s\\?" ) );
		Assert.assertTrue( Palindrome.check( "No 'x' in Nixon", "\\s\\'" ) );
		
		Assert.assertFalse( Palindrome.check( "1121" ) );
		Assert.assertFalse( Palindrome.check( "12" ) );
		Assert.assertFalse( Palindrome.check( "123" ) );
		Assert.assertFalse( Palindrome.check( "1234" ) );
		Assert.assertFalse( Palindrome.check( "put it up" ) );
		Assert.assertFalse( Palindrome.check( "A man, a plan, a canal, Panama!" ) );
		Assert.assertFalse( Palindrome.check( "Amor, Roma" ) );
		Assert.assertFalse( Palindrome.check( "race car" ) );
		Assert.assertFalse( Palindrome.check( "stack cats" ) );
		Assert.assertFalse( Palindrome.check( "Step On No Pets" ) );
		Assert.assertFalse( Palindrome.check( "taco cat" ) );
		Assert.assertFalse( Palindrome.check( "Was it a car or a cat I saw?" ) );
		Assert.assertFalse( Palindrome.check( "No 'x' in Nixon" ) );
	}
	
}
