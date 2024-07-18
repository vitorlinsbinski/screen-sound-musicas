package br.com.alura.screensoundmusicas.model;

public enum ArtistCategory {
    SOLO,
    DUPLA,
    BANDA;

    public static ArtistCategory fromString(String text) {
        for(ArtistCategory artistCategory : ArtistCategory.values()) {
            if(artistCategory.toString().equalsIgnoreCase(text)) {
                return artistCategory;
            }
        }

        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
