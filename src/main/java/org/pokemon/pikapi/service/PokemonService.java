package org.pokemon.pikapi.service;

import org.pokemon.pikapi.infra.adapter.PokemonAdapter;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService {

	@Autowired
	private PokemonAdapter adapter;

	public Optional<Pokemon> getPokemon(Integer number) {
		return null;
	}

	public String getPokemonNicknameOrNone(Integer number){
		return "";
	}

	public List<Pokemon> getAllPokemon() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * retourne la liste des pokemons légendaires
	 * @return
	 */
	public List<Pokemon> getAllLegendaryPokemon() {
				return Collections.EMPTY_LIST;
		}




	/**
	 *
	 * @param nbPokemon
	 * @return
	 */
	public List<String> getFastestPokemon(int nbPokemon) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * retourn la liste des types de pokemons légendaire
	 * @return
	 */
	public List<String> getAllTypeOfLegendaryPokemon() {
			return  Collections.EMPTY_LIST;
	}






	/**
	 * retourne un pokemon qui est le résultat de la fusion de l'ensemble des pokemons passés en paramétres
	 * @param numeros
	 * @return
	 */
	public Pokemon getFusionPokemon(Integer... numeros) {
		return new Pokemon();
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
