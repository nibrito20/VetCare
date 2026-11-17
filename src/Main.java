import conexao.Conexao;
import dao.AnimalDAO;
import dao.DonoDAO;
import model.Animal;
import model.Dono;
import ui.Tema;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Main extends JFrame {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    // ---------- Campos do CRUD ----------
    private JTextField txtDonoCpf, txtDonoNome, txtDonoTelefone, txtDonoCidade, txtDonoRua, txtDonoBairro, txtDonoNumero;
    private JTextField txtAnimalCodigo, txtAnimalNome, txtAnimalEspecie, txtAnimalRaca, txtAnimalIdade, txtAnimalDia, txtAnimalMes, txtAnimalAno, txtAnimalSexo, txtAnimalCpfDono;

    // ---------- Consultas ----------
    private final DefaultTableModel modeloTabela = modeloSomenteLeitura();
    private final JLabel lblResultados = Tema.textoSuave(" ");
    private Tema.Botao btnExecutarConsulta;

    private static final String[] CONSULTAS = {
            "1. Animais Caninos (Filtro)",
            "2. Atendimentos com JOIN Completo",
            "3. Agregação por Espécie",
            "4. Veterinários Acima da Média de Custo",
            "5. Donos sem Animais (Anti Join)",
            "6. Animais e Donos (Full Outer Join / UNION)",
            "7. Atendimentos com Custo Acima da Média (Subconsulta)",
            "8. Funcionários com Atendimentos Críticos (Subconsulta IN)",
            "9. VIEW: Prontuário Completo de Atendimentos",
            "10. VIEW: Prescrições Detalhadas por Animal"
    };

    private static final String[] CONSULTAS_DESCRICAO = {
            "Lista os animais da espécie Canino, em ordem alfabética.",
            "Mostra cada atendimento com o animal, o dono, o veterinário e o custo.",
            "Conta os atendimentos e soma o valor faturado em cada espécie.",
            "Veterinários cujo total faturado é maior que o custo médio de um atendimento.",
            "Donos que ainda não têm nenhum animal cadastrado.",
            "Todos os animais e todos os donos, inclusive animais sem dono e donos sem animal.",
            "Atendimentos mais caros que a média geral de custo.",
            "Funcionários que realizaram pelo menos um atendimento de gravidade crítica.",
            "View com o prontuário completo de cada atendimento.",
            "View com as prescrições detalhadas de cada animal."
    };

    private static final String[] CONSULTAS_SQL = {
            "SELECT Codigo, Nome, Especie, Raca FROM Animal WHERE Especie = 'Canino' ORDER BY Nome ASC",

            "SELECT a.Codigo AS ID, an.Nome AS Animal, d.Nome AS Dono, f.Nome AS Veterinario, a.Custo " +
                    "FROM Atendimento a " +
                    "JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo " +
                    "JOIN Dono d ON an.fk_Dono_CPF = d.CPF " +
                    "JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF",

            "SELECT an.Especie, COUNT(a.Codigo) AS Total_Atendimentos, SUM(a.Custo) AS Total_Faturado " +
                    "FROM Atendimento a JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo GROUP BY an.Especie",

            "SELECT f.Nome AS Veterinario, COUNT(a.Codigo) AS Qtd, SUM(a.Custo) AS Total " +
                    "FROM Funcionario f JOIN Atendimento a ON f.CPF = a.fk_Funcionario_CPF " +
                    "GROUP BY f.CPF, f.Nome HAVING SUM(a.Custo) > (SELECT AVG(Custo) FROM Atendimento)",

            "SELECT d.CPF, d.Nome, d.Cidade FROM Dono d LEFT JOIN Animal a ON d.CPF = a.fk_Dono_CPF WHERE a.Codigo IS NULL",

            "SELECT a.Nome AS Nome_Animal, a.Especie, d.Nome AS Nome_Dono FROM Animal a LEFT JOIN Dono d ON a.fk_Dono_CPF = d.CPF " +
                    "UNION " +
                    "SELECT a.Nome AS Nome_Animal, a.Especie, d.Nome AS Nome_Dono FROM Animal a RIGHT JOIN Dono d ON a.fk_Dono_CPF = d.CPF",

            "SELECT Codigo, Diagnostico, Custo FROM Atendimento WHERE Custo > (SELECT AVG(Custo) FROM Atendimento)",

            "SELECT CPF, Nome, Telefone FROM Funcionario WHERE CPF IN (SELECT fk_Funcionario_CPF FROM Atendimento WHERE Nivel_de_Gravidade = 'Crítica')",

            "SELECT * FROM vw_Prontuario_Completo",

            "SELECT * FROM vw_Presricoes_Detalhadas"
    };

    // ---------- Início: últimos atendimentos ----------
    // Ordena pelo código (maior = mais recente). Se a tabela tiver uma coluna de data,
    // troque o ORDER BY por ela (ex.: ORDER BY a.Data DESC).
    private static final String SQL_RECENTES =
            "SELECT a.Codigo AS Codigo, an.Nome AS Animal, d.Nome AS Dono, f.Nome AS Veterinario, " +
                    "a.Diagnostico AS Diagnostico, a.Nivel_de_Gravidade AS Gravidade, a.Custo AS Custo " +
                    "FROM Atendimento a " +
                    "JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo " +
                    "JOIN Dono d ON an.fk_Dono_CPF = d.CPF " +
                    "JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF " +
                    "ORDER BY a.Codigo DESC LIMIT 8";

    private final DefaultTableModel modeloRecentes = modeloSomenteLeitura();
    private final JLabel lblRecentes = Tema.textoSuave(" ");

    // ---------- Dashboard ----------
    private final List<JLabel> lblsClientes = new ArrayList<>();
    private final List<JLabel> lblsAnimais = new ArrayList<>();
    private final List<JLabel> lblsAtendimentos = new ArrayList<>();
    private final List<JLabel> lblsMedia = new ArrayList<>();

    // ---------- Estado da conexão ----------
    private final Tema.IndicadorStatus statusBanco = new Tema.IndicadorStatus();
    private Boolean bancoOnline = null;     // null = ainda não verificado
    private int versaoIndicadores = 0;      // descarta respostas antigas

    // ---------- Navegação ----------
    private static final String[] IDS = {"inicio", "crud", "consultas", "procedures", "dashboard"};
    private static final String[] MENU = {"Início", "Gestão (CRUD)", "Consultas e Views", "Procedures", "Dashboard"};
    private static final Tema.Icone[] ICONES = {Tema.Icone.INICIO, Tema.Icone.CADASTRO,
            Tema.Icone.CONSULTAS, Tema.Icone.PROCEDURES, Tema.Icone.DASHBOARD};
    // O índice 0 (Início) não exibe cabeçalho: a própria tela já tem a faixa de boas-vindas.
    private static final String[] TITULOS = {
            "Início",
            "Gestão de Donos e Animais",
            "Consultas e Views",
            "Procedures Armazenadas",
            "Dashboard Estatístico"};
    private static final String[] SUBTITULOS = {
            "Visão geral da clínica e atalhos para as principais telas.",
            "Cadastre, atualize e remova donos e animais da clínica.",
            "Execute consultas SQL avançadas e visualize as views do banco.",
            "Execute procedures armazenadas diretamente do sistema.",
            "Indicadores gerais e gráficos de distribuição dos atendimentos."};

    // Menu lateral (recolhível)
    private static final int LARGURA_ABERTA = 270;
    private static final int LARGURA_FECHADA = 84;
    private final List<Tema.BotaoMenu> botoesMenu = new ArrayList<>();
    private JPanel sidebar, topoSidebar, rodapeSidebar, cabecalho;
    private JLabel logoCompleta, logoMini, lblSecaoMenu;
    private boolean recolhido = false;
    private int paginaAtual = 0;
    private javax.swing.Timer animacao;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel areaConteudo = new JPanel(cardLayout);
    private final JLabel lblTitulo = new JLabel();
    private final JLabel lblSubtitulo = Tema.textoSuave(" ");

    public Main() {
        setTitle("VetCare - Sistema de Gerenciamento");
        setSize(1240, 800);
        setMinimumSize(new Dimension(1050, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Tema.FUNDO);
        setLayout(new BorderLayout());

        areaConteudo.setOpaque(false);
        areaConteudo.setBorder(BorderFactory.createEmptyBorder(0, 32, 28, 32));
        areaConteudo.add(criarPainelInicio(), IDS[0]);
        areaConteudo.add(criarPainelCRUD(), IDS[1]);
        areaConteudo.add(criarPainelConsultas(), IDS[2]);
        areaConteudo.add(criarPainelProcedures(), IDS[3]);
        areaConteudo.add(criarPainelDashboard(), IDS[4]);

        cabecalho = criarCabecalho();
        JPanel direita = Tema.painel(new BorderLayout());
        direita.add(cabecalho, BorderLayout.NORTH);
        direita.add(areaConteudo, BorderLayout.CENTER);

        add(criarSidebar(), BorderLayout.WEST);
        add(direita, BorderLayout.CENTER);

        registrarAtalhos();
        navegar(0); // já dispara atualizarIndicadores()

        // Reverifica a conexão de tempos em tempos (e recarrega os dados quando ela volta)
        new javax.swing.Timer(15000, e -> verificarConexao()).start();
    }

    // =====================================================================
    //  Estrutura: sidebar + cabeçalho
    // =====================================================================
    private JPanel criarSidebar() {
        sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(LARGURA_ABERTA, 0));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Tema.BORDA),
                BorderFactory.createEmptyBorder(20, 14, 20, 14)));

        // Topo: botão "sanduíche"
        Tema.BotaoHamburguer hamburguer = new Tema.BotaoHamburguer();
        hamburguer.addActionListener(e -> alternarSidebar());
        topoSidebar = Tema.painel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topoSidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        topoSidebar.add(hamburguer);

        // Centro: itens do menu
        JPanel menu = Tema.painel(null);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        lblSecaoMenu = Tema.rotulo("MENU");
        lblSecaoMenu.setBorder(BorderFactory.createEmptyBorder(0, 6, 10, 0));
        lblSecaoMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(lblSecaoMenu);

        ButtonGroup grupo = new ButtonGroup();
        for (int i = 0; i < MENU.length; i++) {
            final int indice = i;
            Tema.BotaoMenu b = new Tema.BotaoMenu(MENU[i], ICONES[i]);
            b.setSelected(i == 0);
            b.addActionListener(e -> navegar(indice));
            grupo.add(b);
            botoesMenu.add(b);
            menu.add(b);
            menu.add(Box.createVerticalStrut(4));
        }

        // Rodapé: logo completa (menu aberto) ou só o símbolo (menu recolhido)
        logoCompleta = Tema.logo(206);
        logoMini = Tema.logoIcone(44);
        rodapeSidebar = Tema.painel(new BorderLayout());
        rodapeSidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.BORDA),
                BorderFactory.createEmptyBorder(16, 0, 0, 0)));
        rodapeSidebar.add(logoCompleta, BorderLayout.CENTER);

        // Indicador "banco conectado" (clique = verificar de novo)
        statusBanco.aoClicar(() -> {
            statusBanco.setEstado(Tema.IndicadorStatus.Estado.VERIFICANDO);
            verificarConexao();
        });
        JPanel sul = Tema.painel(new BorderLayout(0, 12));
        sul.add(statusBanco, BorderLayout.NORTH);
        sul.add(rodapeSidebar, BorderLayout.CENTER);

        sidebar.add(topoSidebar, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);
        sidebar.add(sul, BorderLayout.SOUTH);
        return sidebar;
    }

    private void alternarSidebar() {
        if (animacao != null && animacao.isRunning()) return;
        recolhido = !recolhido;
        aplicarModoSidebar();

        final int alvo = recolhido ? LARGURA_FECHADA : LARGURA_ABERTA;
        animacao = new javax.swing.Timer(12, null);
        animacao.addActionListener(e -> {
            int atual = sidebar.getPreferredSize().width;
            int novo = atual < alvo ? Math.min(atual + 30, alvo) : Math.max(atual - 30, alvo);
            sidebar.setPreferredSize(new Dimension(novo, 0));
            sidebar.revalidate();
            sidebar.repaint();
            if (novo == alvo) animacao.stop();
        });
        animacao.start();
    }

    private void aplicarModoSidebar() {
        for (Tema.BotaoMenu b : botoesMenu) b.setRecolhido(recolhido);
        statusBanco.setRecolhido(recolhido);
        lblSecaoMenu.setVisible(!recolhido);
        ((FlowLayout) topoSidebar.getLayout()).setAlignment(recolhido ? FlowLayout.CENTER : FlowLayout.LEFT);
        topoSidebar.revalidate();
        rodapeSidebar.removeAll();
        // Recolhido: se o símbolo da logo já está no topo (botão), o rodapé fica vazio
        if (!recolhido) rodapeSidebar.add(logoCompleta, BorderLayout.CENTER);
        else if (!Tema.temLogoIcone()) rodapeSidebar.add(logoMini, BorderLayout.CENTER);
        rodapeSidebar.revalidate();
        rodapeSidebar.repaint();
    }

    private JPanel criarCabecalho() {
        JPanel cab = Tema.painel(new BorderLayout(0, 4));
        cab.setBorder(BorderFactory.createEmptyBorder(26, 32, 20, 32));
        lblTitulo.setFont(Tema.FONTE_TITULO);
        lblTitulo.setForeground(Tema.COR_ESCURA);
        cab.add(lblTitulo, BorderLayout.NORTH);
        cab.add(lblSubtitulo, BorderLayout.CENTER);
        return cab;
    }

    private void navegar(int i) {
        paginaAtual = i;
        cardLayout.show(areaConteudo, IDS[i]);
        botoesMenu.get(i).setSelected(true);
        lblTitulo.setText(TITULOS[i]);
        lblSubtitulo.setText(SUBTITULOS[i]);
        cabecalho.setVisible(i != 0); // o Início não repete título/subtítulo
        if (i == 0 || i == 4) atualizarIndicadores();
    }

    // =====================================================================
    //  Atalhos de teclado
    //    Cmd/Ctrl + 1..5 : trocar de tela      Cmd/Ctrl + B : abrir/fechar menu
    //    F5              : executar consulta (Consultas) ou atualizar dados (Início/Dashboard)
    // =====================================================================
    private void registrarAtalhos() {
        int mod = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        for (int i = 0; i < IDS.length; i++) {
            final int destino = i;
            String chave = "ir-" + IDS[i];
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1 + i, mod), chave);
            am.put(chave, acao(() -> navegar(destino)));
        }

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, mod), "alternar-menu");
        am.put("alternar-menu", acao(this::alternarSidebar));

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "atualizar");
        am.put("atualizar", acao(() -> {
            if (paginaAtual == 2) btnExecutarConsulta.doClick();
            else if (paginaAtual == 0 || paginaAtual == 4) atualizarIndicadores();
        }));
    }

    private static Action acao(Runnable r) {
        return new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { r.run(); }
        };
    }

    // =====================================================================
    //  0. Início
    // =====================================================================
    private JPanel criarPainelInicio() {
        JPanel painel = Tema.painel(new BorderLayout(0, 22));
        // Sem cabeçalho nesta tela, então o respiro do topo vem daqui
        painel.setBorder(BorderFactory.createEmptyBorder(26, 0, 0, 0));

        // Faixa de boas-vindas
        Tema.Destaque hero = new Tema.Destaque(new BorderLayout(), 28);
        JLabel data = new JLabel(dataExtenso().toUpperCase());
        data.setFont(Tema.FONTE_PEQUENA);
        data.setForeground(new Color(0xF5B5B0));
        JLabel ola = new JLabel("Bem-vindo ao VetCare");
        ola.setFont(new Font("SansSerif", Font.BOLD, 30));
        ola.setForeground(Color.WHITE);
        JLabel desc = new JLabel("Gerencie donos, animais e atendimentos da clínica em um só lugar.");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 15));
        desc.setForeground(new Color(0xFBDCDA));
        JPanel textos = Tema.painel(new GridLayout(3, 1, 0, 6));
        textos.add(data);
        textos.add(ola);
        textos.add(desc);
        hero.add(textos, BorderLayout.CENTER);

        // Atalhos
        JPanel acesso = Tema.painel(new BorderLayout(0, 14));
        acesso.add(Tema.titulo("Acesso rápido"), BorderLayout.NORTH);
        JPanel grade = Tema.painel(new GridLayout(1, 4, 16, 0));
        grade.add(cartaoAcesso("Cadastrar dono", "Registrar um novo tutor", Tema.Icone.PESSOA, 1));
        grade.add(cartaoAcesso("Cadastrar animal", "Abrir ficha de paciente", Tema.Icone.PATA, 1));
        grade.add(cartaoAcesso("Consultas e Views", "Relatórios e consultas SQL", Tema.Icone.CONSULTAS, 2));
        grade.add(cartaoAcesso("Dashboard", "Gráficos e estatísticas", Tema.Icone.DASHBOARD, 4));
        acesso.add(grade, BorderLayout.CENTER);

        // Indicadores + atalhos no topo; últimos atendimentos ocupam o espaço restante
        JPanel topo = Tema.painel(new BorderLayout(0, 22));
        topo.add(criarIndicadores(), BorderLayout.NORTH);
        topo.add(acesso, BorderLayout.CENTER);

        JPanel meio = Tema.painel(new BorderLayout(0, 22));
        meio.add(topo, BorderLayout.NORTH);
        meio.add(criarCartaoRecentes(), BorderLayout.CENTER);

        painel.add(hero, BorderLayout.NORTH);
        painel.add(meio, BorderLayout.CENTER);
        return painel;
    }

    private Tema.Cartao criarCartaoRecentes() {
        JTable tabela = new JTable(modeloRecentes);
        Tema.estilizarTabela(tabela);
        tabela.setFocusable(false);
        tabela.setRowSelectionAllowed(false);

        lblRecentes.setText("Carregando…");
        JPanel cab = Tema.painel(new GridLayout(2, 1, 0, 2));
        cab.add(Tema.titulo("Últimos atendimentos"));
        cab.add(lblRecentes);

        Tema.Cartao cartao = new Tema.Cartao(new BorderLayout(0, 12), 20);
        cartao.add(cab, BorderLayout.NORTH);
        cartao.add(Tema.scroll(tabela), BorderLayout.CENTER);
        return cartao;
    }

    private Tema.Cartao cartaoAcesso(String titulo, String descricao, Tema.Icone icone, int destino) {
        Tema.Cartao c = new Tema.Cartao(new BorderLayout(0, 16), 20);
        JPanel linhaIcone = Tema.painel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linhaIcone.add(Tema.distintivo(icone, Tema.COR));
        JPanel textos = Tema.painel(new GridLayout(2, 1, 0, 3));
        JLabel t = new JLabel(titulo);
        t.setFont(Tema.FONTE_NEGRITO);
        t.setForeground(Tema.COR_ESCURA);
        textos.add(t);
        textos.add(Tema.textoSuave(descricao));
        c.add(linhaIcone, BorderLayout.NORTH);
        c.add(textos, BorderLayout.CENTER);
        return c.clicavel(() -> navegar(destino));
    }

    private String dataExtenso() {
        String s = LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", PT_BR));
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private JPanel criarIndicadores() {
        JPanel cards = Tema.painel(new GridLayout(1, 4, 16, 0));
        cards.add(criarCard("Total de Clientes", novoLabel(lblsClientes, "—"), Tema.COR));
        cards.add(criarCard("Total de Animais", novoLabel(lblsAnimais, "—"), Tema.COR_CLARA));
        cards.add(criarCard("Total de Atendimentos", novoLabel(lblsAtendimentos, "—"), Tema.COR_ESCURA));
        cards.add(criarCard("Média de Custo", novoLabel(lblsMedia, "—"), Tema.SUCESSO));
        return cards;
    }

    private JLabel novoLabel(List<JLabel> lista, String inicial) {
        JLabel l = new JLabel(inicial);
        lista.add(l);
        return l;
    }

    private void definir(List<JLabel> labels, String texto) {
        for (JLabel l : labels) l.setText(texto);
    }

    // =====================================================================
    //  1. CRUD
    // =====================================================================
    private JPanel criarPainelCRUD() {
        JPanel principal = Tema.painel(new GridLayout(1, 2, 20, 0));

        // ----- Donos -----
        txtDonoCpf = Tema.campo(10);
        txtDonoNome = Tema.campo(10);
        txtDonoTelefone = Tema.campo(10);
        txtDonoCidade = Tema.campo(10);
        txtDonoRua = Tema.campo(10);
        txtDonoBairro = Tema.campo(10);
        txtDonoNumero = Tema.campo(10);

        JPanel formDono = novoForm();
        campo(formDono, "CPF", txtDonoCpf, 0, 0);
        campo(formDono, "NOME", txtDonoNome, 1, 0);
        campo(formDono, "TELEFONE", txtDonoTelefone, 0, 1);
        campo(formDono, "CIDADE", txtDonoCidade, 1, 1);
        campo(formDono, "RUA", txtDonoRua, 0, 2);
        campo(formDono, "BAIRRO", txtDonoBairro, 1, 2);
        campo(formDono, "NÚMERO", txtDonoNumero, 0, 3);

        Tema.Botao btnSalvarDono = new Tema.Botao("Inserir", Tema.Tipo.PRIMARIO);
        Tema.Botao btnAtualizarDono = new Tema.Botao("Atualizar", Tema.Tipo.SECUNDARIO);
        Tema.Botao btnDeletarDono = new Tema.Botao("Eliminar", Tema.Tipo.PERIGO);
        Tema.Botao btnLimparDono = new Tema.Botao("Limpar", Tema.Tipo.SECUNDARIO);

        principal.add(cartaoFormulario("Donos", "Dados do tutor do animal  •  Enter para inserir", formDono,
                btnLimparDono, btnDeletarDono, btnAtualizarDono, btnSalvarDono));

        // ----- Animais -----
        txtAnimalCodigo = Tema.campo(10);
        txtAnimalNome = Tema.campo(10);
        txtAnimalEspecie = Tema.campo(10);
        txtAnimalRaca = Tema.campo(10);
        txtAnimalIdade = Tema.campo(10);
        txtAnimalDia = Tema.campo(10);
        txtAnimalMes = Tema.campo(10);
        txtAnimalAno = Tema.campo(10);
        txtAnimalSexo = Tema.campo(10);
        txtAnimalCpfDono = Tema.campo(10);

        JPanel formAnimal = novoForm();
        campo(formAnimal, "NOME", txtAnimalNome, 0, 0);
        campo(formAnimal, "ESPÉCIE", txtAnimalEspecie, 1, 0);
        campo(formAnimal, "RAÇA", txtAnimalRaca, 0, 1);
        campo(formAnimal, "SEXO (M/F)", txtAnimalSexo, 1, 1);
        campo(formAnimal, "IDADE", txtAnimalIdade, 0, 2);
        campo(formAnimal, "CPF DO DONO", txtAnimalCpfDono, 1, 2);
        campo(formAnimal, "DIA NASC.", txtAnimalDia, 0, 3);
        campo(formAnimal, "MÊS NASC.", txtAnimalMes, 1, 3);
        campo(formAnimal, "ANO NASC.", txtAnimalAno, 0, 4);
        campo(formAnimal, "CÓDIGO (PARA ELIMINAR)", txtAnimalCodigo, 1, 4);

        Tema.Botao btnSalvarAnimal = new Tema.Botao("Inserir", Tema.Tipo.PRIMARIO);
        Tema.Botao btnDeletarAnimal = new Tema.Botao("Eliminar", Tema.Tipo.PERIGO);
        Tema.Botao btnLimparAnimal = new Tema.Botao("Limpar", Tema.Tipo.SECUNDARIO);

        principal.add(cartaoFormulario("Animais", "Ficha do paciente  •  Enter para inserir", formAnimal,
                btnLimparAnimal, btnDeletarAnimal, btnSalvarAnimal));

        // ----- Enter nos campos = Inserir -----
        for (JTextField f : new JTextField[]{txtDonoCpf, txtDonoNome, txtDonoTelefone,
                txtDonoCidade, txtDonoRua, txtDonoBairro, txtDonoNumero}) {
            f.addActionListener(e -> btnSalvarDono.doClick());
        }
        // (o campo "Código para eliminar" fica de fora de propósito, para o Enter nunca apagar/inserir sem querer)
        for (JTextField f : new JTextField[]{txtAnimalNome, txtAnimalEspecie, txtAnimalRaca, txtAnimalSexo,
                txtAnimalIdade, txtAnimalCpfDono, txtAnimalDia, txtAnimalMes, txtAnimalAno}) {
            f.addActionListener(e -> btnSalvarAnimal.doClick());
        }

        // ----- Ações (lógica original mantida) -----
        btnSalvarDono.addActionListener(e -> {
            try {
                new DonoDAO().inserir(lerDono());
                JOptionPane.showMessageDialog(this, "Dono inserido com sucesso!");
                atualizarIndicadores();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
        });

        btnAtualizarDono.addActionListener(e -> {
            try {
                new DonoDAO().atualizar(lerDono());
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

        btnLimparDono.addActionListener(e -> limpar(txtDonoCpf, txtDonoNome, txtDonoTelefone,
                txtDonoCidade, txtDonoRua, txtDonoBairro, txtDonoNumero));

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

        btnLimparAnimal.addActionListener(e -> limpar(txtAnimalCodigo, txtAnimalNome, txtAnimalEspecie,
                txtAnimalRaca, txtAnimalIdade, txtAnimalDia, txtAnimalMes, txtAnimalAno,
                txtAnimalSexo, txtAnimalCpfDono));

        return principal;
    }

    private Dono lerDono() {
        Dono d = new Dono();
        d.setCpf(txtDonoCpf.getText());
        d.setNome(txtDonoNome.getText());
        d.setTelefone(txtDonoTelefone.getText());
        d.setCidade(txtDonoCidade.getText());
        d.setRua(txtDonoRua.getText());
        d.setBairro(txtDonoBairro.getText());
        d.setNumero(txtDonoNumero.getText());
        return d;
    }

    private void limpar(JTextField... campos) {
        for (JTextField c : campos) c.setText("");
    }

    private JPanel novoForm() {
        return Tema.painel(new GridBagLayout());
    }

    /** Adiciona "rótulo + campo" em uma grade de 2 colunas. */
    private void campo(JPanel form, String rotulo, JTextField campo, int coluna, int linha) {
        int esq = coluna == 0 ? 0 : 8;
        int dir = coluna == 0 ? 8 : 0;
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = coluna;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridy = linha * 2;
        g.insets = new Insets(linha == 0 ? 0 : 14, esq, 5, dir);
        form.add(Tema.rotulo(rotulo), g);

        g.gridy = linha * 2 + 1;
        g.insets = new Insets(0, esq, 0, dir);
        form.add(campo, g);
    }

    private Tema.Cartao cartaoFormulario(String titulo, String subtitulo, JPanel form, JButton... botoes) {
        Tema.Cartao cartao = new Tema.Cartao(new BorderLayout(0, 18), 24);

        JPanel cab = Tema.painel(new GridLayout(2, 1, 0, 2));
        cab.add(Tema.titulo(titulo));
        cab.add(Tema.textoSuave(subtitulo));

        JPanel centro = Tema.painel(new BorderLayout());
        centro.add(form, BorderLayout.NORTH);

        JPanel acoes = Tema.painel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        for (JButton b : botoes) acoes.add(b);

        cartao.add(cab, BorderLayout.NORTH);
        cartao.add(centro, BorderLayout.CENTER);
        cartao.add(acoes, BorderLayout.SOUTH);
        return cartao;
    }

    // =====================================================================
    //  2. Consultas e Views
    // =====================================================================
    private JPanel criarPainelConsultas() {
        JPanel painel = Tema.painel(new BorderLayout(0, 18));

        JComboBox<String> comboConsultas = Tema.combo(CONSULTAS);
        comboConsultas.setPreferredSize(new Dimension(480, 40));
        btnExecutarConsulta = new Tema.Botao("Executar", Tema.Tipo.PRIMARIO);
        Tema.Botao btnExportar = new Tema.Botao("Exportar CSV", Tema.Tipo.SECUNDARIO);
        btnExportar.setEnabled(false);

        // Barra: seletor + botões, e abaixo a descrição da consulta escolhida
        JPanel esquerda = Tema.painel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        esquerda.add(Tema.rotulo("SELECIONE"));
        esquerda.add(comboConsultas);
        JPanel botoes = Tema.painel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(btnExportar);
        botoes.add(btnExecutarConsulta);
        JPanel linha = Tema.painel(new BorderLayout(14, 0));
        linha.add(esquerda, BorderLayout.CENTER);
        linha.add(botoes, BorderLayout.EAST);

        JLabel lblDescricao = Tema.textoSuave(CONSULTAS_DESCRICAO[0]);
        lblDescricao.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        comboConsultas.addActionListener(e ->
                lblDescricao.setText(CONSULTAS_DESCRICAO[comboConsultas.getSelectedIndex()]));

        Tema.Cartao barra = new Tema.Cartao(new BorderLayout(0, 12), 16);
        barra.add(linha, BorderLayout.NORTH);
        barra.add(lblDescricao, BorderLayout.CENTER);

        // Tabela + rodapé (contagem de registros e barra de carregamento)
        JTable tabela = new JTable(modeloTabela);
        Tema.estilizarTabela(tabela);

        JProgressBar progresso = new JProgressBar();
        progresso.setIndeterminate(true);
        progresso.setBorderPainted(false);
        progresso.setForeground(Tema.COR);
        progresso.setBackground(Tema.COR_FUNDO);
        progresso.setPreferredSize(new Dimension(140, 6));
        progresso.setVisible(false);
        JPanel envolveProgresso = Tema.painel(new GridBagLayout());
        envolveProgresso.add(progresso);

        lblResultados.setText("Selecione uma consulta e clique em Executar (F5).");
        JPanel rodape = Tema.painel(new BorderLayout(12, 0));
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 10, 4, 10));
        rodape.add(lblResultados, BorderLayout.CENTER);
        rodape.add(envolveProgresso, BorderLayout.EAST);

        Tema.Cartao cartaoTabela = new Tema.Cartao(new BorderLayout(0, 8), 8);
        cartaoTabela.add(Tema.scroll(tabela), BorderLayout.CENTER);
        cartaoTabela.add(rodape, BorderLayout.SOUTH);

        // Execução (em segundo plano: a tela não trava em consultas lentas)
        final int[] ultima = {-1};
        final boolean[] emAndamento = {false};

        btnExecutarConsulta.addActionListener(e -> {
            if (emAndamento[0]) return;
            final int escolha = comboConsultas.getSelectedIndex();
            final String sql = CONSULTAS_SQL[escolha];
            emAndamento[0] = true;

            executarAsync(
                    () -> {
                        try (Connection conn = Conexao.getConexao();
                             PreparedStatement stmt = conn.prepareStatement(sql);
                             ResultSet rs = stmt.executeQuery()) {
                            return lerResultSet(rs);
                        }
                    },
                    dados -> {
                        emAndamento[0] = false;
                        aplicarNaTabela(modeloTabela, dados);
                        ultima[0] = escolha;
                        int n = dados.linhas.size();
                        lblResultados.setText(n + (n == 1 ? " registro encontrado" : " registros encontrados"));
                        btnExportar.setEnabled(n > 0);
                    },
                    ex -> {
                        emAndamento[0] = false;
                        lblResultados.setText("A consulta não pôde ser executada.");
                        JOptionPane.showMessageDialog(this, "Erro SQL: " + ex.getMessage());
                    },
                    carregando -> {
                        progresso.setVisible(carregando);
                        if (carregando) lblResultados.setText("Executando consulta…");
                        ocupado(carregando);
                    });
        });

        btnExportar.addActionListener(e ->
                exportarCSV(modeloTabela, String.format("consulta_%02d", Math.max(ultima[0], 0) + 1)));

        painel.add(barra, BorderLayout.NORTH);
        painel.add(cartaoTabela, BorderLayout.CENTER);
        return painel;
    }

    // =====================================================================
    //  3. Procedures
    // =====================================================================
    private JPanel criarPainelProcedures() {
        JPanel painel = Tema.painel(new BorderLayout(0, 18));

        JComboBox<String> comboProcedures = Tema.combo(new String[]{
                "Resumo Animal",
                "Atualizar Telefone Dono"
        });
        comboProcedures.setPreferredSize(new Dimension(300, 40));

        JPanel painelFormulario = Tema.painel(new CardLayout());
        CardLayout cl = (CardLayout) painelFormulario.getLayout();

        // Formulário 1: Resumo Animal
        JTextField txtCodAnimal = Tema.campo(8);
        Tema.Botao btnExecResumo = new Tema.Botao("Executar resumo", Tema.Tipo.PRIMARIO);
        JPanel cardResumo = Tema.painel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        cardResumo.add(Tema.rotulo("CÓDIGO DO ANIMAL (VAZIO PARA TODOS)"));
        cardResumo.add(txtCodAnimal);
        cardResumo.add(btnExecResumo);

        // Formulário 2: Atualizar Telefone
        JTextField txtCpfDono = Tema.campo(12);
        JTextField txtNovoTel = Tema.campo(12);
        Tema.Botao btnExecUpdate = new Tema.Botao("Atualizar telefone", Tema.Tipo.PRIMARIO);
        JPanel cardUpdate = Tema.painel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        cardUpdate.add(Tema.rotulo("CPF DO DONO"));
        cardUpdate.add(txtCpfDono);
        cardUpdate.add(Tema.rotulo("NOVO TELEFONE"));
        cardUpdate.add(txtNovoTel);
        cardUpdate.add(btnExecUpdate);

        painelFormulario.add(cardResumo, "Resumo Animal");
        painelFormulario.add(cardUpdate, "Atualizar Telefone Dono");

        comboProcedures.addActionListener(e -> cl.show(painelFormulario, (String) comboProcedures.getSelectedItem()));

        // Enter nos campos = executar
        txtCodAnimal.addActionListener(e -> btnExecResumo.doClick());
        txtCpfDono.addActionListener(e -> btnExecUpdate.doClick());
        txtNovoTel.addActionListener(e -> btnExecUpdate.doClick());

        Tema.Cartao controles = new Tema.Cartao(new BorderLayout(0, 14), 18);
        JPanel linhaCombo = Tema.painel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        linhaCombo.add(Tema.rotulo("PROCEDURE"));
        linhaCombo.add(comboProcedures);
        controles.add(linhaCombo, BorderLayout.NORTH);
        controles.add(painelFormulario, BorderLayout.CENTER);

        DefaultTableModel modeloProc = modeloSomenteLeitura();
        JTable tabelaProcResult = new JTable(modeloProc);
        Tema.estilizarTabela(tabelaProcResult);
        Tema.Cartao cartaoTabela = new Tema.Cartao(new BorderLayout(), 8);
        cartaoTabela.add(Tema.scroll(tabelaProcResult), BorderLayout.CENTER);

        btnExecResumo.addActionListener(e -> {
            String codigo = txtCodAnimal.getText().trim();
            executarAsync(
                    () -> {
                        try (Connection conn = Conexao.getConexao();
                             CallableStatement stmt = conn.prepareCall("{CALL sp_RelatorioResumoAnimal(?)}")) {

                            if (codigo.isEmpty()) stmt.setNull(1, Types.INTEGER);
                            else stmt.setInt(1, Integer.parseInt(codigo));

                            try (ResultSet rs = stmt.executeQuery()) {
                                return lerResultSet(rs);
                            }
                        }
                    },
                    dados -> aplicarNaTabela(modeloProc, dados),
                    ex -> JOptionPane.showMessageDialog(this, "Erro ao executar procedure: " + ex.getMessage()),
                    this::ocupado);
        });

        btnExecUpdate.addActionListener(e -> {
            String cpf = txtCpfDono.getText().trim();
            String novoTel = txtNovoTel.getText().trim();

            if (cpf.isEmpty() || novoTel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o CPF e o novo telefone!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            executarAsync(
                    () -> {
                        try (Connection conn = Conexao.getConexao();
                             CallableStatement stmt = conn.prepareCall("{CALL sp_AtualizarTelefoneDono(?, ?)}")) {
                            stmt.setString(1, cpf);
                            stmt.setString(2, novoTel);
                            stmt.execute();
                        }
                        return null;
                    },
                    ok -> {
                        JOptionPane.showMessageDialog(this,
                                "Sucesso! O telefone do dono (CPF: " + cpf + ") foi alterado para: " + novoTel,
                                "Atualização Realizada",
                                JOptionPane.INFORMATION_MESSAGE);
                        txtCpfDono.setText("");
                        txtNovoTel.setText("");
                    },
                    ex -> JOptionPane.showMessageDialog(this, "Erro ao atualizar telefone: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE),
                    this::ocupado);
        });

        painel.add(controles, BorderLayout.NORTH);
        painel.add(cartaoTabela, BorderLayout.CENTER);
        return painel;
    }

    // =====================================================================
    //  4. Dashboard
    // =====================================================================
    private JPanel criarPainelDashboard() {
        JPanel painel = Tema.painel(new BorderLayout(0, 20));

        JPanel cards = criarIndicadores();

        String[] ficheiros = {"grafico1.png", "grafico2.png", "grafico3.png",
                "grafico4.png", "grafico5.png", "grafico6.png"};

        JComboBox<String> comboGraficos = Tema.combo(new String[]{
                "Gráfico 1 - Histograma Simples",
                "Gráfico 2 - Densidade KDE",
                "Gráfico 3 - Distribuição com Média e Mediana",
                "Gráfico 4 - Boxplot Simples",
                "Gráfico 5 - Boxplot Detalhado",
                "Gráfico 6 - Visão Completa (Boxplot + Histograma + KDE)"
        });
        comboGraficos.setPreferredSize(new Dimension(400, 40));

        Tema.ImagemAjustada imagem = new Tema.ImagemAjustada();
        Runnable carregarImagem = () ->
                imagem.mostrar(new File("graficos", ficheiros[comboGraficos.getSelectedIndex()]));
        comboGraficos.addActionListener(e -> carregarImagem.run());
        carregarImagem.run();

        JPanel topoGrafico = Tema.painel(new BorderLayout());
        topoGrafico.add(Tema.titulo("Análise estatística dos custos"), BorderLayout.WEST);
        topoGrafico.add(comboGraficos, BorderLayout.EAST);

        Tema.Cartao cartaoGrafico = new Tema.Cartao(new BorderLayout(0, 14), 20);
        cartaoGrafico.add(topoGrafico, BorderLayout.NORTH);
        cartaoGrafico.add(imagem, BorderLayout.CENTER);

        painel.add(cards, BorderLayout.NORTH);
        painel.add(cartaoGrafico, BorderLayout.CENTER);
        return painel;
    }

    private Tema.Cartao criarCard(String titulo, JLabel lblValor, Color cor) {
        Tema.Cartao card = new Tema.Cartao(new BorderLayout(0, 6), 18, cor);
        lblValor.setFont(Tema.FONTE_NUMERO);
        lblValor.setForeground(cor);
        lblValor.setHorizontalAlignment(SwingConstants.LEFT);
        card.add(Tema.rotulo(titulo.toUpperCase()), BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }

    // =====================================================================
    //  Estado da conexão
    // =====================================================================
    private void definirStatusBanco(boolean ok) {
        bancoOnline = ok;
        statusBanco.setEstado(ok ? Tema.IndicadorStatus.Estado.ONLINE : Tema.IndicadorStatus.Estado.OFFLINE);
    }

    /** Teste leve de conexão. Se o banco voltou depois de estar fora, recarrega os dados. */
    private void verificarConexao() {
        executarAsync(
                () -> {
                    try (Connection c = Conexao.getConexao()) {
                        return c != null && c.isValid(3);
                    }
                },
                ok -> {
                    boolean estavaFora = bancoOnline != null && !bancoOnline;
                    definirStatusBanco(ok);
                    if (ok && estavaFora) atualizarIndicadores();
                },
                ex -> definirStatusBanco(false),
                c -> { });
    }

    // =====================================================================
    //  Acesso a dados
    // =====================================================================
    private static final class DadosTabela {
        final List<String> colunas = new ArrayList<>();
        final List<Object[]> linhas = new ArrayList<>();
    }

    private static final class Resumo {
        String clientes = "—", animais = "—", atendimentos = "—", media = "—";
        DadosTabela recentes;
        String erroRecentes;
    }

    private void atualizarIndicadores() {
        final int versao = ++versaoIndicadores;

        executarAsync(
                () -> {
                    Resumo r = new Resumo();
                    try (Connection conn = Conexao.getConexao();
                         Statement stmt = conn.createStatement()) {

                        r.clientes = contar(stmt, "SELECT COUNT(*) FROM Dono");
                        r.animais = contar(stmt, "SELECT COUNT(*) FROM Animal");
                        r.atendimentos = contar(stmt, "SELECT COUNT(*) FROM Atendimento");

                        try (ResultSet rs = stmt.executeQuery("SELECT AVG(Custo) FROM Atendimento")) {
                            if (rs.next()) r.media = String.format(PT_BR, "R$ %.2f", rs.getDouble(1));
                        }

                        try (ResultSet rs = stmt.executeQuery(SQL_RECENTES)) {
                            r.recentes = lerResultSet(rs);
                            formatarCusto(r.recentes);
                        } catch (SQLException ex) {
                            r.erroRecentes = "Não foi possível carregar os atendimentos recentes.";
                        }
                    }
                    return r;
                },
                r -> {
                    if (versao != versaoIndicadores) return;
                    definirStatusBanco(true);
                    definir(lblsClientes, r.clientes);
                    definir(lblsAnimais, r.animais);
                    definir(lblsAtendimentos, r.atendimentos);
                    definir(lblsMedia, r.media);

                    if (r.recentes != null) {
                        aplicarNaTabela(modeloRecentes, r.recentes);
                        lblRecentes.setText(r.recentes.linhas.isEmpty()
                                ? "Nenhum atendimento registrado ainda."
                                : "Atendimentos mais recentes da clínica.");
                    } else {
                        modeloRecentes.setRowCount(0);
                        lblRecentes.setText(r.erroRecentes);
                    }
                },
                ex -> {
                    if (versao != versaoIndicadores) return;
                    definirStatusBanco(false);
                    definir(lblsClientes, "—");
                    definir(lblsAnimais, "—");
                    definir(lblsAtendimentos, "—");
                    definir(lblsMedia, "—");
                    modeloRecentes.setRowCount(0);
                    lblRecentes.setText("Sem conexão com o banco de dados. "
                            + "Clique no indicador do menu para tentar novamente.");
                },
                c -> { });
    }

    private static String contar(Statement stmt, String sql) throws SQLException {
        try (ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getString(1) : "0";
        }
    }

    private static void formatarCusto(DadosTabela d) {
        int idx = -1;
        for (int i = 0; i < d.colunas.size(); i++) {
            if (d.colunas.get(i).equalsIgnoreCase("Custo")) { idx = i; break; }
        }
        if (idx < 0) return;
        for (Object[] linha : d.linhas) {
            if (linha[idx] instanceof Number) {
                linha[idx] = String.format(PT_BR, "R$ %.2f", ((Number) linha[idx]).doubleValue());
            }
        }
    }

    /** Lê todo o ResultSet para memória (pode rodar fora da thread da interface). */
    private static DadosTabela lerResultSet(ResultSet rs) throws SQLException {
        DadosTabela d = new DadosTabela();
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        for (int i = 1; i <= cols; i++) d.colunas.add(meta.getColumnLabel(i));
        while (rs.next()) {
            Object[] row = new Object[cols];
            for (int i = 1; i <= cols; i++) row[i - 1] = rs.getObject(i);
            d.linhas.add(row);
        }
        return d;
    }

    /** Aplica os dados ao modelo (chamar apenas na thread da interface). */
    private static void aplicarNaTabela(DefaultTableModel modelo, DadosTabela d) {
        modelo.setDataVector(d.linhas.toArray(new Object[0][]), d.colunas.toArray());
    }

    private static DefaultTableModel modeloSomenteLeitura() {
        return new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    // =====================================================================
    //  Execução em segundo plano
    // =====================================================================

    /**
     * Roda a tarefa fora da thread da interface. O aviso de "carregando" só aparece se a
     * tarefa demorar mais de 250 ms, para não piscar em consultas rápidas.
     */
    private <T> void executarAsync(Callable<T> tarefa, Consumer<T> sucesso,
                                   Consumer<Exception> falha, Consumer<Boolean> carregando) {
        javax.swing.Timer atraso = new javax.swing.Timer(250, e -> carregando.accept(true));
        atraso.setRepeats(false);
        atraso.start();

        new SwingWorker<T, Void>() {
            @Override protected T doInBackground() throws Exception {
                return tarefa.call();
            }

            @Override protected void done() {
                atraso.stop();
                carregando.accept(false);
                try {
                    sucesso.accept(get());
                } catch (ExecutionException ex) {
                    Throwable causa = ex.getCause();
                    falha.accept(causa instanceof Exception ? (Exception) causa : new Exception(causa));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }.execute();
    }

    private void ocupado(boolean ocupado) {
        setCursor(ocupado ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }

    // =====================================================================
    //  Exportação CSV
    // =====================================================================

    /** CSV com ';' como separador e UTF-8 com BOM: abre direto no Excel em português. */
    private void exportarCSV(DefaultTableModel modelo, String nomeSugerido) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar resultado para CSV");
        fc.setSelectedFile(new File(nomeSugerido + ".csv"));
        fc.setFileFilter(new FileNameExtensionFilter("Arquivo CSV (*.csv)", "csv"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File arquivo = fc.getSelectedFile();
        if (!arquivo.getName().toLowerCase().endsWith(".csv")) {
            arquivo = new File(arquivo.getParentFile(), arquivo.getName() + ".csv");
        }
        if (arquivo.exists() && JOptionPane.showConfirmDialog(this,
                "O arquivo '" + arquivo.getName() + "' já existe. Deseja substituí-lo?",
                "Confirmar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        try (Writer w = new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8)) {
            w.write('\uFEFF');

            List<String> cabecalhoCsv = new ArrayList<>();
            for (int c = 0; c < modelo.getColumnCount(); c++) cabecalhoCsv.add(celulaCsv(modelo.getColumnName(c)));
            w.write(String.join(";", cabecalhoCsv) + "\r\n");

            for (int r = 0; r < modelo.getRowCount(); r++) {
                List<String> celulas = new ArrayList<>();
                for (int c = 0; c < modelo.getColumnCount(); c++) celulas.add(celulaCsv(modelo.getValueAt(r, c)));
                w.write(String.join(";", celulas) + "\r\n");
            }

            JOptionPane.showMessageDialog(this,
                    modelo.getRowCount() + " linhas exportadas para:\n" + arquivo.getAbsolutePath(),
                    "Exportação concluída", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar o arquivo: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String celulaCsv(Object v) {
        if (v == null) return "";
        String s;
        if (v instanceof BigDecimal) {
            s = ((BigDecimal) v).toPlainString().replace('.', ',');
        } else if (v instanceof Double || v instanceof Float) {
            s = BigDecimal.valueOf(((Number) v).doubleValue()).toPlainString().replace('.', ',');
        } else {
            s = v.toString();
        }
        if (s.contains(";") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            s = "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    public static void main(String[] args) {
        Tema.instalar();
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}