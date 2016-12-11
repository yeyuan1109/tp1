package tp1;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
import java.net.UnknownHostException; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 
import javax.swing.JScrollPane;


  
public class MainClient extends Frame{  
    
    //boolean bConnected = false;  
    Socket clientSocket=null;
    BufferedReader bufferedReader=null;
    PrintStream printStream = null;
    TextField tf = new TextField();
    TextArea ta = new TextArea();
    
    //JButton jbtSend;
    
  
    
    public static void main(String[] args) throws UnknownHostException, IOException {   
        new MainClient().creatFrame();
    }  
    
    public void creatFrame(){
		this.setTitle("client");
		//this.setSize(900, 900);
		//this.setBounds(500, 500, 0, 0);
        ta.setEditable(false);
        this.add(new JScrollPane(tf), BorderLayout.SOUTH);
        this.add(new JScrollPane(ta),BorderLayout.NORTH);
        
        //jbtSend = new JButton("send");
		//this.add(jbtSend,BorderLayout.EAST);
		//this.add(jbtSend,BorderLayout.CENTER);
		
        //this.add(ta, BorderLayout.NORTH);
        this.addWindowListener(new WindowAdapter() { //响应关闭窗口事件
        	public void windowClosing(WindowEvent e) {
        		disconnect();
                //System.out.println("disconnected");
                System.exit(0);
                }
            }
        );
        tf.addActionListener(new tfListener()); //响应输入事件
        this.pack();
        this.setVisible(true);
        connect();
        try{
			new Thread(new ClientThread(clientSocket,ta)).start();
		} catch(IOException e){
			e.printStackTrace();
		}
        //new Thread(r).start();
	}
	
	
    
    public void connect(){
		 try{
			 clientSocket = new Socket("127.0.0.1", 30000);
			 bufferedReader = new BufferedReader(new InputStreamReader(System.in));  
			 printStream = new PrintStream(clientSocket.getOutputStream());  	
			 //bConnected=true;
		 } catch (UnknownHostException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } 
	 }  
	 
	 public void disconnect(){
		 try{
			 printStream.close();
			 bufferedReader.close();
			 clientSocket.close();
		 } catch (IOException e) {            
            e.printStackTrace();
         }
     }
     
     class tfListener implements ActionListener { 
        public void actionPerformed(ActionEvent e) {
            String str = tf.getText();
            tf.setText("");
            printStream.println(str);
            printStream.flush();
        }
     }
}
