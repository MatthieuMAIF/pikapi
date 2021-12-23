package org.pokemon.pikapi.dto;

import io.vavr.control.Either;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReponseDTO<T> {
	private String error;
	T data;

	public static <T> ReponseDTO<T> reponse(T data){
		return ReponseDTO.<T>builder()
				.data(data)
				.build();
	}
	public static <T>  ReponseDTO<T> error(String error){
		return ReponseDTO.<T>builder()
				.error(error)
				.build();
	}
	public static <T> ReponseDTO<T> result(Either<String, T> either){
		return  null;

	}
}
