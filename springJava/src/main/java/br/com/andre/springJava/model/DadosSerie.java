package br.com.andre.springJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // tudo que não definimos ele não converte

public record DadosSerie(@JsonAlias("Title") String titulo,                                 //extraindo os dados: título,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,                // total temporadas e
                         @JsonAlias("imdbRating") String avaliacao) {                       // avaliacao do json

    

}
