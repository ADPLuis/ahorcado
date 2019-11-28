package dad.javafx.ahorcado;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class partida {
	private int vidaactual;
	private int puntuacion;
	private String nombrejugador;
	private String palabrasecreta;
	private int ahorcado;
	
	public int getAhorcado() {
		return ahorcado;
	}

	public void setAhorcado(int ahorcado) {
		this.ahorcado = ahorcado;
	}

	public partida(){
		vidaactual = 8;
		puntuacion = 0;
		ahorcado = 2;
		TextInputDialog dial = new TextInputDialog();
		dial.setHeaderText("Jugador");
		dial.setContentText("Introduce tu nombre: ");
		Optional<String> res = dial.showAndWait();
		nombrejugador = res.get();
		
	}

	public int getVidaactual() {
		return vidaactual;
	}

	public void setVidaactual(int vidaactual) {
		this.vidaactual = vidaactual;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getNombrejugador() {
		return nombrejugador;
	}

	public void setNombrejugador(String nombrejugador) {
		this.nombrejugador = nombrejugador;
	}

	public String getPalabrasecreta() {
		return palabrasecreta;
	}

	public void setPalabrasecreta(String palabrasecreta) {
		this.palabrasecreta = palabrasecreta;
	}
	
}
