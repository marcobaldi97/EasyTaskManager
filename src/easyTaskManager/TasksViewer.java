package easyTaskManager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Point;
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
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TasksViewer extends JInternalFrame {
	private JTable tableTasksData;

	/**
	 * Launch the application.
	 */
	public static ResultSet getRowsFromTableTarea(String odbc_location, String user_db, String pass_db, Container contentPane, String usuario) {;
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM tarea WHERE usuario='"+usuario+"' AND task_state <> 'Eliminado'");
            connectionLocal.close();
			return resultSet;
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(contentPane,"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
            return null;
        }
	}
	
	private ResultSet getRowsFromTableUsuario(String odbc_location, String user_db, String pass_db,	Container contentPane) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            Statement statement = connectionLocal.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT usuario FROM persona");
            connectionLocal.close();
			return resultSet;
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(contentPane,"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
            return null;
        }
	}
	
	private void refreshTable(ResultSet tableContentSQL, JTable tableTasksData2) throws SQLException {
		DefaultTableModel tableModel = (DefaultTableModel) tableTasksData.getModel();
		tableModel.setRowCount(0);
		int colNo = 7;
		while(tableContentSQL.next()){
		 Object[] objects = new Object[colNo];
		 objects[0]=tableContentSQL.getObject(1);
		 objects[1]=tableContentSQL.getObject(4);
		 objects[2]=tableContentSQL.getObject(3);
		 objects[3]=tableContentSQL.getObject(2);
		 objects[4]=tableContentSQL.getObject(5);
		 objects[5]=tableContentSQL.getObject(6);
		 objects[6]=tableContentSQL.getObject(7);
		 tableModel.addRow(objects);
		}
		tableTasksData.setModel(tableModel);
	}//clean and refresh the table with new user.
	
	protected void completeSelectedTask(String odbc_location, String user_db, String pass_db, JTable tableTasksData2) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            String id_tarea = tableTasksData2.getValueAt(tableTasksData2.getSelectedRow(),0).toString();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			Date date = new Date();
			String fecha = formatter.format(date);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("UPDATE tarea SET task_state = 'Completo', fecha_task_state = '"+fecha+"' WHERE id_tarea = "+id_tarea+"");
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}
	
	private void markAsInProcessSelectedTask(String odbc_location, String user_db, String pass_db,JTable tableTasksData2) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            String id_tarea = tableTasksData2.getValueAt(tableTasksData2.getSelectedRow(),0).toString();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			Date date = new Date();
			String fecha = formatter.format(date);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("UPDATE tarea SET task_state = 'En proceso.' ,fecha_task_state= '"+fecha+"' WHERE id_tarea = "+id_tarea+"");
            preparedStmt1.execute();//bingo.
            connectionLocal.close();
		} catch (SQLException f) {
            //System.out.println("Connection failure.");
            JOptionPane.showMessageDialog(getContentPane(),"No se pudo conectar a la base de datos SQL.","Error",JOptionPane.ERROR_MESSAGE);
            f.printStackTrace();
        }
	}
	
	protected void markAsEliminatedSelectedTask(String odbc_location, String user_db, String pass_db, JTable tableTasksData2) {
		try (Connection connectionLocal = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            String id_tarea = tableTasksData2.getValueAt(tableTasksData2.getSelectedRow(),0).toString();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			Date date = new Date();
			String fecha = formatter.format(date);
            PreparedStatement preparedStmt1 = connectionLocal.prepareStatement("UPDATE tarea SET task_state = 'Eliminado', fecha_task_state= '"+fecha+"' WHERE id_tarea = "+id_tarea+"");
            preparedStmt1.execute();//bingo.
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
					TasksViewer frame = new TasksViewer(odbc_location, user_db, pass_db, usuario, mainPanel);
					System.out.println("How is your sex life?");
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
	 * @throws SQLException 
	 */
	public TasksViewer(String odbc_location, String user_db, String pass_db, String usuario, JDesktopPane mainPanel) throws SQLException {
		setForeground(new Color(255, 0, 0));
		setTitle("Ver Tareas");
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		System.out.println("Hi Mark");
		ResultSet tableContentSQL = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(),usuario);
		ResultSet usuariosSQL = getRowsFromTableUsuario(odbc_location, user_db, pass_db, getContentPane());
		setBounds(100, 100, 865, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblVerTareasDe = new JLabel("Ver tareas de:");
		lblVerTareasDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVerTareasDe.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblVerTareasDe);
		
		JComboBox comboBoxUsuariosTareas = new JComboBox();
		while(usuariosSQL.next()) {
			Object objeto = new Object();
			objeto = usuariosSQL.getObject(1);
			comboBoxUsuariosTareas.addItem(objeto);
			if(objeto.toString().equals(usuario))comboBoxUsuariosTareas.setSelectedItem(objeto);
		}
		comboBoxUsuariosTareas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet tableContentSQLMKTwo = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(), comboBoxUsuariosTareas.getSelectedItem().toString());
				try {
					refreshTable(tableContentSQLMKTwo, tableTasksData);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(comboBoxUsuariosTareas);
		
		
		JButton btnRefresh = new JButton("Refrescar");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet tableContentSQLMKTwo = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(), comboBoxUsuariosTareas.getSelectedItem().toString());
				try {
					refreshTable(tableContentSQLMKTwo, tableTasksData);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnRefresh);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel columnsNames = new JPanel();
		panel_1.add(columnsNames, BorderLayout.NORTH);
		columnsNames.setLayout(new GridLayout(0, 7, 0, 0));
		
		JLabel lblIdtarea = new JLabel("ID_Tarea");
		lblIdtarea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIdtarea.setHorizontalAlignment(SwingConstants.CENTER);
		columnsNames.add(lblIdtarea);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		columnsNames.add(lblFecha);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		columnsNames.add(lblDescripcion);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		columnsNames.add(lblUsuario);
		
		JLabel lblPrioridad = new JLabel("Prioridad");
		lblPrioridad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrioridad.setHorizontalAlignment(SwingConstants.CENTER);
		columnsNames.add(lblPrioridad);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		columnsNames.add(lblEstado);
		
		JLabel lblFechaltimoEstado = new JLabel("Fecha \u00FAltimo estado");
		lblFechaltimoEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		columnsNames.add(lblFechaltimoEstado);
		tableTasksData = new JTable();
		tableTasksData.setCellSelectionEnabled(true);
		tableTasksData.setColumnSelectionAllowed(true);
		tableTasksData.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		panel_1.add(tableTasksData, BorderLayout.CENTER);
		refreshTable(tableContentSQL, tableTasksData);
		tableTasksData.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					Object id_tareaObject = tableTasksData.getValueAt(tableTasksData.getSelectedRow(), 0);
					int id_tarea = Integer.parseInt(id_tareaObject.toString());
					SingleTaskViewer staskViewerWindow = new SingleTaskViewer(odbc_location, user_db, pass_db, id_tarea);
					mainPanel.add(staskViewerWindow);
					mainPanel.setLayout(null);
					staskViewerWindow.setVisible(true);// set the f1 frame visible
					try {
						staskViewerWindow.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		});
	
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnCompletar = new JButton("Completar");
		panel_3.add(btnCompletar);
		btnCompletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableTasksData.getSelectedRow() != -1) {
					completeSelectedTask(odbc_location, user_db, pass_db, tableTasksData);
					ResultSet tableContentSQLMKTwo = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(), comboBoxUsuariosTareas.getSelectedItem().toString());
					try {
						refreshTable(tableContentSQLMKTwo, tableTasksData);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(getContentPane(),"No hay una fila seleccionada.","Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCompletar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnEnProceso = new JButton("En proceso");
		btnEnProceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableTasksData.getSelectedRow() != -1) {
					markAsInProcessSelectedTask(odbc_location, user_db, pass_db, tableTasksData);
					ResultSet tableContentSQLMKTwo = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(), comboBoxUsuariosTareas.getSelectedItem().toString());
					try {
						refreshTable(tableContentSQLMKTwo, tableTasksData);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(getContentPane(),"No hay una fila seleccionada.","Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEnProceso.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(btnEnProceso);
		
		JButton btnVerTarea = new JButton("Ver Tarea");
		btnVerTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableTasksData.getSelectedRow() != -1) {
					Object id_tareaObject = tableTasksData.getValueAt(tableTasksData.getSelectedRow(), 0);
					int id_tarea = Integer.parseInt(id_tareaObject.toString());
					SingleTaskViewer staskViewerWindow = new SingleTaskViewer(odbc_location, user_db, pass_db, id_tarea);
					mainPanel.add(staskViewerWindow);
					mainPanel.setLayout(null);
					staskViewerWindow.setVisible(true);// set the f1 frame visible
					try {
						staskViewerWindow.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(getContentPane(),"No hay una fila seleccionada.","Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnVerTarea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(btnVerTarea);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnModificar = new JButton("Modificar");
		panel_4.add(btnModificar);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableTasksData.getSelectedRow() != -1) {
					Object id_tareaObject = tableTasksData.getValueAt(tableTasksData.getSelectedRow(), 0);
					int id_tarea = Integer.parseInt(id_tareaObject.toString());
					ModificarTarea modifcarTareaWindow = new ModificarTarea(odbc_location, user_db, pass_db, usuario, id_tarea);
					mainPanel.add(modifcarTareaWindow);
					mainPanel.setLayout(null);
					modifcarTareaWindow.setVisible(true);// set the f1 frame visible
					try {
						modifcarTareaWindow.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(getContentPane(),"No hay una fila seleccionada.","Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableTasksData.getSelectedRow() != -1) {
					markAsEliminatedSelectedTask(odbc_location, user_db, pass_db, tableTasksData);
					ResultSet tableContentSQLMKTwo = getRowsFromTableTarea(odbc_location, user_db, pass_db, getContentPane(), comboBoxUsuariosTareas.getSelectedItem().toString());
					try {
						refreshTable(tableContentSQLMKTwo, tableTasksData);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(getContentPane(),"No hay una fila seleccionada.","Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel_4.add(btnEliminar);
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));

	}
}