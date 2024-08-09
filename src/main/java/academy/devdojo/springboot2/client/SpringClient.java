package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Anime> entity = new RestTemplate ().getForEntity ( "http://localhost:8080/animes/{id}", Anime.class, 3 );
        log.info ( entity );

        Anime object = new RestTemplate ().getForObject ( "http://localhost:8080/animes/{id}", Anime.class, 3 );
        log.info ( object );

        ResponseEntity<List<Anime>> exchange = new RestTemplate ().exchange ( "http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>> () {
        } );
        log.info ( exchange );

//        Anime kingdom = Anime.builder().name ( "kindom"  ).build ();
//        Anime kingdomSaved = new RestTemplate ().postForObject ( "http://localhost:8080/animes", kingdom, Anime.class );
//        log.info ("saved anime {}", kingdomSaved );

        Anime samuraiX = Anime.builder ().name ( "Samurai-X" ).build ();
        ResponseEntity<Anime> samuraiSaved = new RestTemplate ().exchange ( "http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<> ( samuraiX, createdJsonHearders () ),
                Anime.class );
        log.info ( "saved anime {}", samuraiSaved );

        Anime animeToUpdate = samuraiSaved.getBody ();
        animeToUpdate.setName ( "Samurai-XY" );


        ResponseEntity<Void> samuraiUpdate = new RestTemplate ().exchange ( "http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<> ( animeToUpdate, createdJsonHearders () ),
                Void.class );
        log.info ( samuraiUpdate );

        ResponseEntity<Void> samuraiDelete = new RestTemplate ().exchange ( "http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,animeToUpdate.getId () );
        log.info ( samuraiDelete );

    }

    private static HttpHeaders createdJsonHearders() {
        HttpHeaders headers = new HttpHeaders ();
        headers.setContentType ( MediaType.APPLICATION_JSON );
        return headers;

    }

}
