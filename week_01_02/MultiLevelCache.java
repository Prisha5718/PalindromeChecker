import java.util.*;

public class MultiLevelCache {

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private int capacity;

        LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    static LRUCache<String, String> L1 = new LRUCache<>(3);
    static LRUCache<String, String> L2 = new LRUCache<>(5);
    static HashMap<String, String> L3 = new HashMap<>();
    static HashMap<String, Integer> accessCount = new HashMap<>();

    static String getVideo(String id) {

        if (L1.containsKey(id)) {
            System.out.println("L1 HIT");
            return L1.get(id);
        }

        if (L2.containsKey(id)) {
            System.out.println("L2 HIT → Promoted to L1");
            String data = L2.get(id);
            L1.put(id, data);
            return data;
        }

        if (L3.containsKey(id)) {
            System.out.println("L3 HIT → Added to L2");
            String data = L3.get(id);
            L2.put(id, data);
            return data;
        }

        System.out.println("MISS");
        return null;
    }

    static void addVideo(String id, String data) {
        L3.put(id, data);
    }

    static void invalidate(String id) {
        L1.remove(id);
        L2.remove(id);
        L3.remove(id);
        accessCount.remove(id);
        System.out.println("Invalidated: " + id);
    }

    static void printStats() {
        System.out.println("L1 Size: " + L1.size());
        System.out.println("L2 Size: " + L2.size());
        System.out.println("L3 Size: " + L3.size());
    }

    public static void main(String[] args) {

        addVideo("video1", "Data1");
        addVideo("video2", "Data2");
        addVideo("video3", "Data3");

        getVideo("video1");
        getVideo("video1");
        getVideo("video2");

        printStats();

        invalidate("video1");

        printStats();
    }
}
