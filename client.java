package files;
import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
//implements Runnable
public class client extends JFrame  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JFrame jf;
    JTextArea ta;
    JPanel newPanel;
    JButton btn1;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            client frame = new client();
            try (Socket soc = new Socket("localhost", 5000)) {
                dis = new DataInputStream(soc.getInputStream());
                dos = new DataOutputStream(soc.getOutputStream());
                String s="";
                while((s = dis.readUTF())!="")
                {
                    System.out.println("Client nhận được" + s);
                    frame.ta.append(s);
                }
            } catch (Exception e) {
                System.err.println("Lỗi port và socket ko hoạt động");
            }
            //frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the frame.
     */
    public client() throws IOException {
        jf = new JFrame("Chương trình đọc ghi file");
        newPanel = new JPanel(new GridLayout(1, 1));
        //jf.setBounds(150,150,600,480);// set minimum and app
        jf.setSize(1200, 680);
        ta = new JTextArea(10, 10); // Nếu để frame thì chỉ nhận 1 component
        
       
        newPanel.add(ta);

        jf.add(newPanel);
        //jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}



