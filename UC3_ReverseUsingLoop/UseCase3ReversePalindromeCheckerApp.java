public class UseCase3ReversePalindromeCheckerApp {

    public static void main(String[] args) {

        // Step 1: Declare string
        String word = "level";

        // Step 2: Create empty string for reversed value
        String reversed = "";

        // Step 3: Reverse using loop
        for (int i = word.length() - 1; i >= 0; i--) {
            reversed = reversed + word.charAt(i);
        }

        // Step 4: Compare original and reversed
        if (word.equals(reversed)) {
            System.out.println("The string is a Palindrome.");
        } else {
            System.out.println("The string is NOT a Palindrome.");
        }

    }
}