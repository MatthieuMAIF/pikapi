package org.pokemon.pikapi.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error implements IError {

    ECHEC_RECUPERATION_POKEMON("Ce numéro de pokemon %s est inconnu");

    private String msg;
}
