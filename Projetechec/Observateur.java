/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec;

/**
 *
 * @author lebas
 */
public interface Observateur {
    
    public void avertirDeplacementPossible(boolean tab[][]);
    
    public void avertirDeplacement(int i_select, int j_select, int i, int j, int pieceMangee);
    
    public void avertirPromotion(int i, int j, int i_select, int j_select, int nextPlayer);
    
    public void avertirRoque(int i, int j, int i_select, int j_select);
    
    public void avertirMiseEnDanger();
    
    public void avertirMiseEnEchec();
    
    public void avertirNewGame();
    
    public void avertirFinPartie(boolean avecVainqueur);

}
