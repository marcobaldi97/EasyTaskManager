package easyTaskManagerEx;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginWindowEx extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField textFieldContraseña;

	/**
	 * Launch the application.
	 * @param pass_db 
	 * @param user_db 
	 * @param odbc_location 
	 */
	public static void main(DtGlobalParams globalParams) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindowEx frame = new LoginWindowEx(globalParams);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param pass_db 
	 * @param user_db 
	 * @param odbc_location 
	 */
	public LoginWindowEx(DtGlobalParams globalParams) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 744, 471);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		contentPane.add(panel);
		
		JLabel lblLeftImage = new JLabel("New label");
		lblLeftImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLeftImage.setIcon(new ImageIcon(LoginWindowEx.class.getResource("/easyTaskManager/imgs/spiderweb1.jpg")));
		panel.add(lblLeftImage);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblWelcome = new JLabel("Welcome!");
		lblWelcome.setForeground(UIManager.getColor("Button.light"));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panel_1.add(lblWelcome);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setForeground(UIManager.getColor("Button.light"));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_2.add(lblUser);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
		panel_2.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(UIManager.getColor("Button.light"));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_2.add(lblPassword);
		
		textFieldContraseña = new JPasswordField();
		textFieldContraseña.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
		panel_2.add(textFieldContraseña);
		textFieldContraseña.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection connectionLocal = globalParams.getConnectionLocal()) {
		            Statement statement = connectionLocal.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT usuario, passwordusuario FROM public.persona WHERE usuario='"+textFieldUsuario.getText()+"'");
					if(resultSet.next()) {
						String passwordIngresada = textFieldContraseña.getText();
						if(passwordIngresada.equals(resultSet.getString(2))) {
							String current_user = textFieldUsuario.getText();
							globalParams.set_current_user(current_user);
							MainMenuWindowsEx mainMenuFrameEx = new MainMenuWindowsEx(globalParams);
							mainMenuFrameEx.setVisible(true);
							setVisible(false);
							dispose();
						}else {
							JOptionPane.showMessageDialog(contentPane,"Contraseña Incorrecta.","Error",JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(contentPane,"Usuario ingresado no existe.","Aviso",JOptionPane.WARNING_MESSAGE);
					}
		        } catch (SQLException f) {
		            //System.out.println("Connection failure.");
		            JOptionPane.showMessageDialog(contentPane,"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
		            f.printStackTrace();
		        }
			}
		});
		btnLogin.setForeground(Color.DARK_GRAY);
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 50));
		panel_3.add(btnLogin);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBackground(Color.WHITE);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnQuit.setVerticalAlignment(SwingConstants.BOTTOM);
		btnQuit.setForeground(Color.DARK_GRAY);
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel_3.add(btnQuit, BorderLayout.SOUTH);
		
		setUndecorated(true);//to remove borders,etc.
	}

}
