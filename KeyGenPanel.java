package rsa;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KeyGenPanel extends JPanel {

	/**
	 *  In this class one can generate new values for p, q, phi, etc. based on a selected bit length.
	 */

	private static final long serialVersionUID = 1L;
	private static String p, q, n, phi, d, E, bitLength;
	private static JComboBox<String> bitLengths;
	private static JTextArea jtaP, jtaQ, jtaN, jtaPhi, jtaD, jtaE;
	private static JScrollPane jspP, jspQ, jspN, jspPhi, jspD, jspE;

	// Constructs KeyGenPanel()
	public KeyGenPanel() {

		p = "not set yet";
		q = "not set yet";
		n = "not set yet";
		phi = "not set yet";
		d = "not set yet";
		E = "not set yet";

		initializeGUI();

	}

	private void initializeGUI() {

		// Lays out necessary components vertically
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxLayout);

		// Adds JLabel prompting bitLength selection
		JLabel inputEncodeLab = new JLabel("Choose prefered bit length: ");
		add(inputEncodeLab);

		// Creates JComboBox with ActionListener filled with possible bit lengths
		String[] bookTitles = new String[] {"4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048"};
		bitLengths = new JComboBox<>(bookTitles);
		ActionListener jcombobox_listener = new jcombobox_Listener();
		bitLengths.addActionListener(jcombobox_listener);
		add(bitLengths);

		// Update value of bitLength with the selected item
		bitLength = (String) bitLengths.getSelectedItem();

		// Update JTextArea with new value of p
		jtaP = new JTextArea("p is " + p);
		jtaP.setLineWrap(true);
		jtaP.setEditable(false);

		// Update JScrollPane with new JText Area and add new JScrollPane to JPanel
		jspP = new JScrollPane(jtaP, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspP);

		// Repeats steps done for p to q, n, phi, d, and e
		jtaQ = new JTextArea("q is " + q);
		jtaQ.setLineWrap(true);
		jspQ = new JScrollPane(jtaQ, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspQ);
		
		jtaN = new JTextArea("n is " + n);
		jtaN.setLineWrap(true);
		jtaN.setEditable(false);
		jspN = new JScrollPane(jtaN, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspN);
		
		jtaPhi = new JTextArea("phi is " + phi);
		jtaPhi.setLineWrap(true);
		jtaPhi.setEditable(false);
		jspPhi = new JScrollPane(jtaPhi, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspPhi);
		
		jtaE = new JTextArea("e is " + E);
		jtaE.setLineWrap(true);
		jtaE.setEditable(false);
		jspE = new JScrollPane(jtaE, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspE);
		
		jtaD = new JTextArea("d is " + d);
		jtaD.setLineWrap(true);
		jtaD.setEditable(false);
		jspD = new JScrollPane(jtaD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspD);

		// Makes Generate Key Button and adds to JPanel
		JButton key_gen_button = new JButton("Generate Key");
		key_gen_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		ActionListener button_listener = new key_gen_Listener();
		key_gen_button.addActionListener(button_listener);
		add(key_gen_button);

	}

	private class key_gen_Listener implements ActionListener{

		// Listens to JButton, generates p, q, phi, etc. on click, and writes public and private keys to file
		public void actionPerformed(ActionEvent e) {

			// Generate Keys
			logic generated_keys = new logic();
			generated_keys.GenerateKey(Integer.parseInt(bitLength));

			// Updates values of p, q, phi, etc.
			p = generated_keys.getP().toString();
			q = generated_keys.getQ().toString();
			n = generated_keys.getN().toString();
			phi = generated_keys.getPhi().toString();
			E = generated_keys.getE().toString();
			d = generated_keys.getD().toString();

			// Writes public key to file
			try{
				PrintWriter writer = new PrintWriter("public_key.txt");
				writer.println(generated_keys.CreatePublicKey());
				writer.close();
			} catch (IOException e1) {
				System.err.println("File not found");
			}

			// Writes private key to file
			try{
				PrintWriter writer = new PrintWriter("private_key.txt");
				writer.println(generated_keys.CreatePrivateKey());
				writer.close();
			} catch (IOException e1) {
				System.err.println("File not found");
			}
			
			// Updates JTextAreas with new values
			jtaP.setText("p is " + p);
			jtaQ.setText("q is " + q);
			jtaN.setText("n is " + n);
			jtaPhi.setText("phi is " + phi);
			jtaE.setText("e is " + E);
			jtaD.setText("d is " + d);
			
		}
		
	}

	private class jcombobox_Listener implements ActionListener{

		@Override
		// Listens to JComboBox
		public void actionPerformed(ActionEvent e) {

			// Updates bitLength with selected value
			bitLength = (String) bitLengths.getSelectedItem();

		}

	}

	public String getP(){
		return p;
	}

	public String getQ(){
		return q;
	}

	public String getN(){
		return n;
	}

	public String getPhi(){
		return phi;
	}

	public String getE(){
		return E;
	}

	public String getD(){
		return d;
	}

}