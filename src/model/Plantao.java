package model;

import java.sql.Time;

public class Plantao {

    private int codigo;
    private int dia;
    private int mes;
    private int ano;
    private Time horarioInicio;
    private Time horarioFim;
    private String funcionarioCPF;

    public Plantao() {
    }

    public Plantao(int codigo, int dia, int mes, int ano, Time horarioInicio, Time horarioFim, String funcionarioCPF) {
        this.codigo = codigo;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.funcionarioCPF = funcionarioCPF;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Time getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Time horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Time getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Time horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getFuncionarioCPF() {
        return funcionarioCPF;
    }

    public void setFuncionarioCPF(String funcionarioCPF) {
        this.funcionarioCPF = funcionarioCPF;
    }

    @Override
    public String toString() {
        return "Plantao{" +
                "codigo=" + codigo +
                ", dia=" + dia +
                ", mes=" + mes +
                ", ano=" + ano +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                ", funcionarioCPF='" + funcionarioCPF + '\'' +
                '}';
    }
}