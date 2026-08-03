package id.co.juaracoding.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

import static id.co.juaracoding.util.TextUtil.countVowels;
import static id.co.juaracoding.util.TextUtil.isPalindrome;

public class TextUtilTest {
    @Test
    public void testIsPalindrome() {
        String input = "tacocat";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsPalindromeMixedCase() {
        String input = "TacoCat";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsNotPalindrome() {
        String input = "hello";
        boolean result = isPalindrome(input);
        Assert.assertFalse(result);
    }

    @Test
    public void testIsNotPalindromeMixedCase() {
        String input = "Hello";
        boolean result = isPalindrome(input);
        Assert.assertFalse(result);
    }

    public void testIsPalindromeEmpty() {
        String input = "";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    public void testIsPalindromeNull() {
       
        boolean result = isPalindrome(null);
        Assert.assertFalse(result);
    }
    
    @Test
    public void testCountVowels() {
        String input = "acute";
        int result = countVowels(input);
        Assert.assertEquals(result, 3);
    }

    @Test
    public void testCountVowelsMixedCase() {
        String input = "Acute";
        int result = countVowels(input);
        Assert.assertEquals(result, 3);
    }

    @Test
    public void testCountVowelsZero() {
        String input = "crypt";
        int result = countVowels(input);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void testCountVowelsEmpty() {
        String input = "";
        int result = countVowels(input);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void testCountVowelsNull() {
        int result = countVowels(null);
        Assert.assertEquals(result, 0);
    }

    

}
