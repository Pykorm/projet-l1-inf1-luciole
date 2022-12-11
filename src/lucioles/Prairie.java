package lucioles;


// Étapes 2 et 3 : Définition de prairies, et simulation sans interaction

public class Prairie {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;

	/**
	 * Génère une luciole avec un niveau d'énergie aléatoire et un delta aléatoire
	 */
	public static double[] creerLuciole() {
		return new double[] { RandomGen.rGen.nextDouble() * SEUIL, RandomGen.rGen.nextDouble() };
	}

	/**
	 * modifie la luciole passé en paramètre pour la faire passer au pas suivant
	 * @param luciole la luciole, sous forme d'un tableau de deux élément, son energie actuelle et son delta
	 */
	public static void incrementeLuciole(double[] luciole) {
		if (luciole[ENERGIE] > SEUIL) {
			luciole[ENERGIE] = 0.0;
		}
		else {
			luciole[ENERGIE] += luciole[DELTA];
		}
	}

	/**
	 * Créer une population de luciole générée aléatoirement
	 * @see creerLuciole
	 * @param nbLucioles nombre de luciole dans la population
	 * @return une population, c'est à dire un tableau de lucioles (donc un tableau de tableau)
	 */
	public static double[][] creerPopulation(int nbLucioles) {
		
		double[][] pop = new double[nbLucioles][2];
		for (int i = 0; i < nbLucioles; i++) {
			pop[i] = creerLuciole();
		}
		return pop;
	}
	
	/**
	 * Créer une prairie vide
	 * @param nbLigne nombre de ligne dans la prairie
	 * @param nbColonnes nombre de colonne dans la prairie
	 * 
	 * @return une prairie, c'est à dire un tableau de tableau dont chaque element est l'indice d'une luciole dans la population 
	 */
	public static int[][] prairieVide (int nbLignes, int nbColonnes) {
		int[][] prairie = new int[nbLignes][nbColonnes];
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				prairie[i][j] = -1;
			}
		}
		return prairie;
	}

	/**
	 * affiche une prairie, en symbolisant chaque luciole par  '.' ou '*', et l'abscence de luciole par ' '
	 * @param prairie la prairie à afficher
	 * @param population la population associé à la prairie
	 */
	public static void affichePrairie(int[][] prairie, double[][] population) {
		if (prairie.length == 0) return;
		for (int i = 0 ; i < prairie[0].length + 2; i++) {
			System.out.print("#");
		}
		System.out.println();
		for (int i = 0; i < prairie.length; i++) {
			System.out.print("#");
			for(int j = 0; j < prairie[i].length; j++) {
				if (prairie[i][j] == -1) System.out.print(' ');
				else if (population[prairie[i][j]][ENERGIE] > SEUIL) System.out.print('*');
				else System.out.print('.');
			}
			System.out.print("#");
			System.out.println();
		}
		for (int i = 0 ; i < prairie[0].length + 2; i++) {
			System.out.print("#");
		}
		System.out.println();
	}

	/**
	 * Génère une prairie, en répartissant toutes les lucioles de la population aléatoirement
	 * @param nbLignes Nombre de lignes de la colonne
	 * @param nbColonnes Nombre de colonne de la prairie
	 * @param population La population à répartir dans la prairie
	 * 
	 * @return la prairie générée, sous forme d'un tableau de nbLignes contenant des tableaux de nbColonnes dont chaque élément est l'indice d'une luciole dans la population
	 */
	public static int[][] prairieLucioles(int nbLignes, int nbColonnes, double[][] population) {
		
		int[][] prairie = prairieVide(nbLignes, nbColonnes);

		for (int i = 0; i < population.length; i++) {
			int x = RandomGen.rGen.nextInt(nbColonnes);
			int y = RandomGen.rGen.nextInt(nbLignes);
			while (prairie[y][x] != -1) {
				x = RandomGen.rGen.nextInt(nbColonnes);
				y = RandomGen.rGen.nextInt(nbLignes);
			}
			prairie[y][x] = i;
		}
		return prairie;
	}

	/**
	 * Fait évoluer les lucioles de nbPas, en affichant la prairie à chaque pas.
	 * @see incrementeLuciole
	 * 
	 * @param prairie la prairie à faire écolu
	 */
	public static void simulationPrairie(int[][] prairie, double[][] population, int nbPas) {
		
		for (int i = 0; i< nbPas; i++) {
			for (int j = 0; j < population.length; j++) {
				incrementeLuciole(population[j]);
			}
			affichePrairie(prairie, population);
		}
	}

	public static void main(String[] args) {
		double[][] lucioles = creerPopulation(10);
		for (int i = 0; i < lucioles.length; i++) {
			System.out.print(lucioles[i][ENERGIE]);
			System.out.print(" " + lucioles[i][DELTA]);
			System.out.println();
		}
		
		affichePrairie(prairieVide(2, 2), lucioles);
		int[][] prairie1 = prairieVide(10, 20);
		prairie1[4][5] = 3;
		prairie1[5][4] = 2;
		prairie1[7][1] = 1;
		affichePrairie(prairie1, lucioles);

		int[][] prairie2 = prairieVide(10, 10);
		prairie2[0][0] = 1;
		prairie2[1][1] = 2;
		prairie2[2][2] = 3;
		prairie2[3][3] = 4;
		prairie2[4][4] = 5;
		prairie2[5][5] = 6;
		prairie2[6][6] = 7;
		prairie2[7][7] = 8;
		prairie2[8][8] = 9;
		affichePrairie(prairie2, lucioles);

		double[][] population = creerPopulation(4);
		int[][] prairie3 = prairieLucioles(2, 2, population);
		affichePrairie(prairie3, population);
		
		simulationPrairie(prairie3, population, 200);
	}

}
