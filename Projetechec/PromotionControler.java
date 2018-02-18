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
public class PromotionControler {

    public PromotionControler(int i, int j, EchecModel modele, int choix, int joueur) {
        int piecePromu;
        switch (choix){
            case 0:
                piecePromu = 4;
                break;
            case 1:
               piecePromu = 3;
               break;
            case 2:
                piecePromu = 2;
                break;
            case 3:
                piecePromu = 1;
                break;
            default:
                piecePromu = 0;
        }
        if(joueur == 1){
            modele.setCase(i, j, 10+piecePromu);
        }else{
            modele.setCase(i, j, 20+piecePromu);
        }
    }
    
}
