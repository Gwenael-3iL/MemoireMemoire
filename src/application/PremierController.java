package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PremierController {
	private List<Button> listeCarte = new ArrayList<Button>();
	private List<Button> listeCarteRetournee = new ArrayList<Button>();
	private List<Button> listePairesValides = new ArrayList<Button>();
	private List<String> couleur = new ArrayList<String>();
	
	private final int TEMPS_DEFAUT = 10;
	private int tempsPartie = TEMPS_DEFAUT;
	private int scoreJoueur = 0;
	private boolean partieFinie = false;
	private Timeline timerJeu;
	
	@FXML
	private Button carte1;
	@FXML
	private Button carte2;
	@FXML
	private Button carte3;
	@FXML
	private Button carte4;
	@FXML
	private Button carte5;
	@FXML
	private Button carte6;
	@FXML
	private Button carte7;
	@FXML
	private Button carte8;
	@FXML
	private Button relancer;
	
	@FXML
	private Label score;
	@FXML
	private Label etatPartie;
	@FXML
	private Label timer;
	
	@FXML
	public void initialize() {
		// Initialiser le timer
		timer.setText(Integer.toString(TEMPS_DEFAUT) + " sec");
		timerJeu = new Timeline(new KeyFrame(Duration.millis(1000), ae -> afficherTimer()));
		timerJeu.setCycleCount(TEMPS_DEFAUT);
		timerJeu.play();
		
		// Initialiser toutes les cartes par défaut
		Image img = new Image("defaut.png");
		
		// Mélanger les couleurs 
		couleur.add("Rouge");
		couleur.add("Rouge");
		couleur.add("Jaune");
		couleur.add("Jaune");
		couleur.add("Vert");
		couleur.add("Vert");
		couleur.add("Bleu");
		couleur.add("Bleu");
		Collections.shuffle(couleur);
		
		// Créer la liste de cartes de dos
		listeCarte.add(carte1);
		listeCarte.add(carte2);
		listeCarte.add(carte3);
		listeCarte.add(carte4);
		listeCarte.add(carte5);
		listeCarte.add(carte6);
		listeCarte.add(carte7);
		listeCarte.add(carte8);
		for (Button carte : listeCarte) {
			carte.setGraphic(new ImageView(img));
		    carte.setContentDisplay(ContentDisplay.TOP);
		}
	}
	
	/**
	 * Actualise la partie quand le temps est écoulé
	 * - Change le label d'état de la partie
	 * - Fige l'état du jeu
	 */
	private void tempsEcoule() {
		etatPartie.setText("PERDU ! TEMPS ÉCOULÉ !");
		for (Button carte : listeCarte) {
			carte.setDisable(true);
		}
	}
	
	/**
	 * Actualise le timer de la partie
	 * Si la partie est en cours actualiser le temps
	 * Si le temps est écoulé -> fin de partie
	 * Si la partie est terminée, figer le temps
	 */
	private void afficherTimer( ) {	
		if (!partieFinie) {
			tempsPartie--;
			timer.setText(Integer.toString(tempsPartie) + " sec");
			if (tempsPartie == 0)
				tempsEcoule();
		} else {
			timer.setText(tempsPartie + " sec");
		}	
	}
	
	/**
	 * Traitement d'une carte
	 * @param carte sélectionnée par le joueur
	 * @param name nom de la carte
	 */
	private void traitementCarte(Button carte, String nom, String image) {
		// Retourner la carte
		carte.setText(nom);
		Image img = new Image(image);
	    ImageView view = new ImageView(img);
	    carte.setGraphic(view);
	    carte.setContentDisplay(ContentDisplay.TOP);
		
		// Désactiver le bouton (empêcher de sélectionner 2 fois la même carte)
		carte.setDisable(true);
		carte.setOpacity(50);
		
		// Ajouter à la liste si size < 3
		if (listeCarteRetournee.size() < 3)
			listeCarteRetournee.add(carte);
		
		// Si deux cartes sont déjà retournées et qu'une 3ème est retournée
		if (listeCarteRetournee.size() == 3) {
			// Retourner les anciennes cartes sauf si paire valide
			if (!listePairesValides.contains(listeCarteRetournee.get(0)) 
					&& !listePairesValides.contains(listeCarteRetournee.get(1))) {
				Image imgDef = new Image("defaut.png");
				
				listeCarteRetournee.get(0).setText("Carte");
				listeCarteRetournee.get(0).setDisable(false);
				listeCarteRetournee.get(0).setGraphic(new ImageView(imgDef));
				listeCarteRetournee.get(0).setContentDisplay(ContentDisplay.TOP);
				
				listeCarteRetournee.get(1).setText("Carte");
				listeCarteRetournee.get(1).setDisable(false);
				listeCarteRetournee.get(1).setGraphic(new ImageView(imgDef));
				listeCarteRetournee.get(1).setContentDisplay(ContentDisplay.TOP);
				
				// Actualiser le score
				score.setText(Integer.toString(--scoreJoueur));
			}
			
			// Supprimer de la liste
			listeCarteRetournee.clear();
			listeCarteRetournee.add(carte);
		}
		
		// Si liste count == 2, comparer les cartes
		if (listeCarteRetournee.size() == 2) {
			// Si deux cartes identiques, valider la paire
			if (listeCarteRetournee.get(0).getText().equals(listeCarteRetournee.get(1).getText())) {
				// Ajouter les paires valides
				listePairesValides.add(listeCarteRetournee.get(0));
				listePairesValides.add(listeCarteRetournee.get(1));
				// Incrémenter le score
				score.setText(Integer.toString(++scoreJoueur));
				// Si toutes les cartes sont retournées
				if (listePairesValides.size() == 8) {   
					etatPartie.setText("Vous avez gagné ! Barvo !!!");
					partieFinie = true;
				}
			} 
		}
	}
	
	@FXML
	private void clicCarte(ActionEvent evt) throws InterruptedException {
		// Varifier que l'élément cliqué est bien un bouton
		if (evt.getSource() instanceof Button) {
			Button button = (Button) evt.getSource();
			String buttonName = button.getId();
			
			// Réaliser l'action en fonction de la carte sélectionnée
			switch(buttonName) {
			case "carte1" :
				traitementCarte(carte1, couleur.get(0), couleur.get(0)+".png");
				break;
			case "carte2" :
				traitementCarte(carte2, couleur.get(1), couleur.get(1)+".png");
				break;
			case "carte3" :
				traitementCarte(carte3, couleur.get(2), couleur.get(2)+".png");
				break;
			case "carte4" :
				traitementCarte(carte4, couleur.get(3), couleur.get(3)+".png");
				break;
			case "carte5" :
				traitementCarte(carte5, couleur.get(4), couleur.get(4)+".png");
				break;
			case "carte6" :
				traitementCarte(carte6, couleur.get(5), couleur.get(5)+".png");
				break;
			case "carte7" :
				traitementCarte(carte7, couleur.get(6), couleur.get(6)+".png");
				break;
			case "carte8" :
				traitementCarte(carte8, couleur.get(7), couleur.get(7)+".png");
				break;
			}
		}
	}
	
	@FXML
	private void clicRelancer(ActionEvent evt) {
		// Réinitialiser le score
		scoreJoueur = 0;
		score.setText(Integer.toString(0));
		
		// Set etatPartie
		etatPartie.setText("Partie en cours !");
		partieFinie = false;
		
		// Réinitialiser le timer
		tempsPartie = TEMPS_DEFAUT;
		timerJeu.stop();
		timerJeu = new Timeline(new KeyFrame(Duration.millis(1000), ae -> afficherTimer()));
		timerJeu.setCycleCount(tempsPartie);
		timerJeu.play();
		
		// Réintialiser les listes
		listeCarteRetournee.clear();
		listePairesValides.clear();
		Collections.shuffle(couleur);
		
		// Retourner toutes les cartes
		Image img = new Image("defaut.png");
		for (Button carte : listeCarte) {
			carte.setDisable(false);
			carte.setText("Carte");
			carte.setGraphic(new ImageView(img));
			carte.setContentDisplay(ContentDisplay.TOP);
		}
	}
}
