/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetechec;

import java.util.ArrayList;

/**
 *
 * @author lebas
 */
class EchecModel {
    
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
    
    public void selection(int i, int j){
        int piece = this.getCase(i, j);
        if(piece/10 == this.nextPlayer){
            this.i_select = i;
            this.j_select = j;
            if(this.miseEnDanger(i, j, this.nextPlayer) == false){
                this.avertirDeplacementPossibleAllObservateurs(this.getMvtAutorises(i, j));
            }else{
                this.avertirMiseEnDangerAllObservateurs();
            }
        }
    }
    
    public void deselection(boolean nouvelleSelection){
        if(nouvelleSelection){
            this.avertirDeplacementAllObservateurs(i_select, j_select, 0);
        }
        this.i_select = -1;
        this.j_select = -1;
    }
    
    public boolean[][] mvtPion(int _i, int _j, int joueur){
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        if(joueur == 1){
            if(_i-1 >= 0 && this.getCase(_i-1, _j)/10 != joueur){
                mvt_autorises[_i-1][_j] = true;
                if(_j-1 >= 0 && this.getCase(_i-1, _j-1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i-1][_j-1] = true;
                }
                if(_j+1 < TAILLE && this.getCase(_i-1, _j+1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i-1][_j+1] = true;
                }
                // Pour voir si les pions sont à leurs positions d'origine
                if(_i == 1 || _i == 6){
                    if(_i-2 >= 0 && this.getCase(_i-2, _j)/10 != joueur && this.getCase(_i-1, _j)/10 == 0){
                        mvt_autorises[_i-2][_j] = true;
                    }
                }
            }
        }else{
            if(_i+1 < TAILLE && this.getCase(_i+1, _j)/10 != joueur){
                mvt_autorises[_i+1][_j] = true;
                if(_j-1 >= 0 && this.getCase(_i+1, _j-1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i+1][_j-1] = true;
                }
                if(_j+1 < TAILLE && this.getCase(_i+1, _j+1)/10 == this.getAutreJoueur()){
                    mvt_autorises[_i+1][_j+1] = true;
                }
                // Pour voir si les pions sont à leurs positions d'origine
                if(_i == 1 || _i == 6){
                    if(_i+2 < TAILLE && this.getCase(_i+2, _j)/10 != joueur && this.getCase(_i+1, _j)/10 == 0){
                        mvt_autorises[_i+2][_j] = true;
                    }
                }
            }
        }
        return mvt_autorises;
    }
    
    
    public boolean[][] mvtTour(int _i, int _j, int joueur){
        int i;
        int j;
        boolean obstacle = false;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
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
        return mvt_autorises;
    }
    
    
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
    
    
    public boolean[][] mvtReine(int _i, int _j, int joueur){
        int i;
        int j;
        boolean obstacle = false;
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
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
    
    
    public boolean[][] mvtRoi(int _i, int _j, int joueur){
        boolean mvt_autorises[][] = new boolean[TAILLE][TAILLE];
        boolean[][] mvt_tueurs;
        //On regarde les mouvements autorisés des autres pièces adverses seulement si le roi du joueur actuel est selectionné
        //Si une pièce adverse met en danger le roi lorsqu'il se déplacealors le déplacement du roi sur cette case n'est pas possible
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
            if(_j-1 >= 0){
                if(this.getCase(_i-1, _j-1)/10 != joueur && mvt_tueurs[_i-1][_j-1] == false){
                    mvt_autorises[_i-1][_j-1] = true;
                }
            }
            if(_j+1 < TAILLE){
                if(this.getCase(_i-1, _j+1)/10 != joueur && mvt_tueurs[_i-1][_j+1] == false){
                    mvt_autorises[_i-1][_j+1] = true;
                }
            }
            if(this.getCase(_i-1, _j)/10 != joueur && mvt_tueurs[_i-1][_j] == false){
                mvt_autorises[_i-1][_j] = true;
            }
        }
        if(_i+1 < TAILLE){
            if(_j-1 >= 0){
                if(this.getCase(_i+1, _j-1)/10 != joueur && mvt_tueurs[_i+1][_j-1] == false){
                    mvt_autorises[_i+1][_j-1] = true;
                }
            }
            if(_j+1 < TAILLE){
                if(this.getCase(_i+1, _j+1)/10 != joueur && mvt_tueurs[_i+1][_j+1] == false){
                    mvt_autorises[_i+1][_j+1] = true;
                }
            }
            if(this.getCase(_i+1, _j)/10 != joueur && mvt_tueurs[_i+1][_j] == false){
                mvt_autorises[_i+1][_j] = true;
            }
        }
        if(_j-1 >= 0){
            if(this.getCase(_i, _j-1)/10 != joueur && mvt_tueurs[_i][_j-1] == false){
                mvt_autorises[_i][_j-1] = true;
            }
        }
        if(_j+1 < TAILLE){
            if(this.getCase(_i, _j+1)/10 != joueur && mvt_tueurs[_i][_j+1] == false){
                mvt_autorises[_i][_j+1] = true;
            }
        }
        return mvt_autorises;
    }
    
    
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
    
    // Fonction qui retours tous les futurs déplacements possibles d'un joueur
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
    
    // Fonction qui détermine si le mouvement d'une pièce met son roi en échec
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
    
    public int getJoueur(){
        return nextPlayer;
    }
    
    private void changerJoueur(){
        if(nextPlayer == 1){
            nextPlayer = 2;
        }else{
            nextPlayer = 1;
        }
    }
    
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
    
    public void promotion(int i, int j, int piece){
        this.setCase(i_select, j_select, 0);
        this.avertirPromotionAllObservateurs(i, j, this.nextPlayer);
    }
    
    public void play(int i, int j) {
        int piece = board[i_select][j_select];
        int pieceMangee = board[i][j];
        boolean manger = false;
        if(this.getCase(i, j) != 0){
            manger = true;
        }
        if((piece == 10 || piece == 20) && (i == 0 || i == 7)){
            this.promotion(i, j, piece);
        }else{
            board[i][j] = piece;
            board[i_select][j_select] = 0;
            this.avertirDeplacementAllObservateurs(i, j, pieceMangee);
        }
        if(this.echecEtMat(i, j, nextPlayer)){
            this.avertirFinPartieAllObservateurs(true);
            this.avertirNewGameAllObservateurs();
            this.nextPlayer = 2;
        }else{
            if(this.nbCoups > 100){
                this.avertirFinPartieAllObservateurs(false);
                this.avertirNewGameAllObservateurs();
                this.nextPlayer = 2;
            }
        }
        this.changerJoueur();
        this.nbCoups ++;
    }

    public int getCase(int i, int j) {
        return board[i][j];
    }
    
    public void setCase(int i, int j, int piece){
        board[i][j] = piece;
    }
    
    public int getNbCoup(){
        return nbCoups;
    }

    public void register(Observateur o){
        this.observateur.add(o);
    }
    
    public void unregister(Observateur o){
        this.observateur.remove(o);
    }
    
    public void avertirMiseEnDangerAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirMiseEnDanger();
        }
    }
    
    public void avertirMiseEnEchecAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirMiseEnEchec();
        }
    }
    
    public void avertirDeplacementPossibleAllObservateurs(boolean tab[][]){
        for(Observateur o: this.observateur){
            o.avertirDeplacementPossible(tab);
        }
    }
    
    public void avertirDeplacementAllObservateurs(int i, int j, int pieceMangee){
        for(Observateur o: this.observateur){
            o.avertirDeplacement(i_select, j_select, i, j, pieceMangee);
        }
    }
    
    private void avertirPromotionAllObservateurs(int i, int j, int nextPlayer) {
        for(Observateur o: this.observateur){
            o.avertirPromotion(i, j, this.i_select, this.j_select, nextPlayer);
        }
    }
    
    public void avertirFinPartieAllObservateurs(boolean avecVainqueur){
        for(Observateur o: this.observateur){
            o.avertirFinPartie(avecVainqueur);
        }
    }
    
    public void avertirNewGameAllObservateurs(){
        for(Observateur o: this.observateur){
            o.avertirNewGame();
        }
    }
    
    public void avertirRoqueAllObservateurs(int i, int j, int i_select, int j_select) {
        for(Observateur o: this.observateur){
            o.avertirRoque(i, j, i_select, j_select);
        }
    }

    private void afficherTabBool(boolean[][] mvtAttaque) {
        for(int i = 0; i < TAILLE; i++){
            for(int j = 0; j < TAILLE; j++){
                System.out.print(mvtAttaque[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
