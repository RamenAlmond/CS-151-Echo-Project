package echo;
import java.net.Socket;

class HitCounter {
    private int hits = 0;
    synchronized public void incHits() { hits++; }
    synchronized public int getHits() { return hits; }
}
public class HitCountHandler extends ProxyHandler {

   private static HitCounter counter = new HitCounter();

    public HitCountHandler(Socket s) {
        super(s);
        counter.incHits();
    }
    public HitCountHandler() {
        super();
        counter.incHits();
    }

    protected String response(String request) throws Exception {
        if (request.equalsIgnoreCase("hits")) {
            return "" + counter.getHits();
        } else {
            return super.response(request);
        }
    }

}