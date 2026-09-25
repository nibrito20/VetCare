package dao;

import conexao.Conexao;
import model.Atendimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDAO {

    public boolean inserir(Atendimento atendimento) {
        String sql = "INSERT INTO Atendimento (Status, Dia, Mes, Ano, Horario, Diagnostico, Custo, " +
                "Nivel_de_Gravidade, Atendimento_TIPO, fk_Animal_Codigo, fk_Funcionario_CPF, " +
                "fk_Especialidade_Codigo, fk_Plantao_Codigo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            preencherStatement(stmt, atendimento);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir atendimento: " + e.getMessage());
            return false;
        }
    }


    public List<Atendimento> listarTodos() {
        List<Atendimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Atendimento";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAtendimento(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar atendimentos: " + e.getMessage());
        }

        return lista;
    }


    public Atendimento buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM Atendimento WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarAtendimento(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar atendimento: " + e.getMessage());
        }

        return null; // não encontrado
    }

    public boolean atualizar(Atendimento atendimento) {
        String sql = "UPDATE Atendimento SET Status = ?, Dia = ?, Mes = ?, Ano = ?, Horario = ?, " +
                "Diagnostico = ?, Custo = ?, Nivel_de_Gravidade = ?, Atendimento_TIPO = ?, " +
                "fk_Animal_Codigo = ?, fk_Funcionario_CPF = ?, fk_Especialidade_Codigo = ?, " +
                "fk_Plantao_Codigo = ? WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            int proximoIndice = preencherStatement(stmt, atendimento);
            stmt.setInt(proximoIndice, atendimento.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar atendimento: " + e.getMessage());
            return false;
        }
    }


    public boolean deletar(int codigo) {
        String sql = "DELETE FROM Atendimento WHERE Codigo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar atendimento: " + e.getMessage());
            return false;
        }
    }



    private int preencherStatement(PreparedStatement stmt, Atendimento atendimento) throws SQLException {
        stmt.setString(1, atendimento.getStatus());
        stmt.setInt(2, atendimento.getDia());
        stmt.setInt(3, atendimento.getMes());
        stmt.setInt(4, atendimento.getAno());
        stmt.setTime(5, atendimento.getHorario());
        stmt.setString(6, atendimento.getDiagnostico());
        stmt.setBigDecimal(7, atendimento.getCusto());
        stmt.setString(8, atendimento.getNivelDeGravidade());
        stmt.setInt(9, atendimento.getAtendimentoTipo());
        stmt.setInt(10, atendimento.getAnimalCodigo());
        stmt.setString(11, atendimento.getFuncionarioCpf());
        stmt.setInt(12, atendimento.getEspecialidadeCodigo());

        // fk_Plantao_Codigo pode ser NULL (atendimento normal não tem plantão associado)
        if (atendimento.getPlantaoCodigo() != null) {
            stmt.setInt(13, atendimento.getPlantaoCodigo());
        } else {
            stmt.setNull(13, Types.INTEGER);
        }

        return 14; // próximo índice livre (usado pelo UPDATE para o WHERE Codigo = ?)
    }

    private Atendimento montarAtendimento(ResultSet rs) throws SQLException {
        Atendimento atendimento = new Atendimento();
        atendimento.setCodigo(rs.getInt("Codigo"));
        atendimento.setStatus(rs.getString("Status"));
        atendimento.setDia(rs.getInt("Dia"));
        atendimento.setMes(rs.getInt("Mes"));
        atendimento.setAno(rs.getInt("Ano"));
        atendimento.setHorario(rs.getTime("Horario"));
        atendimento.setDiagnostico(rs.getString("Diagnostico"));
        atendimento.setCusto(rs.getBigDecimal("Custo"));
        atendimento.setNivelDeGravidade(rs.getString("Nivel_de_Gravidade"));
        atendimento.setAtendimentoTipo(rs.getInt("Atendimento_TIPO"));
        atendimento.setAnimalCodigo(rs.getInt("fk_Animal_Codigo"));
        atendimento.setFuncionarioCpf(rs.getString("fk_Funcionario_CPF"));
        atendimento.setEspecialidadeCodigo(rs.getInt("fk_Especialidade_Codigo"));

        // fk_Plantao_Codigo pode vir NULL do banco
        int plantaoCodigo = rs.getInt("fk_Plantao_Codigo");
        atendimento.setPlantaoCodigo(rs.wasNull() ? null : plantaoCodigo);

        return atendimento;
    }
}