package dao;

import conexao.Conexao;
import model.Especialidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {


    public boolean inserir(Especialidade especialidade) {
        String sql = "INSERT INTO Especialidade (Codigo, Descricao) VALUES (?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, especialidade.getCodigo());
            stmt.setString(2, especialidade.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir especialidade: " + e.getMessage());
            return false;
        }
    }


    public List<Especialidade> listarTodos() {
        List<Especialidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM Especialidade";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarEspecialidade(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar especialidades: " + e.getMessage());
        }

        return lista;
    }

    public Especialidade buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Especialidade WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarEspecialidade(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar especialidade: " + e.getMessage());
        }

        return null;
    }


    public boolean atualizar(Especialidade especialidade) {
        String sql = "UPDATE Especialidade SET Descricao = ? WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, especialidade.getDescricao());
            stmt.setInt(2, especialidade.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar especialidade: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Especialidade WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar especialidade: " + e.getMessage());
            return false;
        }
    }


    private Especialidade montarEspecialidade(ResultSet rs) throws SQLException {
        Especialidade especialidade = new Especialidade();
        especialidade.setCodigo(rs.getInt("Codigo"));
        especialidade.setDescricao(rs.getString("Descricao"));
        return especialidade;
    }
}