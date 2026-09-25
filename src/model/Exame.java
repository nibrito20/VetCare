package model;

public class Exame {

    private int codigo;
    private String descricao;
    private int atendimentoCodigo;

    public Exame() {
    }

    public Exame(int codigo, String descricao, int atendimentoCodigo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.atendimentoCodigo = atendimentoCodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getAtendimentoCodigo() {
        return atendimentoCodigo;
    }

    public void setAtendimentoCodigo(int atendimentoCodigo) {
        this.atendimentoCodigo = atendimentoCodigo;
    }

    @Override
    public String toString() {
        return "Exame{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", atendimentoCodigo=" + atendimentoCodigo +
                '}';
    }
}