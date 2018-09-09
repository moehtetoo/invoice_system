package com.acm.javafx.invoicesystem.view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Rectangle2D bound= Screen.getPrimary().getVisualBounds();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root,bound.getWidth()-30,bound.getHeight()-30);
			if(bound.getWidth()>1366) {
				scene.getStylesheets().add(getClass().getResource("fullResolution.css").toExternalForm());
			}else {
				scene.getStylesheets().add(getClass().getResource("normalResolution.css").toExternalForm());
			}
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/icon.jpg")));
			primaryStage.isFocused();
			primaryStage.setTitle("ZTT System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
