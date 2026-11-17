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
    private JLabel lblTotalClientes = new JLabel("0");
    private JLabel lblTotalAnimais = new JLabel("0");
    private JLabel lblTotalAtendimentos = new JLabel("0");
    private JLabel lblMediaCusto = new JLabel("R$ 0,00");

    public Main() {
        setTitle("VetCare - Sistema de Gestão e Dashboard");
        setSize(1050, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("1. Gestão (CRUD)", criarPainelCRUD());
        abas.addTab("2. Consultas e Views", criarPainelConsultas());
        abas.addTab("3. Procedures e Triggers", criarPainelProcedures());
        abas.addTab("4. Dashboard Estatístico", criarPainelDashboard());

        add(abas);
        atualizarIndicadores();
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
                atualizarIndicadores();
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
                atualizarIndicadores();
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
                atualizarIndicadores();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnDeletarAnimal.addActionListener(e -> {
            try {
                new AnimalDAO().deletar(Integer.parseInt(txtAnimalCodigo.getText().trim()));
                JOptionPane.showMessageDialog(this, "Animal removido com sucesso!");
                atualizarIndicadores();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        painelPrincipal.add(painelDono);
        painelPrincipal.add(painelAnimal);
        return painelPrincipal;
    }

    private JPanel criarPainelConsultas(){
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel superior com o seletor de Consultas e Views
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Visualização de Consultas e Views"));

        JComboBox<String> comboConsultas = new JComboBox<>(new String[]{
                // Etapa 3
                "1. Animais Caninos (Filtro)",
                "2. Atendimentos com JOIN Completo",
                "3. Agregação por Espécie",
                "4. Veterinários Acima da Média de Custo",
                // Etapa 4 (Consultas Avançadas)
                "5. Donos sem Animais (Anti Join)",
                "6. Animais e Donos (Full Outer Join / UNION)",
                "7. Atendimentos com Custo Acima da Média (Subconsulta)",
                "8. Funcionários com Atendimentos Críticos (Subconsulta IN)",
                // Views
                "9. VIEW: Prontuário Completo de Atendimentos",
                "10. VIEW: Prescrições Detalhadas por Animal"
        });

        JButton btnExecutar = new JButton("Executar Consulta / View");
        painelTopo.add(new JLabel("Selecione:"));
        painelTopo.add(comboConsultas);
        painelTopo.add(btnExecutar);

        modeloTabela = new DefaultTableModel();
        tabelaConsultas = new JTable(modeloTabela);

        // Ação do botão para rodar a query correspondente
        btnExecutar.addActionListener(e -> {
            int escolha = comboConsultas.getSelectedIndex();
            String sql = "";

            switch (escolha) {
                // Etapa 3
                case 0:
                    sql = "SELECT Codigo, Nome, Especie, Raca FROM Animal WHERE Especie = 'Canino' ORDER BY Nome ASC";
                    break;
                case 1:
                    sql = "SELECT a.Codigo AS ID, an.Nome AS Animal, d.Nome AS Dono, f.Nome AS Veterinario, a.Custo " +
                            "FROM Atendimento a " +
                            "JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo " +
                            "JOIN Dono d ON an.fk_Dono_CPF = d.CPF " +
                            "JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF";
                    break;
                case 2:
                    sql = "SELECT an.Especie, COUNT(a.Codigo) AS Total_Atendimentos, SUM(a.Custo) AS Total_Faturado " +
                            "FROM Atendimento a JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo GROUP BY an.Especie";
                    break;
                case 3:
                    sql = "SELECT f.Nome AS Veterinario, COUNT(a.Codigo) AS Qtd, SUM(a.Custo) AS Total " +
                            "FROM Funcionario f JOIN Atendimento a ON f.CPF = a.fk_Funcionario_CPF " +
                            "GROUP BY f.CPF, f.Nome HAVING SUM(a.Custo) > (SELECT AVG(Custo) FROM Atendimento)";
                    break;
                // Etapa 4 - Consultas Avançadas
                case 4: // Anti Join
                    sql = "SELECT d.CPF, d.Nome, d.Cidade FROM Dono d LEFT JOIN Animal a ON d.CPF = a.fk_Dono_CPF WHERE a.Codigo IS NULL";
                    break;
                case 5: // Full Outer Join simulado com UNION
                    sql = "SELECT a.Nome AS Nome_Animal, a.Especie, d.Nome AS Nome_Dono FROM Animal a LEFT JOIN Dono d ON a.fk_Dono_CPF = d.CPF " +
                            "UNION " +
                            "SELECT a.Nome AS Nome_Animal, a.Especie, d.Nome AS Nome_Dono FROM Animal a RIGHT JOIN Dono d ON a.fk_Dono_CPF = d.CPF";
                    break;
                case 6: // Subconsulta
                    sql = "SELECT Codigo, Diagnostico, Custo FROM Atendimento WHERE Custo > (SELECT AVG(Custo) FROM Atendimento)";
                    break;
                case 7: // Subconsulta IN
                    sql = "SELECT CPF, Nome, Telefone FROM Funcionario WHERE CPF IN (SELECT fk_Funcionario_CPF FROM Atendimento WHERE Nivel_de_Gravidade = 'Crítica')";
                    break;
                // Views
                case 8:
                    sql = "SELECT * FROM vw_Prontuario_Completo";
                    break;
                case 9:
                    sql = "SELECT * FROM vw_Presricoes_Detalhadas";
                    break;
            }

            if (!sql.isEmpty()) {
                executarSQL(sql);
            }
        });

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaConsultas), BorderLayout.CENTER);
        return painel;
    }


    private JPanel criarPainelProcedures() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Execução de Procedures Armazenadas (Etapa 5)"));

        JComboBox<String> comboProcedures = new JComboBox<>(new String[]{
                "Resumo Animal",
                "Atualizar Telefone Dono"
        });

        painelTopo.add(new JLabel("Selecione a Procedure:"));
        painelTopo.add(comboProcedures);

        JPanel painelFormulario = new JPanel(new CardLayout());

        // Formulário 1: Resumo Animal
        JPanel cardResumo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCodAnimal = new JTextField(5);
        JButton btnExecResumo = new JButton("Executar Resumo");
        cardResumo.add(new JLabel("Código do Animal (vazio para todos):"));
        cardResumo.add(txtCodAnimal);
        cardResumo.add(btnExecResumo);

        // Formulário 2: Atualizar Telefone
        JPanel cardUpdate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCpfDono = new JTextField(11);
        JTextField txtNovoTel = new JTextField(10);
        JButton btnExecUpdate = new JButton("Atualizar Telefone");
        cardUpdate.add(new JLabel("CPF do Dono:"));
        cardUpdate.add(txtCpfDono);
        cardUpdate.add(new JLabel("Novo Telefone:"));
        cardUpdate.add(txtNovoTel);
        cardUpdate.add(btnExecUpdate);

        CardLayout cl = (CardLayout) painelFormulario.getLayout();
        painelFormulario.add(cardResumo, "Resumo Animal");
        painelFormulario.add(cardUpdate, "Atualizar Telefone Dono");

        comboProcedures.addActionListener(e -> {
            String selecionado = (String) comboProcedures.getSelectedItem();
            cl.show(painelFormulario, selecionado);
        });

        DefaultTableModel modeloProc = new DefaultTableModel();
        JTable tabelaProcResult = new JTable(modeloProc);

        btnExecResumo.addActionListener(e -> {
            String codigo = txtCodAnimal.getText().trim();
            String sql = codigo.isEmpty() ? "{CALL sp_RelatorioResumoAnimal(NULL)}" : "{CALL sp_RelatorioResumoAnimal(" + codigo + ")}";

            try (Connection conn = Conexao.getConexao();
                 CallableStatement stmt = conn.prepareCall(sql);
                 ResultSet rs = stmt.executeQuery()) {

                modeloProc.setRowCount(0);
                modeloProc.setColumnCount(0);
                ResultSetMetaData metaData = rs.getMetaData();
                int cols = metaData.getColumnCount();
                for (int i = 1; i <= cols; i++) modeloProc.addColumn(metaData.getColumnName(i));
                while (rs.next()) {
                    Object[] row = new Object[cols];
                    for (int i = 1; i <= cols; i++) row[i - 1] = rs.getObject(i);
                    modeloProc.addRow(row);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao executar procedure: " + ex.getMessage());
            }
        });

        // Ação do botão de Atualizar Telefone (sp_AtualizarTelefoneDono)
        btnExecUpdate.addActionListener(e -> {
            String cpf = txtCpfDono.getText().trim();
            String novoTel = txtNovoTel.getText().trim();

            if (cpf.isEmpty() || novoTel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o CPF e o novo telefone!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sqlCall = "{CALL sp_AtualizarTelefoneDono(?, ?)}";
            try (Connection conn = Conexao.getConexao();
                 CallableStatement stmt = conn.prepareCall(sqlCall)) {

                stmt.setString(1, cpf);
                stmt.setString(2, novoTel);
                stmt.execute();

                // --> É ESTA MENSAGEM QUE VOCÊ VAI COLAR/ATUALIZAR NO SEU CÓDIGO <--
                JOptionPane.showMessageDialog(this,
                        "Sucesso! O telefone do dono (CPF: " + cpf + ") foi alterado para: " + novoTel,
                        "Atualização Realizada",
                        JOptionPane.INFORMATION_MESSAGE);

                // Limpa os campos após o sucesso
                txtCpfDono.setText("");
                txtNovoTel.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar telefone: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.add(painelTopo, BorderLayout.NORTH);
        painelNorte.add(painelFormulario, BorderLayout.SOUTH);

        painel.add(painelNorte, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaProcResult), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelDashboard() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCards = new JPanel(new GridLayout(1, 4, 10, 10));
        painelCards.add(criarCard("Total de Clientes", lblTotalClientes));
        painelCards.add(criarCard("Total de Animais", lblTotalAnimais));
        painelCards.add(criarCard("Total Atendimentos", lblTotalAtendimentos));
        painelCards.add(criarCard("Média de Custo", lblMediaCusto));

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
                Image img = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
                labelImagem.setIcon(new ImageIcon(img));
                labelImagem.setText("");
            } else {
                labelImagem.setIcon(null);
                labelImagem.setText("O ficheiro '" + ficheiros[index] + "' não foi encontrado na pasta 'graficos'.");
            }
        };

        comboGraficos.addActionListener(e -> carregarImagem.run());
        carregarImagem.run();

        JPanel painelTopoGrafico = new JPanel(new FlowLayout());
        painelTopoGrafico.add(new JLabel("Selecione o Gráfico Estatístico:"));
        painelTopoGrafico.add(comboGraficos);

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.add(painelTopoGrafico, BorderLayout.NORTH);
        painelCentro.add(new JScrollPane(labelImagem), BorderLayout.CENTER);

        painel.add(painelCards, BorderLayout.NORTH);
        painel.add(painelCentro, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarCard(String titulo, JLabel lblValor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(titulo));
        lblValor.setFont(new Font("Arial", Font.BOLD, 18));
        lblValor.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }

    private void atualizarIndicadores() {
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement()) {

            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM Dono");
            if (rs1.next()) lblTotalClientes.setText(rs1.getString(1));

            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM Animal");
            if (rs2.next()) lblTotalAnimais.setText(rs2.getString(1));

            ResultSet rs3 = stmt.executeQuery("SELECT COUNT(*) FROM Atendimento");
            if (rs3.next()) lblTotalAtendimentos.setText(rs3.getString(1));

            ResultSet rs4 = stmt.executeQuery("SELECT AVG(Custo) FROM Atendimento");
            if (rs4.next()) {
                double media = rs4.getDouble(1);
                lblMediaCusto.setText(String.format("R$ %.2f", media));
            }
        } catch (Exception ignored) {}
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