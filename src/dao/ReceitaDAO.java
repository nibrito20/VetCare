package dao;

import conexao.Conexao;
import model.Receita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {



    public boolean inserir(Receita receita) {
        String sql = "INSERT INTO Receita (fk_Tratamento_Codigo, fk_Medicamento_Codigo, Horario, Quantidade) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, receita.getTratamentoCodigo());
            stmt.setInt(2, receita.getMedicamentoCodigo());
            stmt.setTime(3, receita.getHorario());
            stmt.setBigDecimal(4, receita.getQuantidade());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir receita: " + e.getMessage());
            return false;
        }
    }


    public List<Receita> listarTodos() {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receita";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarReceita(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar receitas: " + e.getMessage());
        }

        return lista;
    }


    public List<Receita> listarPorTratamento(int tratamentoCodigo) {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receita WHERE fk_Tratamento_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, tratamentoCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarReceita(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar receitas do tratamento: " + e.getMessage());
        }

        return lista;
    }


    public List<Receita> listarPorMedicamento(int medicamentoCodigo) {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receita WHERE fk_Medicamento_Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, medicamentoCodigo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarReceita(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar receitas do medicamento: " + e.getMessage());
        }

        return lista;
    }


    public Receita buscarPorChave(int tratamentoCodigo, int medicamentoCodigo, Time horario) {
        String sql = "SELECT * FROM Receita WHERE fk_Tratamento_Codigo = ? AND fk_Medicamento_Codigo = ? AND Horario = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, tratamentoCodigo);
            stmt.setInt(2, medicamentoCodigo);
            stmt.setTime(3, horario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarReceita(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar receita: " + e.getMessage());
        }

        return null; // não encontrada
    }


    public boolean atualizar(Receita receita) {
        String sql = "UPDATE Receita SET Quantidade = ? " +
                "WHERE fk_Tratamento_Codigo = ? AND fk_Medicamento_Codigo = ? AND Horario = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBigDecimal(1, receita.getQuantidade());
            stmt.setInt(2, receita.getTratamentoCodigo());
            stmt.setInt(3, receita.getMedicamentoCodigo());
            stmt.setTime(4, receita.getHorario());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar receita: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int tratamentoCodigo, int medicamentoCodigo, Time horario) {
        String sql = "DELETE FROM Receita WHERE fk_Tratamento_Codigo = ? AND fk_Medicamento_Codigo = ? AND Horario = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, tratamentoCodigo);
            stmt.setInt(2, medicamentoCodigo);
            stmt.setTime(3, horario);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar receita: " + e.getMessage());
            return false;
        }
    }


    private Receita montarReceita(ResultSet rs) throws SQLException {
        Receita receita = new Receita();
        receita.setTratamentoCodigo(rs.getInt("fk_Tratamento_Codigo"));
        receita.setMedicamentoCodigo(rs.getInt("fk_Medicamento_Codigo"));
        receita.setHorario(rs.getTime("Horario"));
        receita.setQuantidade(rs.getBigDecimal("Quantidade"));
        return receita;
    }
}