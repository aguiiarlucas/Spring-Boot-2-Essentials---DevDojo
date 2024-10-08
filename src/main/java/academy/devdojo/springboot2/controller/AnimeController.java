package academy.devdojo.springboot2.controller;


import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@AllArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;
    private final AnimeRepository animeRepository;

    //    @GetMapping
//    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
//        log.info ( dateUtil.formatLocalDateTimeToDatabaseStyle ( LocalDateTime.now () ) );
//        return ResponseEntity.ok ( animeService.listAll (pageable) );
//
//    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        log.info ( dateUtil.formatLocalDateTimeToDatabaseStyle ( LocalDateTime.now () ) );
        return ResponseEntity.ok ( animeService.listAllNonPageable () );

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok ( animeService.findByIdOrThrowBadRequestException ( id ) );

    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok ( animeService.findByName ( name ) );

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anime save(@Valid @RequestBody AnimePostRequestBody animePostRequestBody) {
        return  animeRepository.save ( (AnimeMapper.INSTANCE.toAnime ( animePostRequestBody )) );

}

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete ( id );
        return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.update ( animePutRequestBody );
        return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
    }
}