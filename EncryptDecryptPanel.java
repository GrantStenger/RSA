package rsa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EncryptDecryptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTextField messagejtf, cipherjtf;
	private static JTextArea encryptedTextjta, decryptedTextjta;
	private static JScrollPane encryptedTextjsp;
	private static String encrypted_text, decrypted_text;

	public EncryptDecryptPanel() {

		initializeGUI();

	}

	private void initializeGUI() {

		// Lays out necessary components vertically
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxLayout);

		// Adds JLabel prompting user to input message
		JLabel inputEncodeLab = new JLabel("Input message to encrypt: ");
		add(inputEncodeLab);

		// Creates and add JTextField to add message
		messagejtf = new JTextField();
		add(messagejtf);

		// Makes Encrypt Message button, adds ActionListener, and adds to JPanel
		JButton encryptButton = new JButton("Encrypt Message");
		ActionListener encrypt_button_listener = new encryptButtonListener();
		encryptButton.addActionListener(encrypt_button_listener);
		add(encryptButton);

		// Creates and Adds JLabel denoting that the encrypted text will be printed below
		JLabel encryptedTextLab = new JLabel("Encrypted Text: ");
		add(encryptedTextLab);

		// Initializes values of encrypted and decrypted text to describe that there is nothing to print yet
		encrypted_text = "Nothing encrypted yet.";
		decrypted_text = "Nothing decrypted yet.";

		// Creates, formats, and adds JTextArea that will contain encrypted text
		encryptedTextjta = new JTextArea(encrypted_text);
		encryptedTextjta.setLineWrap(true);
		encryptedTextjta.setEditable(false);
		encryptedTextjsp = new JScrollPane(encryptedTextjta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(encryptedTextjsp);

		// Adds JLabel prompting user to input cipher text
		JLabel inputDecodeLab = new JLabel("Input cipher to decrypt: ");
		add(inputDecodeLab);

		// Creates and add JTextField to add cipher text
		cipherjtf = new JTextField();
		add(cipherjtf);

		// Makes Decrypt Message button, adds ActionListener, and adds to JPanel
		JButton decryptButton = new JButton("Decrypt Message");
		ActionListener decrypt_button_listener = new decryptButtonListener();
		decryptButton.addActionListener(decrypt_button_listener);
		add(decryptButton);

		// Creates and Adds JLabel denoting that the decrypted text will be printed below
		JLabel decryptedTextLab = new JLabel("Decrypted Text: ");
		add(decryptedTextLab);

		// Creates, formats, and adds JTextArea that will contain decrypted text
		decryptedTextjta = new JTextArea(decrypted_text);
		decryptedTextjta.setLineWrap(true);
		decryptedTextjta.setEditable(false);
		JScrollPane decryptedTextjsp = new JScrollPane(decryptedTextjta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(decryptedTextjsp);

	}

	private class encryptButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Grabs public_key from file
			File public_key_file = new File("public_key.txt");
			String public_key = null;
			try {
				Scanner scanner = new Scanner(public_key_file);
				public_key = scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			// Create encrypted_text from message and public_key
			logic generated_keys = new logic();
			String message = messagejtf.getText();
			encrypted_text = generated_keys.Encrypt(message, public_key);

			// Fill JTextField with encrypted_text
			encryptedTextjta.setText(encrypted_text);

		}

	}

	private class decryptButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			// Grabs public_key from file
			File private_key_file = new File("private_key.txt");
			String private_key = null;
			try {
				Scanner scanner = new Scanner(private_key_file);
				private_key = scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			// Creates decrypted_text from cipher and private_key
			logic generated_keys = new logic();
			String cipher = cipherjtf.getText();
			decrypted_text = generated_keys.Decrypt(cipher, private_key);

			// Fill JTextField with decrypted_text
			decryptedTextjta.setText(decrypted_text);

		}
	}
}