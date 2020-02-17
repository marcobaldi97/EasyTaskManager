package easyTaskManagerEx;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.GridLayout;

public class MainMenuWindowsEx extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(DtGlobalParams globalParams) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuWindowsEx frame = new MainMenuWindowsEx(globalParams);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param globalParams 
	 */
	public MainMenuWindowsEx(DtGlobalParams globalParams) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 509);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.DARK_GRAY);
		menuBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		setJMenuBar(menuBar);
		
		JMenu mnTareas = new JMenu("Tareas");
		mnTareas.setForeground(Color.WHITE);
		mnTareas.setBackground(Color.DARK_GRAY);
		mnTareas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnTareas);
		
		JMenuItem mntmVerTareas = new JMenuItem("Ver Tareas");
		mntmVerTareas.setForeground(Color.WHITE);
		mntmVerTareas.setBackground(Color.DARK_GRAY);
		mntmVerTareas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnTareas.add(mntmVerTareas);
		
		JMenuItem mntmAadirTarea = new JMenuItem("A\u00F1adir Tarea");
		mntmAadirTarea.setForeground(Color.WHITE);
		mntmAadirTarea.setBackground(Color.DARK_GRAY);
		mntmAadirTarea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnTareas.add(mntmAadirTarea);
		
		JMenuItem mntmModificarTarea = new JMenuItem("Modificar Tarea");
		mntmModificarTarea.setForeground(Color.WHITE);
		mntmModificarTarea.setBackground(Color.DARK_GRAY);
		mntmModificarTarea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnTareas.add(mntmModificarTarea);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		mnUsuarios.setForeground(Color.WHITE);
		mnUsuarios.setBackground(Color.DARK_GRAY);
		mnUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmAadirUsuario = new JMenuItem("A\u00F1adir Usuario");
		mntmAadirUsuario.setForeground(Color.WHITE);
		mntmAadirUsuario.setBackground(Color.DARK_GRAY);
		mntmAadirUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnUsuarios.add(mntmAadirUsuario);
		
		JMenuItem mntmModificarUsuario = new JMenuItem("Modificar Usuario");
		mntmModificarUsuario.setForeground(Color.WHITE);
		mntmModificarUsuario.setBackground(Color.DARK_GRAY);
		mntmModificarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnUsuarios.add(mntmModificarUsuario);
		
		JMenu mnSalida = new JMenu("Salida");
		mnSalida.setForeground(Color.WHITE);
		mnSalida.setBackground(Color.DARK_GRAY);
		mnSalida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnSalida);
		
		JMenuItem mntmLogout = new JMenuItem("LogOut");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LoginWindowEx loginWindowExObject = new LoginWindowEx(globalParams);
				loginWindowExObject.setVisible(true);
				dispose();
			}
		});
		mntmLogout.setForeground(Color.WHITE);
		mntmLogout.setBackground(Color.DARK_GRAY);
		mntmLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnSalida.add(mntmLogout);
		
		JMenuItem mntmExit = new JMenuItem("Salir");
		mntmExit.setForeground(Color.WHITE);
		mntmExit.setBackground(Color.DARK_GRAY);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnSalida.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.GRAY);
		contentPane.add(desktopPane);
		
		setUndecorated(true);//to remove borders,etc.
	}

}
