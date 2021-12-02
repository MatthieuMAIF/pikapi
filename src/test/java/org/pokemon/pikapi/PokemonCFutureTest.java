package org.pokemon.pikapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pokemon.pikapi.controler.PokemonControler;
import org.pokemon.pikapi.dto.ReponseDTO;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class PokemonCFutureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCFutureTest.class);
    @Autowired
    private PokemonControler controler;

    @Test
    public void exercice2() {
        LOGGER.warn("test with apply");
        controler.getPokemon(25)
                .thenApply(ReponseDTO::getData)
                .thenApply(Pokemon::getName)
                .thenAccept(name -> {
                    Assertions.assertEquals("Pikachu", name);
                    LOGGER.warn("assert apply");
                });
        LOGGER.warn("end of test with apply");
        LOGGER.warn("test withjoin");
        Assertions.assertEquals("Pikachu", controler.getPokemon(25)
                .toCompletableFuture()
                .join().getData().getName());
        LOGGER.warn("assert join");
        LOGGER.warn("end of test with join");

    }

    @Test
    public void exercice3(){

        final CompletableFuture<ReponseDTO<Pokemon>> completableFutureCompletableFuture =
                controler.getLegendsPokemon()
                        .thenApply(ReponseDTO::getData)
                        .thenApply(l -> l.get(0))
                        .thenCompose(number -> controler.getPokemon(number));

        completableFutureCompletableFuture
                .thenApply(ReponseDTO::getData)
                .thenApply(Pokemon::getName)
                .thenApply(name -> {
                    Assertions.assertEquals("Articuno", name);
                    return name;
                })
                .join();

    }

    @Test
    public void exercice4() {

        CompletableFuture<ReponseDTO<Pokemon>> pokemon3 = controler.getPokemon(3).toCompletableFuture();
        CompletableFuture<ReponseDTO<Pokemon>> pokemon6 = controler.getPokemon(6).toCompletableFuture();
        CompletableFuture<ReponseDTO<Pokemon>> pokemon21 = controler.getPokemon(25).toCompletableFuture();

        CompletableFuture.allOf(pokemon3, pokemon6, pokemon21)
                .thenAccept(v ->
                        Stream.of(pokemon3, pokemon6, pokemon21)
                                .map(CompletableFuture::join)
                                .map(ReponseDTO::getData)
                                .map(Pokemon::getName)
                                .forEach(name -> Assertions.assertTrue(List.of("Venusaur", "Charizard", "Pikachu")
                                        .contains(name),name)))

                .join();

    }

}
