package Morpion_Main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Morpion_amelioration {
	// Déclaration du tableau (grille)
    static char[][] schema = new char[3][3];
    // Variable pour compter le nombre de mouvements
    static int mouvements = 0;
    // Variables pour suivre le gagnant
    static boolean victoireX = false, victoireO = false;
    // Variable pour savoir quel joueur joue (X ou O)
    static char tour = 'X';
    

    public static void main(String[] args) {
        int ligne,colonne,nb_jeu;
        Scanner scanner = new Scanner(System.in);
        
        
        // Amelioration
        while(true) {
            // Demande à l'utilisateur de saisir le nombre de jeux, qui doit être 1, 3 ou 5
            System.out.print("Nombre de jeux (1, 3 ou 5) : "); 
            try {
                nb_jeu = scanner.nextInt(); // Lit l'entrée de l'utilisateur pour le nombre de jeux
                // Vérifie si le nombre de jeux est valide
                if (nb_jeu == 1 || nb_jeu == 3 || nb_jeu == 5) {
                    break; // Sort de la boucle si l'entrée est valide
                } else {
                    // Informe l'utilisateur de l'entrée invalide
                    System.out.println("Veuillez entrer 1, 3 ou 5.\n"); 
                    continue; // Continue à demander une entrée valide
                }
                
            } catch(InputMismatchException e) {
                // Gère les entrées invalides
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.\n"); 
                scanner.nextLine(); // Efface la ligne d'entrée invalide pour éviter une boucle infinie
            }
        }
        
     // Stocke les résultats des jeux (X, O ou Égalité)
        char[] histoire = new char[nb_jeu]; // Utilisation de nb_jeu pour rendre le code plus flexible

        // Variables pour suivre les victoires consécutives
        int consecutiveWinsX = 0; // Nombre de victoires consécutives de X
        int consecutiveWinsO = 0; // Nombre de victoires consécutives de O
        int consecutiveEgalite = 0; // Nombre d'égalités consécutives
        boolean finJeu = false; // Indicateur pour arrêter le jeu en cas de victoire consécutive

        // Boucle pour jouer plusieurs jeux (nb_jeu fois)
        for (int i = 0; i < nb_jeu; i++) {
            // Initialisation de la grille pour chaque jeu
            initialiser(schema);

            // Remise à zéro du compteur de mouvements pour le nouveau jeu
            mouvements = 0;

            // Boucle principale pour un jeu
            while (true) {
                // Affiche la grille actuelle
                voir(schema);

                // Boucle pour demander des coordonnées valides au joueur
                while (true) {
                    System.out.println("[Tour " + tour + "] Entrez la ligne (0, 1 ou 2) et la colonne (0, 1 ou 2) : ");
                    try {
                        ligne = scanner.nextInt(); // Lit l'entrée pour la ligne
                        colonne = scanner.nextInt(); // Lit l'entrée pour la colonne

                        System.out.println("Ligne: " + ligne + ", Colonne: " + colonne);
                        break; // Sort de la boucle si les entrées sont valides

                    } catch (InputMismatchException e) {
                        System.out.println("Entrée invalide. Veuillez entrer un nombre entier.\n");
                        scanner.nextLine(); // Nettoie l'entrée invalide pour éviter une boucle infinie
                    }
                }
                

                // Effectue le déplacement avec les coordonnées fournies
                nouveau_deplacement(schema, tour, ligne, colonne);

                // Incrémente le compteur de mouvements
                mouvements++;

                // Vérifie s'il y a un gagnant après ce mouvement
                if (aGagne(schema, tour)) {
                    voir(schema);
                    System.out.println("FÉLICITATIONS " + tour + ", TU AS GAGNÉ !!!");
                    System.out.println("Jeu: " + (i + 1) + " sur " + nb_jeu + "\n");
                    histoire[i] = tour; // Stocke le gagnant du jeu actuel

                    // Met à jour le compteur de victoires consécutives
                    if (tour == 'X') {
                        consecutiveWinsX++;
                        consecutiveWinsO = 0; // Réinitialise le compteur de victoires de O
                    } else {
                        consecutiveWinsO++;
                        consecutiveWinsX = 0; // Réinitialise le compteur de victoires de X
                    }

                    // Vérifie si un joueur a gagné plusieurs fois consécutivement
                    if (nb_jeu > 1) { // Vérifie seulement si nb_jeu > 1
                        if ((nb_jeu == 3 && consecutiveWinsX == 2) || (nb_jeu == 5 && consecutiveWinsX == 3)) {
                            System.out.println("X a gagné " + (nb_jeu == 3 ? "2" : "3") + " fois de suite ! Fin du jeu.");
                            finJeu = true; // Active l'indicateur pour terminer le jeu
                            return; // Quitte la méthode main (et le jeu)
                        }
                        if ((nb_jeu == 3 && consecutiveWinsO == 2) || (nb_jeu == 5 && consecutiveWinsO == 3)) {
                            System.out.println("O a gagné " + (nb_jeu == 3 ? "2" : "3") + " fois de suite ! Fin du jeu.");
                            finJeu = true; // Active l'indicateur pour terminer le jeu
                            return; // Quitte la méthode main (et le jeu)
                        }
                    }
                    break; // Quitte la boucle de jeu courant car il y a un gagnant
                }

                // Si tous les mouvements sont faits sans gagnant, c'est un match nul
                if (mouvements == 9) {
                    voir(schema);
                    System.out.println("Rien à faire... ÉGALITÉ !");
                    System.out.println("Jeu: " + (i + 1) + " sur " + nb_jeu + "\n");
                    histoire[i] = 'E'; // Stocke l'égalité pour ce jeu
                    consecutiveEgalite++; // Incrémente le compteur d'égalités consécutives

                    // Vérifie si le jeu doit se terminer après plusieurs égalités consécutives
                    if (nb_jeu > 1) {
                        if ((nb_jeu == 3 && consecutiveEgalite == 2) || (nb_jeu == 5 && consecutiveEgalite == 3)) {
                            System.out.println("Égalité ! Fin du jeu.");
                            finJeu = true; // Active l'indicateur pour terminer le jeu
                            return; // Quitte la méthode (et le jeu)
                        }
                    }
                    break; // Quitte la boucle de jeu courant car c'est une égalité
                }

                // Change le tour de jeu entre X et O
                tour = (tour == 'X') ? 'O' : 'X';
            }

            // Si le jeu s'est terminé à cause d'une victoire consécutive, sortir de la boucle principale
            if (finJeu) {
                break; // Quitte la boucle for principale si une condition de fin est remplie
            }

            // Si X gagne le premier jeu, O commence le jeu suivant
            tour = (tour == 'X') ? 'O' : 'X';
        }

        // Si on atteint ici, cela signifie qu'il n'y a pas eu de gagnant consécutif
        // Vérifie qui est le gagnant final après tous les jeux
        double X = 0, O = 0, E = 0;
        for (int y = 0; y < histoire.length; y++) {
            if (histoire[y] == 'X') {
                X++; // Compte les victoires de X
                if (X / histoire.length >= 0.6) { // Si X a gagné plus de 60% des jeux
                    System.out.println("Gagnant est X avec " + X + " gagnés sur " + histoire.length + " jeu(x)");
                    break;
                }
            }
            if (histoire[y] == 'O') {
                O++; // Compte les victoires de O
                if (O / histoire.length >= 0.6) { // Si O a gagné plus de 60% des jeux
                    System.out.println("Gagnant est O avec " + O + " gagnés sur " + histoire.length + " jeu(x).");
                    break;
                }
            }
            if (histoire[y] == 'E') {
                E++; // Compte les égalités
                if (E / histoire.length >= 0.6) { // Si plus de 60% des jeux sont des égalités
                    System.out.println("Égalité entre X et O");
                    break;
                }
            }
        }
        scanner.close();
        System.out.println("FIN DU JEU DE MORPION"); // Affiche la fin du jeu de Morpion 
    }


    private static void initialiser(char[][] schema) {
    	/**
    	 * Méthode pour initialiser la grille de jeu
    	 * Cette méthode initialise chaque case de la grille 3x3 avec un espace vide (' ').
    	 * @param schema : Le tableau 2D représentant la grille de jeu.
    	 */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                schema[i][j] = ' ';// Chaque case est initialisée à vide (espace)
            }
        }
    }

    private static void voir(char[][] schema) {
    	/**
    	 * Cette méthode permet d'afficher une grille de jeu de type 3x3.
    	 * Elle affiche chaque élément du tableau avec un format de grille
    	 * en séparant les lignes et colonnes par des traits.
    	 *
    	 * @param schema : Un tableau 2D de type `char[][]` représentant la grille de jeu.
    	 *                 La grille est supposée être de dimensions 3x3.
    	 */
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + schema[i][j] + " |");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }


    private static void nouveau_deplacement(char[][] schema, char tour,int ligne,int colonne) {
    	/**
    	 * Méthode pour gérer un nouveau déplacement du joueur.
    	 * Cette méthode demande au joueur d'entrer les coordonnées 
    	 * de la ligne et de la colonne pour effectuer un déplacement.
    	 * Elle vérifie si le déplacement est valide, c'est-à-dire 
    	 * s'il est à l'intérieur des limites et si la case est libre.
    	 *
    	 * @param schema : Le tableau représentant le plateau de jeu.
    	 * @param tour   : Le symbole du joueur actuel ('X' ou 'O').
    	 */
    	Scanner scanner = new Scanner(System.in);
    	
    	// Boucle infinie pour continuer à demander des entrées jusqu'à ce qu'une condition soit remplie
	    while (true){
	    	// Vérifie si les valeurs de ligne et colonne sont valides (0, 1 ou 2)
	    	if((ligne == 0 || ligne == 1 || ligne == 2) && (colonne == 0 || colonne == 1 || colonne == 2)) {
	    		break;
	    	}else {
	    	// Si les valeurs ne sont pas valides, afficher un message d'erreur
	    	System.out.println("0, 1 et 2 sont les numéros possibles que vous pouvez choisir.\n");
	    	System.out.println("[Tour " + tour + "] Entrez la ligne (0, 1 ou 2) et la colonne (0, 1 ou 2) :");
	    	ligne = scanner.nextInt();
	        colonne = scanner.nextInt();
	    	}
    
	    }
        while (true) {
            // Vérification si le déplacement est valide (dans les limites et case libre)
            if (schema[ligne][colonne] == ' ') {
                schema[ligne][colonne] = tour; // Marquer la case avec le symbole du joueur
                break;
            } else {
                System.out.println("Place déjà occupée, essayez à nouveau.\n");
                System.out.println("[Tour " + tour + "] Entrez la ligne (0, 1 ou 2) et la colonne (0, 1 ou 2) :");
    	    	ligne = scanner.nextInt();
    	        colonne = scanner.nextInt();
            }
        }
    }


    private static boolean aGagne(char[][] schema, char joueur) {
    	/**
    	 * Méthode pour vérifier si un joueur a gagné dans un jeu de morpion.
    	 * Cette méthode vérifie les lignes, les colonnes et les diagonales du tableau.
    	 *
    	 * @param schema : Le tableau représentant l'état du jeu.
    	 * @param joueur      : Le symbole du joueur (par exemple, 'X' ou 'O').
    	 * @return booléen true si le joueur a gagné, false sinon.
    	 */
    	
        // Vérification des lignes, colonnes et diagonales
        for (int i = 0; i < 3; i++) {
            if (schema[i][0] == joueur  && schema[i][1] == joueur  && schema[i][2] == joueur ) {
                return true; // Ligne
            }
            if (schema[0][i] == joueur  && schema[1][i] == joueur  && schema[2][i] == joueur ) {
                return true; // Colonne
            }
        }
        if (schema[0][0] == joueur  && schema[1][1] == joueur  && schema[2][2] == joueur ) {
            return true; // Diagonale principale
        }
        if (schema[0][2] == joueur  && schema[1][1] == joueur  && schema[2][0] == joueur ) {
            return true; // Diagonale secondaire
        }
        return false;
    }
}