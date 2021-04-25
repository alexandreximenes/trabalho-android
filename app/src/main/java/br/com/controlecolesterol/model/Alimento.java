package br.com.controlecolesterol.model;

public class Alimento {

    private Integer id;
    private String nome;
    private String descricao;
    private String consumoRecomendado;
    private boolean alimentoBom;

    public Alimento() {
    }

    public Alimento(Integer id, String nome, String descricao, String consumoRecomendado, boolean alimentoBom, String quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.consumoRecomendado = consumoRecomendado;
        this.alimentoBom = alimentoBom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConsumoRecomendado() {
        return consumoRecomendado;
    }

    public void setConsumoRecomendado(String consumoRecomendado) {
        this.consumoRecomendado = consumoRecomendado;
    }

    public boolean getAlimentoBom() {
        return alimentoBom;
    }

    public void setAlimentoBom(boolean alimentoBom) {
        this.alimentoBom = alimentoBom;
    }

    @Override
    public String toString() {
        return id + " - " +nome+ ", " +consumoRecomendado+ ", " + alimentoBom;
    }
}
