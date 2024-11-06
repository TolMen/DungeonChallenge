package DungeonChallenge;

import java.util.Scanner;
import java.util.Random;

/**
 * Jeu de donjon textuel avec différentes phases de jeu : exploration,
 * affrontement et embuscade.
 */
public class DungeonChallenge {

    // Instances pour la saisie de l'utilisateur et la génération de valeurs aléatoires
    private static final Scanner saisie = new Scanner(System.in);
    private static final Random random = new Random();
    
    // Variables globales pour les pièces (coins) et les points de vie (pv) du joueur
    private static int coins = 0;
    private static int pv = 7;
    
    // Pause pour ajouter un délai entre les actions
    public static void pause() {
	try {
	    Thread.sleep(300);  // Pause de 300 ms
	} catch (InterruptedException e) {
	    // Ne rien faire en cas d'interruption
	}
    }
    
    // Point d'entrée principal du jeu
    public static void main(String[] args) {
	introduction();  // Message de bienvenue
	phase1();  // Début de la première phase
	
	System.out.println("");
	System.out.println("Score : " + coins);  // Affiche le score final du joueur
    }

    // Introduction du jeu
    private static void introduction() {
	System.out.println("Le donjon !!\n");
	System.out.println("Bienvenue dans mon jeu aventurier !");
    }

    // Phase 1 : Le joueur choisit d'avancer ou de quitter l'aventure
    private static void phase1() {
	pause();
	
	System.out.println("\n------------------------------");
	System.out.println("\nPhase 1 :\n");
	System.out.println("Quel est ton choix aventurier ?\nAvancer -> 1 / Partir -> 2");
	
	int choix = saisie.nextInt();
	if (choix == 1) {  // Si le joueur choisit d'avancer
	    System.out.println("\nVous avancez dans le donjon !");
	    phase2();  // Passe à la phase suivante
	} else {  // Sinon, le joueur quitte le jeu
	    System.out.println("\nVous décidez d'arrêter l'aventure !");
	}
    }

    // Phase 2 : Exploration du donjon avec un lancer de pièce pour déterminer la suite
    private static void phase2() {
	pause();
	
	System.out.println("\n------------------------------");
	System.out.println("\nPhase 2 :\n");
	System.out.println("Vous lancez une pièce.\n");

	// Face ou pile pour décider de l'événement suivant
	String[] cotePiece = {"face", "pile"};
	int index = random.nextInt(cotePiece.length);

	pause();
	System.out.println("Vous êtes tombé sur " + cotePiece[index] + " !\n");

	// Si la pièce tombe sur pile, passe à l'affrontement ; sinon, donne des coins
	if (cotePiece[index].equals("pile")) {
	    phase3();
	} else {
	    pause();
	    int randomCoins = (int) (Math.random() * 20 + 1);  // Gagne entre 1 et 20 coins
	    System.out.println("Vous gagnez " + randomCoins + " coins.");
	    
	    coins += randomCoins;
	    phase5();  // Passe à la phase de résolution
	}
    }

    // Phase 3 : Affrontement avec un gardien et choix de stratégie
    public static void phase3() {
	pause();

	System.out.println("------------------------------");
	System.out.println("\nPhase 3 :\n");
	System.out.println("AIE ! Vous avez réveillé un gardien !!\n");

	// Instructions de choix de stratégie de combat
	System.out.println("Trois choix s'offre à vous :");
	System.out.println("Choix 1 : Attaquer -> Gain : 70 coins / 66% perdre 3 PV");
	System.out.println("Choix 2 : Défense  -> Gain : 30 coins / 33% perdre 3 PV");
	System.out.println("Choix 3 : Fuite    -> 66% Gains : 15 coins / 33% perdre 2 PV");
	System.out.println("Que faire ?");

	int choixCombat = saisie.nextInt();
	System.out.println("");

	// Résultats basés sur le choix du joueur
	if (choixCombat == 1) {  // Choix : attaquer
	    int randomAttack = random.nextInt(3) + 1;
	    if (randomAttack == 1) {
		System.out.println("Vous avez vaincu le gardien !");
		coins += 70;
	    } else {
		System.out.println("Le combat est gagné, mais à quel prix...");
		coins += 70;
		pv -= 3;
	    }
	} else if (choixCombat == 2) {  // Choix : défense
	    int randomDefense = random.nextInt(3) + 1;
	    if (randomDefense == 3) {
		System.out.println("Le gardien est mort, mais vous avez été blessé...");
		coins += 30;
		pv -= 3;
	    } else {
		System.out.println("Votre contre-attaque a éliminé le gardien !");
		coins += 30;
	    }
	} else {  // Choix : fuite
	    int randomFuite = random.nextInt(3) + 1;
	    if (randomFuite == 3) {
		System.out.println("La fuite est une option douloureuse...");
		pv -= 2;
	    } else {
		System.out.println("Quelle réussite de fuir en volant son ennemi !");
		coins += 15;
	    }
	}

	// Vérifie si le joueur a encore des PV, puis passe à la phase suivante ou termine
	if (pv <= 0) {
	    phase5();
	} else {
	    phase4();
	}
    }

    // Phase 4 : Embuscade aléatoire avec différents événements
    public static void phase4() {
	pause();

	System.out.println("\n------------------------------");
	System.out.println("\nPhase 4 :\n");
	
	int randomEmbuscade = random.nextInt(6) + 1;
	switch (randomEmbuscade) {
	    case 1 -> {
		System.out.println("Une créature vous blesse avant de fuir...");
		pv -= 2;
		phase5();
	    }
	    case 6 -> {
		System.out.println("Tatatada !!!\nVous avez trouvé une potion +1 PV !");
		pv += 1;
		phase5();
	    }
	    default -> {
		System.out.println("Vous avancez tranquillement.");
		phase5();
	    }
	}
    }

    // Phase 5 : Résolution et affichage des statistiques du joueur
    public static void phase5() {
	pause();

	System.out.println("\n------------------------------");
	System.out.println("\nPhase 5 :\n");

	pause();
	
	// Vérifie si le joueur a encore des PV ; sinon, affiche le GAME OVER
	if (pv <= 0) {
	    coins = 0;
	    System.out.println("GAME OVER !");
	} else {
	    System.out.println("Voici vos statistiques :");
	    System.out.println("Vos points de vie : " + pv);
	    System.out.println("Vos coins : " + coins);
	    
	    pause();
	    phase1();  // Relance la première phase pour continuer l'aventure
	}
    }
}
