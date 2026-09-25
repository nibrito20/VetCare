package model;

public class Tratamento {

    private int atendimentoCodigo;

    public Tratamento() {
    }

    public Tratamento(int atendimentoCodigo) {
        this.atendimentoCodigo = atendimentoCodigo;
    }

    public int getAtendimentoCodigo() {
        return atendimentoCodigo;
    }

    public void setAtendimentoCodigo(int atendimentoCodigo) {
        this.atendimentoCodigo = atendimentoCodigo;
    }

    @Override
    public String toString() {
        return "Tratamento{" +
                "atendimentoCodigo=" + atendimentoCodigo +
                '}';
    }
}