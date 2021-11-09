package org.pokemon.pikapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.pokemon.pikapi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PokemonOptTest {
	@Autowired
	private PokemonService service;

	@Test
	public void exercice1() {
		/** Tester que l'on n'a pas de pokemon */
		Assertions.assertTrue(service.getPokemon(2500).isEmpty());
		/** Tester que l'on a un pokemon */
		Assertions.assertTrue(service.getPokemon(25).isPresent());
	}

	@Test
	public void exercice2() {
		/** Retourne le surnom des pokemons ou "NONE"*/
		String nickname = service.getPokemonNicknameOrNone(25);
		Assertions.assertEquals(nickname,"Pique à chou");
		nickname = service.getPokemonNicknameOrNone(12);
		Assertions.assertEquals(nickname,"NONE");
	}
}