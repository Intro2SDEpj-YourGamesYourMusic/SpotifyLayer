package introsde.spotifylayer.soap.endpoint;
import javax.xml.ws.Endpoint;
import introsde.spotifylayer.soap.ws.SpotiImpl;
//Endpoint publisher
public class SpotiPublisher{
    public static void main(String[] args) {
       Endpoint.publish("http://localhost:6900/ws/hello", new SpotiImpl());
    }
}