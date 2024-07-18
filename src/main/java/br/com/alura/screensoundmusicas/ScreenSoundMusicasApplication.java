package br.com.alura.screensoundmusicas;

import br.com.alura.screensoundmusicas.main.Main;
import br.com.alura.screensoundmusicas.repository.ArtistRepository;
import br.com.alura.screensoundmusicas.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenSoundMusicasApplication implements CommandLineRunner {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private SongRepository songRepository;

    public static void main(String[] args) {
        SpringApplication.run(ScreenSoundMusicasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var main = new Main(artistRepository, songRepository);

        main.execute();
    }
}
