package vue;

import model.GestionBDD;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;


/**
 * Classe Interface BBD permettant l'affichage.
 * @author Yohann&Evan
 * @since 06/05/2015
 * @version 1.0
 */

public class InterfaceBDD extends JFrame implements ActionListener, ListSelectionListener { 
    
	private static final long serialVersionUID = 1L;

	private JButton but_creer_categorie, but_modifier_categorie, but_supprimer_categorie, but_ajout_question, but_modif_question, but_supprimer_question;

    private JButton but_ajout_theme, but_modif_theme, but_supprimer_theme;
    private JMenuBar menu;
    private JMenu apropos, aide, deconnecter, informations;
    private JMenuItem apropos2, quitter, util, nbrCategorie, nbrTheme, nbrQuestion;
    private GestionBDD bdd;
    private ArrayList<Integer> list_idQuestion;
    private ArrayList<Integer> list_idTheme;
    private JPanel panQuestion, panCategorie, panTheme;
    private JLabel Rep;
    private JTextArea jtaExpli;
    private int id_theme, id_question;
    private String ValeurCategorie;


    private JList list, list_question,list_theme;

    /**
     *  Constructeur de la classe InterfaceBDD
     * @param bddtmp Récupère la base de donnée initialisé dans la classe Main
     */
    
    public InterfaceBDD(GestionBDD bddtmp) {
        super("Editeur de question du Burger Quiz by Yoh et Vavan");
        ImageIcon img = new ImageIcon(InterfaceBDD.class.getResource("/images/burger.png"));
        this.setIconImage(img.getImage());
        this.bdd = bddtmp;

        this.creerInterface();
        this.creerBarMenu();
        mettreAjour();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * Méthode créant les différents panel, permettant aussi l'ajout des listeners.
     */

    private void creerInterface() {

        //Categorie
        this.panCategorie = new JPanel();
        panCategorie.setBackground(Color.white);
        panCategorie.setPreferredSize(new Dimension(325, 300));
        panCategorie.setBorder(BorderFactory.createTitledBorder("Catégorie"));
        this.list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel panBouton = new JPanel();
        panBouton.setBackground(Color.white);

        this.but_creer_categorie = new JButton("Ajouter");
        this.but_modifier_categorie = new JButton("Modifier");
        this.but_supprimer_categorie = new JButton("Supprimer");
        panBouton.add(but_creer_categorie);
        panBouton.add(but_modifier_categorie);
        panBouton.add(but_supprimer_categorie);

        but_creer_categorie.addActionListener(this);
        but_modifier_categorie.addActionListener(this);
        but_supprimer_categorie.addActionListener(this);
        JScrollPane jsp_cat = new JScrollPane(list);
        jsp_cat.setPreferredSize(new Dimension(200, 200));

        panCategorie.add(jsp_cat);
        panCategorie.add(panBouton);

        //Theme
        this.panTheme = new JPanel();
        panTheme.setBackground(Color.white);
        panTheme.setPreferredSize(new Dimension(325, 300));
        panTheme.setBorder(BorderFactory.createTitledBorder("Theme"));
        panTheme.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Theme", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));


        list_idTheme = new ArrayList<Integer>();
        new JLabel("Theme");
        this.list_theme = new JList();
        list_theme.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panBoutonTheme = new JPanel();
        panBoutonTheme.setBackground(Color.white);


        this.but_ajout_theme = new JButton("Ajouter ");
        this.but_modif_theme = new JButton("Modifier ");
        this.but_supprimer_theme = new JButton("Supprimer ");

        panBoutonTheme.add(but_ajout_theme);
        panBoutonTheme.add(but_modif_theme);
        panBoutonTheme.add(but_supprimer_theme);
        JScrollPane jsp_theme = new JScrollPane(list_theme);
        jsp_theme.setPreferredSize(new Dimension(200, 200));

        panTheme.add(jsp_theme);
        panTheme.add(panBoutonTheme);
        but_ajout_theme.addActionListener(this);
        but_modif_theme.addActionListener(this);
        but_supprimer_theme.addActionListener(this);


        //Question
        list_idQuestion = new ArrayList<Integer>();
        this.panQuestion = new JPanel();
        panQuestion.setBackground(Color.white);
        panQuestion.setPreferredSize(new Dimension(325, 300));
        panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

        new JLabel("Question");
        this.list_question = new JList();
        list_question.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JPanel panBoutonQuestion = new JPanel();
        panBoutonQuestion.setBackground(Color.white);


        this.but_ajout_question = new JButton("Ajouter");
        this.but_modif_question = new JButton("Modifier");
        this.but_supprimer_question = new JButton("Supprimer");
        panBoutonQuestion.add(but_ajout_question);
        panBoutonQuestion.add(but_modif_question);
        panBoutonQuestion.add(but_supprimer_question);

        JScrollPane jsp_question = new JScrollPane(list_question);
        jsp_question.setPreferredSize(new Dimension(200, 200));

        panQuestion.add(jsp_question);

        panQuestion.add(panBoutonQuestion);
        but_ajout_question.addActionListener(this);
        but_modif_question.addActionListener(this);
        but_supprimer_question.addActionListener(this);

        //REPONSE && EXPLICATION
        JPanel panRep = new JPanel();
        panRep.setBackground(Color.white);
        panRep.setPreferredSize(new Dimension(325, 75));
        panRep.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Réponses", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
        this.Rep = new JLabel();

        panRep.add(Rep);

        final JPanel panExp = new JPanel();
        panExp.setBackground(Color.white);
        panExp.setPreferredSize(new Dimension(325, 225));
        panExp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Explications", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
        this.jtaExpli = new JTextArea(1,1);
        JScrollPane jspExpli = new JScrollPane();
        jspExpli.setPreferredSize(new Dimension(200, 50));
        jspExpli.setViewportView(jtaExpli);
        jspExpli.setBorder(null);
        jtaExpli.setEnabled(false);
        jtaExpli.setDisabledTextColor(Color.black);
        System.out.println(jtaExpli.getText());
        panExp.add(jspExpli);
        System.out.println(jtaExpli.getText());



        JPanel panRepExp = new JPanel();
        panRepExp.setLayout(new BoxLayout(panRepExp, BoxLayout.Y_AXIS));

        panRepExp.add(panRep);
        panRepExp.add(panExp);


        JPanel content = new JPanel();

        content.setBackground(Color.white);
        content.add(panCategorie);
        content.add(panTheme);
        content.add(panQuestion);
        content.add(panRepExp);
        ImageIcon imgPrincipal = new ImageIcon(InterfaceBDD.class.getResource("/images/banniere.png"));

        JLabel image = new JLabel(imgPrincipal);
        JPanel panImage = new JPanel();
        panImage.setBackground(Color.white);

        panImage.add(image);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(panImage, BorderLayout.NORTH);


        this.but_modifier_categorie.setEnabled(false);
        this.but_supprimer_categorie.setEnabled(false);
        this.but_ajout_theme.setEnabled(false);
        this.but_modif_theme.setEnabled(false);
        this.but_supprimer_theme.setEnabled(false);
        this.but_ajout_question.setEnabled(false);
        this.but_modif_question.setEnabled(false);
        this.but_supprimer_question.setEnabled(false);


        this.list.addListSelectionListener(this);
        list_theme.addListSelectionListener(this);
        list_question.addListSelectionListener(this);

    }
    
    /**
     * Méthode permettant la création de la barre de menu.
     */

    private void creerBarMenu() {

        this.menu = new JMenuBar();

        this.apropos = new JMenu("A propos");
        this.aide = new JMenu("Aide");
        this.deconnecter = new JMenu("Déconnecter");
        this.quitter = new JMenuItem("Quitter");
        quitter.addActionListener(this);
        this.deconnecter.add(quitter);

        this.apropos2 = new JMenuItem("Informations");
        apropos2.addActionListener(this);
        this.util = new JMenuItem("Comment utiliser l'interface ?");

        this.apropos.add(apropos2); // On initialise le menu
        this.aide.add(util);

        this.informations = new JMenu("Statistiques BDD");
        this.nbrCategorie = new JMenuItem("Nombre de catégories");
        this.nbrTheme = new JMenuItem("Nombre de Thèmes");
        this.nbrQuestion = new JMenuItem("Nombre de Questions");
        nbrCategorie.addActionListener(this);
        nbrTheme.addActionListener(this);
        nbrQuestion.addActionListener(this);

        this.informations.add(nbrCategorie);
        this.informations.add(nbrTheme);
        this.informations.add(nbrQuestion);

        this.menu.add(apropos);
        this.menu.add(informations);
        this.menu.add(aide);
        this.menu.add(deconnecter);

        this.setJMenuBar(menu);

    }

    /**
     * Méthode mettant à jour le panel catégorie.
     */
    private void mettreAjour() {
        this.list.removeAll();
        try{
            bdd.listerCategorie();
            couleurCategorie(bdd.nombreCategorie());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,  "Impossible de lister les catégories. Vérifier que vous êtes connecter à la bonne base de données.", e.getMessage(), JOptionPane.ERROR_MESSAGE);

        }
        list.setListData(bdd.getList_categorie().toArray());
    }
    
    /**
     * Méthode mettant à jour le panel question.
     * @param id_theme entier correspondant à l'id de la table thème.
     */

    private void mettreAjourQuestion(int id_theme) {
        try {
            bdd.listerQuestion(id_theme);
            couleurQuestion(bdd.nombreQuestion(id_theme));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Impossible de lister les questions. ", JOptionPane.ERROR_MESSAGE);

        }
        list_question.removeAll();
        list_question.setListData(bdd.getList_question().toArray());
    }
    
    /**
     * Méthode mettant à jour le panel thème.
     * @param valCat chaine de caractère correspondant à Nom_catégorie dans la BDD.
     */

    private void mettreAjourTheme(String valCat) {
        try{
            bdd.listerTheme(valCat);
            couleurTheme(bdd.nombreTheme((String) list.getSelectedValue()));


        } catch (Exception e) {
            e.printStackTrace();
        }
        list_theme.removeAll();
        list_theme.setListData(bdd.getList_theme().toArray());
    }
    
    /**
     *Méthode mettant à jour le panel réponse.  
     * @param id_question entier correspondant à l'id question dans la BDD
     * @param id_reponse entier correspondant à l'id réponse dans la BDD
     */

    private void mettreAjourReponse(int id_question, int id_reponse) {
    	try{
        if (bdd.BonneReponse(id_question)==1){
                Rep.setText(bdd.Theme1(id_theme));
            
        }else if(bdd.BonneReponse(id_question)==2){
                Rep.setText(bdd.Theme2(id_theme));
            

        }else{
            if (id_reponse == 0){
                Rep.setText("");
            }else{
                Rep.setText("Les deux");

            }

        }
    }catch(Exception e) {
        e.printStackTrace();
    }

    }
    
    /**
     * Méthode permettant le changement de couleur du panel catégorie.
     * @param nombreCategorie entier qui permet, si sa valeur est inférieur à 2 d'afficher en rouge le contour du panel, sinon en vert.
     */
    
    private void couleurCategorie(int nombreCategorie){
        if (nombreCategorie >= 2) {
            panCategorie.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(61, 181, 115)), "Categorie", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(61, 181, 115)));
        } else {
            panCategorie.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red), "Categorie", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), Color.red));

        }

    }

    /**
     * Méthode permettant le changement de couleur du panel Theme
     * @param nombreTheme int, qui permet, si la valeur est inférieur à 2 d'afficher en rouge le contour du panel, sinon en vert.
     */
    private void couleurTheme(int nombreTheme){
    		if (nombreTheme >=2){

    	        panTheme.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(61, 181, 115)), "Theme", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(61, 181, 115)));

    	        } else {
    	            panTheme.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red), "Theme", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), Color.red));

    	        }
    	
        
    }

    /**
     * Méthode permettant le changement de couleur du panel Question
     * @param nombreQuestion int, si sa valeur est >= 3, affiche le contour du panel en vert, sinon l'affiche en rouge.
     */
    private void couleurQuestion(int nombreQuestion){
        if (nombreQuestion >= 3) {
            panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(61, 181, 115)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(61, 181, 115)));

        } else {
            panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), Color.red));

        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == but_creer_categorie) {
            String txt = JOptionPane.showInputDialog(null, "Saisir le nom de la nouvelle catégorie : ");
            if (!txt.isEmpty()){
                System.out.println(txt);
                try {

                    bdd.ajouterCategorie(txt);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Vous ne pouvez rajoueter une catégorie déjà existante ! ", JOptionPane.ERROR_MESSAGE);

                }
            }


        mettreAjour();
        

    }

        if (e.getSource() == but_modifier_categorie) {
            String txt = JOptionPane.showInputDialog(null, "Saisir la modification de la catégorie : ", ValeurCategorie);
            if (!txt.isEmpty()) {
                try{
                    bdd.modifierCategorie(ValeurCategorie, txt);

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null,e1);
                    e1.printStackTrace();
                }
            }
            mettreAjour();

        }

        if (e.getSource() == but_supprimer_categorie) {
            try{
                bdd.supprimerCategorie(ValeurCategorie);

            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Impossible de supprimer la catégorie.", JOptionPane.ERROR_MESSAGE);

            }
            mettreAjour();
        }



        if (e.getSource() == but_modif_theme) {
            
            JTextField Theme1 = null;
            JTextField Theme2 = null;

            try {
                Theme1 = new JTextField(bdd.Theme1(id_theme));
                Theme2 = new JTextField(bdd.Theme2(id_theme));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            int choix = JOptionPane.showOptionDialog(null,
                    new Object[]{"Saisir Theme1 :", Theme1, "Saisir theme2 :", Theme2},
                    "Modification Theme",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (choix == 0 && !Theme1.getText().isEmpty() && !Theme2.getText().isEmpty()) {
                try {
                    bdd.modifTheme(Theme1.getText(), id_theme, Theme2.getText());
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Impossible de modifier le theme.", JOptionPane.ERROR_MESSAGE);

                }

            } else {
                JOptionPane.showMessageDialog(null, "La modification n'a pus se faire car vous n'avez pas saisi de valeur dans un des deux champs.", "Erreur", JOptionPane.ERROR_MESSAGE);

            }

            mettreAjourTheme(ValeurCategorie);
            mettreAjourQuestion(0);
            mettreAjourReponse(0, 0);
            panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

        }

        if (e.getSource() == but_supprimer_theme) {
            try{
                bdd.supprimerTheme(id_theme);

            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Impossible de supprimer le theme. ", JOptionPane.ERROR_MESSAGE);

            }

            mettreAjourTheme(ValeurCategorie);
            mettreAjourQuestion(0);
            mettreAjourReponse(0, 0);
            panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

        }

        if (e.getSource() == but_ajout_theme) {
            
            int retour = 1;
            while (retour == 1) {
                JTextField Theme1 = new JTextField();
                JTextField Theme2 = new JTextField();

                int choix = JOptionPane.showOptionDialog(null,
                        new Object[]{"Saisir Theme1 :", Theme1, "Saisir theme2 :", Theme2},
                        "Modification Theme",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                switch (choix) {
                    case JOptionPane.YES_OPTION:
                        if (!Theme1.getText().isEmpty() && !Theme2.getText().isEmpty()) {
                            try {
                                bdd.createTheme(ValeurCategorie, Theme1.getText(), Theme2.getText());
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreuru lors de la création de theme. ", JOptionPane.ERROR_MESSAGE);

                            }
                            mettreAjourTheme(ValeurCategorie);
                        } else {
                            JOptionPane.showMessageDialog(null, "Vous avez essayé d'ajouter un theme sans remplir les champs ...", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }

                        break;
                    case JOptionPane.CANCEL_OPTION: break;


                }

                retour = JOptionPane.CLOSED_OPTION;


            }

            mettreAjourTheme(ValeurCategorie);
            mettreAjourQuestion(0);
            mettreAjourReponse(0, 0);
            panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

        }


        if (e.getSource() == but_ajout_question) {
            QuestionJDialog test = new QuestionJDialog(this,bdd, id_theme, true);
            test.dispose();
            mettreAjourQuestion(id_theme);
            mettreAjourReponse(0, 0);

        }
        if (e.getSource() == but_modif_question) {
             String valuequestion = new String((String)list_question.getSelectedValue());
			try {
				int valeurReponse = bdd.Reponse(id_question);
				QuestionJDialog modif = new QuestionJDialog(this, bdd, id_theme, valuequestion,valeurReponse , id_question);
	            modif.dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            
            mettreAjourQuestion(id_theme);
            mettreAjourReponse(0, 0);

        }


        if (e.getSource() == but_supprimer_question) {
            try {
                bdd.deleteQuestion(id_question);
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Impossible de supprimer la question.", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

            }
            mettreAjourQuestion(id_theme);
            mettreAjourReponse(0, 0);
        }

        if (e.getSource() == apropos2) {
        	AproposDialog about = new AproposDialog(this);
        	about.dispose();
        }
        if (e.getSource() == quitter) System.exit(0);
        
        if (e.getSource() == nbrCategorie) {
			try {
				int tmp = bdd.nombreCategorie();
	            JOptionPane.showMessageDialog(null, "Il y a actuellement " + tmp + " catégories dans la BDD");

			} catch (Exception e1) {
				e1.printStackTrace();
			}

        }
        if (e.getSource() == nbrTheme) {
        	try{
                int tmp = bdd.statTheme();
                JOptionPane.showMessageDialog(null, "Il y a actuellement " + tmp + " thèmes dans la BDD");


        	}catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

            }

        }
        if (e.getSource() == nbrQuestion) {
			try {
				int tmp = bdd.statQuestion();
	            JOptionPane.showMessageDialog(null, "Il y a actuellement " + tmp + " questions dans la BDD");

			} catch (Exception e1) {
				e1.printStackTrace();
			}

        }

    }

    @Override
    public void valueChanged(ListSelectionEvent eventList) {
        if (eventList.getSource() == list) {
            if (!eventList.getValueIsAdjusting()) {
                if (list.getSelectedIndex() == -1) {
                    but_modifier_categorie.setEnabled(false);
                    but_supprimer_categorie.setEnabled(false);
                    but_ajout_theme.setEnabled(false);
                    mettreAjourTheme("");
                    mettreAjourQuestion(0);
                    mettreAjourReponse(0, 0);
                    jtaExpli.setText("");
                    panTheme.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Theme", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

                    panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
                    try {
						couleurCategorie(bdd.nombreCategorie());
					} catch (Exception e) {
						e.printStackTrace();
					}
                } else {
                    mettreAjourQuestion(0);
                    mettreAjourReponse(0, 0);
                    jtaExpli.setText("");
                    but_supprimer_categorie.setEnabled(true);
                    but_modifier_categorie.setEnabled(true);
                    but_ajout_theme.setEnabled(true);
					try {
						int nbr = bdd.nombreCategorie();
	                    couleurCategorie(nbr);

					} catch (Exception e) {
						e.printStackTrace();
					}
                    ValeurCategorie = (String) list.getSelectedValue();
                    mettreAjourTheme(ValeurCategorie);
                    list_idTheme = bdd.getList_idTheme();
                    panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));


                }
            }
        }
        if (eventList.getSource() == list_theme) {
            if (eventList.getValueIsAdjusting() == false) {
                if (list_theme.getSelectedIndex() == -1) {
                    but_ajout_question.setEnabled(false);
                    but_modif_theme.setEnabled(false);
                    but_supprimer_theme.setEnabled(false);
                    but_modif_question.setEnabled(false);
                    but_supprimer_question.setEnabled(false);
                    panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
                    jtaExpli.setText("");
                } else {

                    try {
						couleurTheme(bdd.nombreTheme((String) list.getSelectedValue()));
					} catch (Exception e) {
						e.printStackTrace();
					}

                    id_theme = list_idTheme.get((int) list_theme.getSelectedIndex());
                    mettreAjourQuestion(id_theme);
                    //int idtheme= list_idTheme.get((int)list_theme.getSelectedIndex());

                    //couleurQuestion(bdd.nombreQuestion(id_theme));
                    but_ajout_question.setEnabled(true);
                    but_modif_theme.setEnabled(true);
                    but_supprimer_theme.setEnabled(true);
                    list_idQuestion = bdd.getList_idQuestion();

                    mettreAjourQuestion(id_theme);
                    mettreAjourReponse(0, 0);

                }
            }


        }
        if (eventList.getSource() == list_question){
            if (eventList.getValueIsAdjusting() == false) {
                if (list_question.getSelectedIndex() == -1) {
                    but_modif_question.setEnabled(false);
                    but_supprimer_question.setEnabled(false);
                    jtaExpli.setText("");

                } else {

                    but_modif_question.setEnabled(true);
                    but_supprimer_question.setEnabled(true);
                    id_question = list_idQuestion.get(list_question.getSelectedIndex());
                    mettreAjourReponse(id_question, id_theme);
					try {
						String Explication = bdd.Explication(id_question);
	                    jtaExpli.setText(Explication);

					} catch (Exception e) {
						e.printStackTrace();
					}

                }
            }
        }

    }

	
}
