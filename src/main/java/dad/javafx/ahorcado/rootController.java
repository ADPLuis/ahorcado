package dad.javafx.ahorcado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class rootController implements Initializable {
	private partida actual;
	// model
	rootModel modelo = new rootModel();
	// view

	@FXML
	private TabPane root;

	@FXML
	private Tab partidaTab;

	@FXML
	private BorderPane partidaView;

	@FXML
	private HBox puntuacionHBox;

	@FXML
	private VBox puntuacionVBox;

	@FXML
	private Label puntosLabel;

	@FXML
	private Label puntuacionLabel;

	@FXML
	private ImageView imagen;

	@FXML
	private Label adivinarLabel;

	@FXML
	private Label fallosLabel;

	@FXML
	private HBox partidabotonera;

	@FXML
	private TextField intentoText;

	@FXML
	private Button letraButton;

	@FXML
	private Button resolverButton;

	@FXML
	private Tab palabrasTab;

	@FXML
	private BorderPane palabrasView;

	@FXML
	private ListView<String> palabraslista;

	@FXML
	private VBox palabrasbotonera;

	@FXML
	private Button anadirButton;

	@FXML
	private Button quitarButton;

	@FXML
	private Tab puntuacionesTab;

	@FXML
	private ListView<String> puntuacionesView;

	public rootController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rootView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		// bindeos
		palabraslista.itemsProperty().bind(modelo.palabrasProperty());
		puntuacionesView.itemsProperty().bind(modelo.puntuacionesProperty());
		adivinarLabel.textProperty().bind(modelo.adivinarProperty());
		puntuacionLabel.textProperty().bind(modelo.puntosProperty());
		modelo.fallosProperty().bindBidirectional(fallosLabel.textProperty());
		modelo.intentoProperty().bindBidirectional(intentoText.textProperty());

		// botones
		anadirButton.setOnAction(e -> onAnadirButton(e));
		quitarButton.setOnAction(e -> onQuitarButton(e));
		letraButton.setOnAction(e -> onLetraAction(e));
		resolverButton.setOnAction(e -> onResolverAction(e));
	}
	
	private void onAnadirButton(ActionEvent e) {
		TextInputDialog dial = new TextInputDialog();
		dial.setHeaderText("Añadir una palabra");
		dial.setContentText("Introduce la palabra para añadir: ");
		Optional<String> res = dial.showAndWait();
		
		if (modelo.palabrasProperty().contains(res.get())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Palabra repetida.");
			alert.setContentText("La palabra que has intentado añadir ya figura en la lista.");
			alert.showAndWait();
		} else {
			modelo.palabrasProperty().add(res.get());
			try {				
				File f = new File("palabras.dat");
				StringBuilder palabras = new StringBuilder();
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				palabras.append(br.readLine());
				if (palabras.toString().equals("null")) {
					palabras = new StringBuilder();
				}
				br.close();
				fr.close();
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				palabras.append(res.get()+",");
				bw.write(palabras.toString());
				bw.flush();
				bw.close();
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		cargarPalabras();
		palabramisteriosa();
	}

	private void onQuitarButton(ActionEvent e) {
		modelo.palabrasProperty().remove(palabraslista.getSelectionModel().getSelectedIndex());
		try {
			
			File f = new File("palabras.dat");
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);

			ArrayList<String> list = new ArrayList<String>(FXCollections.observableArrayList());
			list.addAll(modelo.palabrasProperty().get());

			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i) + ",");
			}
			bw.flush();
			bw.close();
			fw.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	cargarPalabras();
	palabramisteriosa();
	}

	private void onLetraAction(ActionEvent e) {
		String intento = modelo.getIntento();
		char letra = intento.charAt(0);
		Boolean restarvida = true;
		String [] mist = modelo.getAdivinar().split(" ");
		for (int i = 0; i < actual.getPalabrasecreta().length(); i++) {
			if (letra == actual.getPalabrasecreta().charAt(i)) {
				mist[i] = ""+letra;
				restarvida = false;
			}
		}
		if (restarvida == true) {
			actual.setVidaactual(actual.getVidaactual()-1);
			String img = "/images/hangman/"+actual.getAhorcado()+".png";
			imagen.setImage(new Image(img));
			actual.setAhorcado(actual.getAhorcado()+1);
			StringBuilder fallos = new StringBuilder();
			fallos.append(modelo.getFallos());
			fallos.append(letra+" ");
			modelo.setFallos(fallos.toString());
			
		}
		
		StringBuilder test = new StringBuilder();
		for (int i = 0; i < mist.length; i++) {
			test.append(mist[i]+" ");
		}
		modelo.setAdivinar(test.toString());
		
		if (actual.getVidaactual()==0) {
			imagen.setImage(new Image("/images/hangman/9.png"));
			perder();
		}
		modelo.setIntento("");
	}

	private void onResolverAction(ActionEvent e) {
		String intento = modelo.getIntento();
		String adivinar = modelo.getAdivinar().trim();
		
		if (intento.equals(actual.getPalabrasecreta()) | adivinar.equals(actual.getPalabrasecreta())) {
			ganar();
		} else {
			perder();
		}
		modelo.setIntento("");
	}
	public void empezarjuego() {
		actual = new partida();
		this.cargarPalabras();
		this.puntuacionescargar();
		actual.setPalabrasecreta(this.palabramisteriosa());
	}
	private void perder() {
		this.puntuacionesguardar();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("¡RIP!");
		alert.setHeaderText("Has perdido.");
		alert.setContentText("Las puntuaciones se han guardado y la partida se reseteará.");
		alert.showAndWait();
		modelo.setAdivinar("");
		modelo.setFallos("Fallos: ");
		imagen.setImage(new Image("/images/hangman/1.png"));
		puntuacionescargar();
		empezarjuego();
		
	}
	private void ganar() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("¡HAS GANADO!");
		alert.setHeaderText("Enhorabuena, has adivinado la palabra.");
		alert.showAndWait();
		actual.setPuntuacion(actual.getPuntuacion()+1);
		modelo.setPuntos(""+actual.getPuntuacion());
		modelo.setAdivinar("");
		modelo.setFallos("Fallos: ");
		actual.setPalabrasecreta(palabramisteriosa());
		
	}
	
	private String palabramisteriosa() {
		ArrayList<String> listado = new ArrayList<String>(FXCollections.observableArrayList());
		listado.addAll(modelo.getPalabras());

		int random = (int) Math.floor(Math.random() * (listado.size()));
		
		int letrasPalabraSecreta = listado.get(random).length();
		StringBuilder incog = new StringBuilder();
				
		for (int i = 0; i < letrasPalabraSecreta; i++) {
			incog.append("_ ");
		}
		modelo.setAdivinar(incog.toString()); 

		return listado.get(random);

	}

	private void cargarPalabras() {
		File f = new File("palabras.dat");
		try {
			if (!f.exists()) {
				f.createNewFile();
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("canario,volcan,gofio,espatula,");
				bw.flush();
				bw.close();
				fw.close();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String lectorpalabras = br.readLine().trim();
			if (lectorpalabras.toString().equals("null")) {
				lectorpalabras ="canario,volcan,gofio,espatula,";
			}
			br.close();
			fr.close();
			String[] palabras = lectorpalabras.split(",");
			
			for (int i = 0; i < palabras.length; i++) {
				if(modelo.palabrasProperty().contains(palabras[i])) {
					
				}
				else {
					modelo.palabrasProperty().add(palabras[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void puntuacionesguardar() {
		File f = new File("puntuaciones.dat");
		try {
			f.createNewFile();
			StringBuilder puntuaciones = new StringBuilder();
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			puntuaciones.append(br.readLine());
			if (puntuaciones.toString().equals("null")) {
				puntuaciones = new StringBuilder();
			}
			br.close();
			fr.close();
			
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			puntuaciones.append(actual.getNombrejugador()+","+actual.getPuntuacion()+",");
			bw.write(puntuaciones.toString());
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void puntuacionescargar() {
		modelo.puntuacionesProperty().clear();
		File f = new File("puntuaciones.dat");
		try {
			f.createNewFile();
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String test = br.readLine();
			br.close();
			fr.close();
			if(test!=null && test.trim()!=null) {
				String [] datos = test.split(",");
				String [] jugadores = new String[datos.length/2];
				Integer [] puntos = new Integer[datos.length/2];
				int apuntador = 0;
				for (int i = 0; i < datos.length-1; i=i+2) {
					jugadores[apuntador] = datos[i];
					puntos[apuntador] = Integer.parseInt(datos[i+1]);
					apuntador++;
				}
				//Ordenar arrays
				String aux;
				int temp;
				for(int i=0; i < puntos.length-1; i++){  
					for(int j=0; j < (puntos.length-i-1); j++){  
						if(puntos[j] < puntos[j+1]){   
							temp = puntos[j];  
							puntos[j] = puntos[j+1];  
							puntos[j+1] = temp;  
							
							aux = jugadores[j];
							jugadores[j] = jugadores[j+1];
							jugadores[j+1] = aux;
							
						}  
					}  
		        }  
				for (int i = 0; i < puntos.length; i++) {
					modelo.puntuacionesProperty().add(jugadores[i]+"--"+puntos[i]);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TabPane getrootview() {
		return root;
	}
}
