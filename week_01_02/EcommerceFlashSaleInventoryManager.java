import java.util.*;

public class EcommerceFlashSaleInventoryManager {

    static class FlashSale {

        HashMap<String, Integer> stock = new HashMap<>();
        HashMap<String, LinkedList<Integer>> waitingList = new HashMap<>();

        public void checkStock(String productId) {
            int count = stock.getOrDefault(productId, 0);
            System.out.println(productId + " → " + count + " units available");
        }

        public synchronized void purchaseItem(String productId, int userId) {

            int count = stock.getOrDefault(productId, 0);

            if (count > 0) {
                stock.put(productId, count - 1);
                System.out.println("User " + userId + " purchase Success, " + (count - 1) + " units remaining");
            } 
            else {
                waitingList.putIfAbsent(productId, new LinkedList<>());
                waitingList.get(productId).add(userId);

                int position = waitingList.get(productId).size();
                System.out.println("User " + userId + " → Added to waiting list, position #" + position);
            }
        }
    }

    public static void main(String[] args) {

        FlashSale system = new FlashSale();

        system.stock.put("IPHONE15_256GB", 3);

        system.checkStock("IPHONE15_256GB");

        system.purchaseItem("IPHONE15_256GB", 12345);
        system.purchaseItem("IPHONE15_256GB", 67890);
        system.purchaseItem("IPHONE15_256GB", 22222);
        system.purchaseItem("IPHONE15_256GB", 99999);
    }
}
