package introsde.spotifylayer.soap.client;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import introsde.spotifylayer.soap.model.Artist;
import introsde.spotifylayer.soap.model.Song;
import introsde.spotifylayer.soap.ws.Spoti;

public class SpotiClient {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://sdeproject-spotilayer.herokuapp.com/spoti?wsdl");
        // 1st argument service URI, refer to wsdl document above
        // 2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.soap.spotifylayer.introsde/", "SpotiImplService");
        Service service = Service.create(url, qname);
        Spoti spoti = service.getPort(Spoti.class);
      //search an artist and get his name and id (and genres [NOT ID! CARE])
      		Artist a=spoti.searchArtist("Avicii");
      		System.out.println(a.getIdArtist()+" "+a.getName());
      		System.out.println();
      		
      		//search a song and get artist, name of the song and id of the song
      		Song s=spoti.searchSong("Everytime we touch");
      		System.out.println(s.getIdSong()+" "+s.getArtistName()+" "+s.getName());
      		System.out.println();
      		
      		//doesnt return anything, just has a list of available genre ids, could be useful for recommendation (not implemented)
      		System.out.println();
      		
      		//returns the top songs (name and id) of a given artist id
      		List<Song> songList=spoti.getTopTrackByArtist("1vCWHaC5f2uS3yhpwWbIA6");
      		for(Song song:songList)
      			System.out.println(song.getIdSong()+" "+song.getName()+" "+song.getArtistName());
      		System.out.println();
      		
      		//recommendation method, you can give a total of 5 ids between artists, songs (and genres [not implemented]), returns 5 songs with artist, songname and songid
      		songList=spoti.getRecommendation("1vCWHaC5f2uS3yhpwWbIA6", "5YnKmPZytgkywuHktntXDV,0JhKJg5ejeQ8jq89UQtnw8");
      		for(Song song:songList)
      			System.out.println(song.getIdSong()+" "+song.getName()+" "+song.getArtistName());
      		
    }
}