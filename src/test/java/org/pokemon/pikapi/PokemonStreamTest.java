package org.pokemon.pikapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.pokemon.pikapi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class PokemonStreamTest {
	@Autowired
	private PokemonService service;

	@Test
	public void exercice1() {
		/** Réunir l'ensemble des données dans une liste*/
		final List<Pokemon> allPokemon = service.getAllPokemon();
		Assertions.assertEquals(allPokemon.size(),800);
	}
	@Test
	public void exercice2() {
		/** Retourne les pokemons legendaire*/
		final List<Pokemon> allPokemon = service.getAllLegendaryPokemon();
		Assertions.assertEquals(allPokemon.size(),65);
	}

	@Test
	public void exercice3() {
		/** Retourne les types des pokemons legendaire*/
		final List<String> types = service.getAllTypeOfLegendaryPokemon();
		Assertions.assertEquals(types.size(),16);
	}

	@Test
	public void exercice4() {
		/** Retourne le nom des pokemons les plus rapide*/
		final List<String> names = service.getFastestPokemon(3);
		Assertions.assertEquals(names,List.of("Shuckle", "Munchlax", "Trapinch"));
	}


	@Test
	public void exercice5() {
		/** Retourne un pokemon fusionné*/
		final Pokemon pokemon = service.getFusionPokemon(2,25);
		Assertions.assertEquals(pokemon.getName(),"Ivyachu");
		final Pokemon pokemon1 = service.getFusionPokemon(6,19,30);
		Assertions.assertEquals(pokemon1.getName(),"Charrina");
	}

}
