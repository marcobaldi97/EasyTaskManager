package easyTaskManager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SingleTaskViewer extends JInternalFrame {

	private void cargarCampos(String odbc_location, String user_db, String pass_db, int id_tarea, JLabel lblEstado, JLabel lblFecha, JLabel lblIdDeLa, JLabel lblPrioridad,JLabel lblUsuarioCreador, JTextArea textAreaDescripcion) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
            //carga de Descripción.
			ResultSet resultSet = statement.executeQuery("SELECT * FROM tarea WHERE id_tarea ="+id_tarea);
			resultSet.next();
			textAreaDescripcion.setText(resultSet.getString("descripcion_tarea"));
			lblEstado.setText(lblEstado.getText().concat(" "+resultSet.getString("task_state")));
			lblFecha.setText(lblFecha.getText().concat(" "+resultSet.getString("fecha_tarea")));
			lblIdDeLa.setText(lblIdDeLa.getText().concat(" "+resultSet.getString("id_tarea")));
			lblUsuarioCreador.setText(lblUsuarioCreador.getText().concat(" "+resultSet.getString("usuario")));
			lblPrioridad.setText(lblPrioridad.getText().concat(" "+resultSet.getString("priority")));
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"ID incorrecta o no se pudo conectar con la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
        }
	}
	
	public static void main(String odbc_location, String user_db, String pass_db, int id_tarea) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SingleTaskViewer frame = new SingleTaskViewer(odbc_location, user_db, pass_db, id_tarea);
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
	public SingleTaskViewer(String odbc_location, String user_db, String pass_db, int id_tarea) {
		setMaximizable(true);
		setClosable(true);
		setTitle("Ver informaci\u00F3n de Tarea");
		try {
			setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(true);
		setBounds(100, 100, 824, 364);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel lblIdDeLa = new JLabel("Id de la tarea:");
		lblIdDeLa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblIdDeLa);
		
		JLabel lblUsuarioCreador = new JLabel("Usuario creador: ");
		lblUsuarioCreador.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblUsuarioCreador);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblDescripcin);
		
		JTextArea textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setFont(new Font("Monospaced", Font.PLAIN, 16));
		panel.add(textAreaDescripcion);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblFecha);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(lblPrioridad);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(lblEstado);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnAceptar);

		cargarCampos(odbc_location, user_db, pass_db, id_tarea, lblEstado, lblFecha, lblIdDeLa, lblPrioridad, lblUsuarioCreador, textAreaDescripcion);
	}
}
