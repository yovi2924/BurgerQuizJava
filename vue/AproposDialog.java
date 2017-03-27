/**
 * 
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe pour la création d'une JDialog affichant l'image du menu A propos
 * @author Yohann
 *
 */
public class AproposDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JButton btnOK;
	/**
	 * Constructeur de la classe AproposDialog, elle permet l'affichage d'une image dans une jdialog personnalisé. 
	 * @param f Frame parent, dans le InterfaceBDD on passe this qui est la fenêtre parent.
	 */
	public AproposDialog(JFrame f){
		super(f,"A propos",true);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
       

        ImageIcon img = new ImageIcon(AproposDialog.class.getResource("/images/about.png"));
        int imgHeight = img.getIconHeight();
        int imgWidth = img.getIconWidth();
        int imgX = (screenDimension.width - imgWidth) / 2;
        int imgY = (screenDimension.height - imgHeight) / 2;
        
        setLocation(imgX,imgY);

        JLabel image = new JLabel(img);
        JPanel panImage = new JPanel();
        panImage.setBackground(Color.white);

        panImage.add(image);
        
        JPanel panBouton = new JPanel();
         btnOK = new JButton("OK");
        btnOK.addActionListener(this);
        panBouton.add(btnOK);
        panBouton.setBackground(Color.white);
        
        
        this.getContentPane().add(panImage, BorderLayout.NORTH);
        this.getContentPane().add(panBouton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);

	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnOK){
			this.setVisible(false);
			this.dispose();
		}
	}

}
