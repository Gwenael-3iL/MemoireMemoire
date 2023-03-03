package application;

public class Carte {
	String nom;
	String image;
	
	public Carte(String nom, String image) {
		this.nom = nom;
		this.image = image;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getImage() {
		return this.image;
	}
}
