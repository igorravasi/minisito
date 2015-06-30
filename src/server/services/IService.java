package server.services;

import java.io.IOException;
import java.net.Socket;

import server.basics.HttpRequest;


public interface IService {

	
	public void sendHttpResponse(final Socket clientSocket, HttpRequest request) throws IOException;
	
}
