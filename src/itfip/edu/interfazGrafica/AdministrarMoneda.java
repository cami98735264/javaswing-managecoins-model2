package itfip.edu.interfazGrafica;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import itfip.edu.database_conexion.Coneccion;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JTextField;

public class AdministrarMoneda extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField txtDescripcion;
	private JTextField txtNacionalidad;
	private JTextField txtAbreviatura;
	private JTextField txtEstatus;
	private DefaultTableModel model2;
	private Connection coneccionDB;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("serial")
	public AdministrarMoneda(Connection coneccion) {
		Connection coneccionDBQuery = new Coneccion("system", "cristian123").conectarDB();
		coneccionDB = coneccion;
		Container contenedor = getContentPane();
		setBounds(100, 100, 877, 581);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Holaaaaa");
			lblNewLabel.setBounds(352, 27, 42, 13);
			contentPanel.add(lblNewLabel);
		}
		
		{
			model2 = new DefaultTableModel() {
				public boolean isCellEditable(int row, int col) {
					if (col == 0) {
					            return false;
					        } else {
					            return true;
					        }       
					    }
			};
			table = new JTable(model2);
			table.setBounds(10, 43, 750, 415);
			

			model2.addColumn("Id");
			model2.addColumn("Descripcion");
			model2.addColumn("Nacionalidad");
			model2.addColumn("Abreviatura");
			model2.addColumn("Estatus");
			table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			try {
				
				Statement st = coneccionDBQuery.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM monedas_lista ORDER BY id ASC");
				while(rs.next()) {
					model2.addRow(new Object[]{rs.getInt("id"), rs.getString("descripcion"), rs.getString("nacionalidad"), rs.getString("abreviatura"), rs.getString("estatus")});
				}
				st.close();
				rs.close();
				coneccionDBQuery.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contentPanel.add(table);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(10, 10, 843, 378);
			contenedor.add(scrollPane);
		}
		{
			JButton btnNewButton = new JButton("Eliminar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hacerQuery("eliminar", "DELETE FROM monedas_lista", true);
				}
			});
			btnNewButton.setFont(new Font("Arial", Font.PLAIN, 13));
			btnNewButton.setBounds(10, 409, 120, 37);
			getContentPane().add(btnNewButton);
		}
		{
			JButton btnInhabilitar = new JButton("Inhabilitar");
			btnInhabilitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hacerQuery("inhabilitar", "UPDATE monedas_lista SET estatus = 'false'", true);
				}
			});
			btnInhabilitar.setFont(new Font("Arial", Font.PLAIN, 13));
			btnInhabilitar.setBounds(140, 409, 120, 37);
			getContentPane().add(btnInhabilitar);
		}
		{
			JButton btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					if(row == -1) {
						JOptionPane.showMessageDialog(null, "¡Selecciona una celda para hacer esta acción!");
					} else {
						try {
							String idValue = table.getValueAt(row, 0).toString();
							String descripcion = table.getValueAt(row, 1).toString().length() < 29 ? table.getValueAt(row, 1).toString() : table.getValueAt(row, 1).toString().substring(0, 29);
							String nacionalidad = table.getValueAt(row, 2).toString().length() < 24 ? table.getValueAt(row, 2).toString() : table.getValueAt(row, 2).toString().substring(0, 24);
							String abreviatura = table.getValueAt(row, 3).toString().length() < 3 ? table.getValueAt(row, 3).toString() : table.getValueAt(row, 3).toString().substring(0, 3);
							String estatus = !(table.getValueAt(row, 4).toString().equals("true") || table.getValueAt(row, 4).toString().equals("false")) ? "true" : table.getValueAt(row, 4).toString();
							String query = "UPDATE monedas_lista SET descripcion = ?, nacionalidad = ?, abreviatura = ?, estatus = ? WHERE id = ?";
							PreparedStatement ps = coneccionDB.prepareStatement(query);
							ps.setString(1, descripcion);
							ps.setString(2, nacionalidad);
							ps.setString(3, abreviatura);
							ps.setString(4, estatus);
							ps.setInt(5, Integer.valueOf(idValue));
							int affectedRows = ps.executeUpdate();
							ps.close();
							coneccionDB.close();
							Coneccion coneccion = new Coneccion("system", "cristian123");
							coneccionDB = coneccion.conectarDB();
							if(affectedRows == 1) {
								model2.setValueAt(descripcion, row, 1);
								model2.setValueAt(nacionalidad, row, 2);
								model2.setValueAt(abreviatura, row, 3);
								model2.setValueAt(estatus, row, 4);
								model2.fireTableDataChanged();
								JOptionPane.showMessageDialog(null, "¡Acabas de " + "actualizar" + " el registro con ID: " + (idValue) + " exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
								
							} else {
								JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error intentando " + "actualizar" + " ese registro!", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error intentando " + "actualizar" + " ese registro, asegurate de que la longitud y los parametros son afines a la base de datos!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			btnGuardar.setFont(new Font("Arial", Font.PLAIN, 13));
			btnGuardar.setBounds(400, 409, 120, 37);
			getContentPane().add(btnGuardar);
		}
		{
			txtDescripcion = new JTextField();
			txtDescripcion.setText("descripcion");
			txtDescripcion.setToolTipText("");
			txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
			txtDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
			txtDescripcion.setBounds(10, 475, 316, 34);
			getContentPane().add(txtDescripcion);
			txtDescripcion.setColumns(10);
		}
		{
			txtNacionalidad = new JTextField();
			txtNacionalidad.setFont(new Font("Arial", Font.PLAIN, 13));
			txtNacionalidad.setText("nacionalidad");
			txtNacionalidad.setColumns(10);
			txtNacionalidad.setBounds(336, 475, 172, 34);
			getContentPane().add(txtNacionalidad);
		}
		{
			txtAbreviatura = new JTextField();
			txtAbreviatura.setFont(new Font("Arial", Font.PLAIN, 13));
			txtAbreviatura.setText("abreviatura");
			txtAbreviatura.setColumns(10);
			txtAbreviatura.setBounds(518, 475, 116, 34);
			getContentPane().add(txtAbreviatura);
		}
		{
			txtEstatus = new JTextField();
			txtEstatus.setText("estatus");
			txtEstatus.setFont(new Font("Arial", Font.PLAIN, 13));
			txtEstatus.setColumns(10);
			txtEstatus.setBounds(644, 475, 79, 34);
			getContentPane().add(txtEstatus);
		}
		{
			JButton btnAadir = new JButton("Añadir");
			btnAadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String descripcion = txtDescripcion.getText();
					String nacionalidad = txtNacionalidad.getText();
					String abreviatura = txtAbreviatura.getText();
					String estatus = txtEstatus.getText();
					if(descripcion.equals("descripcion") || nacionalidad.equals("nacionalidad") || abreviatura.equals("abreviatura") || estatus.equals("estatus")) {
						JOptionPane.showMessageDialog(null, "¡Todas los inputs deben tener un valor para insertar!", "Alerta", JOptionPane.WARNING_MESSAGE);
					} else {
						int affectedrows = 0;
						try {
							String sql = "INSERT INTO monedas_lista (descripcion, nacionalidad, abreviatura, estatus) VALUES(?,?,?,?)";				
							PreparedStatement ps = coneccionDB.prepareStatement(sql, new String [] {"id"});
							ps.setString(1, descripcion);
							ps.setString(2, nacionalidad);
							ps.setString(3, abreviatura);
							ps.setString(4, estatus);
							affectedrows = ps.executeUpdate();
							ResultSet rs = ps.getGeneratedKeys();
							int lastRow = 0;
							if(affectedrows == 1) {
								JOptionPane.showMessageDialog(null, "¡Registro añadido correctamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
								while(rs.next()) {
									System.out.println(rs.getInt(1));
									lastRow = rs.getInt(1);
								}
								model2.addRow(new Object[]{lastRow, descripcion, nacionalidad, abreviatura, estatus});
								model2.fireTableDataChanged();
							}
							else {
								JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al añadir ese registro!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							ps.close();
							rs.close();
						} catch(SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			btnAadir.setFont(new Font("Arial", Font.PLAIN, 13));
			btnAadir.setBounds(733, 474, 120, 37);
			getContentPane().add(btnAadir);
		}
		
		JButton btnHabilitar = new JButton("Habilitar");
		btnHabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Le dista a Habilitar!");
				hacerQuery("habilitar", "UPDATE monedas_lista SET estatus = 'true'", true);
			}
		});
		btnHabilitar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnHabilitar.setBounds(270, 409, 120, 37);
		getContentPane().add(btnHabilitar);
	}
	public void hacerQuery(String palabraClave, String sqlQuery, Boolean añadirWhere) {
		int column = 0;
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "¡Selecciona una celda para hacer esta acción!", "Error", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				String value = table.getValueAt(row, column).toString();
				String query = sqlQuery + " " + "WHERE id = ?";
				PreparedStatement ps = coneccionDB.prepareStatement(query);
				ps.setInt(1, Integer.valueOf(value));
				int affectedRows = ps.executeUpdate();
				ps.close();
				coneccionDB.close();
				Coneccion coneccion = new Coneccion("system", "cristian123");
				coneccionDB = coneccion.conectarDB();
				if(affectedRows == 1) {
					switch(palabraClave) {
					case "eliminar":
							model2.removeRow(row);
							model2.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "¡Acabas de " + palabraClave + " el registro con ID: " + (value) + " exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
							break;
					case "inhabilitar":
							model2.setValueAt("false", row, 4);
							model2.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "¡Acabas de " + palabraClave + " el registro con ID: " + (value) + " exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
							break;
					case "habilitar":
							model2.setValueAt("true", row, 4);
							model2.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "¡Acabas de " + palabraClave + " el registro con ID: " + (value) + " exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
							break;
					}
				} else {
					JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error intentando " + palabraClave + " ese registro!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
