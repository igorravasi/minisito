package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import server.basics.HttpRequest;
import server.services.IService;



public class ServerThread extends Thread implements Runnable{

	private Socket clientSocket;
	
	private Map<String, IService> services = new HashMap<String, IService>();
	
	
	
	public ServerThread(Socket clientSocket,
			 Map<String, IService> services) {
		super();
		this.clientSocket = clientSocket;
		this.services = services;
	}
	

	@Override
	public void run() {

	
		try {
			
			HttpRequest request = new HttpRequest(clientSocket);
		
			writeLog(request);
			
			try {
				handleRequest(request);
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally {
				clientSocket.close();
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void handleRequest(HttpRequest request)throws FileNotFoundException, IOException {
			
		String page = request.getUri();

		IService service = services.get(page);
		
		while (service == null) {
			
			page = page.substring(0, page.lastIndexOf("/"));
			if (page.equalsIgnoreCase("")) {
				page = "/";
			}
			service = services.get(page);
		}
		
		service.sendHttpResponse(clientSocket, request);
			
		}
	
	private void writeLog(HttpRequest request){
		
		String logLine = request.getUri();
		if (request.getContent() != null ) {
			logLine += "\n\tPOST:\t" + request.getContent();
		}
		
		
		
		Map<String,String> cookies = request.getCookies();
		Set<String> keySet = cookies.keySet();
		
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String cookieName = (String) iterator.next();
			
			logLine += "\n\tCookie:\t" + cookieName + "=" + cookies.get(cookieName);
		}
		
		System.out.println(logLine);
		
	}
}
