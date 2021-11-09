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
		Assertions.assertEquals(795,allPokemon.size());
	}
	@Test
	public void exercice2() {
		/** Retourne les pokemons legendaire*/
		final List<Pokemon> allPokemon = service.getAllLegendaryPokemon();
		Assertions.assertEquals(65,allPokemon.size());
	}

	@Test
	public void exercice3() {
		/** Retourne les types des pokemons legendaire*/
		final List<String> types = service.getAllTypeOfLegendaryPokemon();
		Assertions.assertEquals(16,types.size());
	}

	@Test
	public void exercice4() {
		/** Retourne le nom des pokemons les plus rapide*/
		final List<String> names = service.getFastestPokemon(3);
		Assertions.assertEquals(List.of("Shuckle", "Munchlax", "Trapinch"),names);
	}


	@Test
	public void exercice5() {
		/** Retourne un pokemon fusionné*/
		final Pokemon pokemon = service.getFusionPokemon(2,25);
		Assertions.assertEquals("Ivyachu",pokemon.getName());
		final Pokemon pokemon1 = service.getFusionPokemon(6,19,30);
		Assertions.assertEquals("Charrina",pokemon1.getName());
	}

}
