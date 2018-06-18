package introsde.spotifylayer.soap.ws;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

//import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONObject;

import introsde.spotifylayer.soap.model.*;
//Service Implementation
@WebService(
		endpointInterface = "introsde.spotifylayer.soap.ws.Spoti"
		) 
public class SpotiImpl implements Spoti {
	//configurations
	static ClientConfig clientConfig = new ClientConfig();
	static Client client = ClientBuilder.newClient(clientConfig);
	static WebTarget service = client.target("https://api.spotify.com");
	static String access_token="";

	//authenticator method
	public static void authenticator() {
		byte[] encodedBytes = Base64.getEncoder().encode("*CENSURED*".getBytes());
		//System.out.println("encodedBytes " + new String(encodedBytes));
		WebTarget athservice=client.target("https://accounts.spotify.com");
		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("grant_type","client_credentials");
		Response response=athservice.path("/api/token").request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Basic "+new String(encodedBytes)).post(Entity.form(formData));
		//System.out.println(response.readEntity(JSONObject.class));
		/*String s=response.readEntity(String.class).toString();
		System.out.println(s);*/
		JSONObject jsonresponse=response.readEntity(JSONObject.class);
		access_token=(String)jsonresponse.get("access_token");
	}
	
	@Override
	public Artist searchArtist(String name) {
		Artist a=new Artist();
		Response response;
		authenticator();
		response=service.path("/v1/search").queryParam("q", name).queryParam("type", "artist").queryParam("limit", 1).request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+access_token).get();
		JSONObject jsonresponse=response.readEntity(JSONObject.class);
		LinkedHashMap lhm=(LinkedHashMap)jsonresponse.get("artists");
		ArrayList<LinkedHashMap> items=(ArrayList<LinkedHashMap>)lhm.get("items");
		a.setName((String)items.get(0).get("name"));
		a.setIdArtist((String)items.get(0).get("id"));
		ArrayList<String> genres=(ArrayList<String>)items.get(0).get("genres");
		System.out.println(genres);
		return a;
	}

	@Override
	public Song searchSong(String name) {
		Song s=new Song();
		Response response;
		authenticator();
		response=service.path("/v1/search").queryParam("q", name).queryParam("type", "track").queryParam("limit", 1).request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+access_token).get();
		//System.out.println(response.readEntity(String.class));
		JSONObject jsonresponse=response.readEntity(JSONObject.class);
		//System.out.println(jsonresponse);
		LinkedHashMap lhm=(LinkedHashMap)jsonresponse.get("tracks");
		ArrayList<LinkedHashMap> items=(ArrayList<LinkedHashMap>)lhm.get("items");
		s.setName((String)items.get(0).get("name"));
		s.setIdSong((String)items.get(0).get("id"));
		s.setArtistName((String)((ArrayList<LinkedHashMap>)((LinkedHashMap)items.get(0).get("album")).get("artists")).get(0).get("name"));
		return s;
	}

	@Override
	public List<Song> getTopTrackByArtist(String id) {
		List<Song> returnList=new ArrayList<Song>();
		authenticator();
		Response response=service.path("/v1/artists/"+id+"/top-tracks").queryParam("country", "IT").request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+access_token).get();
		//System.out.println(response.readEntity(String.class));
		JSONObject jsonresponse=response.readEntity(JSONObject.class);
		ArrayList<LinkedHashMap> lhm=(ArrayList<LinkedHashMap>)jsonresponse.get("tracks");
		for (int i=0;i<lhm.size();i++) {
			Song s=new Song();
			s.setName((String)lhm.get(i).get("name"));
			s.setIdSong((String)lhm.get(i).get("id"));
			returnList.add(s);
		}
		return returnList;
	}

	@Override
	public List<Song> getRecommendation(String artistSeeds, String songSeeds) {
		authenticator();
		Response response=service.path("/v1/recommendations").queryParam("seed_artists",artistSeeds).queryParam("seed_tracks", songSeeds).queryParam("limit", 5).request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+access_token).get();
		//System.out.println(response.readEntity(String.class));
		//GET https://api.spotify.com/v1/recommendations
		List<Song> returnList=new ArrayList<Song>();
		JSONObject jsonresponse=response.readEntity(JSONObject.class);
		ArrayList<LinkedHashMap> lhm=(ArrayList<LinkedHashMap>)jsonresponse.get("tracks");
		for (int i=0;i<lhm.size();i++) {
			Song s=new Song();
			s.setArtistName((String)((ArrayList<LinkedHashMap>)lhm.get(i).get("artists")).get(0).get("name"));
			s.setName((String)lhm.get(i).get("name"));
			s.setIdSong((String)lhm.get(i).get("id"));
			returnList.add(s);
		}
		return returnList;
	}

	

	/*@Override
	public int updatePersonHP(int id, LifeStatus hp) {
		LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
		if (ls.getPerson().getIdPerson() == id) {
			LifeStatus.updateLifeStatus(hp);
			return hp.getIdMeasure();
		} else {
			return -1;
		}
	}*/

}
