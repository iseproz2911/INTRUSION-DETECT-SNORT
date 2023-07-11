import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import files.client;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;

public class main extends JFrame {

	private JPanel contentPane;
	private static DataInputStream dis;
    private static DataOutputStream dos;
	Thread thread;
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 652);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnAlert = new JButton("Alert");
		btnAlert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(thread != null) thread.stop();
				Alerts a = new Alerts();
	            a.GUI();
	            a.setLocation(450, 110);
			}
		});
		btnAlert.setBounds(331, 20, 111, 21);
		panel.add(btnAlert);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(110, 20, 111, 21);
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 65, 775, 540);
		panel.add(scrollPane);
		
		JTextArea ta = new JTextArea();
		scrollPane.setViewportView(ta);
		
		JButton btnMore = new JButton("More Info");
		btnMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(thread != null) thread.stop();
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
							ta.removeAll();
							try {
					            try{
					                dis = new DataInputStream(soc.getInputStream());
					                dos = new DataOutputStream(soc.getOutputStream());
					                String s="";
					                dos.writeUTF("Lenh");
					                
					                s = dis.readUTF();
					                    System.out.println("Client nhận được" + s);
					                    ta.setText(s);
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
		});
		btnMore.setBounds(552, 20, 111, 21);
		panel.add(btnMore);
	}
}
