package org.pokemon.pikapi;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pokemon.pikapi.controler.PokemonControler;
import org.pokemon.pikapi.dto.ReponseDTO;
import org.pokemon.pikapi.error.Potential;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.pokemon.pikapi.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SpringBootTest
public class PokemonEitherTest {

	@Autowired
	private PokemonControler controler;
	@Autowired
	private PokemonService pokemonService;

	@Test
	public void exercice1() {
		controler.getPokemon(25000).toCompletableFuture()
				.thenApply(ReponseDTO::getError)
				.thenAccept(error -> Assertions.assertEquals("Ce numéro de pokemon 25000 est inconnu", error))
				.join();
	}

	@Test
	public void exercice2() {

		Potential<Tuple2<Pokemon, Pokemon>> result = Potential.concat(
				pokemonService.getPokemonPotential(21000),
				pokemonService.getPokemonPotential(25000));

		Assertions.assertEquals(2,result.getLeft().errors.size());
		Assertions.assertEquals("Ce numéro de pokemon 21000 est inconnu",result.getLeft().errors.get(0).toString());
		Assertions.assertEquals("Ce numéro de pokemon 25000 est inconnu",result.getLeft().errors.get(1).toString());

	}
}
