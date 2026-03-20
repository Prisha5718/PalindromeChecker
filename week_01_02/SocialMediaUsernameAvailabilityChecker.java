import java.util.*;

public class SocialMediaUsernameAvailabilityChecker {

    static class UsernameChecker {

        HashMap<String, Integer> users = new HashMap<>();
        HashMap<String, Integer> attempts = new HashMap<>();

        public boolean checkAvailability(String username) {

            attempts.put(username, attempts.getOrDefault(username, 0) + 1);

            return !users.containsKey(username);
        }

        public List<String> suggestAlternatives(String username) {

            List<String> suggestions = new ArrayList<>();

            suggestions.add(username + "1");
            suggestions.add(username + "2");
            suggestions.add(username.replace("_", "."));

            return suggestions;
        }

        public String getMostAttempted() {

            String maxUser = "";
            int max = 0;

            for (String key : attempts.keySet()) {
                if (attempts.get(key) > max) {
                    max = attempts.get(key);
                    maxUser = key;
                }
            }

            return maxUser + " (" + max + " attempts)";
        }
    }

    public static void main(String[] args) {

        UsernameChecker system = new UsernameChecker();

        system.users.put("john_doe", 1);
        system.users.put("admin", 2);

        System.out.println("john_doe available? " + system.checkAvailability("john_doe"));
        System.out.println("jane_smith available? " + system.checkAvailability("jane_smith"));

        System.out.println("Suggestions for john_doe: " + system.suggestAlternatives("john_doe"));

        system.checkAvailability("admin");
        system.checkAvailability("admin");
        system.checkAvailability("admin");

        System.out.println("Most attempted: " + system.getMostAttempted());
    }
}
