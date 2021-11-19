package org.pokemon.pikapi.service;

import org.pokemon.pikapi.infra.data.Pokemon;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokeUtils {

	public static  String fusionName(Pokemon p2, Pokemon p) {
		return p.getName().substring(0, p.getName().length()/2).concat(
				p2.getName().substring(p2.getName().length()/2));
	}

	public static Integer getNextNumberPokemon(List<Pokemon> pokemonList) {
		return  pokemonList.stream().map(Pokemon::getNumber).max(Integer::compareTo).orElse(0);
	}
	public static List<String> concatType(Pokemon p1, Pokemon p2) {
		return Stream.concat(p1.getType().stream(), p2.getType().stream())
				.distinct()
				.collect(Collectors.toList());
	}

}
