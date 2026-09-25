package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Conexao {

    public static Connection getConexao() throws SQLException {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            String url = props.getProperty("db.url");
            String usuario = props.getProperty("db.usuario");
            String senha = props.getProperty("db.senha");

            return DriverManager.getConnection(url, usuario, senha);

        } catch (IOException e) {
            throw new SQLException("Erro ao ler config.properties: " + e.getMessage());
        }
    }
}