package introsde.spotifylayer.soap.model;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="song")
@XmlAccessorType(XmlAccessType.FIELD)
public class Song implements Serializable{

	private static final long serialVersionUID = -3461577826003516075L;
	
	@XmlAttribute
	private String idSong;
	
	
	private String name;
	
	
	private String artistName;
	
	public Song() {}
	
	public Song(String id, String name, String artist) {
		this.idSong=id;
		this.name=name;
		this.artistName=artist;
	}

	public String getIdSong() {
		return idSong;
	}

	public void setIdSong(String idSong) {
		this.idSong = idSong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	
}
