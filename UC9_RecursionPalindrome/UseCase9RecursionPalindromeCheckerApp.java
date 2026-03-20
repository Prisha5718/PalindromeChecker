public class UseCase9RecursionPalindromeCheckerApp {

    static boolean checkPalindrome(String str, int start, int end) {

        if (start >= end) {
            return true;
        }

        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }

        return checkPalindrome(str, start + 1, end - 1);
    }

    public static void main(String[] args) {

        String str = "madam";

        boolean result = checkPalindrome(str, 0, str.length() - 1);

        if (result) {
            System.out.println(str + " is a Palindrome");
        } else {
            System.out.println(str + " is NOT a Palindrome");
        }
    }
}