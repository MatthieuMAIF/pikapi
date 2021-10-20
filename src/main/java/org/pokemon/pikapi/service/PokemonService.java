package org.pokemon.pikapi.service;

import org.pokemon.pikapi.infra.adapter.PokemonAdapter;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService {


	@Autowired
	private PokemonAdapter adapter;

	public List<Pokemon> getAllPokemon() {
			return adapter.getListAllPokemon().collect(Collectors.toList());

	}

	public List<Pokemon> getAllLegendaryPokemon() {
			return adapter.getListAllPokemon()
					.filter(Pokemon::isLegendary)
					.collect(Collectors.toList());

	}

	public List<String> getAllTypeOfLegendaryPokemon() {
			return adapter.getListAllPokemon()
					.filter(Pokemon::isLegendary)
					.flatMap(p -> p.getType().stream())
					.distinct()
					.collect(Collectors.toList());
	}

	public List<String> getFastestPokemon(int nbPokemon) {
		return adapter.getListAllPokemon()
				.sorted(Comparator.comparingInt(Pokemon::getSpeed))
				.limit(nbPokemon)
				.map(Pokemon::getName)
				.collect(Collectors.toList());
	}
}
