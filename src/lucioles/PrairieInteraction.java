package lucioles;

// Étape 4: Simulation d'une prairie avec interaction entre les lucioles

public class PrairieInteraction {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;

	// Définition de l'apport d'énergie par flash, et du rayon de voisinage
	public static final double APPORT = 15.0;
	public static final int RAYON = 5;

	// ------------------- Utilitaires ------------------------

	/**
	 * Cette fonction affiche le tableau de tableau passé en paramètre
	 * @param tab le tableau à afficher
	 */
	public static void afficherTab(int[][] tab) {
		for (int i = 0; i < tab.length; i++) {
			for(int j = 0; j < tab[i].length; j++) {
				System.out.print(tab[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Affiche un tableau de tableau représentant une population de luciole. 
	 * Utile pour le débuggage.
	 * @param tab la population à afficher
	 */
	public static void afficherPop(double[][] tab) {
		for (int i = 0; i < tab.length; i++) {
			for(int j = 0; j < tab[i].length; j++) {
				System.out.print(tab[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Créer un sous-tableau d'entier. Un sous-tableau est un tableau inclus 
	 * dans un autre tableau.
	 * @param tab le tableau contenant le sous tableau
	 * @param debut l'index du premier élément du sous-tableau dans le tableau
	 * @param fin l'index du dernier élément du sous-tableau dans le tableau
	 * @return le sous-tableau composé des éléments à l'indice i dans le tableau 
	 * tab, pour i allant de debut à fin
	 */
	public static int[] sous_tableau(int[] tab, int debut, int fin) {
		int[] sous_tab = new int[fin - debut];
		for (int i = 0; i < sous_tab.length; i++) {
			sous_tab[i] = tab[debut];
			debut++;
		}

		return sous_tab;
	}

	public static int enfermer_entier(int a, int min, int max) {
		//Fonction utilitaire qui retourne :
		// a si a >= min et a =< max
		// min si a < min
		// max si a > max
		if (a > max) return max;
		else if (a < min) return min;
		else return a;
	}

	// -------------------------------------------------------

	/**
	 * Renvoie une copie de la population tab. Une population est un tableau 
	 * contenant des tableaux contenant chacun 2 doubles.
	 * @param pop la population à copier
	 * @return une copie de la population
	 */
	public static double[][] copiePopulation (double[][] pop) {
		double[][] copie = new double[pop.length][];
		for (int i = 0; i < pop.length; i++) {
			copie[i] = new double[pop[i].length];
			for(int j = 0; j < pop[i].length; j++) {
				copie[i][j] = pop[i][j];
			}
		}
		return copie;
	}

	/**
	 * Copie une luciole. Une luciole est un tableau de 2 doubles.
	 * le premier représente le niveau d'énergie, le deuxième le delta.
	 * @param luciole la luciole à copier 
	 * @return une copie de la luciole
	 */
	public static double[] copieLuciole(double[] luciole) {
		return new double[] {luciole[ENERGIE] , luciole[DELTA]};
	} 
	
	/**
	 * Renvoie le nombre de lucioles dans la prairie.
	 * @param prairie la prairie dont on compte le nombre de luciole
	 * @return le nombre de lucioles dans la prairie 
	 */
	public static int nb_lucioles(int[][] prairie) {
		int nb_lucioles = 0;

		for (int i = 0; i < prairie.length; i++) {
			for (int j = 0; j < prairie[0].length; j++) {
				if (prairie[i][j] != -1){
					nb_lucioles++;
				}
			} 
		}
		return nb_lucioles;
	}

	/**
	 * Fonction qui retourne un tableau contenant les des lucioles qui se trouve
	 * dans une zone de la prairie. Une zone est un rectangle dont le coin 
	 * supérieur gauche est à la ligne y1 et à la colonne x1
	 * tandis que son coin inférieur droit est à la ligne y2 et à la colonne x2. 
	 * On suppose donc que x1 < x2 et y1 < y2. Les indices peuvent déborder de 
	 * la prairie.
	 * @param prairie
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static int[] trouver_lucioles_dans_rectangle(
		int[][] prairie, 
		int x1, 
		int y1, 
		int x2, 
		int y2
	) {
		// System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
		int centre_x = (x1 + x2) /2;
		int centre_y = (y1 + y2) /2;

		//On cacul les cotés du rectangle
		int coin1_x = enfermer_entier(x1, 0, prairie.length -1);
		int coin1_y = enfermer_entier(y1, 0, prairie[0].length -1);
		int coin2_x = enfermer_entier(x2, 0, prairie.length -1);
		int coin2_y = enfermer_entier(y2, 0, prairie[0].length -1);
		int cote_horizontal = coin2_x - coin1_x;
		int cote_vertical = coin2_y - coin1_y;

		//le nombre maximal de lucioles est coté*coté 
		int[] voisines = new int[(cote_horizontal+1)*(cote_vertical+1)];
		int derniere_luciole_indice = 0;

		//On parcourt toute les cases du rectangle et on ajoute les lucioles
		//que l'on trouve à voisines
		for (int i = coin1_x; i <= coin1_x + cote_horizontal; i++) {
			for (int j = coin1_y; j <= coin1_y + cote_vertical; j++) {
				if (prairie[i][j] != -1 && !(i == centre_x && j == centre_y)) {
					voisines[derniere_luciole_indice] = prairie[i][j];
					derniere_luciole_indice++;
				}
			} 
		}

		//On tronque le tableau, car il reste des cases vides si il y a moins de
		//voisines que le maximum de voisines.
		return sous_tableau(voisines, 0, derniere_luciole_indice);
	}

	/**
	 * Calcul le tableau de voisinage d'une prairie.
	 * Le tableau de voisinage d'une prairie est un tableau qui contient à la 
	 * ligne i un tableau contenant toutes les lucioles de la prairie dans un 
	 * rectangle de RAYON*2+1 de coté, centré sur la luciole à l'indice i dans
	 * la population.
	 * Dit autrement, cette fonction renvoie un tableau de tableau qui associe
	 * chaque lucioles à un tableau de toutes ses voisines dans le rayon RAYON.
	 * @param prairie la prairie dont on calcul le tableau de voisinage
	 * @return
	 */
	public static int[][] voisinage (int[][] prairie) {
		int[][] voisinage = new int[nb_lucioles(prairie)][];

		for (int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[0].length; j++) {
				if (prairie[i][j] != - 1) {
					voisinage[prairie[i][j]] = trouver_lucioles_dans_rectangle(
						prairie,
						i - RAYON,
						j - RAYON, 
						i + RAYON,
						j + RAYON
					);
					System.out.println();
				}
			}
		}

		return voisinage;
	}

	/**
	 * Fait passer une luciole d'un pas au suivant, d'après les règles définie
	 * par le sujet.
	 * @param population la population correspondant au voisinage (à la prairie
	 *  de ce voisinage)
	 * @param voisinage le tableau de voisinage de la prairie correspondant à la
	 * population
	 * @param luciole la luciole à incrémenter. Ce tableau sera modifié.
	 * @param numero_luciole le numéro de la luciole dans la population.
	 */
	public static void incrementeLuciole (
		double[][] population, 
		int[][] voisinage, 
		double[] luciole, 
		int numero_luciole
	) {
		if (luciole[ENERGIE] > SEUIL) {
			luciole[ENERGIE] = luciole[ENERGIE] % SEUIL;
		}

		luciole[ENERGIE] += luciole[DELTA];

		for (int i = 0; i < voisinage[numero_luciole].length; i++) {
			if (population[voisinage[numero_luciole][i]][ENERGIE] > SEUIL) {
				luciole[ENERGIE] += APPORT;
			}
		}
	}

	/**
	 * simule l'évolution d'une prairie et de sa population sur un certain 
	 * nombre de pas, affiche la prairie à chaque pas et créer une image 
	 * gif nommée "pas-n.bmp" ou n est le numéro du pas,dans le dossier /img de 
	 * ce projet. Enfin, cette fonction génère un gif nommé "simulation.gif"
	 * dans le dossier /simu.
	 * @param population la populatuion qui va évoluer
	 * @param prairie la prairie correspondant à la population
	 * @param nbPas le nombre de pas à simuler.
	 */
	public static void simulerPrairieNbPas(
		double[][] population, 
		int[][] prairie, 
		int nbPas
	) {
		int[][] voisinage = voisinage(prairie);
		String[] images_generee = new String[nbPas];

		for (int i = 0; i < nbPas; i++) {
			System.out.println(i);
			double[][] nouvelle_population = copiePopulation(population);
			for (int j = 0; j < voisinage.length; j++) {
				double[] copie_luciole = copieLuciole(population[j]);
				incrementeLuciole(population, voisinage, copie_luciole, j);
				nouvelle_population[j][ENERGIE] = copie_luciole[ENERGIE];
				nouvelle_population[j][DELTA] = copie_luciole[DELTA];
			}
			for (int j = 0; j < population.length; j++) {
				population[j] = nouvelle_population[j];
			}
			// Prairie.affichePrairie(prairie, nouvelle_population);
			String nom = "img/pas-" + i + ".bmp";
			outils.BitMap.bmpEcritureFichier(
				nom, 
				prairie, 
				nouvelle_population, 
				SEUIL
			);
			images_generee[i] = nom;
		}

		for (int i = 0; i < images_generee.length; i++) {
			System.out.println(images_generee[i]);
		}

		outils.GifCreator.construitGIF("simu/simulation.gif", images_generee);
	}

	/**
	 * Créer un prairie linéaire, c'est à dire de dimension 1xn, n étant 
	 * l'argument de la fonction.
	 * Afficher les lucioles est pratique pour visualiser la propagation des
	 * lumière quand une luciole s'allume.
	 * @param n
	 * @return
	 */
	public static int[][] creerPrairieLigne(int n) {
		int[][] prairie = new int[1][n];
		for (int i =0; i< n; i++) {
			prairie[0][i] = i;
		}
		return prairie;
	}

	public static void main(String[] args) {
		double[][] population = Prairie.creerPopulation(500);
		int[][] prairie = Prairie.prairieLucioles(
			150,
			150, 
			population
		);
		// int[][] prairie = creerPrairieLigne(15);
		// afficherTab(voisinage(prairie));

		simulerPrairieNbPas(population, prairie, 500);
	}

}
