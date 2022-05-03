package br.com.novoanjo.domain.commons.constante;

import br.com.novoanjo.infra.util.model.Constante;

public enum Approved implements Constante<String> {
    S("Sim", "S"),
    N("Não", "N");

    private final String descricao;
    private final String valor;

    Approved(final String descricao, final String valor) {
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
