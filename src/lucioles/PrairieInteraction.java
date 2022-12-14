package lucioles;

// Étape 4: Simulation d'une prairie avec interaction entre les lucioles

public class PrairieInteraction {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;

	// Définition de l'apport d'énergie par flash, et du rayon de voisinage
	public static final double APPORT = 5.0;
	public static final int RAYON = 5;

	// ------------------- Utilitaires ------------------------

	/**
	 * Cette fonction affiche le tableau de tableau d'entier passé en paramètre
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
	 * @param debut l'indice du premier élément du sous-tableau dans le tableau
	 * @param fin l'indice du dernier élément du sous-tableau das le tableau
	 * @return le sous-tableau composé des éléments de l'indice debut jusqu'a 
	 * l'indice fin dans le tableau.
	 */
	public static int[] sousTableau(int[] tab, int debut, int fin) {
		int[] sousTab = new int[fin - debut];
		for (int i = 0; i < sousTab.length; i++) {
			sousTab[i] = tab[debut];
			debut++;
		}

		return sousTab;
	}

	/** 
	 * Fonction utilitaire qui retourne :
	 * a si a >= min et a =< max
	 * min si a < min
	 * max si a > max
	 * @param a
	 * @param min
	 * @param max
	 */
	public static int enfermerEntier(int a, int min, int max) {
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
	public static int nbLucioles(int[][] prairie) {
		int nbLucioles = 0;

		for (int i = 0; i < prairie.length; i++) {
			for (int j = 0; j < prairie[0].length; j++) {
				if (prairie[i][j] != -1){
					nbLucioles++;
				}
			} 
		}
		return nbLucioles;
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
	public static int[] trouverLuciolesDansRectangle(
		int[][] prairie, 
		int x1, 
		int y1, 
		int x2, 
		int y2
	) {
		// System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
		int centreX = (x1 + x2) /2;
		int centreY = (y1 + y2) /2;

		//On cacul les cotés du rectangle
		int coin1X = enfermerEntier(x1, 0, prairie.length -1);
		int coin1Y = enfermerEntier(y1, 0, prairie[0].length -1);
		int coin2X = enfermerEntier(x2, 0, prairie.length -1);
		int coin2Y = enfermerEntier(y2, 0, prairie[0].length -1);
		int coteHorizontal = coin2X - coin1X;
		int coteVertical = coin2Y - coin1Y;

		//le nombre maximal de lucioles est coté*coté 
		int[] voisines = new int[(coteHorizontal+1)*(coteVertical+1)];
		int derniereLucioleIndice = 0;

		//On parcourt toute les cases du rectangle et on ajoute les lucioles
		//que l'on trouve à voisines
		for (int i = coin1X; i <= coin1X + coteHorizontal; i++) {
			for (int j = coin1Y; j <= coin1Y + coteVertical; j++) {
				if (prairie[i][j] != -1 && !(i == centreX && j == centreY)) {
					voisines[derniereLucioleIndice] = prairie[i][j];
					derniereLucioleIndice++;
				}
			} 
		}

		//On tronque le tableau, car il reste des cases vides si il y a moins de
		//voisines que le maximum de voisines.
		return sousTableau(voisines, 0, derniereLucioleIndice);
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
		int[][] voisinage = new int[nbLucioles(prairie)][];

		for (int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[0].length; j++) {
				if (prairie[i][j] != - 1) {
					voisinage[prairie[i][j]] = trouverLuciolesDansRectangle(
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
	 * @param numeroLuciole le numéro de la luciole dans la population.
	 */
	public static void incrementeLuciole (
		double[][] population, 
		int[][] voisinage, 
		double[] luciole, 
		int numeroLuciole
	) {
		if (luciole[ENERGIE] > SEUIL) {
			luciole[ENERGIE] = luciole[ENERGIE] % SEUIL;
			//return; 
			// en sortant de la fonction ici, on ajoute une règle
			// intérressante : une luciole ne peut pas être allumée deux tours
			// de suite.
		}

		luciole[ENERGIE] += luciole[DELTA];

		for (int i = 0; i < voisinage[numeroLuciole].length; i++) {
			if (population[voisinage[numeroLuciole][i]][ENERGIE] > SEUIL) {
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
		String[] imageGenerees = new String[nbPas];

		for (int i = 0; i < nbPas; i++) {
			System.out.println((double) i / nbPas * 100 + " %");
			double[][] nouvellePopulation = copiePopulation(population);
			for (int j = 0; j < voisinage.length; j++) {
				double[] copieLuciole = copieLuciole(population[j]);
				incrementeLuciole(population, voisinage, copieLuciole, j);
				nouvellePopulation[j][ENERGIE] = copieLuciole[ENERGIE];
				nouvellePopulation[j][DELTA] = copieLuciole[DELTA];
			}
			for (int j = 0; j < population.length; j++) {
				population[j] = nouvellePopulation[j];
			}
			// Prairie.affichePrairie(prairie, nouvellePopulation);
			String nom = "img/pas-" + i + ".bmp";
			outils.BitMap.bmpEcritureFichier(
				nom, 
				prairie, 
				nouvellePopulation, 
				SEUIL
			);
			imageGenerees[i] = nom;
		}

		for (int i = 0; i < imageGenerees.length; i++) {
			System.out.println(imageGenerees[i]);
		}

		outils.GifCreator.construitGIF("simu/simulation.gif", imageGenerees);
	}

	/**
	 * Créer un prairie linéaire, c'est à dire de dimension 1xn.
	 * Afficher les lucioles en ligne est pratique pour visualiser la 
	 * propagation des lumière quand une luciole s'allume.
	 * @param n la longueur de la prairie linéaire
	 * @return
	 */
	public static int[][] creerPrairieLigne(int n) {
		int[][] prairie = new int[1][n];
		for (int i =0; i< n; i++) {
			prairie[0][i] = i;
		}
		return prairie;
	}

	/**
	 * Génère une prairie carré dans laquelles les lucioles sont groupées. 
	 * La prairie contiendra nbGroupe groupes, chaque groupe contient 
	 * lucioleParGroupe lucioles, qui sont toutes dans un carré de RAYON de 
	 * coté.
	 * Les lucioles d'un groupe peuvent donc toute s'influencer les unes les
	 * autres, ce qui permet d'observer la propagation de l'énergie entre les
	 * lucioles d'un groupe.
	 * Attention : luciolesParGroupe doit être inférieur à RAYON² si RAYON
	 * est paire, et (RAYON-1)² si RAYON est impaire. Sinon, il y aura plus de
	 * lucioles que de place par groupe.
	 * @param c la dimension de la prairie, qui sera un carré de c de coté.
	 * @param nbGroupe nombre de groupe dans la prairie
	 * @param lucioleParGroupe nombre de lucioles par groupe (/!\ doit être
	 * inférieur a RAYON² si RAYON est paire, et (RAYON-1)² si RAYON est 
	 * impaire)
	 * @return une prairie de dimension cxc, dans laquelle les lucioles sont
	 * répartie dans des groupes.
	 */
	public static int[][] creerPrairieGroupes(
		int c, 
		int nbGroupe, 
		int lucioleParGroupe
	) {
		int[][] prairie = Prairie.prairieVide(c, c);

		for (int i = 0; i < nbGroupe; i++) {
			int centreGroupeX = RandomGen.rGen.nextInt(c - RAYON) + RAYON/2;
			int centreGroupeY = RandomGen.rGen.nextInt(c - RAYON) + RAYON/2;

			for (int j = 0; j < lucioleParGroupe; j++) {
				int lucioleX = centreGroupeX + RandomGen.rGen.nextInt(RAYON) - RAYON/2;
				int lucioleY = centreGroupeY + RandomGen.rGen.nextInt(RAYON) - RAYON/2;

				while (prairie[lucioleX][lucioleY] != -1) {
					lucioleX = centreGroupeX + RandomGen.rGen.nextInt(RAYON) - RAYON/2;
					lucioleY = centreGroupeY + RandomGen.rGen.nextInt(RAYON) - RAYON/2;
				}

				prairie[lucioleX][lucioleY] = i * lucioleParGroupe + j;
			}
		}

		return prairie;
	}

	public static void main(String[] args) {

		// le fichier simulation_classique.gif contient une simulation d'une 
		// prairie classique, avec un répartion aléatoire des lucioles.
		// Pour obtenir un fichier similaire :
		// double[][] pop = Prairie.creerPopulation(2000);
		// int[][] prairie = Prairie.prairieLucioles(150, 150, pop);
		// simulerPrairieNbPas(pop, prairie, 50);

		// Le fichier propagation_groupe.gif contient une simulation d'une
		// prairie générée par creerPrairieGroupes(), et dont les lucioles sont
		// groupées, ce qui permet d'observation l'interraction entre les 
		// lucioles.
		// Il a été généré pour une prairie de 150 de coté, avec 15 groupes de 
		// 10 lucioles, un RAYON de 5, et pendant 50 pas.
		// Pour généré un fichier similaire : 
		// int[][] prairie = creerPrairieGroupes(150, 15, 10);
		// double[][] population = Prairie.creerPopulation(150);
		// simulerPrairieNbPas(population, prairie, 50);

		// Le fichier prairie_ligne.gif est le résultat d'une simulation de 
		// prairie linéaire, c'est à dire de dimension 1x100, générée par
		// creerPrairieLigne(). Ce type de prairie permet de vérifier que la
		// propagation d'énergie de luciole en luciole se fait correctement.
		// Cependant, ce n'est pas très interressant à regarder.
		// Pour généré un fichier similaire : 
		// int[][] prairieLigne = creerPrairieLigne(100);
		// double[][] pop = Prairie.creerPopulation(100);
		// simulerPrairieNbPas(pop, prairieLigne, 50);


		// Le fichier vent.gif est le résultat d'une simulation d'une prairie
		// aléatoire générée par Prairie.prairieLucioles, mais avec une version
		// modifié de la fonction voisinage. Chaque lucioles n'est influencée
		// que par les lucioles en dessous d'elle, ce qui donne une simulation
		// dans laquelle la propagation de l'énergie ne se fait que vers le 
		// haut. Ca donne l'impression qu'il y a du vent dan la prairie, et que
		// les lucioles sont emportées.
		//Pour généré un fichier similaire : 
		// double[][] pop = Prairie.creerPopulation(5000);
		// int[][] prairie = Prairie.prairieLucioles(150, 150, pop);
		// simulerPrairieNbPas(pop, prairie, 50);
	}

}
