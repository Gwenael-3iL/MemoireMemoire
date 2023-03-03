package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PremierVue.fxml"));
			Scene scene = new Scene(root,1600,1000);
			//root.setStyle("-fx-background-image:url('plateau.jpg'); -fx-background-repeat: no-repeat; -fx-background-size: cover; -fx-background-position: center center;");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Mémoire Mémoire - Le Jeu");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() throws Exception {
		super.init();
	}
	
	@Override
	public void stop() throws Exception {
		super.init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
