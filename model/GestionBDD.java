package model;

import java.sql.*;
import java.util.ArrayList;


/**
 * Classe GestionBDD. C'est cet classe qui procède aux connexion, ajout, modification, suppression, déconnexion de
 * la base de donnée.
 * @author Yohann & Evan
 * @since 07/05/2015
 * @version 1.0.1
 */
public class GestionBDD {

    private ArrayList<String> list_categorie;
    private ArrayList<String> list_theme;
    private ArrayList<String> list_question;
    private ArrayList<Integer> list_idQuestion;
    private ArrayList<Integer> list_idTheme;
    private Connection con;
    private PreparedStatement pst;
    private Statement st;
    private ResultSet rs;
    private String driver, url, user, passwd;

    /**
     * Constructeur de la classe Gestion BDD
     * @param adresse de la basse de donnée
     * @param nomBdd nom de la base de donnée
     * @param utilisateur utilisateur de la bdd
     * @param mdp mdp de l'utilisateur de la bdd
     * @throws Exception Remonte les exceptions afin de ne pas être traité dans le modèle 
     */

    public GestionBDD(String adresse, String nomBdd, String utilisateur, String mdp) throws Exception{

        this.list_categorie = new ArrayList<String>();
        this.list_theme = new ArrayList<String>();
        this.list_question = new ArrayList<String>();
        this.list_idQuestion = new ArrayList<Integer>();
        this.list_idTheme = new ArrayList<Integer>();


        this.driver = ("com.mysql.jdbc.Driver");
        this.url = ("jdbc:mysql://"+adresse+"/"+nomBdd);
        this.user = (utilisateur);
        this.passwd = (mdp);
        connexion();


    }

    /**
     * Méthode connexion, utilisé afin de se connecter à la BDD
     * @throws Exception
     */
    public void connexion() throws Exception{
        this.con = null;
        this.st = null;
        this.rs = null;
        this.pst = null;
        con = DriverManager.getConnection(url, user, passwd);
        

    }

    /**
     * Méthode déconnexion, ferme les connexions avec la BDD
     * @throws Exception
     */
    public void deconnexion() throws Exception{
        
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
            if (pst !=null)pst.close();

       
    }

    /**
     * Méthode Lister Catégorie, permet, comme son nom l'indique, de lister les catégories.
     * Pour ce faire, il y a une connexion à la BDD, une rêquete et on ajoute les résultats de la rêqutes à une liste.
     * @throws Exception
     */
    public void listerCategorie() throws Exception{
      
        try {
            connexion();
            Class.forName(driver).newInstance();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM CATEGORIE");

            list_categorie.clear();

            while (rs.next()) {
                list_categorie.add(rs.getString(1));
            }

        } finally {
            deconnexion();
        }

    }

    /**
     * Méthode lister thème, utilisé pour lister les thèmes d'une catégorie.
     * @param categorie String, utilisé dans la requête de selection de theme, afin de ne lister que les theme d'une catégorie
     * @throws Exception
     */
    public void listerTheme(String categorie) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT * FROM `THEME` WHERE Nom_Categorie = (?) ;");
            pst.setString(1, categorie);
            rs = pst.executeQuery();


            list_theme.clear();
            list_idTheme.clear();
            while (rs.next()) {

                int id = rs.getInt(1);
                list_idTheme.add(id);
                String Theme1 = rs.getString(2);
                String Theme2 = rs.getString(3);
                String tmp = Theme1 + " - " + Theme2;

                list_theme.add(tmp);
            }

        } finally {
            deconnexion();


        }


    }

/**
 * Méthode qui ajoute une catégorie dans la bdd.
 * @param ajout String contenant le mot à ajouter dans la bdd.
 * @throws Exception 
 */
    public void ajouterCategorie(String ajout) throws Exception{


        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("INSERT INTO CATEGORIE(Nom_Categorie) VALUES (?)");
            pst.setString(1, ajout);
            pst.executeUpdate();

        }finally {
           deconnexion();

        }
    }

/**
 * Méthode de mofication de catégorie.
 * @param motAmodif String, nom de la catégorie à modifier
 * @param modification String, nouveau nom de la catégorie
 * @throws Exception
 */
    public void modifierCategorie( String motAmodif, String modification) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("UPDATE  CATEGORIE SET Nom_Categorie = (?) WHERE `CATEGORIE`.`Nom_Categorie` = (?) ;");
            pst.setString(1, modification);
            pst.setString(2, motAmodif);
            pst.executeUpdate();
        } finally {
         deconnexion();


        }
    }

    /**
     * Méthode de suppression de catégorie
     * @param motAdelete Nom de la catégorie à modifer.
     * @throws Exception 
     */
    public void supprimerCategorie(String motAdelete) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("DELETE FROM CATEGORIE WHERE Nom_Categorie = (?) ;");
            pst.setString(1, motAdelete);
            pst.executeUpdate();

        }finally {
           deconnexion();
        }
    }

/**
 * Méthode getList_categorie
 * @return list_categorie : la liste des catégories
 */
    public ArrayList<String> getList_categorie() {
        return this.list_categorie;
    }
    /**
     * Méthode getList_idTheme
     * @return list_idTheme
     */
    public ArrayList<Integer> getList_idTheme() {
        return this.list_idTheme;
    }

/**
 * Méthode permettant la suppression d'un thème.
 * @param id_theme id du thème à supprimer.
 * @throws Exception
 */
    public void supprimerTheme(int id_theme) throws Exception{

        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("DELETE FROM THEME WHERE Id_Theme = (?) ;");
            pst.setInt(1, id_theme);
            pst.executeUpdate();

        }finally {
           deconnexion();
        }
    }

/**
 * Méthode permettant la création d'un thème
 * @param cate Nom de la catégorie où ajouter le thème
 * @param Theme1 Nom du sel
 * @param Theme2 Nom du poivre
 * @throws Exception
 */
    public void createTheme(String cate, String Theme1, String Theme2)throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("INSERT INTO `THEME` (`Id_Theme`, `Theme1`, `Theme2`, `Nom_Categorie`) VALUES (NULL, (?), (?), (?));");
            pst.setString(1, Theme1);
            pst.setString(2,Theme2);
            pst.setString(3, cate);


            pst.executeUpdate();

        } finally {
           deconnexion();
        }
    }

/**
 * Méthode de modification de thème
 * @param theme1 Nouveau nom du sel
 * @param id_theme correspond à l'id du thème qui nécessite une modification
 * @param theme2 Nouveau nom du poivre
 * @throws Exception
 */
    public void modifTheme(String theme1, int id_theme, String theme2) throws Exception{

        try {


            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("UPDATE `THEME` SET `Theme1`=(?),`Theme2`=(?) WHERE `Id_Theme`=(?) ; ");
            pst.setString(1, theme1);
            pst.setString(2, theme2);

            pst.setInt(3, id_theme);
            pst.executeUpdate();

        } finally {
            deconnexion();
        }
    }
/**
 * Méthode qui récupérer la liste des thèmes
 * @return list_theme
 */
    public ArrayList<String> getList_theme() {
        return this.list_theme;
    }
    
    
/**
 * Méthode lister question qui liste les questions associé à un thème.
 * @param id_theme id du thème où l'on veut lister les questions
 * @throws Exception
 */
    public void listerQuestion(int id_theme) throws Exception{
        if(id_theme == 0){
            list_question.clear();
        }else {
            try {

                Class.forName(driver).newInstance();
                connexion();


                pst = con.prepareStatement("SELECT * FROM `QUESTION` WHERE Id_Theme = (?) ;");
                pst.setInt(1, id_theme);
                rs = pst.executeQuery();


                list_question.clear();
                list_idQuestion.clear();
                while (rs.next()) {
                    //String tmp = rs.getString(1);
                    //int id = rs.getInt(4);
                    list_idQuestion.add(rs.getInt(4));
                    list_question.add(rs.getString(1));
                }

            } finally {
                deconnexion();
            }
        }

    }
    /**
     * Méthode permettant de récupérer la list des id_question
     * @return list_idQuestion
     */
    public ArrayList<Integer> getList_idQuestion(){return this.list_idQuestion;}

    /**
     * Méthode de création de question
     * @param intitu_question Intitulé de la question à ajouter à la BDD
     * @param reponse entier correspond à la réponse, peut prendre comme valeur 0,1 ou 2
     * @param id_theme correspond à l'id theme de la table question
     * @param explication String, si l'utilisateur saisie une explication, l'ajoute à la bdd, peut être nulle.
     * @throws Exception
     */
    public void createQuestion(String intitu_question, int reponse, int id_theme, String explication) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("INSERT INTO `QUESTION` (`Intitule_Question`, `Reponse`, `Explication`, `Id_Theme`) VALUES ((?),(?), (?), (?));");
            pst.setString(1, intitu_question);
            pst.setInt(2, reponse);
            pst.setString(3, explication);
            pst.setInt(4, id_theme);


            pst.executeUpdate();

        } finally {
           deconnexion();


        }

    }
/**
 * Méthode de modification d'un question.
 * @param intitu_question Nouveau intitulé de question
 * @param id_question Int, correspond à l'id, de la table question, à modifier
 * @param expli String, modifie l'explication
 * @param reponse Int, change la valeur de la réponse qui peut être soit 0, 1 ou 2
 * @throws Exception
 */
    public void modifQuestion(String intitu_question, int id_question, String expli, int reponse) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("UPDATE `QUESTION` SET `Intitule_Question` = (?), `Reponse` = (?), `Explication` = (?) WHERE `QUESTION`.`Id_Question` = (?); ");
            pst.setString(1, intitu_question);
            pst.setInt(2, reponse);
            pst.setString(3, expli);
            pst.setInt(4, id_question);
            pst.executeUpdate();

        } finally {
            deconnexion();


        }

    }
/**
 * Méthode de suppression d'une question
 * @param id_question Int, id de la question à supprimer
 * @throws Exception
 */
    public void deleteQuestion(int id_question) throws Exception{

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("DELETE FROM `QUESTION` WHERE `QUESTION`.`Id_Question` = (?)");
            pst.setInt(1, id_question);
            pst.executeUpdate();

        } finally {
           deconnexion();
        }

    }
/**
 * Méthode getList_question qui permet de récupérer la liste de question.
 * @return
 */
    public ArrayList<String> getList_question() {
        return this.list_question;
    }

    /**
     * Méthode qui permet de récupérer le nom du premier thème.
     * @param id_theme Int, correspond à l'id du thème que l'on souhaite récupérer
     * @return Theme1 
     * @throws Exception
     */
    public String Theme1(int id_theme) throws Exception{

        String tmp = new String();

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT Theme1 FROM THEME WHERE Id_Theme = (?)  ;");
            pst.setInt(1,id_theme);
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getString(1);
            }

        }  finally {
          deconnexion();

        }
        return tmp;

    }
/**
 * Méthode qui permet de récupérer le nom du second thème
 * @param id_theme Int, correspond à l'id du thème que l'on souhaite récupérer
 * @return Theme2
 * @throws Exception
 */
    public String Theme2(int id_theme) throws Exception{

        String tmp = new String();

        try {

            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT Theme2 FROM THEME WHERE Id_Theme = (?)  ;");
            pst.setInt(1,id_theme);
            rs = pst.executeQuery();


            while (rs.next()) {
                tmp = rs.getString(1);
            }

        } finally {
            deconnexion();
        }
        return tmp;

    }

    /**
     * Méthode qui permet de sélectionner la réponse d'une question et de la retourner.
     * @param id_question Int, id de la question dont on a besoin de la réponse
     * @return Reponse
     * @throws Exception
     */
    public int Reponse(int id_question) throws Exception{

        int tmp = 0;

        try {

            Class.forName(driver).newInstance();
            connexion();


            pst = con.prepareStatement("SELECT `Reponse` FROM `QUESTION` WHERE `Id_Question` = (?)   ;");
            pst.setInt(1,id_question);
            rs = pst.executeQuery();

            while (rs.next()){
                tmp = rs.getInt(1);
                return tmp;
            }




        } finally {
           deconnexion();
        }
        return tmp;

    }

    /**
     * Méthode qui compte le nombre de catégorie via une requête sql
     * @return nombreCategorie
     * @throws Exception
     */
    public int nombreCategorie() throws Exception{

        int tmp = 0;
        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT count(*) FROM `CATEGORIE`  ;");
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt(1);
            }
        }  finally {
           deconnexion();
        }
        return tmp;
    }
    /**
     * Méthode qui compte le nombre de thème via une requête sql
     * @param valCat String, correspond à la catégorie 
     * @returnnombreTheme
     * @throws Exception
     */
    public int nombreTheme(String valCat) throws Exception{

        int tmp = 0;
        try {
            Class.forName(driver).newInstance();
connexion();
            pst = con.prepareStatement("SELECT count(*) FROM `THEME` Where `Nom_Categorie` = (?) ;");
            pst.setString(1, valCat);
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt(1);
            }
        } finally {
            deconnexion();
        }
        return tmp;
    }
    /**
     * Méthode qui retourne le nombre de question associé à un thème.
     * @param idtheme Int, id du thème où l'on veut savoir le nombre de question
     * @return nombreQuestion
     * @throws Exception
     */
    public int nombreQuestion(int idtheme) throws Exception{

        int tmp = 0;
        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT count(*) FROM `QUESTION` WHERE `Id_Theme` = (?) ;");
            pst.setInt(1, idtheme);
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt(1);
            }
        } finally {
           deconnexion();

        }
        return tmp;
    }
/**
 * Méthode permettant de récupérer l'explication.
 * @param idQuestion id de la question où l'on veut récupérer l'explication
 * @return Explication
 * @throws Exception
 */
    public String Explication(int idQuestion) throws Exception{

        String explication = new String();
        try {
            Class.forName(driver).newInstance();
connexion();
            pst = con.prepareStatement("SELECT `Explication` FROM `QUESTION` WHERE `Id_Question` = (?) ;");
            pst.setInt(1, idQuestion);
            rs = pst.executeQuery();
            while (rs.next()) {
                explication = rs.getString(1);
            }
        }finally {
            deconnexion();

        }
        return explication;
    }
    /**
     * Méthode qui permet de récupérer l'entier bonne réponse
     * @param idQuestion id question de la table question 
     * @return BonneReponse
     * @throws Exception
     */
    public int BonneReponse(int idQuestion) throws Exception{

        int reponse = 0;
        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT `Reponse` FROM `QUESTION` WHERE `Id_Question` = (?) ;");
            pst.setInt(1, idQuestion);
            rs = pst.executeQuery();
            while (rs.next()) {
                reponse = rs.getInt(1);
            }
        } finally {
            deconnexion();

        }
        return reponse;
    }
    /**
     * Méthode permettant de faire des statistiques sur le nombre de thème présent dans la bdd
     * @return nombre de thème
     * @throws Exception
     */
    public int statTheme() throws Exception{

        int tmp = 0;
        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT count(*) FROM `THEME`  ;");
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt(1);
            }
        } finally {
           deconnexion();
        }
        return tmp;
    }
    /**
     * Méthode permettant de faire des statistiques sur le nombre de question présent dans la bdd
     * @return nombre de question
     * @throws Exception
     */
    
    public int statQuestion() throws Exception{

        int tmp = 0;
        try {
            Class.forName(driver).newInstance();
            connexion();
            pst = con.prepareStatement("SELECT count(*) FROM `QUESTION`  ;");
            rs = pst.executeQuery();
            while (rs.next()) {
                tmp = rs.getInt(1);
            }
        } finally {
           deconnexion();
        }
        return tmp;
    }


}