package server.basics;

import java.io.IOException;
import java.net.Socket;

public class HttpStringMessage extends HttpMessage{
	
	/**
	 * Aggiunge il metodo sendMessage, che invia una stringa automatizzando la procedura dunque semplificandola.
	 * 
	 * @param clientSocket
	 * @param messageBody
	 * @throws IOException
	 */
	
	public void sendMessage(Socket clientSocket, String messageBody) throws IOException{
		
		addHeader("Content-Type", "text/html; charset=utf-8");
		sendResponseHeader(clientSocket);
		getOut().write(messageBody);
		closeHttpResponse();
	}
	
}
