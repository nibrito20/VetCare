package model;

import java.math.BigDecimal;
import java.sql.Time;

public class Atendimento {

    private int codigo;
    private String status; // 'Agendado', 'Realizado', 'Cancelado'
    private int dia;
    private int mes;
    private int ano;
    private Time horario;
    private String diagnostico;
    private BigDecimal custo;
    private String nivelDeGravidade; // pode ser null (só obrigatório se for emergência)
    private int atendimentoTipo;     // 1 = normal, 2 = emergência
    private int animalCodigo;        // fk_Animal_Codigo
    private String funcionarioCpf;   // fk_Funcionario_CPF
    private int especialidadeCodigo; // fk_Especialidade_Codigo
    private Integer plantaoCodigo;   // fk_Plantao_Codigo (pode ser null)

    public Atendimento() {
    }

    public Atendimento(String status, int dia, int mes, int ano, Time horario,
                       String diagnostico, BigDecimal custo, String nivelDeGravidade,
                       int atendimentoTipo, int animalCodigo, String funcionarioCpf,
                       int especialidadeCodigo, Integer plantaoCodigo) {
        this.status = status;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.horario = horario;
        this.diagnostico = diagnostico;
        this.custo = custo;
        this.nivelDeGravidade = nivelDeGravidade;
        this.atendimentoTipo = atendimentoTipo;
        this.animalCodigo = animalCodigo;
        this.funcionarioCpf = funcionarioCpf;
        this.especialidadeCodigo = especialidadeCodigo;
        this.plantaoCodigo = plantaoCodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public String getNivelDeGravidade() {
        return nivelDeGravidade;
    }

    public void setNivelDeGravidade(String nivelDeGravidade) {
        this.nivelDeGravidade = nivelDeGravidade;
    }

    public int getAtendimentoTipo() {
        return atendimentoTipo;
    }

    public void setAtendimentoTipo(int atendimentoTipo) {
        this.atendimentoTipo = atendimentoTipo;
    }

    public int getAnimalCodigo() {
        return animalCodigo;
    }

    public void setAnimalCodigo(int animalCodigo) {
        this.animalCodigo = animalCodigo;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public int getEspecialidadeCodigo() {
        return especialidadeCodigo;
    }

    public void setEspecialidadeCodigo(int especialidadeCodigo) {
        this.especialidadeCodigo = especialidadeCodigo;
    }

    public Integer getPlantaoCodigo() {
        return plantaoCodigo;
    }

    public void setPlantaoCodigo(Integer plantaoCodigo) {
        this.plantaoCodigo = plantaoCodigo;
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "codigo=" + codigo +
                ", status='" + status + '\'' +
                ", data=" + dia + "/" + mes + "/" + ano +
                ", horario=" + horario +
                ", custo=" + custo +
                ", tipo=" + atendimentoTipo +
                ", animalCodigo=" + animalCodigo +
                ", funcionarioCpf='" + funcionarioCpf + '\'' +
                '}';
    }
}