
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class Alerts extends JFrame implements ActionListener{
    
    public JLabel lb1;
    JButton b1;
    JFrame jFrame;
    JTable table;
    JScrollPane scrollPane;
    String[] columnNames;
    Object[][] data;
    JPanel pn1, pn2;
    Thread thread;
    DefaultTableModel defaultTableModel;
    
    public void GUI() {
        lb1 = new JLabel("ALERTS GMAIl");
        pn1 = new JPanel(new FlowLayout());
        this.setTitle("ALERTS");
        JFrame jFrame = new JFrame();
        String[] columnNames = {"SID","Protocol", "IP HomeNet", "Description", "IP Hacker", "DateTime", "Detail"};
        Object[][] data = {};
        defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        jFrame.add(scrollPane, BorderLayout.CENTER);
        jFrame.setSize(375, 250);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        b1 = new JButton("Back");
        b1.addActionListener(this);
        b1.setMargin(new Insets(0,0,0,0));



       pn2 = new JPanel(new BorderLayout());
        pn2.add(lb1, BorderLayout.PAGE_START);
        pn2.add(scrollPane, BorderLayout.CENTER);
        pn2.add(b1, BorderLayout.PAGE_END);
        add(pn2);
        this.setSize(650, 550);
        this.show();
        
        thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				 Socket soc = null;
				try {
					soc = new Socket("localhost", 5000);
				} catch (UnknownHostException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				while(true) {
					System.out.println("Lap");
				// TODO Auto-generated method stub
					//defaultTableModel;
					try {
			            try{
			                DataInputStream dis = new DataInputStream(soc.getInputStream());
			                DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			                String s="";
			                dos.writeUTF("Lenh");
			                
			                s = dis.readUTF();
			                    System.out.println("Client nhận được" + s);
			                    
//			                    [**] [1:10000001:1] My ping test alert [**]
//	                    		[Priority: 0] 
//	                    		12/25-20:18:59.670497 192.168.222.1 -> 192.168.222.130
//	                    		ICMP TTL:128 TOS:0x0 ID:63177 IpLen:20 DgmLen:60
//	                    		Type:8  Code:0  ID:1   Seq:5  ECHO
//
			                    System.out.println("=============================================");
			                    
			                    String[] splited = s.split("\\n", -1);
			                    
			                    //"SID","Protocol", "IP HomeNet", "Description", "IP Hacker", "DateTime"
			                    
			                    String SID = "", Protocol = "", IPHomeNet = "", Des = "", IPHacker = "", Datetime = "", Detail = ""; 
			                    
			                    int length = splited.length;
			                    System.out.println("Length: " + length);
			                    int pivot = 0;
			                    //String[] current = splited[pivot++].split("\\[\\*\\*\\]")[1].split("\\]");
//	                    		for(int i=0;i<current.length;i++) {
//	                    			System.out.println(current[i]);
//	                    		}
			                    
			                    if (defaultTableModel.getRowCount() > 0) {
			                        for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
			                            defaultTableModel.removeRow(i);
			                        }
			                    }
			                    while(length > 0) {
			                    	if(pivot%6 == 0) {
			                    		String[] current = splited[pivot++].split("\\[\\*\\*\\]")[1].split("\\]");
			                    		SID = current[0] + "]";
			                    		Des = current[1];
			                    		pivot++;
			                    		current = splited[pivot++].split(" ");
			                    		Datetime = current[0]; IPHacker = current[1]; IPHomeNet = current[3];
			                    		current = splited[pivot++].split(" ");
			                    		Protocol = current[0];
			                    		Detail = "";
			                    		for(int j=1;j<current.length;j++) {
			                    			Detail = Detail + current[j] +" ";
			                    		}
			                    		pivot++;
			                    		pivot++;
			                    	}
			                    	
			                    	String[] data2 = new String[]{SID, Protocol, IPHomeNet, Des, IPHacker, Datetime, Detail};
				                    defaultTableModel.addRow(data2);
			                    	length -= 1;
			                    }
			                    
			                    
			                    
			                    System.out.println("=============================================");
			                    //ta.setText(s);
			                    System.out.println("Da gan cho Textarea");
			            } catch (Exception e1) {
			                System.err.println("Lỗi port và socket ko hoạt động");
			            }
			            //frame.setVisible(true);
			        } catch (Exception e2) {
			            e2.printStackTrace();
			        }
					System.out.println("Ket thuc vong lap");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
    }



   public static void main(String[] args) {
        Alerts a = new Alerts();
        a.GUI();
        
        
    }



@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(thread != null) thread.stop();
	setVisible(false);
}
}