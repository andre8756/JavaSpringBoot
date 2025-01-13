package br.com.andre.springJava.principal;

import br.com.andre.springJava.model.DadosEpisodio;
import br.com.andre.springJava.model.DadosSerie;
import br.com.andre.springJava.model.DadosTemporada;
import br.com.andre.springJava.model.Episodio;
import br.com.andre.springJava.service.ConsumoAPI;
import br.com.andre.springJava.service.ConverteDados;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para a busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season="+i+ API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        for(int i = 0; i < dados.totalTemporadas(); i ++){
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for(int j = 0; j<episodiosTemporada.size(); j++){
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
            //funcao lambda
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(n -> n.startsWith("N"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("Top 5 episódios");
        dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()).limit(5).forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios ?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null & e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(" Temporada: "+e.getTemporada()+
                        " Episódio: "+e.getTitulo()+
                        " Data Lançamento: "+e.getDataLancamento().format(formatador)));


    }
}
