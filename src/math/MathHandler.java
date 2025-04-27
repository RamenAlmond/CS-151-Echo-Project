package math;

import java.net.*;

import echo.*;

public class MathHandler extends RequestHandler {

	public MathHandler(Socket sock) {
		super(sock);
	}

	public MathHandler() {
		super();
	}

	protected String response(String request) throws Exception {
		String result = "";
		String[] parts = request.split(" ");
		for(int i = 0; i < parts.length; i++) {
			if (parts[i].equalsIgnoreCase("add")) {
				double sum = 0;
				for (int j = i + 1; j < parts.length; j++) {
					sum += Double.parseDouble(parts[j]);
				}
				result = "" + sum;
				break;
			} else if (parts[i].equalsIgnoreCase("sub")) {
				double difference = Double.parseDouble(parts[i + 1]);
				for (int j = i + 2; j < parts.length; j++) {
					difference -= Double.parseDouble(parts[j]);
				}
				result = "" + difference;
				break;
			} else if (parts[i].equalsIgnoreCase("mult")) {
				double product = 1;
				for (int j = i + 1; j < parts.length; j++) {
					product *= Double.parseDouble(parts[j]);
				}
				result = "" + product;
				break;
			} else if (parts[i].equalsIgnoreCase("div")) {
				double quotient = Double.parseDouble(parts[i + 1]);
				for (int j = i + 2; j < parts.length; j++) {
					quotient /= Double.parseDouble(parts[j]);
				}
				result = "" + quotient;
				break;
			} else if(parts[i].equalsIgnoreCase("help")){
				result = "command ::= operator num num etc. operator ::= add | mul | sub | div num ::= any number";
				break;
			} else {
				result = "Unknown command: " + parts[i];
				break;
			}
		}
		return result;
	}

}