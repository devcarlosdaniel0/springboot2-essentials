package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("anime")
public class AnimeController {
    //localhost:8080/anime/list
    @GetMapping(path = "list")
    public List<Anime> list() {
        return List.of(new Anime("DBZ"), new Anime("Beserk"));
    }

    // Fora do curso - só pra testar
    @GetMapping(path = "set")
    public Set<Anime> set() {
        HashSet<Anime> animeHashSet = new HashSet<>();
        animeHashSet.add(new Anime("Hajime no Ippo"));
        animeHashSet.add(new Anime("Boku no Hero"));
        return animeHashSet;
    }
}
