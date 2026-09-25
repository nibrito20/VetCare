package dao;

import conexao.Conexao;
import model.Tratamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TratamentoDAO {


    public boolean inserir(Tratamento tratamento) {
        String sql = "INSERT INTO Tratamento (fk_Atendimento_Codigo) VALUES (?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, tratamento.getAtendimentoCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tratamento: " + e.getMessage());
            return false;
        }
    }


    public List<Tratamento> listarTodos() {
        List<Tratamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tratamento";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarTratamento(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tratamentos: " + e.getMessage());
        }

        return lista;
    }


    public Tratamento buscarPorAtendimento(int atendimentoCodigo) {
        String sql = "SELECT * FROM Tratamento WHERE fk_Atendimento_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, atendimentoCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarTratamento(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar tratamento: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean existePorAtendimento(int atendimentoCodigo) {
        return buscarPorAtendimento(atendimentoCodigo) != null;
    }


    public boolean deletar(int atendimentoCodigo) {
        String sql = "DELETE FROM Tratamento WHERE fk_Atendimento_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, atendimentoCodigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar tratamento: " + e.getMessage());
            return false;
        }
    }


    private Tratamento montarTratamento(ResultSet rs) throws SQLException {
        Tratamento tratamento = new Tratamento();
        tratamento.setAtendimentoCodigo(rs.getInt("fk_Atendimento_Codigo"));
        return tratamento;
    }
}