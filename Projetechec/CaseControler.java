/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

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
        int i_select = this.modele.get_i_select();
        int j_select = this.modele.get_j_select();
        if(this.modele.get_i_select() == -1 && this.modele.get_j_select() == -1){
            if(this.modele.getCase(i, j) != 0){
                this.modele.selection(i, j);
            }
        }else{
            //Si on a séléctionné une première case mais finalement on change de case
            if(this.modele.getCase(i, j)/10 == this.modele.getJoueur()){
                if(this.modele.roquable(i, j) == true){
                    this.modele.roquer(i, j);
                    this.modele.deselection(true);
                }else{
                    this.modele.deselection(true);
                    this.modele.selection(i, j);
                }
            }else{
                this.mvt_autorises = this.modele.getMvtAutorises(i_select, j_select);
                if(this.mvt_autorises[i][j] == true){
                    this.modele.play(i, j);
                    this.modele.deselection(false);
                }
            }
        }
    }
    
    
    
}
