package server.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore di valori dei parametri ("costanti" e ContentType) del server ed i suoi servizi,
 * in modo da non dover riavviare il server quando questi ultimi cambiano.
 */
public class MemoServerConfigurator {

	private static final String dir = "config/";
	private static final String valuesPath = dir + "values.cfg";
	private static final String contentTypesPath = dir + "content-types.cfg";
	private static final String comments = "//";
	private static final String delim = ":\t";
	
	private static MemoServerConfigurator  configurator = null;
	private Map<String, String> contentTypes = new HashMap<String, String>();
	private Map<String, String> values = new HashMap<String, String>();
	
	private Map<String, Long> modifications = new HashMap<String, Long>();
	
	/*
	 * Singleton: costruttore private
	 */
	private MemoServerConfigurator(){
		
		modifications.put(valuesPath, 0L);
		modifications.put(contentTypesPath, 0L);
	}
	
	
	public static MemoServerConfigurator getInstance(){
		if (configurator == null) {
			configurator = new MemoServerConfigurator();
		} 
		
		return configurator;
	}
	
	

	private void reload(String path, Map<String, String> mappa){
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = reader.readLine();
			
			while (line != null) {
				
				if ( !(line.startsWith(comments)) ) {
					
					String parts[] = line.split(delim, 2);
					if (parts.length == 2) {
						mappa.put(parts[0], parts[1]);
					}
		
				}
				
				line = reader.readLine();
			}		

			reader.close();
			
		}
		
		
		 //Non faccio nulla per gestire l'eccezione, tranne che il log, dato che la cosa migliore da fare e' lasciare i calori cosi com'erano prima della chiamata
		 

		  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private boolean hasBeenModified(String path){
		
		Long last = new File(path).lastModified();
		Long difference = last - modifications.get(path);
		
		if (difference > 0) {
			modifications.put(path, last); 
			return true;
		}
		
		return false;
		
	}
	


	public String getContentType(String extension){
		if (hasBeenModified(contentTypesPath)) {
			reload(contentTypesPath, contentTypes);
		}
		return contentTypes.get(extension);
	}
	
	public String getValue(String name){

		if (hasBeenModified(valuesPath)) {
			reload(valuesPath, values);
		}

		return values.get(name);
	}
	

}
