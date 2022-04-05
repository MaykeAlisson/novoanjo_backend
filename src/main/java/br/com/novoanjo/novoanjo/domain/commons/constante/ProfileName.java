package br.com.novoanjo.novoanjo.domain.commons.constante;

import br.com.novoanjo.novoanjo.infra.util.model.Constante;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ProfileName implements Constante<String> {

    A("Anjo", "A"),
    S("Solicitante", "S"),
    M("Master", "M");

    private final String descricao;
    private final String valor;

    ProfileName(final String descricao, final String valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public String getValor() {
        return this.valor;
    }

    public static Boolean existValue(final String profile){
       return !Arrays.stream(ProfileName.values())
               .filter(value -> value.getValor().contains(profile))
               .collect(Collectors.toSet()).isEmpty();
    }
}
