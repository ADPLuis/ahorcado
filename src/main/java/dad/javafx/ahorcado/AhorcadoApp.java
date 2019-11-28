package dad.javafx.ahorcado;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AhorcadoApp extends Application {
	private rootController control;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		control = new rootController();
		control.empezarjuego();
		
		Scene scene = new Scene(control.getrootview(), 620, 480);
		
		primaryStage.setTitle("Ahorcado");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void init() throws Exception {
		super.init();
	}
	
	public void stop() throws Exception {
		super.stop();
		control.puntuacionesguardar();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
