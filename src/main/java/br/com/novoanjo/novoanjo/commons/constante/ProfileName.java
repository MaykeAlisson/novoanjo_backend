package br.com.novoanjo.novoanjo.commons.constante;

import br.com.novoanjo.novoanjo.util.model.Constante;

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
}
