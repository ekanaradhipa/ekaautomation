package id.co.juaracoding;

import static id.co.juaracoding.util.TextUtil.countVowels;
import static id.co.juaracoding.util.TextUtil.isPalindrome;


public class Main {
    public static void main(String[] args) {

        String input = "Tebet";
        System.out.println("Palindrome " + isPalindrome(input));
        System.out.println("Count  Vowels " + countVowels(input));
    }
}