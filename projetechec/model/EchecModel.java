/*  

IMPORTANT

SUR NOTRE PLATEAU LES PIECES SONT REPRESENTEES PAR DES NUMEROS
LES PIECES COMMENCANT PAR 1 SONT BLANCHES ET APPARTIENNENT AU JOUEUR 1
LES PIECES COMMENCANT PAR 2 SONT NOIRES ET APPARTIENNENT AU JOUEUR 2

VALEUR CHIFFRES / PIECES :

    - 10 OU 20 = PIONS
    - 11 OU 21 = TOURS
    - 12 OU 22 = CAVALIERS
    - 13 OU 23 = FOUS
    - 14 OU 24 = REINE
    - 15 OU 25 = ROI


*/

package projetechec.model;

import java.util.ArrayList;


public class EchecModel {
    
    private int[][] board;
    private int i_select = -1;
    private int j_select = -1;
    private int nextPlayer;
    private int nbCoups;
    private ArrayList<Observateur> observateur;
    
    private static final int TAILLE = 8;
    
    
    public EchecModel(){
        this.board = new int[TAILLE][TAILLE];
        this.observateur = new ArrayList<>();
        this.nouvellePartie();
    }

    
    // On initialise un plateau de jeu
    // On place les pièces sur le plateau de jeu à leur poisition de départ
    // Le joueur 1 (les pièces blanches) commencent
    // On avertit toutes les vues du début de la partie
    public void nouvellePartie() {
        this.setPion();
        for(int i = 2; i < TAILLE-2; i++){
            for(int j = 0; j < TAILLE; j++){
                this.board[i][j] = 0;
            }
        }
        this.nextPlayer = 1;
        this.nbCoups = 0;
        this.avertirNewGameAllObservateurs();
    }
    
    
    // Fonction qui place les pièces sur le plateau
    public void setPion(){
        this.board[0][0] = 21;
        this.board[0][1] = 22;
        this.board[0][2] = 23;
        this.board[0][3] = 24;
        this.board[0][4] = 25;
        this.board[0][5] = 23;
        this.board[0][6] = 22;
        this.board[0][7] = 21;
        for(int i = 0; i < TAILLE; i++){
            this.board[1][i] = 20;
        }
        
        this.board[7][0] = 11;
        this.board[7][1] = 12;
        this.board[7][2] = 13;
        this.board[7][3] = 14;
        this.board[7][4] = 15;
        this.board[7][5] = 13;
        this.board[7][6] = 12;
        this.board[7][7] = 11;
        for(int i = 0; i < TAILLE; i++){
            this.board[6][i] = 10;
        }
    }
    
    
    public int get_i_select(){
        return this.i_select;
    }
    
    
    public int get_j_select(){
        return this.j_select;
    }
    
    
    // Fonction qui sélectionne la pièce qui a été cliquée
    public void selection(int i, int j){
        int piece = this.getCase(i, j);
        // On vérifie que la pièce appartient au joueur courant
        if(piece/10 == this.nextPlayer){
            // On la sauvegarde
            this.i_select = i;
            this.j_select = j;
            // On vérifie que le déplacement de la pièce ne mettra pas en danger le roi du joueur
            if(this.miseEnDanger(i, j, this.nextPlayer) == false){
                this.avertirDeplacementPossibleAllObservateurs(this.getMvtAutorises(i, j));
            }else{
                this.avertirMiseEnDangerAllObservateurs();
            }
        }
    }
    
    
    // Fonction pour désélectionner une pièce
    public void deselection(boolean nouvelleSelection){
        if(nouvelleSelection){
            this.avertirDeplacementAllObservateurs(i_select, j_select, 0);
        }
        this.i_select = -1;
        this.j_select = -1;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par un pion
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    public boolean[][] mvtPion(int _i, int _j, int joueur){
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        if(joueur == 1){
            // Pour pièces blanches
            // Si il n'y a pas de pièces alliées devant, il peut avancer d'une case
            if(_i-1 >= 0 && this.getCase(_i-1, _j)/10 != joueur){
                mvt_autorises[_i-1][_j] = true;
                // Prise en passant côté gauche
                if(_j-1 >= 0 && this.getCase(_i-1, _j-1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i-1][_j-1] = true;
                }
                // Prise en passant côté droit
                if(_j+1 < TAILLE && this.getCase(_i-1, _j+1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i-1][_j+1] = true;
                }
                // Pour voir si le pion est à sa position d'origine
                // Si oui il peut avancer de deux cases
                if(_i == 1 || _i == 6){
                    if(_i-2 >= 0 && this.getCase(_i-2, _j)/10 != joueur && this.getCase(_i-1, _j)/10 == 0){
                        mvt_autorises[_i-2][_j] = true;
                    }
                }
            }
        }else{
            // Pour les pièces noires
            // Si il n'y a pas de pièces alliées devant, il peut avancer d'une case
            if(_i+1 < TAILLE && this.getCase(_i+1, _j)/10 != joueur){
                mvt_autorises[_i+1][_j] = true;
                // Prise en passant côté gauche
                if(_j-1 >= 0 && this.getCase(_i+1, _j-1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i+1][_j-1] = true;
                }
                // Prise en passant côté droit
                if(_j+1 < TAILLE && this.getCase(_i+1, _j+1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i+1][_j+1] = true;
                }
                // Pour voir si le pion est à sa position d'origine
                // Si oui il peut avancer de deux cases
                if(_i == 1 || _i == 6){
                    if(_i+2 < TAILLE && this.getCase(_i+2, _j)/10 != joueur && this.getCase(_i+1, _j)/10 == 0){
                        mvt_autorises[_i+2][_j] = true;
                    }
                }
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par une tour
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    // La variable obstacle détecte les pièces qui empêchent l'avancer de la tour
    public boolean[][] mvtTour(int _i, int _j, int joueur){
        int i;
        int j;
        boolean obstacle = false;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        // Détecte les mouvements possibles sur la ligne à droite de la tour
        i = _i;
        for(j = _j; j < TAILLE; j++){
            if(obstacle == false){
                if(j != _j){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la ligne à gauche de la tour
        i = _i;
        obstacle = false;
        for(j = _j; j >= 0; j--){
            if(obstacle == false){
                if(j != _j){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la colonne en bas de la tour
        j = _j;
        obstacle = false;
        for(i = _i; i < TAILLE; i++){
            if(obstacle == false){
                if(i != _i){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la colonne en haut de la tour
        j = _j;
        obstacle = false;
        for(i = _i; i >= 0; i--){
            if(obstacle == false){
                if(i != _i){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Si le roque est possible
        if(joueur == 1){
            // Si le roi est toujours à sa position initiale
            if(this.getCase(7, 4) == 15){
                // Si la tour blanche gauche est toujours à sa position initiale
                if(_i == 7 && _j == 0){
                    // Si l'espace est libre entre la tour gauche et le roi
                    if(this.getCase(7, 1) == 0 && this.getCase(7, 2) == 0 && this.getCase(7, 3) == 0){
                        mvt_autorises[7][1] = true;
                        mvt_autorises[7][2] = true;
                        mvt_autorises[7][3] = true;
                        mvt_autorises[7][4] = true;
                    }
                }
                // Si la tour blanche droite est toujours à sa position initiale
                if(_i == 7 && _j == 7){
                    // Si l'espace est libre entre la tour droite et le roi
                    if(this.getCase(7, 5) == 0 && this.getCase(7, 6) == 0){
                        mvt_autorises[7][4] = true;
                        mvt_autorises[7][5] = true;
                        mvt_autorises[7][6] = true;
                    }
                } 
            }
        }else{
            // Si le roi est toujours à sa position initiale
            if(this.getCase(0, 4) == 25){
                // Si la tour noire gauche est toujours à sa position initiale
                if(_i == 0 && _j == 0){
                    // Si l'espace est libre entre la tour gauche et le roi
                    if(this.getCase(0, 1) == 0 && this.getCase(0, 2) == 0 && this.getCase(0, 3) == 0){
                        mvt_autorises[0][1] = true;
                        mvt_autorises[0][2] = true;
                        mvt_autorises[0][3] = true;
                        mvt_autorises[0][4] = true;
                    }
                }
                // Si la tour noire droite est toujours à sa position initiale
                if(_i == 0 && _j == 7){
                    // Si l'espace est libre entre la tour droite et le roi
                    if(this.getCase(0, 5) == 0 && this.getCase(0, 6) == 0){
                        mvt_autorises[0][4] = true;
                        mvt_autorises[0][5] = true;
                        mvt_autorises[0][6] = true;
                    }
                } 
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par un cavalier
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    // Pour le cavalier, on doit regarder les déplacements possibles un par un par
    public boolean[][] mvtCavalier(int _i, int _j, int joueur){
        int i;
        int j;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        if(_i-2 >= 0){
            if(_j-1 >= 0){
                if(this.getCase(_i-2, _j-1)/10 != joueur){
                    mvt_autorises[_i-2][_j-1] = true;
                }
            }
            if(_j+1 < TAILLE){
                if(this.getCase(_i-2, _j+1)/10 != joueur){
                    mvt_autorises[_i-2][_j+1] = true;
                }
            }
        }
        if(_i+2 < TAILLE){
            if(_j-1 >= 0){
                if(this.getCase(_i+2, _j-1)/10 != joueur){
                    mvt_autorises[_i+2][_j-1] = true;
                }
            }
            if(_j+1 < TAILLE){
                if(this.getCase(_i+2, _j+1)/10 != joueur){
                    mvt_autorises[_i+2][_j+1] = true;
                }
            }
        }
        if(_j-2 >= 0){
            if(_i-1 >= 0){
                if(this.getCase(_i-1, _j-2)/10 != joueur){
                    mvt_autorises[_i-1][_j-2] = true;
                }
            }
            if(_i+1 < TAILLE){
                if(this.getCase(_i+1, _j-2)/10 != joueur){
                    mvt_autorises[_i+1][_j-2] = true;
                }
            }
        }
        if(_j+2 < TAILLE){
            if(_i-1 >= 0){
                if(this.getCase(_i-1, _j+2)/10 != joueur){
                    mvt_autorises[_i-1][_j+2] = true;
                }
            }
            if(_i+1 < TAILLE){
                if(this.getCase(_i+1, _j+2)/10 != joueur){
                    mvt_autorises[_i+1][_j+2] = true;
                }
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par un cavalier
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    // La variable obstacle détecte les pièces qui empêchent l'avancer de la tour
    public boolean[][] mvtFou(int _i, int _j, int joueur){
        int i;
        int j;
        boolean obstacle = false;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        // Diagonale haut gauche
        j = _j-1;
        i = _i-1;
        while(j >= 0 && i >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i--;
                j--;
            }else{
                obstacle = true;
            }
        }
        // Diagonale haut droite
        i = _i-1;
        j = _j+1;
        obstacle = false;
        while(j < TAILLE && i >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                j++;
                i--;
            }else{
                obstacle = true;
            }
        }
        // Diagonale bas droite
        j = _j+1;
        i = _i+1;
        obstacle = false;
        while(i < TAILLE && j < TAILLE && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i++;
                j++;
            }else{
                obstacle = true;
            }
        }
        // Diagonale bas gauche
        j = _j-1;
        i = _i+1;
        obstacle = false;
        while(i < TAILLE && j >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i++;
                j--;
            }else{
                obstacle = true;
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par la Reine
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    // La variable obstacle détecte les pièces qui empêchent l'avancer de la tour
    // Elle reprend les mêmes déplacements de tour + les déplacements du fou
    public boolean[][] mvtReine(int _i, int _j, int joueur){
        int i;
        int j;
        boolean obstacle = false;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        // Détecte les mouvements possibles sur la ligne à droite de la reine
        i = _i;
        for(j = _j; j < TAILLE; j++){
            if(obstacle == false){
                if(j != _j){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la ligne à gauche de la reine
        i = _i;
        obstacle = false;
        for(j = _j; j >= 0; j--){
            if(obstacle == false){
                if(j != _j){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la colonne en bas de la reine
        j = _j;
        obstacle = false;
        for(i = _i; i < TAILLE; i++){
            if(obstacle == false){
                if(i != _i){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Détecte les mouvements possibles sur la colonne en haut de la reine
        j = _j;
        obstacle = false;
        for(i = _i; i >= 0; i--){
            if(obstacle == false){
                if(i != _i){
                    if(this.getCase(i, j)/10 != joueur){
                        mvt_autorises[i][j] = true;
                        if(this.getCase(i, j) != 0){
                            obstacle = true;
                        }
                    }else{
                        obstacle = true;
                    }
                }
            }
        }
        // Diagonale haut gauche
        j = _j-1;
        i = _i-1;
        obstacle = false;
        while(j >= 0 && i >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i--;
                j--;
            }else{
                obstacle = true;
            }
        }
        // Diagonale haut droite
        i = _i-1;
        j = _j+1;
        obstacle = false;
        while(j < TAILLE && i >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                j++;
                i--;
            }else{
                obstacle = true;
            }
        }
        // Diagonale bas droite
        j = _j+1;
        i = _i+1;
        obstacle = false;
        while(i < TAILLE && j < TAILLE && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i++;
                j++;
            }else{
                obstacle = true;
            }
        }
        // Diagonale bas gauche
        j = _j-1;
        i = _i+1;
        obstacle = false;
        while(i < TAILLE && j >= 0 && obstacle == false){
            if(this.getCase(i, j)/10 != joueur){
                mvt_autorises[i][j] = true;
                if(this.getCase(i, j) != 0){
                    obstacle = true;
                }
                i++;
                j--;
            }else{
                obstacle = true;
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui détermine les déplacements autorisés par la Roi
    // Les déplacements autorisés sont représentés par un tableau de booleens de la taille de l'échiquier
    public boolean[][] mvtRoi(int _i, int _j, int joueur){
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        boolean[][] mvt_tueurs;
        //On regarde les mouvements autorisés des autres pièces adverses seulement si le roi du joueur actuel est selectionné
        //Si une pièce adverse met en danger le roi lorsqu'il se déplace alors le déplacement du roi sur cette case n'est pas possible
        //Sinon les mouvements des pièces adverses n'importe pas et donc le tableau des mouvements autorisés est initialisé à FALSE
        if(this.getCase(i_select, j_select) == (joueur*10)+5){
            mvt_tueurs = this.getAllMvtAutorises(this.getAutreJoueur());
        }else{
            mvt_tueurs = new boolean[TAILLE][TAILLE];
            for(int i = 0; i < TAILLE; i++){
                for(int j = 0; j > TAILLE; j++){
                    mvt_tueurs[i][j] = false;
                }
            }
        }
        if(_i-1 >= 0){
            // Case en haut à gauche du Roi
            if(_j-1 >= 0){
                if(this.getCase(_i-1, _j-1)/10 != joueur && mvt_tueurs[_i-1][_j-1] == false){
                    mvt_autorises[_i-1][_j-1] = true;
                }
            }
            // Case en haut à droite du Roi
            if(_j+1 < TAILLE){
                if(this.getCase(_i-1, _j+1)/10 != joueur && mvt_tueurs[_i-1][_j+1] == false){
                    mvt_autorises[_i-1][_j+1] = true;
                }
            }
            // Case en haut du Roi
            if(this.getCase(_i-1, _j)/10 != joueur && mvt_tueurs[_i-1][_j] == false){
                mvt_autorises[_i-1][_j] = true;
            }
        }
        if(_i+1 < TAILLE){
            // Case en bas à gauche du Roi
            if(_j-1 >= 0){
                if(this.getCase(_i+1, _j-1)/10 != joueur && mvt_tueurs[_i+1][_j-1] == false){
                    mvt_autorises[_i+1][_j-1] = true;
                }
            }
            // Case en bas à droite du Roi
            if(_j+1 < TAILLE){
                if(this.getCase(_i+1, _j+1)/10 != joueur && mvt_tueurs[_i+1][_j+1] == false){
                    mvt_autorises[_i+1][_j+1] = true;
                }
            }
            // Case en bas du Roi
            if(this.getCase(_i+1, _j)/10 != joueur && mvt_tueurs[_i+1][_j] == false){
                mvt_autorises[_i+1][_j] = true;
            }
        }
        // Case à gauche du Roi
        if(_j-1 >= 0){
            if(this.getCase(_i, _j-1)/10 != joueur && mvt_tueurs[_i][_j-1] == false){
                mvt_autorises[_i][_j-1] = true;
            }
        }
        // Case à droite du Roi
        if(_j+1 < TAILLE){
            if(this.getCase(_i, _j+1)/10 != joueur && mvt_tueurs[_i][_j+1] == false){
                mvt_autorises[_i][_j+1] = true;
            }
        }
        // Si le roque est possible
        if(joueur == 1){
            // Si le roi blanc est toujours à sa position initiale
            if(_i == 7 && _j == 4){
                // Si la tour gauche blanche est toujours à sa position initiale
                if(this.getCase(7, 0) == 11){
                    // Si l'espace est libre entre la tour gauche et le roi
                    if(this.getCase(7, 1) == 0 && this.getCase(7, 2) == 0 && this.getCase(7, 3) == 0){
                        mvt_autorises[7][0] = true;
                        mvt_autorises[7][1] = true;
                        mvt_autorises[7][2] = true;
                        mvt_autorises[7][3] = true;
                    }
                }
                // Si la droite droite blanche est toujours à sa position initiale
                if(this.getCase(7, 7) == 11){
                    // Si l'espace est libre entre la tour droite et le roi
                    if(this.getCase(7, 5) == 0 && this.getCase(7, 6) == 0){
                        mvt_autorises[7][5] = true;
                        mvt_autorises[7][6] = true;
                        mvt_autorises[7][7] = true;
                    }
                }
            }
        }else{
            // Si le roi noir est toujours à sa position initiale
            if(_i == 0 && _j == 4){
                // Si la tour gauche noire est toujours à sa position initiale
                if(this.getCase(0, 0) == 21){
                    // Si l'espace est libre entre la tour gauche et le roi
                    if(this.getCase(0, 1) == 0 && this.getCase(0, 2) == 0 && this.getCase(0, 3) == 0){
                        mvt_autorises[0][0] = true;
                        mvt_autorises[0][1] = true;
                        mvt_autorises[0][2] = true;
                        mvt_autorises[0][3] = true;
                    }
                }
                // Si la droite droite noire est toujours à sa position initiale
                if(this.getCase(0, 7) == 21){
                    // Si l'espace est libre entre la tour droite et le roi
                    if(this.getCase(0, 5) == 0 && this.getCase(0, 6) == 0){
                        mvt_autorises[0][5] = true;
                        mvt_autorises[0][6] = true;
                        mvt_autorises[0][7] = true;
                    }
                }
            }
        }
        return mvt_autorises;
    }
    
    
    // Fonction qui donne les mouvements autorisés pour une pièce sur une case donnée
    public boolean[][] getMvtAutorises(int _i, int _j){
        int piece = this.board[_i][_j];
        switch (piece){
            case 10:
                return this.mvtPion(_i, _j, 1);
            case 11:
                return this.mvtTour(_i, _j, 1);
            case 12:
                return this.mvtCavalier(_i, _j, 1);
            case 13:
                return this.mvtFou(_i, _j, 1);
            case 14:
                return this.mvtReine(_i, _j, 1);
            case 15:
                return this.mvtRoi(_i, _j, 1);
            case 20:
                return this.mvtPion(_i, _j, 2);
            case 21:
                return this.mvtTour(_i, _j, 2);
            case 22:
                return this.mvtCavalier(_i, _j, 2);
            case 23:
                return this.mvtFou(_i, _j, 2);
            case 24:
                return this.mvtReine(_i, _j, 2);
            case 25:
                return this.mvtRoi(_i, _j, 2);
            default:
                return null;
        }
    }
    
    
    // Fonction qui retourne tous les futurs déplacements possibles d'un joueur
    // On récupère les mouvements possibles pièce par pièce et on les concatène
    public boolean[][] getAllMvtAutorises(int joueur){
        boolean[][] allMvt = new boolean[TAILLE][TAILLE];
        boolean[][] pieceMvt = new boolean[TAILLE][TAILLE];
        for(int i = 0; i < TAILLE; i++){
            for(int j = 0; j < TAILLE; j++){
                if(this.getCase(i, j)/10 == joueur){
                    pieceMvt = this.getMvtAutorises(i, j);
                    allMvt = this.concatenerTabBool(allMvt, pieceMvt);
                }
            }
        }
        return allMvt;
    }
    
    
    // Fonction qui permet d'assembler deux tableaux de booléens
    public boolean[][] concatenerTabBool(boolean[][] tab1, boolean[][] tab2){
        if(tab1.length == tab2.length){
            boolean[][] resultat = new boolean[tab1.length][tab1.length];
            for(int i = 0; i < TAILLE; i++){
                for(int j = 0; j < TAILLE; j++){
                    if(tab1[i][j] == true || tab2[i][j] == true){
                        resultat[i][j] = true;
                    }
                }
            }
            return resultat;
        }
        return null;
    }
    
    
    // Fonction qui détermine si le mouvement d'une pièce met son Roi en échec
    // On détermine les attaques possibles de l'adversaire avec la pièce et sans la pièce
    // Si sans la pièce le Roi du joueur peut être mis en danger par l'adversaire
    // Alors on envoie true pour notifier de la mise en danger du Roi
    public boolean miseEnDanger(int i, int j, int joueur){
        int piece = this.getCase(i, j);
        boolean[][] mvtAttaquePossibleAvec = this.getAllMvtAutorises(this.getAutreJoueur());
        this.setCase(i, j, 0);
        boolean[][] mvtAttaquePossibleSans = this.getAllMvtAutorises(this.getAutreJoueur());
        this.setCase(i, j, piece);
        int[] positionRoi = this.getPositionRoi(joueur);
        if(mvtAttaquePossibleAvec[positionRoi[0]][positionRoi[1]] == false && mvtAttaquePossibleSans[positionRoi[0]][positionRoi[1]] == true){
            return true;
        }else{
            return false;
        }
        
    }
    
    
    // Fonction qui renvoie le joueur actuel
    public int getJoueur(){
        return nextPlayer;
    }
    
    
    // Fonction qui change de joueur
    private void changerJoueur(){
        if(nextPlayer == 1){
            nextPlayer = 2;
        }else{
            nextPlayer = 1;
        }
    }
    
    
    // Fonction qui retourne le joueur qui ne joue pas
    public int getAutreJoueur(){
        if(this.nextPlayer == 1){
            return 2;
        }else{
            return 1;
        }
    }
    
    
    // Fonction qui retourne la position du roi d'un joueur donné
    public int[] getPositionRoi(int joueur){
        int[] positionRoi = new int[2];
        for(int i = 0; i < TAILLE; i++){
            for(int j = 0; j < TAILLE; j++){
                if(this.getCase(i, j) == (joueur*10)+5){
                    positionRoi[0] = i;
                    positionRoi[1] = j;
                }
            }
            
        }
        return positionRoi;
    }
    
    
    // Fonction qui détermine si le roi d'un joueur est en Echec et Mat
    public boolean echecEtMat(int _i, int _j, int joueur){
        boolean echappatoire = false;
        boolean[][] mvtAttaque = this.getMvtAutorises(_i, _j);
        boolean[][] mvtDefense;
        int[] positionRoi = this.getPositionRoi(this.getAutreJoueur());
        mvtDefense = this.mvtRoi(positionRoi[0], positionRoi[1], this.getAutreJoueur());
        // On vérifie si le roi adverse est attaquable par la pièce qui vient de bouger
        // Si il y en a un échappatoire, on notifie quand même que le roi est en Echec
        if(mvtAttaque[positionRoi[0]][positionRoi[1]] == true){
            for(int i = 0; i < TAILLE; i++){
                for(int j = 0; j < TAILLE; j++){
                    if(mvtDefense[i][j] == true){
                        if(mvtAttaque[i][j] == false){
                            echappatoire = true;
                        }
                    }
                }
            }
            if(echappatoire == true){
                this.avertirMiseEnEchecAllObservateurs();
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
    
    
    // Fonction qui détermine si un roque peut être jouer
    public boolean roquable(int i, int j){
        boolean[][] mvtAttaqueAdverse = this.getAllMvtAutorises(this.getAutreJoueur());
        if(this.getCase(i, j) == 11 && this.getCase(i_select, j_select) == 15
                || this.getCase(i, j) == 15 && this.getCase(i_select, j_select) == 11
                    || this.getCase(i, j) == 21 && this.getCase(i_select, j_select) == 25
                        || this.getCase(i, j) == 25 && this.getCase(i_select, j_select) == 21){
            if(j == 0 || j_select == 0){
                for(int k = 1; k < 4; k++){
                    if(this.getCase(i, k) != 0){
                        return false;
                    }
                    if(mvtAttaqueAdverse[i][2] == true){
                        return false;
                    }
                }
                return true;
            }else if(j == 7 || j_select == 7){
                if(this.getCase(i, 5) != 0 || this.getCase(i, 6) != 0){
                    return false;
                }
                if(mvtAttaqueAdverse[i][6] == true){
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    
    
    // Fonction qui réalise le roque
    public void roquer(int i, int j){
        int roi = (this.nextPlayer*10)+5;
        int tour = (this.nextPlayer*10)+1;
        // On vérifie si c'est un grand roque
        if(j == 0 || j_select == 0){
            this.board[i][j] = 0;
            this.board[i_select][j_select] = 0;
            this.board[i][2] = roi;
            this.board[i][3] = tour;
            this.avertirRoqueAllObservateurs(i, j, i_select, j_select);
        }else{
            this.board[i][j] = 0;
            this.board[i_select][j_select] = 0;
            this.board[i][6] = roi;
            this.board[i][5] = tour;
            this.avertirRoqueAllObservateurs(i, j, i_select, j_select);
        }
        this.changerJoueur();
    }
    
    
    // Fonction qui réalise la promotion d'un pion
    public void promotion(int i, int j, int piece){
        this.setCase(i_select, j_select, 0);
        this.avertirPromotionAllObservateurs(i, j, this.nextPlayer);
    }
    
    
    // Fonction qui permet le mouvement de pièce sur le plateau
    // Elle détermine si la partie est terminée
    // Elle peut notifier indirectement les observateurs sur l'avancée du jeu
    public void play(int i, int j) {
        int piece = board[i_select][j_select];
        int pieceMangee = board[i][j];
        // On détermine si un pion est prêt à être promu
        // Sinon il s'agit d'un déplacement classique
        if((piece == 10 || piece == 20) && (i == 0 || i == 7)){
            this.promotion(i, j, piece);
        }else{
            board[i][j] = piece;
            board[i_select][j_select] = 0;
            this.avertirDeplacementAllObservateurs(i, j, pieceMangee);
        }
        // On termine la partie si le roi adverse est en Echec et Mat
        if(this.echecEtMat(i, j, nextPlayer)){
            this.avertirFinPartieAllObservateurs(true);
            this.nouvellePartie();
            this.nextPlayer = 2;
        }
        // Règle des 100 coups : on termine la partie sur un match nul
        if(this.nbCoups > 100){
            this.avertirFinPartieAllObservateurs(false);
            this.nouvellePartie();
            this.nextPlayer = 2;
        }
        this.changerJoueur();
        this.nbCoups ++;
    }

    
    // Fonction qui retourne la pièce dans la case si il y en a une
    public int getCase(int i, int j) {
        return board[i][j];
    }
    
    
    // Fonction qui modifie la pièce de la case
    public void setCase(int i, int j, int piece){
        board[i][j] = piece;
    }
    
    
    // Fonction qui retourne le nombre de coups
    public int getNbCoup(){
        return nbCoups;
    }

    
    // Fonction qui ajoute des observateurs du modèle
    public void register(Observateur o){
        this.observateur.add(o);
    }
    
    
    // Fonction qui retire des observateurs du modèle
    public void unregister(Observateur o){
        this.observateur.remove(o);
    }
    
    
    // Fonction qui notifie les observateurs de la mise en danger
    public void avertirMiseEnDangerAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirMiseEnDanger();
        }
    }
    
    
    // Fonction qui notifie les observateurs de la mise en Echec
    public void avertirMiseEnEchecAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirMiseEnEchec();
        }
    }
    
    
    // Fonction qui notifie les observateurs des déplacements qui sont possibles
    public void avertirDeplacementPossibleAllObservateurs(boolean tab[][]){
        for(Observateur o: this.observateur){
            o.avertirDeplacementPossible(tab);
        }
    }
    
    
    // Fonction qui notifie les observateurs des déplacements réalisés
    public void avertirDeplacementAllObservateurs(int i, int j, int pieceMangee){
        for(Observateur o: this.observateur){
            o.avertirDeplacement(i_select, j_select, i, j, pieceMangee);
        }
    }
    
    
    // Fonction qui notifie les observateurs d'un cas de promotion
    private void avertirPromotionAllObservateurs(int i, int j, int nextPlayer) {
        for(Observateur o: this.observateur){
            o.avertirPromotion(i, j, this.i_select, this.j_select, nextPlayer);
        }
    }
    
    
    // Fonction qui notifie les observateurs d'un roque
    public void avertirRoqueAllObservateurs(int i, int j, int i_select, int j_select) {
        for(Observateur o: this.observateur){
            o.avertirRoque(i, j, i_select, j_select);
        }
    }
    
    
    // Fonction qui notifie les observateurs de la fin d'une partie
    public void avertirFinPartieAllObservateurs(boolean avecVainqueur){
        for(Observateur o: this.observateur){
            o.avertirFinPartie(avecVainqueur);
        }
    }
    
    
    // Fonction qui notifie les observateurs du début d'une partie
    public void avertirNewGameAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirNewGame();
        }
    }
    
    
    // Fonction qui permet d'afficher un tableau de booléens
    private void afficherTabBool(boolean[][] mvtAttaque) {
        for(int i = 0; i < TAILLE; i++){
            for(int j = 0; j < TAILLE; j++){
                System.out.print(mvtAttaque[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
