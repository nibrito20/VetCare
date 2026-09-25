package dao;

import conexao.Conexao;
import model.Dono;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonoDAO {


    public boolean inserir(Dono dono) {
        String sql = "INSERT INTO Dono (CPF, Nome, Telefone, Cidade, Rua, Bairro, Numero) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, dono.getCpf());
            stmt.setString(2, dono.getNome());
            stmt.setString(3, dono.getTelefone());
            stmt.setString(4, dono.getCidade());
            stmt.setString(5, dono.getRua());
            stmt.setString(6, dono.getBairro());
            stmt.setString(7, dono.getNumero());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir dono: " + e.getMessage());
            return false;
        }
    }


    public List<Dono> listarTodos() {
        List<Dono> lista = new ArrayList<>();
        String sql = "SELECT * FROM Dono";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarDono(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar donos: " + e.getMessage());
        }

        return lista;
    }


    public Dono buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Dono WHERE CPF = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarDono(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar dono: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean atualizar(Dono dono) {
        String sql = "UPDATE Dono SET Nome = ?, Telefone = ?, Cidade = ?, Rua = ?, Bairro = ?, Numero = ? " +
                "WHERE CPF = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setString(3, dono.getCidade());
            stmt.setString(4, dono.getRua());
            stmt.setString(5, dono.getBairro());
            stmt.setString(6, dono.getNumero());
            stmt.setString(7, dono.getCpf());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dono: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(String cpf) {
        String sql = "DELETE FROM Dono WHERE CPF = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar dono: " + e.getMessage());
            return false;
        }
    }


    private Dono montarDono(ResultSet rs) throws SQLException {
        Dono dono = new Dono();
        dono.setCpf(rs.getString("CPF"));
        dono.setNome(rs.getString("Nome"));
        dono.setTelefone(rs.getString("Telefone"));
        dono.setCidade(rs.getString("Cidade"));
        dono.setRua(rs.getString("Rua"));
        dono.setBairro(rs.getString("Bairro"));
        dono.setNumero(rs.getString("Numero"));
        return dono;
    }
}