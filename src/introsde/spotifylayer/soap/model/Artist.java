package introsde.spotifylayer.soap.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="artist")
@XmlAccessorType(XmlAccessType.FIELD)
public class Artist implements Serializable{

	private static final long serialVersionUID = -445012277184098930L;

	@XmlAttribute
	private String idArtist;
	
	private String name;
	
	public Artist() {}
    public Artist(String name, String id) {
    	this.name=name;
    	this.idArtist=id;
    }
	public String getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(String idArtist) {
		this.idArtist = idArtist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
