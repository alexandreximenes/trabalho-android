package br.com.controlecolesterol.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Categoria.class, parentColumns = "id", childColumns = "categoriaId"))
public class Alimento {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String nome;
    private String descricao;
    private String consumoRecomendado;
    private boolean alimentoBom;

    @ColumnInfo(index = true)
    private int categoriaId;

    public Alimento() {
    }

    public Alimento(int id, String nome, String descricao, String quantidade, boolean alimentoBom, int categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.consumoRecomendado = quantidade;
        this.alimentoBom = alimentoBom;
        this.categoriaId = categoriaId;
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

    public boolean isAlimentoBom() {
        return alimentoBom;
    }

    public void setAlimentoBom(boolean alimentoBom) {
        this.alimentoBom = alimentoBom;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return id + " - " +nome+ ", " +consumoRecomendado+ ", " + alimentoBom;
    }
}
