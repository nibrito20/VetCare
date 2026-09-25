package model;

import java.math.BigDecimal;
import java.sql.Time;

public class Receita {

    private int tratamentoCodigo;
    private int medicamentoCodigo;
    private Time horario;
    private BigDecimal quantidade;

    public Receita() {
    }

    public Receita(int tratamentoCodigo, int medicamentoCodigo, Time horario, BigDecimal quantidade) {
        this.tratamentoCodigo = tratamentoCodigo;
        this.medicamentoCodigo = medicamentoCodigo;
        this.horario = horario;
        this.quantidade = quantidade;
    }

    public int getTratamentoCodigo() {
        return tratamentoCodigo;
    }

    public void setTratamentoCodigo(int tratamentoCodigo) {
        this.tratamentoCodigo = tratamentoCodigo;
    }

    public int getMedicamentoCodigo() {
        return medicamentoCodigo;
    }

    public void setMedicamentoCodigo(int medicamentoCodigo) {
        this.medicamentoCodigo = medicamentoCodigo;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Receita{" +
                "tratamentoCodigo=" + tratamentoCodigo +
                ", medicamentoCodigo=" + medicamentoCodigo +
                ", horario=" + horario +
                ", quantidade=" + quantidade +
                '}';
    }
}