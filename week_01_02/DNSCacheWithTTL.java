import java.util.*;

public class DNSCacheWithTTL {

    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, int ttl) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttl * 1000;
        }
    }

    static class DNSCache {

        HashMap<String, DNSEntry> cache = new HashMap<>();
        int hits = 0;
        int misses = 0;

        public String resolve(String domain) {

            if (cache.containsKey(domain)) {
                DNSEntry entry = cache.get(domain);

                if (System.currentTimeMillis() < entry.expiryTime) {
                    hits++;
                    System.out.println(domain + " → Cache HIT → " + entry.ip);
                    return entry.ip;
                } 
                else {
                    System.out.println(domain + " → Cache EXPIRED");
                    cache.remove(domain);
                }
            }

            misses++;
            String ip = "172.217.14." + (new Random().nextInt(200));
            System.out.println(domain + " → Cache MISS → Query upstream → " + ip);

            cache.put(domain, new DNSEntry(ip, 5));
            return ip;
        }

        public void getCacheStats() {
            int total = hits + misses;
            double hitRate = total == 0 ? 0 : (hits * 100.0) / total;
            System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
        }
    }

    public static void main(String[] args) throws Exception {

        DNSCache dns = new DNSCache();

        dns.resolve("google.com");
        dns.resolve("google.com");

        Thread.sleep(6000);

        dns.resolve("google.com");

        dns.getCacheStats();
    }
}
