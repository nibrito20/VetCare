import conexao.Conexao;
import dao.AnimalDAO;
import dao.DonoDAO;
import model.Animal;
import model.Dono;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class Main extends JFrame {

    private JTextField txtDonoCpf, txtDonoNome, txtDonoTelefone, txtDonoCidade, txtDonoRua, txtDonoBairro, txtDonoNumero;
    private JTextField txtAnimalCodigo, txtAnimalNome, txtAnimalEspecie, txtAnimalRaca, txtAnimalIdade, txtAnimalDia, txtAnimalMes, txtAnimalAno, txtAnimalSexo, txtAnimalCpfDono;
    private JTable tabelaConsultas;
    private DefaultTableModel modeloTabela;

    public Main() {
        setTitle("VetCare - Sistema de Gestão e Dashboard");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("1. Gestão (CRUD)", criarPainelCRUD());
        abas.addTab("2. Consultas SQL", criarPainelConsultas());
        abas.addTab("3. Dashboard Estatístico", criarPainelDashboard());

        add(abas);
    }

    private JPanel criarPainelCRUD() {
        JPanel painelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelDono = new JPanel(new GridBagLayout());
        painelDono.setBorder(BorderFactory.createTitledBorder("Gestão de Donos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtDonoCpf = new JTextField(10);
        txtDonoNome = new JTextField(10);
        txtDonoTelefone = new JTextField(10);
        txtDonoCidade = new JTextField(10);
        txtDonoRua = new JTextField(10);
        txtDonoBairro = new JTextField(10);
        txtDonoNumero = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0; painelDono.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoCpf, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painelDono.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoNome, gbc);
        gbc.gridx = 0; gbc.gridy = 2; painelDono.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoTelefone, gbc);
        gbc.gridx = 0; gbc.gridy = 3; painelDono.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoCidade, gbc);
        gbc.gridx = 0; gbc.gridy = 4; painelDono.add(new JLabel("Rua:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoRua, gbc);
        gbc.gridx = 0; gbc.gridy = 5; painelDono.add(new JLabel("Bairro:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoBairro, gbc);
        gbc.gridx = 0; gbc.gridy = 6; painelDono.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1; painelDono.add(txtDonoNumero, gbc);

        JButton btnSalvarDono = new JButton("Inserir");
        JButton btnAtualizarDono = new JButton("Atualizar");
        JButton btnDeletarDono = new JButton("Eliminar");

        JPanel btnBoxDono = new JPanel(new FlowLayout());
        btnBoxDono.add(btnSalvarDono);
        btnBoxDono.add(btnAtualizarDono);
        btnBoxDono.add(btnDeletarDono);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; painelDono.add(btnBoxDono, gbc);

        JPanel painelAnimal = new JPanel(new GridBagLayout());
        painelAnimal.setBorder(BorderFactory.createTitledBorder("Gestão de Animais"));

        txtAnimalCodigo = new JTextField(10);
        txtAnimalNome = new JTextField(10);
        txtAnimalEspecie = new JTextField(10);
        txtAnimalRaca = new JTextField(10);
        txtAnimalIdade = new JTextField(10);
        txtAnimalDia = new JTextField(10);
        txtAnimalMes = new JTextField(10);
        txtAnimalAno = new JTextField(10);
        txtAnimalSexo = new JTextField(10);
        txtAnimalCpfDono = new JTextField(10);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 0; painelAnimal.add(new JLabel("Código (p/ Eliminar):"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalCodigo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painelAnimal.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalNome, gbc);
        gbc.gridx = 0; gbc.gridy = 2; painelAnimal.add(new JLabel("Espécie:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalEspecie, gbc);
        gbc.gridx = 0; gbc.gridy = 3; painelAnimal.add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalRaca, gbc);
        gbc.gridx = 0; gbc.gridy = 4; painelAnimal.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalIdade, gbc);
        gbc.gridx = 0; gbc.gridy = 5; painelAnimal.add(new JLabel("Dia Nasc.:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalDia, gbc);
        gbc.gridx = 0; gbc.gridy = 6; painelAnimal.add(new JLabel("Mês Nasc.:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalMes, gbc);
        gbc.gridx = 0; gbc.gridy = 7; painelAnimal.add(new JLabel("Ano Nasc.:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalAno, gbc);
        gbc.gridx = 0; gbc.gridy = 8; painelAnimal.add(new JLabel("Sexo (M/F):"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalSexo, gbc);
        gbc.gridx = 0; gbc.gridy = 9; painelAnimal.add(new JLabel("CPF do Dono:"), gbc);
        gbc.gridx = 1; painelAnimal.add(txtAnimalCpfDono, gbc);

        JButton btnSalvarAnimal = new JButton("Inserir");
        JButton btnDeletarAnimal = new JButton("Eliminar");

        JPanel btnBoxAnimal = new JPanel(new FlowLayout());
        btnBoxAnimal.add(btnSalvarAnimal);
        btnBoxAnimal.add(btnDeletarAnimal);
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2; painelAnimal.add(btnBoxAnimal, gbc);

        btnSalvarDono.addActionListener(e -> {
            try {
                Dono d = new Dono();
                d.setCpf(txtDonoCpf.getText());
                d.setNome(txtDonoNome.getText());
                d.setTelefone(txtDonoTelefone.getText());
                d.setCidade(txtDonoCidade.getText());
                d.setRua(txtDonoRua.getText());
                d.setBairro(txtDonoBairro.getText());
                d.setNumero(txtDonoNumero.getText());
                new DonoDAO().inserir(d);
                JOptionPane.showMessageDialog(this, "Dono inserido com sucesso!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnAtualizarDono.addActionListener(e -> {
            try {
                Dono d = new Dono();
                d.setCpf(txtDonoCpf.getText());
                d.setNome(txtDonoNome.getText());
                d.setTelefone(txtDonoTelefone.getText());
                d.setCidade(txtDonoCidade.getText());
                d.setRua(txtDonoRua.getText());
                d.setBairro(txtDonoBairro.getText());
                d.setNumero(txtDonoNumero.getText());
                new DonoDAO().atualizar(d);
                JOptionPane.showMessageDialog(this, "Dono atualizado com sucesso!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnDeletarDono.addActionListener(e -> {
            try {
                new DonoDAO().deletar(txtDonoCpf.getText());
                JOptionPane.showMessageDialog(this, "Dono removido com sucesso!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnSalvarAnimal.addActionListener(e -> {
            try {
                Animal a = new Animal();
                a.setNome(txtAnimalNome.getText());
                a.setEspecie(txtAnimalEspecie.getText());
                a.setRaca(txtAnimalRaca.getText());
                a.setIdade(Integer.parseInt(txtAnimalIdade.getText().trim().isEmpty() ? "0" : txtAnimalIdade.getText().trim()));
                a.setDia(Integer.parseInt(txtAnimalDia.getText().trim().isEmpty() ? "1" : txtAnimalDia.getText().trim()));
                a.setMes(Integer.parseInt(txtAnimalMes.getText().trim().isEmpty() ? "1" : txtAnimalMes.getText().trim()));
                a.setAno(Integer.parseInt(txtAnimalAno.getText().trim().isEmpty() ? "2020" : txtAnimalAno.getText().trim()));
                a.setSexo(txtAnimalSexo.getText());
                a.setDonoCpf(txtAnimalCpfDono.getText());
                new AnimalDAO().inserir(a);
                JOptionPane.showMessageDialog(this, "Animal inserido com sucesso!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnDeletarAnimal.addActionListener(e -> {
            try {
                new AnimalDAO().deletar(Integer.parseInt(txtAnimalCodigo.getText().trim()));
                JOptionPane.showMessageDialog(this, "Animal removido com sucesso!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        painelPrincipal.add(painelDono);
        painelPrincipal.add(painelAnimal);
        return painelPrincipal;
    }

    private JPanel criarPainelConsultas(){
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnC1 = new JButton("1. Filtro (Caninos)");
        JButton btnC2 = new JButton("2. Atendimentos (JOIN)");
        JButton btnC3 = new JButton("3. Agregação por Espécie");
        JButton btnC4 = new JButton("4. Vet. Acima da Média");

        painelBotoes.add(btnC1); painelBotoes.add(btnC2);
        painelBotoes.add(btnC3); painelBotoes.add(btnC4);

        modeloTabela = new DefaultTableModel();
        tabelaConsultas = new JTable(modeloTabela);

        btnC1.addActionListener(e -> executarSQL("SELECT Codigo, Nome, Especie, Raca FROM Animal WHERE Especie = 'Canino' ORDER BY Nome ASC"));
        btnC2.addActionListener(e -> executarSQL(
                "SELECT a.Codigo AS ID, an.Nome AS Animal, d.Nome AS Dono, f.Nome AS Veterinario, a.Custo " +
                        "FROM Atendimento a " +
                        "JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo " +
                        "JOIN Dono d ON an.fk_Dono_CPF = d.CPF " +
                        "JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF"));
        btnC3.addActionListener(e -> executarSQL(
                "SELECT an.Especie, COUNT(a.Codigo) AS Total_Atendimentos, SUM(a.Custo) AS Total_Faturado " +
                        "FROM Atendimento a JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo GROUP BY an.Especie"));
        btnC4.addActionListener(e -> executarSQL(
                "SELECT f.Nome AS Veterinario, COUNT(a.Codigo) AS Qtd, SUM(a.Custo) AS Total " +
                        "FROM Funcionario f JOIN Atendimento a ON f.CPF = a.fk_Funcionario_CPF " +
                        "GROUP BY f.CPF, f.Nome HAVING SUM(a.Custo) > (SELECT AVG(Custo) FROM Atendimento)"));

        painel.add(painelBotoes, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaConsultas), BorderLayout.CENTER);
        return painel;
    }


    private JPanel criarPainelDashboard() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JComboBox comboGraficos = new JComboBox<>(new String[]{
                "Gráfico 1 - Histograma Simples",
                "Gráfico 2 - Densidade KDE",
                "Gráfico 3 - Distribuição com Média e Mediana",
                "Gráfico 4 - Boxplot Simples",
                "Gráfico 5 - Boxplot Detalhado",
                "Gráfico 6 - Visão Completa (Boxplot + Histograma + KDE)"
        });

        JLabel labelImagem = new JLabel("", SwingConstants.CENTER);

        Runnable carregarImagem = () -> {
            int index = comboGraficos.getSelectedIndex();
            String[] ficheiros = {
                    "grafico1.png", "grafico2.png", "grafico3.png",
                    "grafico4.png", "grafico5.png", "grafico6.png"
            };

            File imgFile = new File("graficos", ficheiros[index]);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(750, 480, Image.SCALE_SMOOTH);
                labelImagem.setIcon(new ImageIcon(img));
                labelImagem.setText("");
            } else {
                labelImagem.setIcon(null);
                labelImagem.setText("O ficheiro '" + ficheiros[index] + "' não foi encontrado na pasta 'graficos'.");
            }
        };

        comboGraficos.addActionListener(e -> carregarImagem.run());
        carregarImagem.run();

        JPanel painelTopo = new JPanel(new FlowLayout());
        painelTopo.add(new JLabel("Escolha o Gráfico Estatístico:"));
        painelTopo.add(comboGraficos);

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(new JScrollPane(labelImagem), BorderLayout.CENTER);

        return painel;
    }

    private void executarSQL(String sql) {
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            modeloTabela.setRowCount(0);
            modeloTabela.setColumnCount(0);

            ResultSetMetaData metaData = rs.getMetaData();
            int cols = metaData.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                modeloTabela.addColumn(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 1; i <= cols; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                modeloTabela.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro SQL: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}