package dao;

import conexao.Conexao;
import model.Medicamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {


    public boolean inserir(Medicamento medicamento) {
        String sql = "INSERT INTO Medicamento (Codigo, Nome, Tarja) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, medicamento.getCodigo());
            stmt.setString(2, medicamento.getNome());
            stmt.setString(3, medicamento.getTarja());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir medicamento: " + e.getMessage());
            return false;
        }
    }


    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarMedicamento(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar medicamentos: " + e.getMessage());
        }

        return lista;
    }


    public Medicamento buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Medicamento WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarMedicamento(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar medicamento: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean atualizar(Medicamento medicamento) {
        String sql = "UPDATE Medicamento SET Nome = ?, Tarja = ? WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNome());
            stmt.setString(2, medicamento.getTarja());
            stmt.setInt(3, medicamento.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar medicamento: " + e.getMessage());
            return false;
        }
    }



    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Medicamento WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar medicamento: " + e.getMessage());
            return false;
        }
    }


    private Medicamento montarMedicamento(ResultSet rs) throws SQLException {
        Medicamento medicamento = new Medicamento();
        medicamento.setCodigo(rs.getInt("Codigo"));
        medicamento.setNome(rs.getString("Nome"));
        medicamento.setTarja(rs.getString("Tarja"));
        return medicamento;
    }
}