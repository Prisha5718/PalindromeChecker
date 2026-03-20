import java.util.concurrent.ConcurrentHashMap;


class TokenBucket {


    int tokens;

    final int maxTokens;

    final double refillRate;

    long lastRefillTime;


    public TokenBucket(int maxTokens, double refillRate) {

        this.maxTokens = maxTokens;

        this.refillRate = refillRate;

        this.tokens = maxTokens;

        this.lastRefillTime = System.currentTimeMillis();

    }


    private void refill() {

        long now = System.currentTimeMillis();


        double secondsPassed = (now - lastRefillTime) / 1000.0;


        int tokensToAdd = (int)(secondsPassed * refillRate);


        if (tokensToAdd > 0) {

            tokens = Math.min(maxTokens, tokens + tokensToAdd);

            lastRefillTime = now;

        }

    }


    public synchronized boolean allowRequest() {


        refill();


        if (tokens > 0) {

            tokens--;

            return true;

        }


        return false;

    }


    public synchronized int getRemainingTokens() {

        refill();

        return tokens;

    }


    public synchronized long retryAfterSeconds() {

        if (tokens > 0) return 0;


        double seconds = 1.0 / refillRate;

        return (long)Math.ceil(seconds);

    }

}


public class RateLimiter {


    private ConcurrentHashMap<String, TokenBucket> clientBuckets = new ConcurrentHashMap<>();


    private final int MAX_REQUESTS = 1000;

    private final double REFILL_RATE = 1000.0 / 3600.0;


    public String checkRateLimit(String clientId) {


        clientBuckets.putIfAbsent(

                clientId,

                new TokenBucket(MAX_REQUESTS, REFILL_RATE)

        );


        TokenBucket bucket = clientBuckets.get(clientId);


        if (bucket.allowRequest()) {


            return "Allowed (" + bucket.getRemainingTokens() + " requests remaining)";

        }

        else {


            return "Denied (0 requests remaining, retry after "

                    + bucket.retryAfterSeconds() + "s)";

        }

    }


    public static void main(String[] args) {


        RateLimiter limiter = new RateLimiter();


        System.out.println(limiter.checkRateLimit("abc123"));

        System.out.println(limiter.checkRateLimit("abc123"));

        System.out.println(limiter.checkRateLimit("abc123"));

    }

}
