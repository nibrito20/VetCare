package dao;

import conexao.Conexao;
import model.AtuaEm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtuaEmDAO {


    public boolean inserir(AtuaEm atuaEm) {
        String sql = "INSERT INTO Atua_em (fk_Funcionario_CPF, fk_Especialidade_Codigo) VALUES (?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, atuaEm.getFuncionarioCPF());
            stmt.setInt(2, atuaEm.getEspecialidadeCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir atua_em: " + e.getMessage());
            return false;
        }
    }


    public List<AtuaEm> listarTodos() {
        List<AtuaEm> lista = new ArrayList<>();
        String sql = "SELECT * FROM Atua_em";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAtuaEm(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar atua_em: " + e.getMessage());
        }

        return lista;
    }


    public List<AtuaEm> listarPorFuncionario(String funcionarioCPF) {
        List<AtuaEm> lista = new ArrayList<>();
        String sql = "SELECT * FROM Atua_em WHERE fk_Funcionario_CPF = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionarioCPF);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarAtuaEm(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar especialidades do funcionario: " + e.getMessage());
        }

        return lista;
    }


    public List<AtuaEm> listarPorEspecialidade(int especialidadeCodigo) {
        List<AtuaEm> lista = new ArrayList<>();
        String sql = "SELECT * FROM Atua_em WHERE fk_Especialidade_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, especialidadeCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarAtuaEm(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionarios da especialidade: " + e.getMessage());
        }

        return lista;
    }


    public boolean existe(String funcionarioCPF, int especialidadeCodigo) {
        String sql = "SELECT 1 FROM Atua_em WHERE fk_Funcionario_CPF = ? AND fk_Especialidade_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionarioCPF);
            stmt.setInt(2, especialidadeCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar atua_em: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(String funcionarioCPF, int especialidadeCodigo) {
        String sql = "DELETE FROM Atua_em WHERE fk_Funcionario_CPF = ? AND fk_Especialidade_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionarioCPF);
            stmt.setInt(2, especialidadeCodigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar atua_em: " + e.getMessage());
            return false;
        }
    }


    private AtuaEm montarAtuaEm(ResultSet rs) throws SQLException {
        AtuaEm atuaEm = new AtuaEm();
        atuaEm.setFuncionarioCPF(rs.getString("fk_Funcionario_CPF"));
        atuaEm.setEspecialidadeCodigo(rs.getInt("fk_Especialidade_Codigo"));
        return atuaEm;
    }
}