package tp1;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.*;  
import java.net.Socket;  

  
public class ServerThread implements Runnable {  
    Socket socket = null;  
    BufferedReader bufferedReader = null;  
      
    public ServerThread(Socket socket) throws IOException {  
        this.socket = socket;  
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
    } 
    
   
      
    public void run() {  
        String content = null;  
        try {  
			FileInputStream fr = new FileInputStream("E:/INSA/3A/reseau/TP1/src/tp1/histoire.txt");
			byte[] buf = new byte[1000];
			fr.read(buf);
			String myStr=new String(buf);
			fr.close();
			PrintStream pS = new PrintStream(socket.getOutputStream());  
            pS.println("Histoire :");
            pS.println(myStr);
            while((content = readFromClient()) != null){  
				
                for (Socket socket:MainServer.socketList) {  
                   PrintStream printStream = new PrintStream(socket.getOutputStream());  
                   printStream.println("From "+socket.getInetAddress()+" : "+content);  //afficher dans le chat room
                   content = "From "+socket.getInetAddress()+" : "+content; //enrigistrer dans l'historique  
                } 
                //PrintStream printStream = new PrintStream(socket.getOutputStream());  
                //printStream.println("From "+socket.getInetAddress()+" : "+content);  
                //content = "From "+socket.getInetAddress()+" : "+content;
                try{
					FileOutputStream fos = new FileOutputStream("E:/INSA/3A/reseau/TP1/src/tp1/histoire.txt",true);
					fos.write(content.getBytes());
					fos.write("\r\n".getBytes());

					
					fos.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				//index++;
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    private String readFromClient(){  
        try {  
            return bufferedReader.readLine();  
        } catch (IOException e) {  
            MainServer.socketList.remove(socket);  
        }  
        return null;  
    }  
}  

