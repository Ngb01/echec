/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec.controler;

import projetechec.model.EchecModel;

/**
 *
 * @author lebas
 */
public class PromotionControler {

    // On récupère le choix de l'utilisateur concernant la promotion dans la fenêtre de selection de la vue Frame
    // On demande au modèle de mettre à jour la nouvelle pièce
    // Le choix 0 correspond à une Reine
    // Le choix 1 correspond à un fou
    // Le choix 2 correspond à un cavalier
    // Le choix 3 correspond à une tour
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
