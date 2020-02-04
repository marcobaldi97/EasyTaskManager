package easyTaskManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField textFieldContraseña;
	private MainMenuWindow mainMenuFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, String odbc_location, String user_db, String pass_db) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow(odbc_location, user_db, pass_db);
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
	public LoginWindow(String odbc_location, String user_db, String pass_db) {
		setBackground(SystemColor.desktop);
		setTitle("Ingresar Usuario");
		setForeground(SystemColor.activeCaptionText);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblUsuario);
		
		textFieldUsuario = new JTextField();
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPassword);
		
		textFieldContraseña = new JPasswordField();
		contentPane.add(textFieldContraseña);
		textFieldContraseña.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
		            Statement statement = connectionLocal.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT usuario, passwordusuario FROM public.persona WHERE usuario='"+textFieldUsuario.getText()+"'");
					if(resultSet.next()) {
						String passwordIngresada = textFieldContraseña.getText();
						if(passwordIngresada.equals(resultSet.getString(2))) {
							mainMenuFrame = new MainMenuWindow(odbc_location, user_db, pass_db, textFieldUsuario.getText());
							mainMenuFrame.setVisible(true);
							setVisible(false);
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
		contentPane.add(btnEntrar);
	}

}
