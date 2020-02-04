package easyTaskManager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ModificarEliminarUsuario extends JInternalFrame {
	private JTextField txtUsuario;
	private JTextField txtPassword;
	private JTextField txtNombre;
	private JTextField txtNombre_1;
	private JTextField txtApellido;
	private JTextField txtApellido_1;

	/**
	 * Launch the application.
	 */
	
	protected void cargarDatosDePersonaConUsuario(String odbc_location, String user_db, String pass_db, String usuario, JComboBox comboBoxNombrePerfil, JTextField txtPassword, JTextField txtNombre, JTextField txtNombre_1, JTextField txtApellido, JTextField txtApellido_1) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
            //Carga perfiles.
			ResultSet  resultSet = statement.executeQuery("SELECT id_perfil FROM persona WHERE usuario='"+usuario+"'");
			resultSet.next();
			int idPerfilUsuario = resultSet.getInt(1);
			resultSet = statement.executeQuery("SELECT nombre_perfil FROM perfil WHERE id_perfil="+idPerfilUsuario);
			resultSet.next();
			String perfilUsuario = resultSet.getString(1);
            resultSet = statement.executeQuery("SELECT nombre_perfil FROM perfil");//reseteo la consulta
            comboBoxNombrePerfil.removeAllItems();
            while (resultSet.next()) {
            	Object object = resultSet.getObject(1);
            	comboBoxNombrePerfil.addItem(object);
            	if(object.equals(perfilUsuario)) comboBoxNombrePerfil.setSelectedItem(object);
            }
			//Fin carga perfiles.
            //carga datos miscelaneos.
			resultSet = statement.executeQuery("SELECT passwordusuario, name1, name2, lastname1, lastname2 FROM persona WHERE usuario='"+usuario+"'");
			resultSet.next();
			txtPassword.setText(resultSet.getString(1));
			txtNombre.setText(resultSet.getString(2));
			txtNombre_1.setText(resultSet.getString(3));
			txtApellido.setText(resultSet.getString(4));
			txtApellido_1.setText(resultSet.getString(5));
            //Fin carga de datos miscelaneos.
			connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"ID incorrecta o no se pudo conectar con la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
        }
		
	}//fin cargarDatosDePersonaConUsuario.
	
	protected void modificarDatosDePersonaConUsuario(String odbc_location, String user_db, String pass_db, String usuario, JComboBox comboBoxNombrePerfil, JTextField txtPassword, JTextField txtNombre, JTextField txtNombre_1, JTextField txtApellido, JTextField txtApellido_1) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
            String nombrePerfil = comboBoxNombrePerfil.getSelectedItem().toString();
			ResultSet resultSet = statement.executeQuery("SELECT id_perfil FROM perfil WHERE nombre_perfil ='"+nombrePerfil+"'");
			resultSet.next();
			int id_perfil = resultSet.getInt(1);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("UPDATE persona\r\n" + 
            		"SET passwordusuario = '"+txtPassword.getText()+"', name1 = '"+txtNombre.getText()+"', name2 = '"+txtNombre_1.getText()+"', lastname1 = '"+txtApellido.getText()+"', lastname2 = '"+txtApellido_1.getText()+"', id_perfil = "+id_perfil+" WHERE usuario = '"+usuario+"';");
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
            JOptionPane.showMessageDialog(getContentPane(),"Usuario modificado con éxito.","Éxito",JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"ID incorrecta o no se pudo conectar con la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
        }
	}
	
	public static void main(String odbc_location, String user_db, String pass_db) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarEliminarUsuario frame = new ModificarEliminarUsuario(odbc_location, user_db, pass_db);
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
	public ModificarEliminarUsuario(String odbc_location, String user_db, String pass_db) {
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		JComboBox comboBoxNombrePerfil = new JComboBox();
		
		setTitle("Modificar/Eliminar Usuario");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatosDePersonaConUsuario(odbc_location,user_db,pass_db,txtUsuario.getText(), comboBoxNombrePerfil, txtPassword, txtNombre, txtNombre_1, txtApellido, txtApellido_1);
			}
		});
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(btnCargar);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
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
		
		JLabel lblNombrePerfil = new JLabel("Nombre perfil:");
		lblNombrePerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePerfil.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNombrePerfil);
		
		comboBoxNombrePerfil.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(comboBoxNombrePerfil);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(btnEliminar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarDatosDePersonaConUsuario(odbc_location, user_db, pass_db, txtUsuario.getText(), comboBoxNombrePerfil, txtPassword, txtNombre, txtNombre_1, txtApellido, txtApellido_1);
			}
		});
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(btnModificar);

	}

}
