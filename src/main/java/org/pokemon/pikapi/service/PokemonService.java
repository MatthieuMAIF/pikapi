package org.pokemon.pikapi.service;

import org.pokemon.pikapi.infra.adapter.PokemonAdapter;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PokemonService {


	@Autowired
	private PokemonAdapter adapter;

	public List<Pokemon> getAllPokemon() {
			return adapter.getListAllPokemon().collect(Collectors.toList());
	}

	public Optional<Pokemon> getPokemon(Integer number) {
		return adapter.getListAllPokemon()
				.filter(p -> p.getNumber().equals(number))
				.findFirst();
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

	public Pokemon getFusionPokemon(Integer... numeros) {
		return adapter.getListAllPokemon()
				.filter(p -> Arrays.stream(numeros).parallel().anyMatch(n -> n.equals(p.getNumber())))
				.reduce(null, this::fusionPokemon);
	}

	private Pokemon fusionPokemon(Pokemon p1, Pokemon p2) {
		return Optional.ofNullable(p1)
				.map(p -> Pokemon.builder()
						.number(getNextNumberPokemon()+1)
						.name(p.getName().substring(0,p.getName().length()/2).concat(
								p2.getName().substring(p2.getName().length()/2)))
						.type(Stream.concat(p1.getType().stream(), p2.getType().stream())
								.distinct()
								.collect(Collectors.toList()))
						.total(p.getTotal()+ p2.getTotal())
						.attack(p.getAttack()+ p2.getAttack())
						.defense(p.getTotal()+ p2.getTotal())
						.specialAttack(p.getSpecialAttack()+ p2.getSpecialAttack())
						.specialDefense(p.getSpecialDefense()+ p2.getSpecialDefense())
						.speed(p.getSpeed()+ p2.getSpeed())
						.generation(9)
						.build())
				.orElse(p2);
	}

	private Integer getNextNumberPokemon() {
		return  adapter.getListAllPokemon().map(Pokemon::getNumber).max(Integer::compareTo).orElse(0);
	}

	public List<String> getFastestPokemon(int nbPokemon) {
		return adapter.getListAllPokemon()
				.sorted((p1,p2)->p1.getSpeed().compareTo(p2.getSpeed()))
				.limit(nbPokemon)
				.map(Pokemon::getName)
				.collect(Collectors.toList());
	}


	public String getPokemonNicknameOrNone(Integer number){
		return getPokemon(number)
				.flatMap(p -> Optional.ofNullable(p.getNickname()))
				.orElse("NONE");
	}

}
