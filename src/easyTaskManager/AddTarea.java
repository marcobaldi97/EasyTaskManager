package easyTaskManager;

import java.awt.EventQueue;


import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;  
import java.util.Date;  

public class AddTarea extends JInternalFrame {
	private JTextField textFieldDescripcion;

	/**
	 * Launch the application.
	 */
	
	protected void createTareaSQL(String odbc_location, String user_db, String pass_db, String usuario, String descripcion, int prioridad) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			Date date = new Date();
			String fecha = formatter.format(date);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("INSERT INTO tarea (usuario, descripcion_tarea, fecha_tarea, priority, task_state, fecha_task_state)\r\n" + 
            		"VALUES ('"+usuario+"','"+descripcion+"','"+fecha+"',"+prioridad+",'Pendiente','"+fecha+"');");
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
            JOptionPane.showMessageDialog(getContentPane(),"Se creó tarea correctamente.","Éxito",JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL o error de comillas simples.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}
	
	private void cargarCampos(String odbc_location, String user_db, String pass_db, String usuario, JComboBox comboBoxPrioridad) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT priority FROM prioridad");
			comboBoxPrioridad.removeAllItems();
			while(resultSet.next()) {
				Object objeto = new Object();
				objeto = resultSet.getInt(1);
				comboBoxPrioridad.addItem(objeto);
			}
			//fin carga de comboBoxPrioridad
			connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}
	
	public static void main(String odbc_location, String user_db, String pass_db, String usuario, JDesktopPane mainPanel) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTarea frame = new AddTarea(odbc_location, user_db, pass_db, usuario, mainPanel);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param mainPanel 
	 * @param pass_db 
	 * @param user_db 
	 * @param odbc_location 
	 * @param usuario 
	 */
	public AddTarea(String odbc_location, String user_db, String pass_db, String usuario, JDesktopPane mainPanel) {
		setTitle("A\u00F1adir Tarea");
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcin.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDescripcin);
		
		textFieldDescripcion = new JTextField();
		panel.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrioridad.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblPrioridad);
		
		JComboBox comboBoxPrioridad = new JComboBox();
		panel.add(comboBoxPrioridad);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldDescripcion.setText("");
				comboBoxPrioridad.setSelectedIndex(0);//esto puede dar error.
			}
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(btnLimpiar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String descripcion = textFieldDescripcion.getText();
				int prioridad = (int) comboBoxPrioridad.getModel().getSelectedItem();
				createTareaSQL(odbc_location,user_db,pass_db,usuario,descripcion,prioridad);
			}
		});
		btnConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(btnConfirmar);

		cargarCampos(odbc_location, user_db, pass_db, usuario, comboBoxPrioridad);
	}
}
