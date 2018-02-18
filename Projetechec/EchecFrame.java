/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author lebas
 */
public class EchecFrame extends javax.swing.JFrame implements Observateur {
    
    private static final ImageIcon PIONB = new ImageIcon("./src/img/blanches/pionB.png");
    private static final ImageIcon TOURB = new ImageIcon("./src/img/blanches/tourB.png");
    private static final ImageIcon CAVALIERB = new ImageIcon("./src/img/blanches/cavalierB.png");
    private static final ImageIcon FOUB = new ImageIcon("./src/img/blanches/fouB.png");
    private static final ImageIcon REINEB = new ImageIcon("./src/img/blanches/reineB.png");
    private static final ImageIcon ROIB = new ImageIcon("./src/img/blanches/roiB.png");
    
    private static final ImageIcon PIONN = new ImageIcon("./src/img/noires/pionN.png");
    private static final ImageIcon TOURN = new ImageIcon("./src/img/noires/tourN.png");
    private static final ImageIcon CAVALIERN = new ImageIcon("./src/img/noires/cavalierN.png");
    private static final ImageIcon FOUN = new ImageIcon("./src/img/noires/fouN.png");
    private static final ImageIcon REINEN = new ImageIcon("./src/img/noires/reineN.png");
    private static final ImageIcon ROIN = new ImageIcon("./src/img/noires/roiN.png");
    
    private static final int TAILLE = 8;

    JLabel[][] jboard;
    EchecModel modele;
    ImageIcon choix_promotion;
    
    public EchecFrame(EchecModel modele) {
        
        initComponents();
        this.modele = modele;
        
        jboard = new JLabel[TAILLE][TAILLE];
        
        for(int i=0;i<TAILLE;i++){
            for(int j=0;j<TAILLE;j++){
                
                jboard[i][j] = new JLabel();
                jboard[i][j].setPreferredSize(new Dimension(45, 45));
                
                if((j+i)%2==0){
                    jboard[i][j].setBackground(new Color(255, 206, 158));
                }else{
                    jboard[i][j].setBackground(new Color(209, 139, 71));
                }
                
                jboard[i][j].setOpaque(true);
                panelJeu.add(jboard[i][j]);
                
                jboard[i][j].addMouseListener(
                        new CaseControler(i,j,this.modele));
                
                ImageIcon icone = getIconFromInt(this.modele.getCase(i, j));
                jboard[i][j].setIcon(icone);
            }
        }
        
        this.pack();
        
    }
    
     
    public ImageIcon getIconFromInt(int i){
        switch (i){
            case 10:
                return PIONB;
            case 11:
                return TOURB;
            case 12:
                return CAVALIERB;
            case 13:
                return FOUB;
            case 14:
                return REINEB;
            case 15:
                return ROIB;
            case 20:
                return PIONN;
            case 21:
                return TOURN;
            case 22:
                return CAVALIERN;
            case 23:
                return FOUN;
            case 24:
                return REINEN;
            case 25:
                return ROIN;
            default:
                return null;
        }
    }
    
    void resetColor(){
        for(int i=0;i<TAILLE;i++){
            for(int j=0;j<TAILLE;j++){
                if((j+i)%2==0){
                    jboard[i][j].setBackground(new Color(255, 206, 158));
                }else{
                    jboard[i][j].setBackground(new Color(209, 139, 71));
                }
            }
        }
    }
    
    void setChoixPromotion(ImageIcon icon){
        this.choix_promotion = icon;
    }
    
    /*int getChoixPromotion(){
        switch (this.choix_promotion){
            case "REINEB":
                return 15;
        if(this.choix_promotion == FOUB){
            return 13;
        }
        if(this.choix_promotion == CAVALIERB){
            return 12;
        }
        if(this.choix_promotion == TOURB){
            return 11;
        }
        if(this.choix_promotion == REINEN){
            return 25;
        }
        if(this.choix_promotion == FOUN){
            return 23;
        }
        if(this.choix_promotion == CAVALIERN){
            return 22;
        }
        if(this.choix_promotion == TOURN){
            return 21;
        }
    }*/
    
    void initBoardPanel(){
        for(int i=0;i<TAILLE;i++){
            for(int j=0;j<TAILLE;j++){
                if((j+i)%2==0){
                    jboard[i][j].setBackground(new Color(255, 206, 158));
                }else{
                    jboard[i][j].setBackground(new Color(209, 139, 71));
                }
                
                ImageIcon icone = getIconFromInt(this.modele.getCase(i, j));
                jboard[i][j].setIcon(icone);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelJeu = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jeu = new javax.swing.JMenu();
        nouvellePartie = new javax.swing.JMenuItem();
        quitter = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelJeu.setLayout(new java.awt.GridLayout(8, 8));

        jeu.setText("Jeu");

        nouvellePartie.setText("Nouvelle Partie");
        nouvellePartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nouvellePartieActionPerformed(evt);
            }
        });
        jeu.add(nouvellePartie);

        quitter.setText("Quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterActionPerformed(evt);
            }
        });
        jeu.add(quitter);

        jMenuBar1.add(jeu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelJeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nouvellePartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nouvellePartieActionPerformed
        this.modele.nouvellePartie();
        initBoardPanel();
    }//GEN-LAST:event_nouvellePartieActionPerformed

    private void quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitterActionPerformed
        System.exit(1);
    }//GEN-LAST:event_quitterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EchecFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EchecFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EchecFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EchecFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                EchecModel em = new EchecModel();
                EchecFrame eframe = new EchecFrame(em);
                EchecVueTextuelle etext = new EchecVueTextuelle(em);
                EchecVueFichier efile = new EchecVueFichier(em);
                eframe.setVisible(true);
                em.register(eframe);
                em.register(etext);
                em.register(efile);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jeu;
    private javax.swing.JMenuItem nouvellePartie;
    private javax.swing.JPanel panelJeu;
    private javax.swing.JMenuItem quitter;
    // End of variables declaration//GEN-END:variables

    @Override
    public void avertirDeplacementPossible(boolean tab[][]) {
        for(int i = 0; i < TAILLE; i++){
            for(int j = 0; j < TAILLE; j++){
                if(tab[i][j] == true){
                    if((j+i)%2==0){
                        jboard[i][j].setBackground(new Color(244, 223, 192));
                    }else{
                        jboard[i][j].setBackground(new Color(227, 185, 144));
                    }
                }
            }
        }
    }
    
    @Override
    public void avertirDeplacement(int i_select, int j_select, int i, int j, int pieceMangee) {
        jboard[i_select][j_select].setIcon(getIconFromInt(modele.getCase(i_select, j_select)));
        jboard[i][j].setIcon(getIconFromInt(modele.getCase(i, j)));
        this.resetColor();
    }

    @Override
    public void avertirNewGame() {
        initBoardPanel();
    }

    @Override
    public void avertirFinPartie(boolean avecVainqueur) {
        if (avecVainqueur) {
            JOptionPane.showMessageDialog(null, "Echec et Mat ! Le joueur "+modele.getJoueur()+" gagne la partie", 
                "Jeu d'Echec - Fin de la partie", JOptionPane.PLAIN_MESSAGE);
            initBoardPanel();
        }
        else if (modele.getNbCoup() == 100) {
            JOptionPane.showMessageDialog(null, "Il n'y a pas de vainqueurs !",
                "Jeu d'Echec - Fin de la partie", JOptionPane.PLAIN_MESSAGE);
            initBoardPanel();
        }
    }

    @Override
    public void avertirMiseEnDanger() {
        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas bouger cette pièce, elle mettrait en échec votre Roi ! ", 
                "Jeu d'Echec - Mouvement impossible", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void avertirMiseEnEchec() {
        JOptionPane.showMessageDialog(null, "Le joueur "+modele.getAutreJoueur()+" est en echec !", 
                "Jeu d'Echec - Mise en Echec", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void avertirPromotion(int i, int j, int i_select, int j_select, int joueur) {
        if(joueur == 1){
            ImageIcon[] options = {REINEB, FOUB, CAVALIERB, TOURB}; 
            int choix = JOptionPane.showOptionDialog(null, "Choississez votre promotion ", 
                "Jeu d'Echec - Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            PromotionControler reponse = new PromotionControler(i, j, modele, choix, joueur);
        }else{
            ImageIcon[] options = {REINEN, FOUN, CAVALIERN, TOURN}; 
            int choix = JOptionPane.showOptionDialog(null, "Choississez votre promotion ", 
                "Jeu d'Echec - Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            PromotionControler reponse = new PromotionControler(i, j, modele, choix, joueur);
        }
        jboard[i_select][j_select].setIcon(getIconFromInt(modele.getCase(i_select, j_select)));
        jboard[i][j].setIcon(getIconFromInt(modele.getCase(i, j)));
        this.resetColor();
        
    }

    @Override
    public void avertirRoque(int i, int j, int i_select, int j_select) {
        if(j == 0 || j_select == 0){
            jboard[i][j].setIcon(getIconFromInt(modele.getCase(i, j)));
            jboard[i_select][j_select].setIcon(getIconFromInt(modele.getCase(i_select, j_select)));
            jboard[i][2].setIcon(getIconFromInt(modele.getCase(i, 2)));
            jboard[i][3].setIcon(getIconFromInt(modele.getCase(i, 3)));
        }else{
            jboard[i][6].setIcon(getIconFromInt(modele.getCase(i, 6)));
            jboard[i][5].setIcon(getIconFromInt(modele.getCase(i, 5)));
        }
        jboard[i][j].setIcon(getIconFromInt(modele.getCase(i, j)));
        jboard[i_select][j_select].setIcon(getIconFromInt(modele.getCase(i_select, j_select)));
        this.resetColor();
    }

}
