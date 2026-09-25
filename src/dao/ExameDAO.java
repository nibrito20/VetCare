package dao;

import conexao.Conexao;
import model.Exame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExameDAO {


    public boolean inserir(Exame exame) {
        String sql = "INSERT INTO Exame (Codigo, Descricao, fk_Atendimento_Codigo) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, exame.getCodigo());
            stmt.setString(2, exame.getDescricao());
            stmt.setInt(3, exame.getAtendimentoCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir exame: " + e.getMessage());
            return false;
        }
    }


    public List<Exame> listarTodos() {
        List<Exame> lista = new ArrayList<>();
        String sql = "SELECT * FROM Exame";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarExame(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar exames: " + e.getMessage());
        }

        return lista;
    }


    public List<Exame> listarPorAtendimento(int atendimentoCodigo) {
        List<Exame> lista = new ArrayList<>();
        String sql = "SELECT * FROM Exame WHERE fk_Atendimento_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, atendimentoCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarExame(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar exames do atendimento: " + e.getMessage());
        }

        return lista;
    }


    public Exame buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Exame WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarExame(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar exame: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean atualizar(Exame exame) {
        String sql = "UPDATE Exame SET Descricao = ?, fk_Atendimento_Codigo = ? WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, exame.getDescricao());
            stmt.setInt(2, exame.getAtendimentoCodigo());
            stmt.setInt(3, exame.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar exame: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Exame WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar exame: " + e.getMessage());
            return false;
        }
    }


    private Exame montarExame(ResultSet rs) throws SQLException {
        Exame exame = new Exame();
        exame.setCodigo(rs.getInt("Codigo"));
        exame.setDescricao(rs.getString("Descricao"));
        exame.setAtendimentoCodigo(rs.getInt("fk_Atendimento_Codigo"));
        return exame;
    }
}