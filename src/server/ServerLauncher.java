package server;

import server.config.MemoServerConfigurator;
import server.services.BinaryFileService;
import server.services.TextFileService;

/**
 *@category serverlauncher
 *@package server
 *Questa classe serve a far partire il server del gioco
 *
 */

public class ServerLauncher {

	public static String PORT_NUMBER_IDENTIFIER = "port_number";
	
	public static void main(String[] args) {

		System.err.println("server script started");
		HttpMemoServer server = new HttpMemoServer();
		
		int port = Integer.parseInt(MemoServerConfigurator.getInstance().getValue(PORT_NUMBER_IDENTIFIER));
		server.setPort(port);
		
		TextFileService fileService = new TextFileService();
		BinaryFileService binaryService = new BinaryFileService();


		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		server.addService("/resources", binaryService);
		
		
		server.launch();
		
	}
	


}
