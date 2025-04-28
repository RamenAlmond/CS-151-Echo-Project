package echo;

import java.net.Socket;
import java.util.HashMap;

public class CacheProxy extends ProxyHandler {

    private static final SafeTable cache = new SafeTable();

    public CacheProxy(Socket s) {
        super(s);
    }

    public CacheProxy() {
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

class SafeTable extends HashMap<String, String> {

    @Override
    public synchronized String get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized String put(String key, String value) {
        return super.put(key, value);
    }
}
