package lucioles;

// Étape 1 : Simulation d'une seule luciole

public class LucioleSeule {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	/**
	 * Retourne un charactère correspondant à l'état de la luciole (allumé ou 
	 * éteint)
	 * @param niveauEnergie le niveau d'energie de la luciole
	 * 
	 * @return '*'' si la luciole est allumée, '.' si elle est éteinte
	 */
	public static char symboliseLuciole(double niveauEnergie){
		
		if (niveauEnergie > SEUIL) {
			return '*';
		}
		else {
			return '.';
		}
	}

	/**
	 * Affiche une luciole
	 * @param niveauEnergie le niveau d'énergie de la luciole
	 * @param verbeux si true affiche le niveau d'énergie à coté du charactère 
	 * représentant la luciole
	 */
	public static void afficheLuciole(double niveauEnergie, boolean verbeux) {
		System.out.print(symboliseLuciole(niveauEnergie));
		if (verbeux) {
			System.out.print(" niveau d'énergie : " + niveauEnergie);
		}
		System.out.println();
	}

	/**
	 * Fait passer la luciole d'un pas au suivant.
	 * @param niveauEnergie le niveau d'energie actuelle de la luciole
	 * @param deltaEnergie la quantité d'énergie gagnée à chaque pas
	 * 
	 * @return le niveau d'energie au pas suivant de la luciole
	 */
	public static double incrementeLuciole(
		double niveauEnergie, 
		double deltaEnergie
	) {
		if (niveauEnergie > SEUIL) {
			return 0.0;
		}
		return niveauEnergie + deltaEnergie;
	}

	/**
 	* fait évoluer la luciole sur plusieurs pas
	* @see incrementeLuciole
	* 
	* @param nbPas le nombre de pas
	*/
	public static void simuleLucioleNbPas(int nbPas) {
		
		double lucioleEnergie = RandomGen.rGen.nextDouble() * 100;
		double lucioleDeltaEnergie = RandomGen.rGen.nextDouble();

		for (int i = 0; i < nbPas; i++) {
			lucioleEnergie = incrementeLuciole(
				lucioleEnergie, 
				lucioleDeltaEnergie
			);
			afficheLuciole(lucioleEnergie, true);
		}
	}
	
	/** 
	 * Simule la luciole jusqu'a ce qu'elle est flashée trois fois
	 * @see simuleLucioleNbPas
	 * @see incrementeLuciole 
	 */ 
	public static void simuleLucioleNbFlashs() {
		
		double lucioleEnergie = RandomGen.rGen.nextDouble() * 100;
		double lucioleDeltaEnergie = RandomGen.rGen.nextDouble();
		int nbFlash = 0;
		
		while (nbFlash < 3) {
			lucioleEnergie = incrementeLuciole(
				lucioleEnergie,
				lucioleDeltaEnergie
			);
			afficheLuciole(lucioleEnergie, true);
			if (lucioleEnergie > SEUIL) {
				lucioleEnergie = 0.0;
				nbFlash++;
			}
		}
	}

	public static void main(String[] args) {
		double lucioleEnergie = RandomGen.rGen.nextDouble() * 100;
		afficheLuciole(lucioleEnergie, true);
		lucioleEnergie = 10.0;
		afficheLuciole(lucioleEnergie, true);
		lucioleEnergie = 30.0;
		afficheLuciole(lucioleEnergie, true);
		lucioleEnergie = 100.0;
		afficheLuciole(lucioleEnergie, true);


		double lucioleDeltaEnergie = RandomGen.rGen.nextDouble();
		lucioleEnergie = 0.0;
		lucioleEnergie = incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie);
		afficheLuciole(lucioleEnergie, true);
		lucioleEnergie = incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie);
		afficheLuciole(lucioleEnergie, true);
		lucioleEnergie = incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie);
		afficheLuciole(lucioleEnergie, true);

		simuleLucioleNbFlashs();
	}

}
