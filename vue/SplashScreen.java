package vue;




import javax.swing.*;

import java.awt.*;


public class SplashScreen extends JWindow {
    
	private static final long serialVersionUID = 1L;
	private static SplashScreen instance;
	
	/**
	 * Classe récupéré sur un site web.  
	 * Utilisé pour l'affichage du SplashScreen.
	 * @author http://www.commentcamarche.net/forum/affich-2671677-java-utiliser-un-splash-screen
	 * @param imagePath correspond au chemin de l'image que l'on souhaite afficher dans le SplashScreen
	 * @param minDuration Correspond au temps d'apparaition du SplashScreen
	 */
    public SplashScreen(String imagePath, long minDuration) {
        super();
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        if (imagePath != null && !imagePath.equals("")) {
            ImageIcon img = new ImageIcon(SplashScreen.class.getResource(imagePath));
            int imgHeight = img.getIconHeight();
            int imgWidth = img.getIconWidth();
            int imgX = (screenDimension.width - imgWidth) / 2;
            int imgY = (screenDimension.height - imgHeight) / 2;
            JLabel jl_img = new JLabel(img);
            jl_img.setPreferredSize(new Dimension(imgWidth, imgHeight));
            getContentPane().add(new JLabel(img));
            setLocation(imgX,imgY);
        } else {
            setLocation(screenDimension.width / 2, screenDimension.height / 2);
        }
        pack();
        setVisible(true);
        long tEnd = System.currentTimeMillis() + minDuration;
        while (System.currentTimeMillis() < tEnd) {
        }
    }

    public static SplashScreen getInstance(String imagePath, long minDuration) {
        if (instance == null) {
            instance = new SplashScreen(imagePath, minDuration);
        }
        return instance;
    }

    public static SplashScreen getInstance(String imagePath) {
        if (instance == null) {
            instance = new SplashScreen(imagePath, 0);
        }
        return instance;
    }

    public static SplashScreen getInstance() {
        return getInstance(null, 0);
    }

    public void end() {
        setVisible(false);
        dispose();
    }


}