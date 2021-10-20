package org.pokemon.pikapi.infra.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pokemon {
	@JsonProperty("Number")
	private Integer number;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Type")
	private List<String> type;
	@JsonProperty("Total")
	private Integer total;
	@JsonProperty("HP")
	private Integer hp;
	@JsonProperty("Attack")
	private Integer attack;
	@JsonProperty("Defense")
	private Integer defense;
	@JsonProperty("Sp. Atk")
	private Integer specialAttack;
	@JsonProperty("Sp. Def")
	private Integer specialDefense;
	@JsonProperty("Speed")
	private Integer speed;
	@JsonProperty("Generation")
	private Integer generation;
	@JsonProperty("Legendary")
	private boolean legendary;
}
