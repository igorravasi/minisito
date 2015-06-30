package server.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {

	private String uri;
	
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> cookies = new HashMap<String, String>();
	private String content = null;
	
	
	public HttpRequest(final Socket clientSocket) throws IOException{
		
		super();
		BufferedReader in = new BufferedReader(	new InputStreamReader( clientSocket.getInputStream() ) );
		
		String line = in.readLine();
		String[] lineElements=line.split(" ");
		
		
		// Controllo se il metodo della richiesta e' POST
		
		boolean isPost = lineElements[0].equalsIgnoreCase("POST") ? true : false;
		
		
		// L'uri e' nella prima riga, al secondo posto.
		
		uri = lineElements[1];

		while(line!=null && line.length() != 0){
			
			String[] headerElements = line.split(" ", 2);
			if (headerElements.length == 2) {
				headers.put(headerElements[0], headerElements[1]);
			}
			
			line = in.readLine();
		}
		
		
		
		//Se la richiesta era di tipo POST devo caricare il contenuto POSTato
		
		if (isPost) {
			content = readContent(in);
		}

	}
	
	
	private String readContent(BufferedReader in) throws IOException{
				
		int contentLength;
		try {
			contentLength = Integer.parseInt( headers.get("Content-Length:") );
		} catch (NumberFormatException e) {
			contentLength = 0;
		}
				
		// Leggo carattere per carattere
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < contentLength; i++) {
			
			int c = in.read();
			builder.append((char) c);
		}
	
		return builder.toString();
	}
	

	private void loadCookies(){
	
		
		String cookiesReceived = headers.get("Cookie:");
		
		if (cookiesReceived != null) {
			StringTokenizer tokenizer = new StringTokenizer(cookiesReceived, "; ");

			// Per ogni stringa separata da ';' la provo a dividere in nome=valore e carico questi ultimi nella mappa dei cookies.
			
			while (tokenizer.hasMoreTokens()) {
				String cookie = tokenizer.nextToken();
				
				String cookieParts[] = cookie.split("=",2);
				if (cookieParts.length == 2) {
					cookies.put(cookieParts[0], cookieParts[1]);
				}
				
			}
		
		}
		
	}
	
	
	public Map<String,String> getCookies(){
		
		// Carico i cookie solo alla prima chiamata di questo metodo
		if (cookies.size() == 0) {
			loadCookies();
		}
		
		return cookies;
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getContent(){
		return content;
	}
	

}
