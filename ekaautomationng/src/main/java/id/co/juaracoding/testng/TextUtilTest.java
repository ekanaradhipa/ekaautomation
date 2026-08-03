package id.co.juaracoding.testng;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static id.co.juaracoding.util.TextUtil.countVowels;
import static id.co.juaracoding.util.TextUtil.isPalindrome;

public class TextUtilTest {

    @BeforeClass
    public void before_class_method() {
        System.out.println("Test Dimulai");
    }

    @Test
    public void test_is_palindrome() {
        String input = "tacocat";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    @Test
    public void test_is_palindrome_mixed_case() {
        String input = "TacoCat";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    @Test
    public void test_is_not_palindrome() {
        String input = "hello";
        boolean result = isPalindrome(input);
        Assert.assertFalse(result);
    }

    @Test
    public void test_is_not_palindrome_mixed_case() {
        String input = "Hello";
        boolean result = isPalindrome(input);
        Assert.assertFalse(result);
    }

    @Test
    public void test_is_palindrome_empty() {
        String input = "";
        boolean result = isPalindrome(input);
        Assert.assertTrue(result);
    }

    @Test
    public void test_is_palindrome_null() {
       
        boolean result = isPalindrome(null);
        Assert.assertFalse(result);
    }
    
    @Test
    public void test_count_vowels() {
        String input = "acute";
        int result = countVowels(input);
        Assert.assertEquals(result, 3);
    }

    @Test
    public void test_count_vowels_mixed_case() {
        String input = "Acute";
        int result = countVowels(input);
        Assert.assertEquals(result, 3);
    }

    @Test
    public void test_count_vowels_zero() {
        String input = "crypt";
        int result = countVowels(input);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void test_count_vowels_empty() {
        String input = "";
        int result = countVowels(input);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void test_count_vowels_null() {
        int result = countVowels(null);
        Assert.assertEquals(result, 0);
    }

    @AfterClass
    public void after_class_method() {
        System.out.println("Test Selesai");
    }
    

}
