import conexao.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Animal;
import dao.AnimalDAO;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.getConexao();
            System.out.println("Conectado com sucesso ao banco de dados!");
            conexao.close();

        } catch (SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }

        AnimalDAO dao = new AnimalDAO();
        List<Animal> animais = dao.listarTodos();
        for (Animal a : animais) {
            System.out.println(a);
        }
    }
}