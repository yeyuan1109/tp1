package tp1;

import java.io.*;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;  
import java.awt.*;
import javax.swing.*;
  
public class MainServer extends JFrame{
	private JTextArea jta = new JTextArea();  
      
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    
     public MainServer()throws IOException{
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);//eviter le deboredement du message

		setTitle("Server");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); 
		
		File h=new File("E:/INSA/3A/reseau/TP1/src/tp1/histoire.txt");
		try{
			if(!h.exists()){
				h.createNewFile();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
        ServerSocket serverSocket = new ServerSocket(30000);  
        int clientNo=1;
        System.out.println("Server ready..."); 
        while (true) {  
			
			
             
            Socket socket = serverSocket.accept();  
            jta.append("client "+clientNo+" connected\n");
            socketList.add(socket);  
            new Thread(new ServerThread(socket)).start();
            clientNo++;
            
        }
        
		
		
	}  
      
    public static void main(String[] args) throws IOException {  
		  new MainServer();
    }  
      
      
          
}  

