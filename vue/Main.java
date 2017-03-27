package vue;
import model.GestionBDD;

import javax.swing.*;
/**
 * Classe principal du programme.
 * @author Yohann
 *
 */
public class Main {
	/**
	 * Méthode main. Point d'entrée de l'application.
	 * @param args
	 */

    public static void main(String[] args) {
    	
       SplashScreen test = new SplashScreen(("/images/load.gif"), 4000);
        test.end();

        JTextField addressBDD = new JTextField("127.0.0.1");

        JTextField nomBDD = new JTextField("BurgerQuiz");
        JTextField utilisateur = new JTextField("Admin");
        JTextField motDePasse = new JTextField();
        int choix = JOptionPane.showOptionDialog(null,
                new Object[] {"Adresse de la BDD",addressBDD,"Nom de la BDD",nomBDD,"Utilisateur :", utilisateur, "Mot de passe :", motDePasse},
                "Connexion",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,  null, null);

        switch (choix) {
            case JOptionPane.OK_OPTION :break;

            case JOptionPane.CANCEL_OPTION: System.exit(0);
            default : JOptionPane.showMessageDialog(null, "Non connecté...", "ATTENTION", JOptionPane.ERROR_MESSAGE); System.exit(0);
        }
		try {
			GestionBDD bdd = new GestionBDD(addressBDD.getText(),nomBDD.getText(),utilisateur.getText(),motDePasse.getText());
	        new InterfaceBDD(bdd);

		} catch (Exception e) {
            JOptionPane.showMessageDialog(null, e,"Erreur",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}



    }
}
