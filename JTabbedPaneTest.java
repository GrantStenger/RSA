package rsa;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class JTabbedPaneTest extends JPanel {

	private static final long serialVersionUID = 1L;

	// Constructs JTabbedPane
	public JTabbedPaneTest() {

		JTabbedPane jtp = new JTabbedPane();
		jtp.setPreferredSize(new Dimension(500, 400));
		jtp.add("Key Generation", new KeyGenPanel());
		jtp.add("Encrypt / Decrypt", new EncryptDecryptPanel());
		add(jtp);

	}

}