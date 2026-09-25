package model;

public class Medicamento {


    public static final String TARJA_NENHUMA = "Nenhuma";
    public static final String TARJA_AMARELA = "Amarela";
    public static final String TARJA_VERMELHA = "Vermelha";
    public static final String TARJA_PRETA = "Preta";

    private int codigo;
    private String nome;
    private String tarja; // default 'Nenhuma' no banco

    public Medicamento() {
    }

    public Medicamento(int codigo, String nome, String tarja) {
        this.codigo = codigo;
        this.nome = nome;
        this.tarja = tarja;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTarja() {
        return tarja;
    }

    public void setTarja(String tarja) {
        this.tarja = tarja;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", tarja='" + tarja + '\'' +
                '}';
    }
}