package tp1;

import java.awt.TextArea;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.Socket;  
  
public class ClientThread implements Runnable {  
  
    private Socket socket;
    private TextArea ta;
    BufferedReader bufferedReader = null;  
      
    public ClientThread(Socket socket, TextArea ta) throws IOException{  
        this.socket = socket;
        this.ta=ta;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));  
    }  
      
    public void run() {  
        String conString = null;  
        try {  
            while ((conString = bufferedReader.readLine()) != null) {  
                System.out.println(conString);
                String str;
                str = ta.getText()+"\n"+conString;
                ta.setText(str);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
