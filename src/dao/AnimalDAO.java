package dao;

import conexao.Conexao;
import model.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public boolean inserir(Animal animal) {
        String sql = "INSERT INTO Animal (Nome, Especie, Raca, Idade, Dia, Mes, Ano, Sexo, fk_Dono_CPF) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdade());
            stmt.setInt(5, animal.getDia());
            stmt.setInt(6, animal.getMes());
            stmt.setInt(7, animal.getAno());
            stmt.setString(8, animal.getSexo());
            stmt.setString(9, animal.getDonoCpf());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir animal: " + e.getMessage());
            return false;
        }
    }


    public List<Animal> listarTodos() {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM Animal";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAnimal(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar animais: " + e.getMessage());
        }

        return lista;
    }


    public Animal buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Animal WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarAnimal(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar animal: " + e.getMessage());
        }

        return null; // não encontrado
    }


    public boolean atualizar(Animal animal) {
        String sql = "UPDATE Animal SET Nome = ?, Especie = ?, Raca = ?, Idade = ?, " +
                "Dia = ?, Mes = ?, Ano = ?, Sexo = ?, fk_Dono_CPF = ? " +
                "WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdade());
            stmt.setInt(5, animal.getDia());
            stmt.setInt(6, animal.getMes());
            stmt.setInt(7, animal.getAno());
            stmt.setString(8, animal.getSexo());
            stmt.setString(9, animal.getDonoCpf());
            stmt.setInt(10, animal.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar animal: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Animal WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar animal: " + e.getMessage());
            return false;
        }
    }


    private Animal montarAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setCodigo(rs.getInt("Codigo"));
        animal.setNome(rs.getString("Nome"));
        animal.setEspecie(rs.getString("Especie"));
        animal.setRaca(rs.getString("Raca"));
        animal.setIdade(rs.getInt("Idade"));
        animal.setDia(rs.getInt("Dia"));
        animal.setMes(rs.getInt("Mes"));
        animal.setAno(rs.getInt("Ano"));
        animal.setSexo(rs.getString("Sexo"));
        animal.setDonoCpf(rs.getString("fk_Dono_CPF"));
        return animal;
    }
}