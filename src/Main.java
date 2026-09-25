import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.getConexao();
            System.out.println("Conectado com sucesso ao banco de dados!");
            conexao.close();

        } catch (SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
}