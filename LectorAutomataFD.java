package lectorAutomatasFD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JTextField;
import reductorARD.reductorARD;

public class LectorAutomataFD {

	JFileChooser seleccionar = new JFileChooser();
	File archivo;
	FileInputStream entrada;
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	//Método auxiliar robado de mi trabajao anterior para poder abrir archivos txt jeje
	private int[][] quintupla(ArrayList<String> automata)
	{
		int estados = Integer.parseInt(automata.get(0));
		String[] alfabeto = automata.get(1).split(",");
		int quintu = 0;
		int f =0;
		int[][] quintupla = new int[estados][alfabeto.length];
		for (var arreglo : automata)
		{
			if(quintu<=2)
			quintu++;
			else
			{
				if(!arreglo.isEmpty())
				{					
						for(int j = 0; j< alfabeto.length; j++)
							quintupla[f][j] = Integer.parseInt(arreglo.split("(\\s)")[j]);
				}
				f++;
			}
		}
		return quintupla;
	}
	
	private boolean AnalizarAutomata(ArrayList<String> automata)
	{
		//1. Número de estados ejemplo: 4
		//2. Alfabeto ejemplo: 1,2... "a", "b" separado por comas
		//3. Se omite q0
		//3. Los estados de aceptación ejemplo: 1,3
		//4. Quintupla
		int quintu = 0;
		int f =0;
		int estados = Integer.parseInt(automata.get(0));
		String[] alfabeto = automata.get(1).split(",");
		String[] aceptacion = automata.get(2).split(",");
		int[][] quintupla = new int[estados][alfabeto.length];
		if(aceptacion.length == 0)
			return false;
		
		if(aceptacion.length > estados)
			return false;
		
		if(alfabeto.length != quintupla[0].length)
			return false;
		
		for (var arreglo : automata)
		{
			if(quintu<=2)
			quintu++;
			else
			{
				if(!arreglo.isEmpty())
				{					
						for(int j = 0; j< alfabeto.length; j++)
							quintupla[f][j] = Integer.parseInt(arreglo.split("(\\s)")[j]);
				}
				f++;
			}
		}
		if(quintupla[0].length != alfabeto.length)
			return false;
		if(quintupla.length != alfabeto.length)
			return false;
		
		return true;
	}
	
	private ArrayList<String> AbrirArchivo(File archivo) {
		ArrayList<String> documento = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	documento.add(line);
		    }
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
		return documento;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LectorAutomataFD window = new LectorAutomataFD();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LectorAutomataFD() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnOpen = new JButton("Abrir Automata");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(seleccionar.showDialog(null, "Abrir")==JFileChooser.APPROVE_OPTION)
				{
					archivo = seleccionar.getSelectedFile();
					if(archivo.canRead())
					{
						if(archivo.getName().endsWith("txt"))
						{
							ArrayList<String> documento = AbrirArchivo(archivo);
							AnalizarAutomata(documento);
							int [][] quinta = quintupla(documento);
							reductorARD reducto = new reductorARD();
							reducto.ReductorAFD(quinta, documento.get(2).split(","), Integer.parseInt(documento.get(0)));
							System.out.println(documento);
							//textPane.setText(documento);
						}else {
							JOptionPane.showMessageDialog(null, "Archivo no compatible");
						}
					}
				}
			}
		});
		btnOpen.setBounds(132, 200, 187, 25);
		frame.getContentPane().add(btnOpen);
		
		JTextPane txtpnDsadadasd = new JTextPane();
		txtpnDsadadasd.setEditable(false);
		txtpnDsadadasd.setBounds(232, 12, 206, 132);
		frame.getContentPane().add(txtpnDsadadasd);
		
		textField = new JTextField();
		textField.setBounds(12, 12, 196, 132);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
