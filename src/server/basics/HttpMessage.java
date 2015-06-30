package server.basics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpMessage {
	
	private static final String separator = ": ";
	private static final String enter ="\n";
	private OutputStream outStream;
	private OutputStreamWriter out;

	private Map<String, String> headers = new HashMap<String, String>();
	private List<String> cookies = new LinkedList<String>();
	
	public HttpMessage() {
	
		String serverDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
		headers.put("Date", serverDate);
	}

	public void sendResponseHeader(Socket clientSocket) throws IOException{
		
		//Inizializzo gli stream
		 
		outStream = clientSocket.getOutputStream();
		out = new OutputStreamWriter(outStream, Charset.forName("UTF-8").newEncoder());
		
		writeln("HTTP/1.1 200 OK");
		
		
		//Scrivo uno ad uno gli header sullo stream e dopo i cookie da impostare
		 
		Set<String> headerNames = headers.keySet();
		for (String name : headerNames) {
			String line = name + headers.get(name);
			writeln(line);
		}

		
		for (String singleCookie : cookies) {
			writeln("Set-cookie" + separator + singleCookie);
		}
		
		writeln("");
		
	}
	
	public void writeln(String line) throws IOException{
		if (line.length() > 0) {
			out.write(line + enter);
		} else {
			out.write(enter);
		}
		
	}
	
	public void closeHttpResponse() throws IOException{
		writeln("");
		out.close();
	}
	
	/**
	 * @return Ritorna l'OutputStream (binario)
	 */
	public OutputStream getOutStream() {
		return outStream;
	}
	
	/**
	 * @return Ritorna l'OutputStreamWriter con codifica UTF-8
	 */
	public OutputStreamWriter getOut(){
		return out;
	}
	
	
	public void addHeader(String headerName, String headerValue){
		
		headers.put(headerValue + separator, headerValue);
	}
	
	public void setCookie (String cookie){
			
		this.cookies.add(cookie);
	}

	
	
}
