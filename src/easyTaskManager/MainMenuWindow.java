package easyTaskManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JDesktopPane;
import javax.swing.UIManager;
import java.awt.Window.Type;
import java.awt.SystemColor;



public class MainMenuWindow extends JFrame {

	private JPanel contentPane;
	private TasksViewer taskvframe;
	private AddTarea addTFrame;
	private ModificarTarea modifyTFrame;
	private AddUsuario addUsuarioFrame;
	private ModificarEliminarUsuario modifcarEliminarUsuarioFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String odbc_location, String user_db, String pass_db, String usuario) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}//setLookAndFeel
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuWindow frame = new MainMenuWindow(odbc_location, user_db, pass_db, usuario);
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
	public MainMenuWindow(String odbc_location, String user_db, String pass_db, String usuario) {
		setBackground(SystemColor.window);
		setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		setType(Type.UTILITY);
		JDesktopPane mainPanel = new JDesktopPane();
		mainPanel.setBackground(UIManager.getColor("Button.background"));
		setForeground(new Color(255, 153, 153));
		setTitle("Easy Tasks Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.setBackground(Color.WHITE);
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnTareas = new JMenu("Tareas");
		mnTareas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnTareas);
		
		JMenuItem rdbtnmntmVerTareas = new JMenuItem("Ver Tareas");
		rdbtnmntmVerTareas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		rdbtnmntmVerTareas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					taskvframe = new TasksViewer(odbc_location, user_db, pass_db, usuario, mainPanel);
					mainPanel.setLayout(null);
					mainPanel.add(taskvframe);
					taskvframe.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnTareas.add(rdbtnmntmVerTareas);
		
		JMenuItem mntmAadirTarea = new JMenuItem("A\u00F1adir tarea");
		mntmAadirTarea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmAadirTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTFrame = new AddTarea(odbc_location, user_db, pass_db, usuario, mainPanel);
				mainPanel.setLayout(null);
				mainPanel.add(addTFrame);
				addTFrame.setVisible(true);
			}
		});
		mnTareas.add(mntmAadirTarea);
		
		JMenuItem mntmEditarborrarTarea = new JMenuItem("Editar/Borrar Tarea");
		mntmEditarborrarTarea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmEditarborrarTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyTFrame = new ModificarTarea(odbc_location, user_db, pass_db, usuario, 0);
				mainPanel.setLayout(null);
				mainPanel.add(modifyTFrame);
				modifyTFrame.setVisible(true);
			}
		});
		mnTareas.add(mntmEditarborrarTarea);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		mnUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmAadirUsuario = new JMenuItem("A\u00F1adir Usuario");
		mntmAadirUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmAadirUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUsuarioFrame = new AddUsuario(odbc_location, user_db, pass_db, usuario);
				mainPanel.setLayout(null);
				mainPanel.add(addUsuarioFrame);
				addUsuarioFrame.setVisible(true);
			}
		});
		mnUsuarios.add(mntmAadirUsuario);
		
		JMenuItem mntmModificareliminarUsuario = new JMenuItem("Modificar/Eliminar Usuario");
		mntmModificareliminarUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmModificareliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifcarEliminarUsuarioFrame = new ModificarEliminarUsuario(odbc_location, user_db, pass_db);
				mainPanel.setLayout(null);
				mainPanel.add(modifcarEliminarUsuarioFrame);
				modifcarEliminarUsuarioFrame.setVisible(true);
			}
		});
		mnUsuarios.add(mntmModificareliminarUsuario);
		
		contentPane.add(mainPanel, BorderLayout.CENTER);
	}

}
