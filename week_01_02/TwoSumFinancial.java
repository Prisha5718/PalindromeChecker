import java.util.*;

public class TwoSumFinancial {

    static class Transaction {
        int id, amount;
        String merchant, account;
        long time;

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    static void twoSum(List<Transaction> list, int target) {
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {
            int comp = target - t.amount;
            if (map.containsKey(comp)) {
                System.out.println("TwoSum: " + map.get(comp).id + ", " + t.id);
                return;
            }
            map.put(t.amount, t);
        }
    }

    static void twoSumWithTime(List<Transaction> list, int target, long window) {
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {
            int comp = target - t.amount;
            if (map.containsKey(comp)) {
                Transaction prev = map.get(comp);
                if (Math.abs(t.time - prev.time) <= window) {
                    System.out.println("TwoSum (Time): " + prev.id + ", " + t.id);
                    return;
                }
            }
            map.put(t.amount, t);
        }
    }

    static void detectDuplicates(List<Transaction> list) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (Transaction t : list) {
            String key = t.amount + "-" + t.merchant;
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.account);
        }

        for (String key : map.keySet()) {
            if (map.get(key).size() > 1)
                System.out.println("Duplicate: " + key + " -> " + map.get(key));
        }
    }

    public static void main(String[] args) {

        List<Transaction> list = Arrays.asList(
                new Transaction(1, 500, "StoreA", "ACC1", 1000),
                new Transaction(2, 300, "StoreB", "ACC2", 1500),
                new Transaction(3, 200, "StoreC", "ACC3", 2000),
                new Transaction(4, 500, "StoreA", "ACC4", 2500)
        );

        twoSum(list, 500);
        twoSumWithTime(list, 500, 3600000);
        detectDuplicates(list);
    }
}
