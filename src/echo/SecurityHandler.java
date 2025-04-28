package echo;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler {

    private static final SafeTable<String, String> userTable = new SafeTable<>();

    private boolean authenticated = false; 
    private String currentUser = null;

    public SecurityHandler(Socket s) {
        super(s);
    }

    public SecurityHandler() {
        super();
    }

    @Override
    protected String response(String msg) throws Exception {
        String[] parts = msg.split(" ", 3);
        String command = parts[0].toLowerCase();

        switch (command) {
            case "new":
                return handleNewUser(parts);
            case "login":
                return handleLogin(parts);
            default:
                return handleAuthenticatedRequest(msg);
        }
    }

    private String handleNewUser(String[] parts) {
        if (parts.length < 3) {
            return "Error: Invalid 'new' command. Usage: new <user> <password>";
        }
        String user = parts[1];
        String password = parts[2];

        // Add the new user to the user table if the username is unique
        synchronized (userTable) {
            if (userTable.get(user) == null) {
                userTable.put(user, password);
                return "User " + user + " created successfully.";
            } else {
                return "Error: User " + user + " already exists.";
            }
        }
    }

    private String handleLogin(String[] parts) {
        if (parts.length < 3) {
            return "Error: Invalid 'login' command. Usage: login <user> <password>";
        }
        String user = parts[1];
        String password = parts[2];

        // Verify the login credentials
        synchronized (userTable) {
            if (password.equals(userTable.get(user))) {
                authenticated = true;
                currentUser = user;
                return "Login successful. Welcome, " + user + "!";
            } else {
                authenticated = false;
                return "Error: Invalid username or password. Session terminated.";
            }
        }
    }

    private String handleAuthenticatedRequest(String msg) throws Exception {
        if (!authenticated) {
            return "Error: You must log in first. Session terminated.";
        }

        // Forward the request to the peer
        return super.response(msg);
    }
}