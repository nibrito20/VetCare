package model;

public class Animal {

    private int codigo;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private int dia;
    private int mes;
    private int ano;
    private String sexo;
    private String donoCpf; // fk_Dono_CPF

    public Animal() {
    }

    public Animal(String nome, String especie, String raca, int idade,
                  int dia, int mes, int ano, String sexo, String donoCpf) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.sexo = sexo;
        this.donoCpf = donoCpf;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDonoCpf() {
        return donoCpf;
    }

    public void setDonoCpf(String donoCpf) {
        this.donoCpf = donoCpf;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", raca='" + raca + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", donoCpf='" + donoCpf + '\'' +
                '}';
    }
}