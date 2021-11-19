package org.pokemon.pikapi.infra.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pokemon.pikapi.infra.data.Pokemon;
import org.pokemon.pikapi.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PokemonAdapter {

	private Logger LOGGER = LoggerFactory.getLogger(PokemonAdapter.class);

	@Value(value = "classpath:data.json")
	private Resource resource;
	public Stream<Pokemon> getStreamAllPokemon(){
		try {
			return new ObjectMapper().readValue(asString(resource), new TypeReference<List<Pokemon>>(){}).stream();
		} catch (IOException e) {
			LOGGER.error("Impossible to collect pokemon datas", e);
		}
		return Stream.empty();
	}
	@Deprecated
	public List<Pokemon> getListAllPokemon(){
		return getStreamAllPokemon().collect(Collectors.toList());
	}

	private static String asString(Resource resource) throws IOException {
		Reader reader = new InputStreamReader(resource.getInputStream(),  StandardCharsets.UTF_8);
		return FileCopyUtils.copyToString(reader);

	}
}
