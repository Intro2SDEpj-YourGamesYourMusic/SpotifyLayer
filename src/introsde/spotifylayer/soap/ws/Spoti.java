package introsde.spotifylayer.soap.ws;
import introsde.spotifylayer.soap.model.*;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface Spoti {
	
    @WebMethod(operationName="searchArtist")
    @WebResult(name="artist") 
    public Artist searchArtist(@WebParam(name="artistName") String name);
    
    @WebMethod(operationName="searchSong")
    @WebResult(name="song") 
    public Song searchSong(@WebParam(name="songName") String name);

    @WebMethod(operationName="getTopTracksByArtist")
    @WebResult(name="songs") 
    public List<Song> getTopTrackByArtist(@WebParam(name="artistId") String id);

    @WebMethod(operationName="getRecommendation")
    @WebResult(name="songs") 
    public List<Song> getRecommendation(@WebParam(name="artistSeeds") String artistSeeds, @WebParam(name="songSeeds") String songSeeds);
 
}
