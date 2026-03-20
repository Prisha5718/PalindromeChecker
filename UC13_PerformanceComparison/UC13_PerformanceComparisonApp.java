import java.util.*;

// Different palindrome approaches
class PalindromeAlgorithms {

    // Method 1: Two-pointer
    public static boolean twoPointer(String str) {
        str = str.replaceAll("\\s+", "").toLowerCase();
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    // Method 2: Stack
    public static boolean stackMethod(String str) {
        str = str.replaceAll("\\s+", "").toLowerCase();
        Stack<Character> stack = new Stack<>();

        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }

        String reversed = "";
        while (!stack.isEmpty()) {
            reversed += stack.pop();
        }

        return str.equals(reversed);
    }

    // Method 3: Reverse String
    public static boolean reverseMethod(String str) {
        str = str.replaceAll("\\s+", "").toLowerCase();
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}

public class UC13_PerformanceComparisonApp {

    public static void main(String[] args) {

        String input = "Never Odd Or Even";

        long start, end;

        // Two Pointer
        start = System.nanoTime();
        boolean result1 = PalindromeAlgorithms.twoPointer(input);
        end = System.nanoTime();
        long time1 = end - start;

        // Stack
        start = System.nanoTime();
        boolean result2 = PalindromeAlgorithms.stackMethod(input);
        end = System.nanoTime();
        long time2 = end - start;

        // Reverse
        start = System.nanoTime();
        boolean result3 = PalindromeAlgorithms.reverseMethod(input);
        end = System.nanoTime();
        long time3 = end - start;

        System.out.println("Input: " + input);
        System.out.println("Two Pointer Result: " + result1 + " | Time: " + time1 + " ns");
        System.out.println("Stack Result: " + result2 + " | Time: " + time2 + " ns");
        System.out.println("Reverse Result: " + result3 + " | Time: " + time3 + " ns");
    }
}