package files;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class server1 {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            while (true) {
                Socket s = server.accept();
                Xuly x = new Xuly(s);
                x.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //new server();
    }

    public server1() {

    }
}

class Xuly extends Thread {
    Socket soc;

    public Xuly(Socket s) {
        soc = s;
    }

    private static String func(String s) throws IOException {
        String value = "";
        FileInputStream fis = new FileInputStream("D:\\PBL4_Snort\\alert (1)"); 
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                value += line + '\n';
            }
        }
        return value;

    }

    @Override
    public void run() {
        try {
            while (true) {
            	DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            	DataInputStream dis = new DataInputStream(soc.getInputStream());
            	System.out.println("Lap Server");
            	boolean loop = true;
            	while(loop) {
            		String in = dis.readUTF();
            		System.out.println(in.equals("Lenh"));
            		if(in.equals("Lenh")) loop = false;
            	}
            	System.out.println("Nhan Lenh");
                String s = "";
                String re = func(s);
                System.out.println("re: " + re);
                dos.writeUTF(re);    
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}