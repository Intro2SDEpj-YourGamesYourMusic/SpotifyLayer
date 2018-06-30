## Denis Gallo (194441) | denis.gallo@studenti.unitn.it
**Heroku Host URL:** https://sdeproject-spotilayer.herokuapp.com/  
**Heroku WSDL URL:** https://sdeproject-spotilayer.herokuapp.com/spoti?wsdl  
**Github repositories of the project:**  
https://github.com/Intro2SDEpj-YourGamesYourMusic  
I worked with Mattia Buffa.  

## SpotifyLayer

This service comunicates with the official Spotify API in order to handle simple requests like searching for artists or songs or the harder request of getting recommendations based on some seeds.

### Description of the tasks

This service contains one main package **introsde.spotifylayer.soap** which is then divided into 4 packages **client**, **endpoint**, **model**, **ws**.  
Endpoint (containing **SpotiPublisher.java**) is only needed for local testing and debugging.  
Initially the **SpotiClient.java** (in client) was used to test locally the functionality of the services (in combination with the publisher), but after pushing the SpotifyLayer on heroku this client was used also to test if it worked correctly.  
Model contains the two models used in this layer: **Artist.java** and **Song.java**. They are described in the description of the project. 
The ws package is the main part of this service. **Spoti.java** is the interface, containing the definition of each method. The implemented methods are four:  
-searchArtist: returns an artist given a name  
-searchSong: returns a song given a name  
-getTopTracksByArtist: returns 5 songs given an artist's ID (handled by spotify)  
-getRecommendation: returns 5 songs given up to 5 seeds of artists or songs (can be combined, like 2 artists and 3 songs or 1 artist and 4 songs). Seeds are basically IDs.  
The implementation of these methods (contained in **SpotiImpl.java**) are explained in the following chapter.  
**build.xml** and **ivy.xml** are almost the same used during the soap classes.  
In WebContent/WEB-INF/ there are two xml files **web.xml** and **sun-jaxws.xml** used to describe where the web services would be placed on heroku (/spoti). This technique was already used for the assignment3 to solve some weird errors which made disappear the web service on heroku.  
Credits to: https://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat/  


### Description of the code

The following description is only about the **SpotiImpl.java** class since the previously mentioned classes do not require further explanations. The authenticator method is required to access to the spotify api, it is basically a unique identifier to allow spotify to monitor which "app" or "company" is accessing them data. This method is just a copy-paste from the Spotify API guide in order to get the authentication which is stored globally in access_token.  
From now on every request is made to api.spotify.com with a REST request on a specific path and with additional query parameters (checkable on their api documentation).  
**searchArtist** is a GET on /v1/search?type=artist&limit=1 sending in the body the access_token obtained above. It is limited to one since it returns a list of artists based on the search but it is assumed that the first one is correct (for simplicity).  
**searchSong** does the same with type=track.  
**getTopTrackByArtist** is a GET on /v1/artists/artistID/top-tracks where artistID is received as input on the method (it therefore requires that other services searched first for an artist in order to get its ID). A query parameter (?country=IT) is also required in order to get the most popular songs in a certain region. As always the access_token is delivered in the body of the request.  
**getRecommendation** is a GET request on /v1/recommendations with query parameters that can be "seed_artists" and "seed_tracks". A maximum of 5 seeds can be delivered to the Spotify API independently of the type of the seed (e.g. 2 artists, 3 songs or 1 artist, 4 songs). artistSeeds and songSeeds are strings of IDs separated by commas.  
For every request a JSON is returned, containing the expected information. The JSONObject class parses the Response and with some iteration on the JSON document, the important information are stored and returned (spotify returns A LOT of information about artists and songs that are not needed in this project).  


## Execution
Each of the 4 methods is callable from another service and returns the required information. The authenticator method is not callable and it is used by every method in order to get the access to the Spotify API (an access_token expires every 30 minutes). There is no much error handling here so it is possible that searching for wrong or non-existent artists or songs lead to a fault. This can be handled by a try-catch at the service that is calling SpotifyLayer. It isn't elegant but simple. 
