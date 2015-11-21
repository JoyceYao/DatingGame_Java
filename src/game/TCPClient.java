package game;


import java.io.*;
import java.net.*;

public class TCPClient {
	
	Socket socket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	PrintWriter out;
	InputStream is;
	
	public void startTCP(String host, int port) throws UnknownHostException, IOException{
		socket = new Socket(host, port);
		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		is = socket.getInputStream();
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void closeTCP() throws IOException{
		socket.close();
	}
	
	public String read() throws IOException{
		String s = inFromServer.readLine();
		//String s = readAll();
		return s;
	}
	
	
	public String readAll() throws IOException{
		byte[] b = new byte[1024];
		int msg = is.read(b);
		return new String(b, 0, msg);
	}
	
	public void write(String output) throws IOException{
		//outToServer.writeBytes(output);
		out.println(output);
	}
}
