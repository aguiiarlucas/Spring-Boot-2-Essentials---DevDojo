package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exceptions.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AnimeService {

    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll ( pageable );
    }


    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll ();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName ( name );
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById ( id )
                .orElseThrow ( () -> new BadRequestException ( "Id not found " ) );
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save ( AnimeMapper.INSTANCE.toAnime ( animePostRequestBody ) );
    }

    public void update(AnimePutRequestBody animePutRequestBody) {
        Anime existingAnime = findByIdOrThrowBadRequestException (animePutRequestBody.getId());
        Anime updatedAnime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        updatedAnime.setId(existingAnime.getId());  // Garantindo que o ID não seja alterado
        animeRepository.save(updatedAnime);
    }

    public void delete(long id) {
        animeRepository.delete ( findByIdOrThrowBadRequestException ( id ) );
    }


}
