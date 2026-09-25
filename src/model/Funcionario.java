package model;

public class Funcionario {

    public static final int TIPO_VETERINARIO = 1;
    public static final int TIPO_AUXILIAR = 2;
    public static final int TIPO_RECEPCIONISTA = 3;
    public static final int TIPO_ADMINISTRATIVO = 4;

    private String cpf;
    private String nome;
    private String telefone;
    private String rua;
    private String bairro;
    private String numero;
    private String cidade;
    private String crv;
    private int funcionarioTipo;

    public Funcionario() {
    }

    public Funcionario(String cpf, String nome, String telefone, String rua, String bairro,
                       String numero, String cidade, String crv, int funcionarioTipo) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.crv = crv;
        this.funcionarioTipo = funcionarioTipo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCrv() {
        return crv;
    }

    public void setCrv(String crv) {
        this.crv = crv;
    }

    public int getFuncionarioTipo() {
        return funcionarioTipo;
    }

    public void setFuncionarioTipo(int funcionarioTipo) {
        this.funcionarioTipo = funcionarioTipo;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cidade='" + cidade + '\'' +
                ", crv='" + crv + '\'' +
                ", funcionarioTipo=" + funcionarioTipo +
                '}';
    }
}