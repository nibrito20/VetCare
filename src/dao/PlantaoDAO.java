package dao;

import conexao.Conexao;
import model.Plantao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlantaoDAO {


    public boolean inserir(Plantao plantao) {
        String sql = "INSERT INTO Plantao (Codigo, Dia, Mes, Ano, Horario_Inicio, Horario_Fim, fk_Funcionario_CPF) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, plantao.getCodigo());
            stmt.setInt(2, plantao.getDia());
            stmt.setInt(3, plantao.getMes());
            stmt.setInt(4, plantao.getAno());
            stmt.setTime(5, plantao.getHorarioInicio());
            stmt.setTime(6, plantao.getHorarioFim());
            stmt.setString(7, plantao.getFuncionarioCPF());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir plantao: " + e.getMessage());
            return false;
        }
    }


    public List<Plantao> listarTodos() {
        List<Plantao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Plantao";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarPlantao(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar plantoes: " + e.getMessage());
        }

        return lista;
    }


    public List<Plantao> listarPorFuncionario(String funcionarioCPF) {
        List<Plantao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Plantao WHERE fk_Funcionario_CPF = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionarioCPF);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarPlantao(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar plantoes do funcionario: " + e.getMessage());
        }

        return lista;
    }


    public Plantao buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Plantao WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarPlantao(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar plantao: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean atualizar(Plantao plantao) {
        String sql = "UPDATE Plantao SET Dia = ?, Mes = ?, Ano = ?, Horario_Inicio = ?, Horario_Fim = ?, " +
                "fk_Funcionario_CPF = ? WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, plantao.getDia());
            stmt.setInt(2, plantao.getMes());
            stmt.setInt(3, plantao.getAno());
            stmt.setTime(4, plantao.getHorarioInicio());
            stmt.setTime(5, plantao.getHorarioFim());
            stmt.setString(6, plantao.getFuncionarioCPF());
            stmt.setInt(7, plantao.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plantao: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Plantao WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar plantao: " + e.getMessage());
            return false;
        }
    }


    private Plantao montarPlantao(ResultSet rs) throws SQLException {
        Plantao plantao = new Plantao();
        plantao.setCodigo(rs.getInt("Codigo"));
        plantao.setDia(rs.getInt("Dia"));
        plantao.setMes(rs.getInt("Mes"));
        plantao.setAno(rs.getInt("Ano"));
        plantao.setHorarioInicio(rs.getTime("Horario_Inicio"));
        plantao.setHorarioFim(rs.getTime("Horario_Fim"));
        plantao.setFuncionarioCPF(rs.getString("fk_Funcionario_CPF"));
        return plantao;
    }
}