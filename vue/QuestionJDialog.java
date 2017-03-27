package vue;

import model.GestionBDD;

import javax.swing.*;
import javax.swing.border.TitledBorder;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Classe QuestionJdialog qui est une JDialog personnalis� afin de permettre l'ajout et la modification de question.
 *  @Author Yohann on 04/06/2015.
 *
 */
public class QuestionJDialog extends JDialog implements ActionListener{

    
	private static final long serialVersionUID = 1L;
	private JButton btnVal, btnCancel;
    private JTextField jtfQuestion;
    private JTextArea jtaExplication;
    private JPanel panQuestion, panReponse, panExplication;
    private JRadioButton Theme1, Theme2, LesDeux;
    private int Reponse;
    private GestionBDD bdd;
    private int idtheme, idquestion;
    private int modif;

    /**
     * Constructeur pour la modification de r�ponse.
     * @param f JFrame, c'est pour d�finir qui est la fen�tre parent, qui est modal par rapport � l'autre.
     * @param bdd GestionBDD afin d'int�rargir avec la BDD.
     * @param idTheme int correspondant � l'id theme de la table theme dans la bdd.
     * @param question String, correspond � la question que l'on veut modifier, c'est pour pr�remplir le JTextField avec l'intitul� de la question, comme �a l'utilisateur voit directement ce qui est existant.
     * @param rep int, correspond � la valeur de la r�ponse, � comme valeur possible 0,1 ou 2.
     * @param idQuestion int, coresspond � l'id question que l'on veut modifier de la table question dans la bdd.
     */
    public QuestionJDialog(JFrame f, GestionBDD bdd, int idTheme, String question, int rep, int idQuestion ) {
        this(f, bdd, idTheme,false);
        this.Reponse=rep;
        this.jtfQuestion.setText(question);
        if (rep == 0){
            this.LesDeux.setSelected(true);

        }else if (rep == 1){
            this.Theme1.setSelected(true);
        }else{
            this.Theme2.setSelected(true);
        }
        this.modif = 1;
        this.idquestion = idQuestion;

        this.setVisible(true);


    }

    /**
     * Constructeur pour l'ajout de question
     * @param f JFrame, correspond � la frame InterfaceBDD, on en a besoin afin de d�termin� qui est modal par rapport � qui.
     * @param bdd GestionBDD afin d'int�rargir avec la BDD.
     * @param idTheme int correspondant � l'id theme de la table question dans la bdd.
     * @param etat_ajout boolean, utilis� afin de savoir si on est dans l'ajout ou la modification (pour le setVisible).
     */
    public QuestionJDialog(JFrame f, GestionBDD bdd, int idTheme, boolean etat_ajout) {
        super(f, true);
        this.setSize(new Dimension(630, 275));
        this.setLocationRelativeTo(null);
        this.bdd = bdd;
        this.idtheme = idTheme;
        this.Reponse = -1; // On donne une valeur par d�fault car sinon il y a un probl�me quand on veut ajouter plusieurs cat�gories
        this.modif = 0;
        /******************************/
        /* PANEL AJOUTER UNE QUESTION */
        /*****************************/

        this.panQuestion = new JPanel();
        panQuestion.setBackground(Color.white);
       // panQuestion.setBorder(BorderFactory.createTitledBorder("Ajouter une question"));
        panQuestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Ajouter une question", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
        panQuestion.setPreferredSize(new Dimension(300,75));
        jtfQuestion = new JTextField(20);
        panQuestion.add(jtfQuestion);


        /******************************/
        /* PANEL AJOUTER UNE REPONSE */
        /*****************************/
        this.panReponse = new JPanel();
        panReponse.setBackground(Color.white);
        panReponse.setBorder(BorderFactory.createTitledBorder("Ajouter une r�ponse"));
        panReponse.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Ajouter une r�ponse", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
        panReponse.setPreferredSize(new Dimension(300, 75));

        ButtonGroup group = new ButtonGroup();
        try {
            this.Theme1 = new JRadioButton(bdd.Theme1(idtheme));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.Theme1.addActionListener(this);

        try {
            this.Theme2 = new JRadioButton(bdd.Theme2(idtheme));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.Theme2.addActionListener(this);

        this.LesDeux = new JRadioButton("Les deux");
        this.LesDeux.addActionListener(this);

        group.add(Theme1);
        group.add(Theme2);
        group.add(LesDeux);
        JPanel panBtnRep = new JPanel();
        panBtnRep.setBackground(Color.white);
        panBtnRep.add(Theme1);
        panBtnRep.add(Theme2);
        panBtnRep.add(LesDeux);
        Theme1.setBackground(Color.white);
        Theme2.setBackground(Color.white);
        LesDeux.setBackground(Color.white);

        panReponse.add(panBtnRep);

        /*********************************/
        /* PANEL AJOUTER UNE EXPLICATION */
        /*********************************/
        this.panExplication = new JPanel();
        panExplication.setBackground(Color.white);
        //panExplication.setBorder(BorderFactory.createTitledBorder("Ajouter une explication (facultatif) :"));
        panExplication.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Ajouter une explication (facultatif) :", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));

        this.jtaExplication = new JTextArea(5,50);
       // jtaExplication.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(85, 85, 85)), "Zone de saisie :", TitledBorder.LEFT, TitledBorder.TOP, new Font("arial", Font.PLAIN, 14), new Color(85, 85, 85)));
        JScrollPane jspExplication = new JScrollPane();
        jspExplication.add(jtaExplication);
        panExplication.add(jtaExplication);


        /*********************************/
        /* PANEL BOUTONS                 */
        /*********************************/

        JPanel panBtn = new JPanel();
        this.btnVal = new JButton("Valider");
        this.btnCancel = new JButton("Annuler");
        panBtn.add(btnVal);
        panBtn.add(btnCancel);
        this.btnVal.addActionListener(this);
        this.btnCancel.addActionListener(this);
        panBtn.setBackground(Color.white);





        JPanel content = new JPanel();
        content.add(panQuestion);
        content.add(panReponse);
        content.setBackground(Color.white);

        this.getContentPane().setBackground(Color.white);

        this.getContentPane().add(content, BorderLayout.NORTH);
        this.getContentPane().add(panExplication,BorderLayout.CENTER);
        this.getContentPane().add(panBtn, BorderLayout.SOUTH);


        this.pack();
        if (etat_ajout)setVisible(true) ;

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVal){

            if ((Reponse ==0 || Reponse ==1  || Reponse ==2) && !jtfQuestion.getText().isEmpty()){
                if (modif == 0){
                    try {
                        bdd.createQuestion(jtfQuestion.getText(), Reponse, idtheme, jtaExplication.getText());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Impossible de cr�er la question.", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                    }

                }else{
                    try {
                        bdd.modifQuestion(jtfQuestion.getText(), idquestion, jtaExplication.getText(),Reponse);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Impossible de modifier la question.", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                    }
                }
                setVisible(false);

            }else{
                JOptionPane.showMessageDialog(null,"Il faut remplir le champ question ainsi que clicker sur un bouton pour la r�ponse","Erreur",JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == Theme1){
            this.Reponse = 1;

        }
        if (e.getSource() == Theme2){
            this.Reponse = 2;

        }if (e.getSource() == LesDeux){
            this.Reponse = 0;
        }

        if (e.getSource() == btnCancel){
            this.setVisible(false);
        }
    }
}
