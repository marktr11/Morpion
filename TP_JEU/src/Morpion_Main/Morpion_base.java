package Morpion_Main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Morpion_base {
	// Déclaration du tableau (grille)
    static char[][] schema = new char[3][3];
    // Variable pour compter le nombre de mouvements
    static int mouvements = 0;
    // Variables pour suivre le gagnant
    static boolean victoireX = false, victoireO = false;
    // Variable pour savoir quel joueur joue (X ou O)
    static char tour = 'X';
    

    public static void main(String[] args) {
        int ligne,colonne;
        Scanner scanner = new Scanner(System.in);
        
        // Initialisation de la grille
        initialiser(schema);

        // Boucle principale du jeu
        while (true) {
            // Affichage de la grille
            voir(schema);
            
            // Boucle pour demander à l'utilisateur de saisir des coordonnées valides
            while (true) { 
                    System.out.println("[Tour " + tour + "] Entrez la ligne (0, 1 ou 2) et la colonne (0, 1 ou 2) : ");
                    try {
                         ligne = scanner.nextInt(); // Lit l'entrée de l'utilisateur pour la ligne
                         colonne = scanner.nextInt(); // Lit l'entrée de l'utilisateur pour la colonne
                        
                        System.out.println("Ligne: " + ligne + ", Colonne: " + colonne);
                        break; // Sort de la boucle si les entrées sont valides

                    } catch (InputMismatchException e) {
                        System.out.println("Entrée invalide. Veuillez entrer un nombre entier.\n");
                        scanner.nextLine(); // Efface la ligne d'entrée invalide pour éviter une boucle infinie
                    }
                }


              
            // Tente d'effectuer le nouveau déplacement en utilisant les coordonnées fournies
            nouveau_deplacement(schema, tour, ligne, colonne);
            
            
            // Incrémente le compteur de mouvements
            mouvements++;

            // Vérification s'il y a un gagnant
            if (aGagne(schema, tour)) {
                voir(schema);
                System.out.println("FÉLICITATIONS " + tour + ", TU AS GAGNÉ !!!");
                break;
            }

            // Si tous les mouvements ont été effectués sans gagnant, match nul
            if (mouvements == 9) {
                voir(schema);
                System.out.println("Rien à faire... EGALITÉ !");
                break;
            }

            // Changement de joueur
            tour = (tour == 'X') ? 'O' : 'X';
        }
        scanner.close();
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