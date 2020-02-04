package easyTaskManager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUsuario extends JInternalFrame {
	private JTextField txtUsuario;
	private JTextField txtPassword;
	private JTextField txtNombre;
	private JTextField txtNombre_1;
	private JTextField txtApellido;
	private JTextField txtApellido_1;

	/**
	 * Launch the application.
	 * @param comboBoxPerfil 
	 */
	
	public void cargarNombresPerfiles(String odbc_location, String user_db, String pass_db, JComboBox comboBoxPerfil) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nombre_perfil FROM perfil");
			comboBoxPerfil.removeAllItems();
			while(resultSet.next()) {
				Object objeto = new Object();
				objeto = resultSet.getString(1);
				comboBoxPerfil.addItem(objeto);
			}
			//fin carga de comboBoxPrioridad
			connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}//end cargarNombresPerfiles
	
	private void addPersonaSQL(String odbc_location, String user_db, String pass_db, String newUsuario,	String password, String nombre1, String nombre2, String apellido1, String apellido2, String nombrePerfil) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id_perfil FROM perfil WHERE nombre_perfil ='"+nombrePerfil+"'");
			resultSet.next();
			int id_perfil = resultSet.getInt(1);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("INSERT INTO persona (usuario, passwordusuario, name1, name2, lastname1, lastname2, id_perfil)\r\n" + 
            		"VALUES ('"+newUsuario+"','"+password+"','"+nombre1+"','"+nombre2+"','"+apellido1+"','"+apellido2+"',"+id_perfil+");");
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
            JOptionPane.showMessageDialog(getContentPane(),"Se creó tarea correctamente.","Éxito",JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL o error de comillas simples.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}
	
	public static void main(String odbc_location, String user_db, String pass_db, String usuario) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUsuario frame = new AddUsuario(odbc_location, user_db, pass_db, usuario);
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
	public AddUsuario(String odbc_location, String user_db, String pass_db, String usuario) {
		JComboBox comboBoxNombrePerfil = new JComboBox();
		setTitle("A\u00F1adir usuario");
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtApellido.getText().equals("") || txtApellido_1.getText().equals("") || txtNombre.getText().equals("") || txtNombre_1.getText().equals("") || txtPassword.getText().equals("") || txtUsuario.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(),"Hay campos sin completar.","Aviso",JOptionPane.WARNING_MESSAGE);
				}else {
					String apellido1 = txtApellido.getText();
					String apellido2 = txtApellido_1.getText();
					String nombre1 = txtNombre.getText();
					String nombre2 = txtNombre_1.getText();
					String newUsuario = txtUsuario.getText();
					String password = txtPassword.getText();
					String nombrePerfil = comboBoxNombrePerfil.getModel().getSelectedItem().toString();
					addPersonaSQL(odbc_location, user_db, pass_db, newUsuario, password, nombre1, nombre2, apellido1, apellido2, nombrePerfil);
				}
			}
		});
		btnCrear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(btnCrear);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblNombres = new JLabel("Nombres:");
		lblNombres.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNombres);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtNombre_1 = new JTextField();
		txtNombre_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(txtNombre_1);
		txtNombre_1.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblApellidos);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_3.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtApellido_1 = new JTextField();
		txtApellido_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_3.add(txtApellido_1);
		txtApellido_1.setColumns(10);
		
		JLabel lblNombrePerfil = new JLabel("Nombre Perfil:");
		lblNombrePerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePerfil.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNombrePerfil);
		comboBoxNombrePerfil.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cargarNombresPerfiles(odbc_location, user_db, pass_db, comboBoxNombrePerfil);
		panel_1.add(comboBoxNombrePerfil);
	}
}