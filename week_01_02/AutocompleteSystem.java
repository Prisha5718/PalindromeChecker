
import java.util.*;


class TrieNode {

    Map<Character, TrieNode> children = new HashMap<>();

    Map<String, Integer> queries = new HashMap<>();

}


class AutocompleteSystem {


    TrieNode root = new TrieNode();

    Map<String, Integer> frequencyMap = new HashMap<>();


    public void insert(String query, int freq) {


        frequencyMap.put(query, frequencyMap.getOrDefault(query, 0) + freq);


        TrieNode node = root;


        for (char c : query.toCharArray()) {


            node.children.putIfAbsent(c, new TrieNode());

            node = node.children.get(c);


            node.queries.put(query, frequencyMap.get(query));

        }

    }


    public List<String> search(String prefix) {


        TrieNode node = root;


        for (char c : prefix.toCharArray()) {


            if (!node.children.containsKey(c))

                return new ArrayList<>();


            node = node.children.get(c);

        }


        PriorityQueue<Map.Entry<String, Integer>> pq =

                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());


        for (Map.Entry<String, Integer> entry : node.queries.entrySet()) {


            pq.offer(entry);


            if (pq.size() > 10)

                pq.poll();

        }


        List<String> result = new ArrayList<>();


        while (!pq.isEmpty())

            result.add(pq.poll().getKey());


        Collections.reverse(result);


        return result;

    }


    public void updateFrequency(String query) {


        insert(query, 1);

    }


    public static void main(String[] args) {


        AutocompleteSystem system = new AutocompleteSystem();


        system.insert("java tutorial", 1234567);

        system.insert("javascript", 987654);

        system.insert("java download", 456789);

        system.insert("java 21 features", 1);


        System.out.println(system.search("jav"));


        system.updateFrequency("java 21 features");

        system.updateFrequency("java 21 features");


        System.out.println(system.search("java"));

    }

}

