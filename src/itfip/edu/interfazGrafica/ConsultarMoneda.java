package itfip.edu.interfazGrafica;

import java.awt.Container;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import itfip.edu.database_conexion.Coneccion;
import javax.swing.ListSelectionModel;
import java.awt.Font;
public class ConsultarMoneda extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Connection coneccionDB;
	/**
	 * Create the dialog.
	 */
	public ConsultarMoneda(Boolean mostrarSoloRegistrosActivos) {
		coneccionDB = new Coneccion("system", "cristian123").conectarDB();
		Container contenedor = getContentPane();
		String oracleQuery = (mostrarSoloRegistrosActivos ? "SELECT * FROM monedas_lista WHERE estatus = 'true'" : "SELECT * FROM monedas_lista") + " ORDER BY id ASC";
		setBounds(100, 100, 886, 599);
		getContentPane().setLayout(null);
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		table.setCellSelectionEnabled(false);
		table.setDefaultEditor(Object.class, null);
		table.setBounds(10, 43, 732, 403);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		contentPanel.setBounds(161, 200, 1, 1);
		contentPanel.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 72, 852, 435);
		{
			contenedor.add(scrollPane);
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Query utilizado: " + oracleQuery);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 852, 52);
		getContentPane().add(lblNewLabel);
		{
			model.addColumn("Id");
			model.addColumn("Descripcion");
			model.addColumn("Nacionalidad");
			model.addColumn("Abreviatura");
			model.addColumn("Estatus");
			try {
				int contador = 0;
				Statement st = coneccionDB.createStatement();
				ResultSet rs = st.executeQuery(oracleQuery);
				while(rs.next()) {
					model.addRow(new Object[]{rs.getInt("id"), rs.getString("descripcion"), rs.getString("nacionalidad"), rs.getString("abreviatura"), rs.getString("estatus")});
					contador++;
				}
				st.close();
				rs.close();
				coneccionDB.close();
				JLabel lblNewLabel_1 = new JLabel("Número de registros encontrados: " + contador);
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
				lblNewLabel_1.setBounds(10, 517, 852, 35);
				getContentPane().add(lblNewLabel_1);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			      try {
			    	System.out.println("Closed connection");
			        coneccionDB.close();  
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }
			    }
			  }); 
		}
		{
	}
}
}
