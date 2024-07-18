package br.com.alura.screensoundmusicas.repository;

import br.com.alura.screensoundmusicas.model.Artist;
import br.com.alura.screensoundmusicas.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByNameContainingIgnoreCase(String name);
    @Query("SELECT s from Artist a JOIN a.songs s WHERE a.name ILIKE %:name%")
    List<Song> findSongsByArtist(String name);
}
