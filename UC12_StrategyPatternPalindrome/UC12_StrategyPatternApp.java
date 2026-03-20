import java.util.*;

// Step 1: Strategy Interface
interface PalindromeStrategy {
    boolean isPalindrome(String input);
}

// Step 2: Stack Strategy
class StackStrategy implements PalindromeStrategy {
    public boolean isPalindrome(String input) {

        input = input.replaceAll("\\s+", "").toLowerCase();
        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            stack.push(ch);
        }

        String reversed = "";
        while (!stack.isEmpty()) {
            reversed += stack.pop();
        }

        return input.equals(reversed);
    }
}

// Step 3: Deque Strategy
class DequeStrategy implements PalindromeStrategy {
    public boolean isPalindrome(String input) {

        input = input.replaceAll("\\s+", "").toLowerCase();
        Deque<Character> deque = new ArrayDeque<>();

        for (char ch : input.toCharArray()) {
            deque.add(ch);
        }

        while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }

        return true;
    }
}

// Step 4: Context Class
class PalindromeService {
    private PalindromeStrategy strategy;

    public void setStrategy(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean check(String input) {
        return strategy.isPalindrome(input);
    }
}

// Step 5: Main Class
public class UC12_StrategyPatternApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PalindromeService service = new PalindromeService();

        System.out.println("Choose Strategy:");
        System.out.println("1. Stack");
        System.out.println("2. Deque");

        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter string: ");
        String input = sc.nextLine();

        if (choice == 1) {
            service.setStrategy(new StackStrategy());
        } else {
            service.setStrategy(new DequeStrategy());
        }

        if (service.check(input)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a Palindrome");
        }

        sc.close();
    }
}