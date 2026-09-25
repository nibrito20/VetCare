package model;

public class AtuaEm {

    private String funcionarioCPF;
    private int especialidadeCodigo;

    public AtuaEm() {
    }

    public AtuaEm(String funcionarioCPF, int especialidadeCodigo) {
        this.funcionarioCPF = funcionarioCPF;
        this.especialidadeCodigo = especialidadeCodigo;
    }

    public String getFuncionarioCPF() {
        return funcionarioCPF;
    }

    public void setFuncionarioCPF(String funcionarioCPF) {
        this.funcionarioCPF = funcionarioCPF;
    }

    public int getEspecialidadeCodigo() {
        return especialidadeCodigo;
    }

    public void setEspecialidadeCodigo(int especialidadeCodigo) {
        this.especialidadeCodigo = especialidadeCodigo;
    }

    @Override
    public String toString() {
        return "AtuaEm{" +
                "funcionarioCPF='" + funcionarioCPF + '\'' +
                ", especialidadeCodigo=" + especialidadeCodigo +
                '}';
    }
}