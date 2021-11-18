package org.pokemon.pikapi.service;

import org.pokemon.pikapi.infra.adapter.PokemonAdapter;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService {

	@Autowired
	private PokemonAdapter adapter;

	public String getPokemonNicknameOrNone(Integer number){
		return getPokemon(number)
				.map(Pokemon::getNickname)
				.orElse("NONE");
	}

	public Optional<Pokemon> getPokemon(Integer number) {
		/*	à remplacer par traitement avec le stream
		for (Pokemon pokemon : adapter.getListAllPokemon())) {
			if(number.equals(pokemon.getNumber())){
				return Optional.ofNullable(pokemon);
			}
		}
		return Optional.empty();
	*/
		return adapter.getStreamAllPokemon()
				.filter(p -> p.getNumber().equals(number))
				.findFirst();
	}


	public List<Pokemon> getAllPokemon() {
		return adapter.getStreamAllPokemon().collect(Collectors.toList());
	}

	/**
	 * retourne la liste des pokemons légendaires
	 * @return
	 */
	public List<Pokemon> getAllLegendaryPokemon() {
			return adapter.getStreamAllPokemon()
					.filter(Pokemon::isLegendary)
					.collect(Collectors.toList());

	}

	/**
	 * retourn la liste des types de pokemons légendaire
	 * @return
	 */
	public List<String> getAllTypeOfLegendaryPokemon() {
			return adapter.getStreamAllPokemon()
					.filter(Pokemon::isLegendary)
					.flatMap(p -> p.getType().stream())
					.distinct()
					.collect(Collectors.toList());
	}



	public List<String> getFastestPokemon(int nbPokemon) {
		return adapter.getStreamAllPokemon()
				.sorted(Comparator.comparing(Pokemon::getSpeed))
				.limit(nbPokemon)
				.map(Pokemon::getName)
				.collect(Collectors.toList());
	}



	/**
	 * retourne un pokemon qui est le résultat de la fusion de l'ensemble des pokemons passés en paramétres
	 * @param numeros
	 * @return
	 */
	public Pokemon getFusionPokemon(Integer... numeros) {
		return adapter.getStreamAllPokemon()
				.filter(p -> Arrays.stream(numeros).parallel().anyMatch(n -> n.equals(p.getNumber())))
				.reduce(null, this::fusionPokemon);
	}

	private  Pokemon fusionPokemon(Pokemon p1, Pokemon p2) {
		return Optional.ofNullable(p1)
				.map(p -> Pokemon.builder()
						.number(PokeUtils.getNextNumberPokemon(getAllPokemon())+1)
						.name(PokeUtils.fusionName(p2, p))
						.type(PokeUtils.concatType(p1, p2))
						.total(p.getTotal()+ p2.getTotal())
						.attack(p.getAttack()+ p2.getAttack())
						.defense(p.getDefense()+ p2.getDefense())
						.specialAttack(p.getSpecialAttack()+ p2.getSpecialAttack())
						.specialDefense(p.getSpecialDefense()+ p2.getSpecialDefense())
						.speed(p.getSpeed()+ p2.getSpeed())
						.generation(9)
						.build())
				.orElse(p2);
	}

}
