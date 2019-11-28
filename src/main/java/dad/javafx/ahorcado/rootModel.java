package dad.javafx.ahorcado;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class rootModel {
	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> puntuaciones = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private StringProperty adivinar = new SimpleStringProperty();
	private StringProperty fallos = new SimpleStringProperty();
	private StringProperty intento = new SimpleStringProperty();
	private StringProperty puntos = new SimpleStringProperty();
	
	
	public final ListProperty<String> palabrasProperty() {
		return this.palabras;
	}
	

	public final ObservableList<String> getPalabras() {
		return this.palabrasProperty().get();
	}
	

	public final void setPalabras(final ObservableList<String> palabras) {
		this.palabrasProperty().set(palabras);
	}


	public final ListProperty<String> puntuacionesProperty() {
		return this.puntuaciones;
	}
	


	public final ObservableList<String> getPuntuaciones() {
		return this.puntuacionesProperty().get();
	}
	


	public final void setPuntuaciones(final ObservableList<String> puntuaciones) {
		this.puntuacionesProperty().set(puntuaciones);
	}


	public final StringProperty adivinarProperty() {
		return this.adivinar;
	}
	


	public final String getAdivinar() {
		return this.adivinarProperty().get();
	}
	


	public final void setAdivinar(final String adivinar) {
		this.adivinarProperty().set(adivinar);
	}


	public final StringProperty fallosProperty() {
		return this.fallos;
	}
	


	public final String getFallos() {
		return this.fallosProperty().get();
	}
	


	public final void setFallos(final String fallos) {
		this.fallosProperty().set(fallos);
	}


	public final StringProperty intentoProperty() {
		return this.intento;
	}
	


	public final String getIntento() {
		return this.intentoProperty().get();
	}
	


	public final void setIntento(final String intento) {
		this.intentoProperty().set(intento);
	}


	public final StringProperty puntosProperty() {
		return this.puntos;
	}
	


	public final String getPuntos() {
		return this.puntosProperty().get();
	}
	


	public final void setPuntos(final String puntos) {
		this.puntosProperty().set(puntos);
	}
	
	
	
	
	
	
}
