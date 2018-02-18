/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec.view;

import projetechec.model.EchecModel;
import projetechec.model.Observateur;

/**
 *
 * @author lebas
 */
public class EchecVueTextuelle implements Observateur{
    
    private EchecModel modele;
    
    
    public EchecVueTextuelle(EchecModel modele){
        this.modele = modele;
        System.out.println("Une nouvelle partie débute. Les pièces blanches commencent.");
    }
    
    
    // Fonction qui permet de retourner le nom de pièce en fonction de sa valeur
    public String getNomPiece(int piece){
        switch (piece){
            case 10:
                return "pion blanc";
            case 11:
                return "tour blanche";
            case 12:
                return "cavalier blanc";
            case 13:
                return "fou blanc";
            case 14:
                return "Reine blanche";
            case 15:
                return "Roi blanc";
            case 20:
                return "pion noir";
            case 21:
                return "tour noire";
            case 22:
                return "cavalier noir";
            case 23:
                return "fou noir";
            case 24:
                return "Reine noire";
            case 25:
                return "Roi noir";
            default:
                return "aucune";
        }
    }

    @Override
    public void avertirDeplacementPossible(boolean[][] tab) {}

    
    // Fonction qui écrit dans la console pour notifier des déplacements réalisées
    @Override
    public void avertirDeplacement(int i_select, int j_select, int i, int j, int pieceMangee) {
        if(i_select != i || j_select != j){
            String resultat = "Le joueur "+modele.getJoueur()+" déplace ";
            if(modele.getCase(i, j)%10 == 1 || modele.getCase(i, j)%10 == 4){
                resultat += "sa "+this.getNomPiece(modele.getCase(i, j))+
                    " située en "+i_select+":"+j_select+" et bouge en "+i+":"+j;
            }else{
                resultat += "son "+this.getNomPiece(modele.getCase(i, j))+
                    " situé en "+i_select+":"+j_select+" et bouge en "+i+":"+j;
            }
            if(pieceMangee != 0){
                if(modele.getCase(i, j)%10 == 1 || modele.getCase(i, j)%10 == 4){
                    resultat += " en mangeant la "+this.getNomPiece(pieceMangee);
                }else{
                    resultat += " en mangeant le "+this.getNomPiece(pieceMangee);
                }
            }
            System.out.println(resultat);
        }
    }

    
    // Fonction qui écrit dans la console pour notifier de la promotion d'un pion
    @Override
    public void avertirPromotion(int i, int j, int i_select, int j_select, int joueur) {
        String couleur = "aucune";
        if(joueur == 1){
            couleur = "blanc";
        }else{
            couleur = "noir";
        }
        System.out.println("Le pion "+couleur+
                " a été promu en "+this.getNomPiece(modele.getCase(i, j)));
    }

    
    // Fonction qui écrit dans la console pour notifier de la mise en danger du Roi
    // Par le déplacement de la pièce selectionnée
    @Override
    public void avertirMiseEnDanger() {
        /*System.out.println("La pièce qui a été selectionnée ne peut pas être bougée, "
                + "puisque qu'elle mettrait en échec le Roi");*/
    }
    
    
    // Fonction qui écrit dans la console pour notifier de la réalisation d'un roque
    @Override
    public void avertirRoque(int i, int j, int i_select, int j_select) {
        System.out.println("Le joueur "+modele.getJoueur()+" a roqué");
    }
    

    // Fonction qui écrit dans la console pour notifier de la mise en Echec
    @Override
    public void avertirMiseEnEchec() {
        System.out.println("Le joueur "+modele.getAutreJoueur()+" est en échec");
    }

    
    // Fonction qui écrit dans la console pour notifier du début d'une nouvelle partie
    @Override
    public void avertirNewGame() {
        System.out.println("\nUne nouvelle partie débute. Les pièces blanches commencent. ");
    }

    
    // Fonction qui écrit dans la console pour notifier de la fin d'une partie
    @Override
    public void avertirFinPartie(boolean avecVainqueur) {
        if(avecVainqueur){
            String resultat = "Le Roi ";
            if(modele.getJoueur() == 1){
                resultat += "blanc ";
            }else{
                resultat += "noir ";
            }
            resultat += "est en Echec et Mat. Le joueur "+modele.getAutreJoueur()+" gagne la partie";
            System.out.println(resultat+"\n");
        }else{
            System.out.println("La partie est terminée : c'est un match nul\n");
        }
    }

}
