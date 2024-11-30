package br.com.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){

        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine().replace(" ", "+");
        var json = consumo.obterDados(ENDERECO + nomeSerie + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

    	List<DadosTemporada> temporadas = new ArrayList<>();
		for(int i=1; i<dados.totalTemporadas(); i++){
			json = consumo.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&season="+i+"&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		//temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }

}
