package org.pokemon.pikapi.controler;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.pokemon.pikapi.dto.ReponseDTO;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.pokemon.pikapi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@RestController("/pokemon")
public class PokemonControler {


	@Autowired
	private PokemonService pokemonService;

	@GetMapping("/{numero}")
	public CompletionStage<ReponseDTO<Pokemon>> getPokemon(Integer number){
	/**
				getAndControlePokemon()
				Thread.sleep(new Random().nextInt(10000) + 1000);
				return ReponseDTO.reponse(pokemon);

				return ReponseDTO.error(e.getMessage());
			**/
		return null;
	}


	@GetMapping("/legends/")
	public CompletableFuture<ReponseDTO<List<Integer>>> getLegendsPokemon( ){
		return CompletableFuture.supplyAsync(() ->
			 ReponseDTO.reponse( pokemonService.getAllLegendaryPokemon()
						.stream()
						.map(Pokemon::getNumber)
						.collect(Collectors.toList()))
		);
	}

	@Deprecated
	private Pokemon getAndControlePokemon(Integer number) throws IllegalArgumentException  {
		return pokemonService.getPokemon(number)
						.orElseThrow(() -> new IllegalArgumentException(String.format("Ce numéro de pokemon %d est inconnu",number)));
	}





}
