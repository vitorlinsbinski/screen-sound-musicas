package br.com.alura.screensoundmusicas.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ArtistCategory artistCategory;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();

    public Artist() {

    }

    public Artist(String name, ArtistCategory artistCategory) {
        this.name = name;
        this.artistCategory = artistCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        songs.forEach(s -> s.setArtist(this));
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", artistCategory=" + artistCategory +
                ", songs=" + songs +
                '}';
    }
}
