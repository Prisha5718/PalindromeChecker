import java.util.*;
import java.util.concurrent.*;


class PageEvent {

    String url;

    String userId;

    String source;


    PageEvent(String url, String userId, String source) {

        this.url = url;

        this.userId = userId;

        this.source = source;

    }

}


class PageStats {

    int visitCount = 0;

    Set<String> uniqueUsers = new HashSet<>();

}


public class StreamingAnalytics {


    private Map<String, PageStats> pageStatsMap = new ConcurrentHashMap<>();

    private Map<String, Integer> trafficSourceMap = new ConcurrentHashMap<>();

    private int totalEvents = 0;


    public synchronized void processEvent(PageEvent event) {


        totalEvents++;


        pageStatsMap.putIfAbsent(event.url, new PageStats());

        PageStats stats = pageStatsMap.get(event.url);


        stats.visitCount++;

        stats.uniqueUsers.add(event.userId);


        trafficSourceMap.put(

                event.source,

                trafficSourceMap.getOrDefault(event.source, 0) + 1

        );

    }


    private List<Map.Entry<String, PageStats>> getTopPages() {


        PriorityQueue<Map.Entry<String, PageStats>> pq =

                new PriorityQueue<>((a, b) ->

                        a.getValue().visitCount - b.getValue().visitCount);


        for (Map.Entry<String, PageStats> entry : pageStatsMap.entrySet()) {
            pq.offer(entry);
            if (pq.size() > 10)
                pq.poll();

        }

        List<Map.Entry<String, PageStats>> result = new ArrayList<>();
        while (!pq.isEmpty())
            result.add(pq.poll());
        Collections.reverse(result);
        return result;

    }

    public synchronized void getDashboard() {
        System.out.println("\nTop Pages:");
        List<Map.Entry<String, PageStats>> topPages = getTopPages();
        int rank = 1;
        for (Map.Entry<String, PageStats> entry : topPages) {
            System.out.println(rank++ + ". " +

                    entry.getKey() +

                    " - " + entry.getValue().visitCount +

                    " views (" +

                    entry.getValue().uniqueUsers.size() +

                    " unique)");

        }


        System.out.println("\nTraffic Sources:");
        for (Map.Entry<String, Integer> entry : trafficSourceMap.entrySet()) {


            double percent =

                    (entry.getValue() * 100.0) / totalEvents;


            System.out.println(entry.getKey() +

                    ": " + String.format("%.2f", percent) + "%");

        }

    }

    public static void main(String[] args) {
        StreamingAnalytics analytics = new StreamingAnalytics();
        ScheduledExecutorService scheduler =

                Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {

            analytics.getDashboard();

        }, 5, 5, TimeUnit.SECONDS);

        analytics.processEvent(new PageEvent("/article/breaking-news","user_123","google"));
        analytics.processEvent(new PageEvent("/article/breaking-news","user_456","facebook"));
        analytics.processEvent(new PageEvent("/sports/championship","user_789","direct"));
        analytics.processEvent(new PageEvent("/sports/championship","user_123","google"));

    }

}
