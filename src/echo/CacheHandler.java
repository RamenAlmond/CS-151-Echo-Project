package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler {

    private static final SafeTable<String, String> cache = new SafeTable<>();

    public CacheHandler(Socket s) {
        super(s);
    }

    public CacheHandler() {
        super();
    }

    @Override
    protected String response(String msg) throws Exception {
        String cachedResponse = cache.get(msg);
        if (cachedResponse != null) {
            if (Server.DEBUG) {
                System.out.println("Cache hit for request: " + msg);
            }
            return cachedResponse; 
        }

        if (Server.DEBUG) {
            System.out.println("Cache miss for request: " + msg);
        }
        String response = super.response(msg);

        
        cache.put(msg, response);
        if (Server.DEBUG) {
            System.out.println("Cache updated for request: " + msg);
        }

        return response;
    }
}

class SafeTable<K, V> {
    private final java.util.Map<K, V> table = new java.util.HashMap<>();

    public synchronized V get(K key) {
        return table.get(key);
    }

    public synchronized void put(K key, V value) {
        table.put(key, value);
    }
}
