package br.com.alura.screensoundmusicas.repository;

import br.com.alura.screensoundmusicas.model.Artist;
import br.com.alura.screensoundmusicas.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
}
