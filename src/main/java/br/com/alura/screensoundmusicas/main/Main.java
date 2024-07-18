package br.com.alura.screensoundmusicas.main;

import br.com.alura.screensoundmusicas.model.Artist;
import br.com.alura.screensoundmusicas.model.ArtistCategory;
import br.com.alura.screensoundmusicas.model.Song;
import br.com.alura.screensoundmusicas.repository.ArtistRepository;
import br.com.alura.screensoundmusicas.repository.SongRepository;
import br.com.alura.screensoundmusicas.service.ConsultaChatGPT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private List<Song> songsFetched = new ArrayList<>();
    private Optional<Artist> artistFetched;
    private ArtistRepository artistRepository;
    private SongRepository songRepository;

    public Main(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    public void execute() {
        var option = -1;

        while(option != 9) {
            var menu = """
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas                                
                    4- Buscar músicas por artistas                                    
                    5- Pesquisar dados sobre um artista                     
                    9- Sair                              
                """;

            System.out.println(menu);
            option = this.scanner.nextInt();
            this.scanner.nextLine();

            switch (option) {
                case 1:
                    registerArtist();
                    break;
                case 2:
                    registerSongs();
                    break;
                case 3:
                    listSongs();
                    break;
                case 4:
                    listSongsByArtist();
                    break;
                case 5:
                    searchForArtistDetails();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public void registerArtist() {
        var option = "S";

        while(!option.equals("N")) {
            System.out.println("Digite o nome do artista:");
            var artistName = this.scanner.nextLine();

            System.out.println("Informe o tipo desse artista: (solo, dupla, " +
                    "banda)");
            var artistCategory = this.scanner.nextLine();
            ArtistCategory category = ArtistCategory.fromString(artistCategory);

            Artist artist = new Artist(artistName, category);

            this.artistRepository.save(artist);

            System.out.println("Cadastrar outro artista? (S/N)");
            option = this.scanner.nextLine();
        }

    }

    public void registerSongs() {
        var option = "S";

        findArtist();

        if(this.artistFetched.isEmpty()) {
            return;
        }

        Artist artist = this.artistFetched.get();

        while(!option.equals("N")) {
            System.out.printf("Digite o nome da música de %s\n",
                    this.artistFetched.get().getName());
            var songTitle = this.scanner.nextLine();

            System.out.printf("Digite o álbum da música de %s\n",
                    this.artistFetched.get().getName());
            var songAlbum = this.scanner.nextLine();

            Song song = new Song(songTitle, songAlbum);
            song.setArtist(artist);

            artist.getSongs().add(song);

            System.out.printf("Cadastrar outra música de %s? (S/N)\n",
                    this.artistFetched.get().getName());
            option = this.scanner.nextLine();
        }

        this.artistRepository.save(artist);
    }

    public void listSongs() {
        this.songsFetched = this.songRepository.findAll();

        this.songsFetched.forEach(song -> {
            System.out.printf("Música: '%s', álbum: '%s', artista: %s\n",
                    song.getTitle(), song.getAlbum(), song.getArtist().getName());
        });
    }

    public void findArtist() {
        System.out.println("Digite o nome do artista para busca");
        var artistName = this.scanner.nextLine();

        Optional<Artist> artist =
                this.artistRepository.findByNameContainingIgnoreCase(artistName);

        if(artist.isPresent()) {
            this.artistFetched = artist;
            System.out.printf("Artista %s encontrado!\n",
                    artist.get().getName());
        } else {
            this.artistFetched = Optional.empty();
            System.out.println("Artista não encontrado!");
        }
    }

    public void listSongsByArtist() {
        System.out.println("Buscar músicas de que artista? ");
        var name = this.scanner.nextLine();

        List<Song> songs = this.artistRepository.findSongsByArtist(name);

        songs.forEach(s -> {
            System.out.printf("%s - %s\n", s.getTitle(), s.getAlbum());
        });
    }

    public void searchForArtistDetails() {
        System.out.println("Pesquisar dados sobre qual artista?");
        var name = this.scanner.nextLine();
        var response = ConsultaChatGPT.obterInformacao(name);
        System.out.println(response.trim());
    }
}
