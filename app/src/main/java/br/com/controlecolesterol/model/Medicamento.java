package br.com.controlecolesterol.model;

public class Medicamento {

    private Integer id;
    private String nome;
    private String tratamento;
    private int dias;
    private int intervalo;
    private String doenca;

    public Medicamento() {
    }

    public Medicamento(Integer id, String nome, String tratamento, int dias, int intervalo, String doenca) {
        this.id = id;
        this.nome = nome;
        this.tratamento = tratamento;
        this.dias = dias;
        this.intervalo = intervalo;
        this.doenca = doenca;
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

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }
}
