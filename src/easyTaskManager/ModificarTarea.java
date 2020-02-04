package easyTaskManager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ModificarTarea extends JInternalFrame {
	private JSpinner textFieldIdTarea;
	private JTextField textField;

	/**
	 * Launch the application.
	 * @param comboBoxPrioridad 
	 */
	
	private void cargarCampos(String odbc_location, String user_db, String pass_db, String usuario, int id_tarea, JTextField textField2, JComboBox comboBoxEstado, JComboBox comboBoxPrioridad) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
            //carga de Descripción.
			ResultSet resultSet = statement.executeQuery("SELECT descripcion_tarea FROM tarea WHERE id_tarea ="+id_tarea);
			resultSet.next();
			textField2.setText(resultSet.getString(1));
			//Fin de carga de descripción.
			//carga de ComboboxEstado
			comboBoxEstado.removeAllItems();
			resultSet = statement.executeQuery("SELECT task_state FROM tarea WHERE id_tarea="+id_tarea);
			resultSet.next();
			String actualState = resultSet.getString(1);
			resultSet = statement.executeQuery("SELECT task_state FROM estado");
			while(resultSet.next()) {
				Object objeto = new Object();
				objeto = resultSet.getObject(1);
				comboBoxEstado.addItem(objeto);
				if(objeto.toString().equals(actualState))comboBoxEstado.setSelectedItem(actualState);
			}
			//fin carga de comboBoxPrioridad
			//carga de ComboboxEsta
			comboBoxPrioridad.removeAllItems();
			resultSet = statement.executeQuery("SELECT priority FROM tarea WHERE id_tarea="+id_tarea);
			resultSet.next();
			int actualStateInt = resultSet.getInt(1);
			resultSet = statement.executeQuery("SELECT priority FROM prioridad");
			while(resultSet.next()) {
				Object objeto = new Object();
				objeto = resultSet.getInt(1);
				comboBoxPrioridad.addItem(objeto);
				if((int) objeto == actualStateInt)comboBoxPrioridad.setSelectedItem(actualStateInt);
			}
			//fin carga de comboBoxPrioridad
			connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"ID incorrecta o no se pudo conectar con la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
        }
	}
	
	protected void modifyTarea(String odbc_location, String user_db, String pass_db, String usuario, int id_tarea2, JTextField textField2, JComboBox comboBoxEstado, JComboBox comboBoxPrioridad) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
			String descripcion = textField2.getText();
			String prioridad = comboBoxPrioridad.getModel().getSelectedItem().toString();
			String estado = comboBoxEstado.getModel().getSelectedItem().toString();
            Statement statement = connectionLocal.createStatement();
            //carga de Descripción.
			ResultSet resultSet = statement.executeQuery("SELECT task_state FROM tarea WHERE id_tarea ="+id_tarea2);
			resultSet.next();
			String old_state = resultSet.getString(1);
			PreparedStatement preparedStmt1 = null;
			if(old_state.equals(estado)) {
	            preparedStmt1 = connectionLocal.prepareStatement("UPDATE tarea SET task_state = '"+estado+"', priority ='"+prioridad+"', descripcion_tarea ='"+descripcion+"' WHERE id_tarea = "+id_tarea2+"");
			}else {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				Date date = new Date();
				String fecha = formatter.format(date);
	            preparedStmt1 = connectionLocal.prepareStatement("UPDATE tarea SET task_state = '"+estado+"', priority ='"+prioridad+"', descripcion_tarea ='"+descripcion+"', fecha_task_state = '"+fecha+"' WHERE id_tarea = "+id_tarea2+"");
			}
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}

	
	public static void main(String odbc_location, String user_db, String pass_db, String usuario, int id_tarea) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarTarea frame = new ModificarTarea(odbc_location, user_db, pass_db, usuario, id_tarea);
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
	public ModificarTarea(String odbc_location, String user_db, String pass_db, String usuario, int id_tarea) {
		setForeground(SystemColor.inactiveCaption);
		setTitle("Modificar Tarea");
		textField = new JTextField();
		JComboBox comboBoxEstado = new JComboBox();
		JComboBox comboBoxPrioridad = new JComboBox();
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblIdtarea = new JLabel("ID_Tarea:");
		lblIdtarea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblIdtarea);
		
		textFieldIdTarea = new JSpinner();
		textFieldIdTarea.setModel(new SpinnerNumberModel(1, 1, 2147483647, 1));
		panel.add(textFieldIdTarea);
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id_tarea2 = (int) textFieldIdTarea.getModel().getValue();
				cargarCampos(odbc_location, user_db, pass_db, usuario, id_tarea2, textField,comboBoxEstado,comboBoxPrioridad);
			}
		});
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(btnCargar);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblDescripcin);
		
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblEstado);
		
		comboBoxEstado.setMaximumRowCount(3);
		panel_2.add(comboBoxEstado);
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrioridad.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblPrioridad);
		
		panel_2.add(comboBoxPrioridad);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textFieldIdTarea.getModel().setValue(1);
			}
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(btnLimpiar);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id_tarea2 = (int) textFieldIdTarea.getModel().getValue();
				modifyTarea(odbc_location, user_db, pass_db, usuario, id_tarea2, textField, comboBoxEstado, comboBoxPrioridad);
				try {
					setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(btnAceptar);
		
		if(id_tarea != 0) {
			textFieldIdTarea.setValue(id_tarea);
			textFieldIdTarea.setEnabled(false);
			btnCargar.setEnabled(false);
			btnLimpiar.setEnabled(false);
			cargarCampos(odbc_location, user_db, pass_db, usuario, id_tarea, textField, comboBoxEstado, comboBoxPrioridad);
		}

	}

}
