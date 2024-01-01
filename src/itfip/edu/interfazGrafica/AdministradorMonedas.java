package itfip.edu.interfazGrafica;


import javax.swing.JFrame;
import javax.swing.JPanel;

import itfip.edu.database_conexion.Coneccion;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Color;

public class AdministradorMonedas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private AdministrarMoneda adminMoneda;
	private ConsultarMoneda consMoneda;
	private Connection coneccionEstablecida;
	/**
	 * Create the frame.
	 */
	public AdministradorMonedas() {
		Coneccion coneccion = new Coneccion("system", "cristian123");
		coneccionEstablecida = coneccion.conectarDB();
		setTitle("Administrador de monedas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 316);
		contentPane = new JPanel();
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		adminMoneda = new AdministrarMoneda(coneccionEstablecida);
		adminMoneda.setVisible(false);
		adminMoneda.setResizable(false);
		JLabel labelTitulo = new JLabel("Administrador de monedas");
		labelTitulo.setFont(new Font("Arial", Font.PLAIN, 15));
		labelTitulo.setBounds(10, 10, 643, 56);
		contentPane.add(labelTitulo);
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton botonAgregarModificar = new JButton("Agregar / Modificar");
		botonAgregarModificar.setBackground(new Color(128, 255, 128));
		botonAgregarModificar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonAgregarModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMoneda.setVisible(true);
				
			}
		});
		botonAgregarModificar.setBounds(10, 76, 645, 45);
		contentPane.add(botonAgregarModificar);
		
		JButton botonConsultar = new JButton("Consultar");
		botonConsultar.setBackground(new Color(255, 128, 128));
		botonConsultar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonConsultar.setBounds(10, 131, 645, 45);
		contentPane.add(botonConsultar);
		
		JCheckBox checkBoxActivos = new JCheckBox("Consultar solo los registros activos");
		checkBoxActivos.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBoxActivos.setBounds(10, 201, 643, 45);
		contentPane.add(checkBoxActivos);
		botonConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consMoneda = new ConsultarMoneda(checkBoxActivos.isSelected());
				consMoneda.setVisible(true);
				consMoneda.setResizable(false);
			}
		});
	}
}
