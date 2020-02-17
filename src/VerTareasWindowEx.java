import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class VerTareasWindowEx extends JInternalFrame {
	private JLabel labelDPriority;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerTareasWindowEx frame = new VerTareasWindowEx();
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
	
	public JPanel createSingleTaskView(String id_tarea, String fecha_tarea, String prioridad_tarea, String estado_tarea, String last_state_fecha, String descripcion_tarea) {
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 747, 150);
		panel_2.setLayout(null);
		
		JLabel lblUsuario = new JLabel("ID Tarea:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsuario.setBounds(10, 11, 73, 14);
		panel_2.add(lblUsuario);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFecha.setBounds(10, 34, 46, 14);
		panel_2.add(lblFecha);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDescripcion.setBounds(156, 11, 83, 14);
		panel_2.add(lblDescripcion);
		
		JLabel labelDDescript = new JLabel(descripcion_tarea);
		labelDDescript.setVerticalAlignment(SwingConstants.TOP);
		labelDDescript.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelDDescript.setBounds(249, 13, 488, 126);
		panel_2.add(labelDDescript);
		
		JLabel lblDIDTarea = new JLabel(id_tarea);
		lblDIDTarea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDIDTarea.setBounds(93, 13, 46, 14);
		panel_2.add(lblDIDTarea);
		
		JLabel lblDFecha = new JLabel(fecha_tarea);
		lblDFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDFecha.setBounds(93, 36, 46, 14);
		panel_2.add(lblDFecha);
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrioridad.setBounds(10, 69, 73, 14);
		panel_2.add(lblPrioridad);
		
		labelDPriority = new JLabel(prioridad_tarea);
		labelDPriority.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDPriority.setBounds(93, 71, 46, 14);
		panel_2.add(labelDPriority);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEstado.setBounds(10, 99, 73, 14);
		panel_2.add(lblEstado);
		
		JLabel labelDState = new JLabel(estado_tarea);
		labelDState.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDState.setBounds(93, 101, 46, 14);
		panel_2.add(labelDState);
		
		JLabel lblFechaUltimoEstado = new JLabel("Fecha ultimo estado:");
		lblFechaUltimoEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFechaUltimoEstado.setBounds(10, 125, 136, 14);
		panel_2.add(lblFechaUltimoEstado);
		
		JLabel labelDLastStateDate = new JLabel(last_state_fecha);
		labelDLastStateDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDLastStateDate.setBounds(156, 127, 93, 14);
		panel_2.add(labelDLastStateDate);
		return panel_2;
	}
	
	public VerTareasWindowEx() {
		getContentPane().setBackground(Color.DARK_GRAY);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblUsuarios = new JLabel("Usuarios:");
		lblUsuarios.setForeground(Color.WHITE);
		lblUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblUsuarios);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(Color.LIGHT_GRAY);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JPanel panel_2 = createSingleTaskView("a", "b", "c", "d", "e", "f");
		panel_1.add(panel_2);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(767, 0, 17, 424);
		panel_1.add(scrollBar);
		setClosable(true);
		setBounds(100, 100, 800, 500);
		
	}
}
