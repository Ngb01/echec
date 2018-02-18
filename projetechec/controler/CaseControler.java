/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec.controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import projetechec.model.EchecModel;

/**
 *
 * @author lebas
 */
public class CaseControler extends MouseAdapter {
    
    int i;
    int j;
    boolean mvt_autorises[][];
    private EchecModel modele;
    
    private static final int TAILLE = 8;

    public CaseControler(int i, int j, EchecModel modele) {
        this.i = i;
        this.j = j;
        this.modele = modele;
        this.mvt_autorises = new boolean[TAILLE][TAILLE];
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        // On récupère dans le modèle la pièce précedemment cliqué
        int i_select = this.modele.get_i_select();
        int j_select = this.modele.get_j_select();
        // Si aucune pièce n'a été cliquée auparavant
        // Alors on sauvegarde la pièce qui vient d'être cliquée si la case n'est pas vide
        if(this.modele.get_i_select() == -1 && this.modele.get_j_select() == -1){
            if(this.modele.getCase(i, j) != 0){
                this.modele.selection(i, j);
            }
        }else{
            // Si une pièce a déjà été cliquée auparavant et est selectionnée
            // Si on clique sur une autre pièce de la même couleur
            if(this.modele.getCase(i, j)/10 == this.modele.getJoueur()){
                // On vérifie si on est dans une position de roquage avant de déselectionner
                // Sinon on déselectionne la case précedemment cliquée et on sélectionne la nouvelle
                if(this.modele.roquable(i, j) == true){
                    this.modele.roquer(i, j);
                    this.modele.deselection(true);
                }else{
                    this.modele.deselection(true);
                    this.modele.selection(i, j);
                }
            }else{
                // Si on clique sur une case vide ou une pièce adverse
                // On vérifie si le mouvement est autorisé
                this.mvt_autorises = this.modele.getMvtAutorises(i_select, j_select);
                if(this.mvt_autorises[i][j] == true){
                    this.modele.play(i, j);
                    this.modele.deselection(false);
                }
            }
        }
    }
    
    
    
}
