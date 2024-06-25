package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Anime>entity = new RestTemplate ().getForEntity ( "http://localhost:8080/animes/{id}", Anime.class,5 );

        log.info ( entity );


        Anime forObject = new RestTemplate ().getForObject ( "http://localhost:8080/animes/{id}", Anime.class,8 );

        log.info ( forObject );



        ResponseEntity<List<Anime>> exchange = new RestTemplate ().exchange ( "http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        } );

        log.info ( exchange.getBody () );

    }


}
