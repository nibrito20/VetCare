package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Tema visual do VetCare (paleta baseada na logo) + componentes estilizados.
 * Nenhuma dependência externa: apenas Swing puro.
 */
public final class Tema {

    private Tema() {}

    // ---------- Paleta (extraída da logo) ----------
    public static final Color COR_ESCURA = new Color(0x7F0F0F);
    public static final Color COR        = new Color(0xD4302A);
    public static final Color COR_CLARA  = new Color(0xE8706A);
    public static final Color COR_FUNDO  = new Color(0xFDECEB);
    public static final Color FUNDO       = new Color(0xF7F5F5);
    public static final Color SUPERFICIE  = Color.WHITE;
    public static final Color BORDA       = new Color(0xE8E2E2);
    public static final Color TEXTO       = new Color(0x1E293B);
    public static final Color TEXTO_SUAVE = new Color(0x64748B);
    public static final Color PERIGO      = new Color(0xD64545);
    public static final Color SUCESSO     = new Color(0x16A34A);
    public static final Color LARANJA     = new Color(0xF59E0B);

    // ---------- Fontes ----------
    public static final Font FONTE          = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_NEGRITO  = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONTE_PEQUENA  = new Font("SansSerif", Font.BOLD, 11);
    public static final Font FONTE_SECAO    = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONTE_TITULO   = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONTE_NUMERO   = new Font("SansSerif", Font.BOLD, 30);

    /** Chamar uma vez, antes de criar qualquer componente. */
    public static void instalar() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            // Metal respeita as cores/bordas que definimos (o Aqua do macOS ignora).
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        for (String k : new String[]{"Label.font", "Button.font", "TextField.font", "ComboBox.font",
                "Table.font", "OptionPane.messageFont", "OptionPane.buttonFont", "ToolTip.font"}) {
            UIManager.put(k, FONTE);
        }
        UIManager.put("Label.foreground", TEXTO);
        UIManager.put("Panel.background", SUPERFICIE);
        UIManager.put("OptionPane.background", SUPERFICIE);
        UIManager.put("OptionPane.messageForeground", TEXTO);
    }

    // ---------- Fábricas simples ----------
    public static JPanel painel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setOpaque(false);
        return p;
    }

    public static JLabel titulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE_SECAO);
        l.setForeground(COR_ESCURA);
        return l;
    }

    public static JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE_PEQUENA);
        l.setForeground(TEXTO_SUAVE);
        return l;
    }

    public static JLabel textoSuave(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE);
        l.setForeground(TEXTO_SUAVE);
        return l;
    }

    public static JTextField campo(int colunas) {
        JTextField f = new JTextField(colunas);
        f.setFont(FONTE);
        f.setForeground(TEXTO);
        f.setBackground(Color.WHITE);
        f.setCaretColor(COR);
        Border normal = new BordaArredondada(BORDA, 10, new Insets(8, 11, 8, 11));
        Border foco   = new BordaArredondada(COR, 10, new Insets(8, 11, 8, 11));
        f.setBorder(normal);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { f.setBorder(foco); }
            @Override public void focusLost(FocusEvent e)   { f.setBorder(normal); }
        });
        return f;
    }

    public static <T> JComboBox<T> combo(T[] itens) {
        JComboBox<T> c = new JComboBox<>(itens);
        c.setUI(new BasicComboBoxUI() {
            @Override protected JButton createArrowButton() {
                BasicArrowButton b = new BasicArrowButton(BasicArrowButton.SOUTH,
                        Color.WHITE, Color.WHITE, TEXTO_SUAVE, Color.WHITE);
                b.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return b;
            }
        });
        c.setFont(FONTE);
        c.setForeground(TEXTO);
        c.setBackground(Color.WHITE);
        c.setBorder(new BordaArredondada(BORDA, 10, new Insets(2, 6, 2, 2)));
        c.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean sel, boolean foc) {
                JLabel lb = (JLabel) super.getListCellRendererComponent(l, v, i, sel, foc);
                lb.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                lb.setBackground(sel ? COR_FUNDO : Color.WHITE);
                lb.setForeground(TEXTO);
                return lb;
            }
        });
        c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return c;
    }

    /** Redução em etapas (metade por vez): evita o serrilhado de reduzir muito de uma só vez. */
    static BufferedImage reduzir(BufferedImage src, int w, int h) {
        BufferedImage atual = src;
        int cw = src.getWidth(), ch = src.getHeight();
        if (cw <= w) return src;
        while (cw > w) {
            int nw = Math.max(w, cw / 2);
            int nh = Math.max(1, Math.round(ch * (nw / (float) cw)));
            BufferedImage tmp = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = tmp.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(atual, 0, 0, nw, nh, null);
            g.dispose();
            atual = tmp;
            cw = nw;
            ch = nh;
        }
        return atual;
    }

    /** Ícone de imagem: guarda o dobro da resolução e desenha suavizado (nítido também em telas Retina). */
    private static class IconeImagem implements Icon {
        private final BufferedImage img;
        private final int w, h;

        IconeImagem(BufferedImage original, int w, int h) {
            this.w = w;
            this.h = h;
            this.img = reduzir(original, w * 2, h * 2);
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }

        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.drawImage(img, x, y, w, h, null);
            g2.dispose();
        }
    }

    private static JLabel carregarImagem(String[] caminhos, int largura) {
        for (String caminho : caminhos) {
            File f = new File(caminho);
            if (f.exists()) {
                try {
                    BufferedImage img = ImageIO.read(f);
                    int h = Math.round(img.getHeight() * (largura / (float) img.getWidth()));
                    JLabel l = new JLabel(new IconeImagem(img, largura, h));
                    l.setHorizontalAlignment(SwingConstants.CENTER);
                    return l;
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    /** Logo completa (imagens/logo.png). Sem o arquivo, mostra o nome em texto. */
    public static JLabel logo(int largura) {
        JLabel l = carregarImagem(new String[]{"imagens/logo.png", "imagens/logo.jpg", "logo.png"}, largura);
        if (l != null) return l;
        l = new JLabel("<html><span style='color:#7F0F0F'>Vet</span><span style='color:#D4302A'>Care</span></html>");
        l.setFont(new Font("SansSerif", Font.BOLD, 28));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    /** Só o símbolo da logo (imagens/logo_icone.png), usado com o menu recolhido. */
    public static JLabel logoIcone(int largura) {
        JLabel l = carregarImagem(new String[]{"imagens/logo_icone.png"}, largura);
        if (l != null) return l;
        l = new JLabel("VC");
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        l.setForeground(COR);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    // ---------- Tabelas e scroll ----------
    public static void estilizarTabela(JTable t) {
        t.setFont(FONTE);
        t.setForeground(TEXTO);
        t.setRowHeight(34);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setFillsViewportHeight(true);
        t.setSelectionBackground(COR_FUNDO);
        t.setSelectionForeground(COR_ESCURA);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tb, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(tb, v, sel, false, r, c);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setHorizontalAlignment(SwingConstants.LEFT);
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(0xFCFAFA));
                    setForeground(TEXTO);
                }
                return this;
            }
        });

        JTableHeader h = t.getTableHeader();
        h.setReorderingAllowed(false);
        h.setPreferredSize(new Dimension(0, 38));
        h.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tb, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(tb, v, false, false, r, c);
                setText(v == null ? "" : v.toString().toUpperCase());
                setFont(FONTE_PEQUENA);
                setForeground(COR_ESCURA);
                setBackground(new Color(0xFBEFEE));
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA),
                        BorderFactory.createEmptyBorder(0, 12, 0, 12)));
                return this;
            }
        });
    }

    public static JScrollPane scroll(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUI(new ScrollFino());
        sp.getHorizontalScrollBar().setUI(new ScrollFino());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    // ---------- Componentes ----------
    public enum Tipo { PRIMARIO, SECUNDARIO, PERIGO }

    public static class Botao extends JButton {
        private final Tipo tipo;

        public Botao(String texto, Tipo tipo) {
            super(texto);
            this.tipo = tipo;
            setFont(FONTE_NEGRITO);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean press = getModel().isPressed(), hover = getModel().isRollover();
            Color fundo, texto;
            switch (tipo) {
                case SECUNDARIO:
                    fundo = press ? new Color(0xDDE1E7) : hover ? new Color(0xE6E9EE) : new Color(0xEFF1F5);
                    texto = TEXTO;
                    break;
                case PERIGO:
                    fundo = press ? new Color(0xF4BDBD) : hover ? new Color(0xF9D3D3) : new Color(0xFDECEC);
                    texto = PERIGO;
                    break;
                default:
                    fundo = press ? new Color(0xA82019) : hover ? new Color(0xBE2A24) : COR;
                    texto = Color.WHITE;
            }
            g2.setColor(fundo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();
            setForeground(texto);
            super.paintComponent(g);
        }
    }

    public enum Icone { INICIO, CADASTRO, CONSULTAS, PROCEDURES, DASHBOARD, PESSOA, PATA }

    /** Ícones desenhados em código (sem arquivos de imagem), numa grade de 20x20. */
    public static class IconeVetor implements Icon {
        private final Icone tipo;
        private final int tamanho;
        private Color cor;

        public IconeVetor(Icone tipo, int tamanho, Color cor) {
            this.tipo = tipo;
            this.tamanho = tamanho;
            this.cor = cor;
        }

        public void setCor(Color cor) { this.cor = cor; }
        @Override public int getIconWidth() { return tamanho; }
        @Override public int getIconHeight() { return tamanho; }

        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.translate(x, y);
            double s = tamanho / 20.0;
            g2.scale(s, s);
            g2.setColor(cor);
            g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            switch (tipo) {
                case INICIO: {
                    Path2D telhado = new Path2D.Float();
                    telhado.moveTo(2, 10); telhado.lineTo(10, 3); telhado.lineTo(18, 10);
                    g2.draw(telhado);
                    Path2D corpo = new Path2D.Float();
                    corpo.moveTo(4, 9); corpo.lineTo(4, 17); corpo.lineTo(16, 17); corpo.lineTo(16, 9);
                    g2.draw(corpo);
                    Path2D porta = new Path2D.Float();
                    porta.moveTo(8, 17); porta.lineTo(8, 12); porta.lineTo(12, 12); porta.lineTo(12, 17);
                    g2.draw(porta);
                    break;
                }
                case CADASTRO:
                    g2.drawRoundRect(3, 2, 14, 16, 4, 4);
                    g2.drawLine(6, 7, 14, 7);
                    g2.drawLine(6, 11, 14, 11);
                    g2.drawLine(6, 15, 11, 15);
                    break;
                case CONSULTAS:
                    g2.drawOval(2, 2, 11, 11);
                    g2.drawLine(11, 11, 17, 17);
                    break;
                case PROCEDURES: {
                    Path2D raio = new Path2D.Float();
                    raio.moveTo(11, 2); raio.lineTo(4, 11); raio.lineTo(9, 11);
                    raio.lineTo(8, 18); raio.lineTo(16, 8); raio.lineTo(11, 8);
                    raio.closePath();
                    g2.draw(raio);
                    break;
                }
                case DASHBOARD:
                    g2.fillRoundRect(3, 10, 4, 7, 2, 2);
                    g2.fillRoundRect(8, 6, 4, 11, 2, 2);
                    g2.fillRoundRect(13, 2, 4, 15, 2, 2);
                    break;
                case PESSOA:
                    g2.drawOval(7, 2, 6, 6);
                    g2.drawArc(3, 11, 14, 12, 0, 180);
                    break;
                case PATA:
                    g2.fillOval(4, 10, 12, 8);
                    g2.fillOval(1, 7, 4, 5);
                    g2.fillOval(6, 2, 4, 5);
                    g2.fillOval(11, 2, 4, 5);
                    g2.fillOval(15, 7, 4, 5);
                    break;
            }
            g2.dispose();
        }
    }

    /** Ícone de fundo suave e cantos arredondados, para os cartões de atalho. */
    public static JLabel distintivo(Icone tipo, Color cor) {
        JLabel l = new JLabel(new IconeVetor(tipo, 22, cor)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COR_FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setOpaque(false);
        l.setPreferredSize(new Dimension(46, 46));
        return l;
    }

    /** Indica se o símbolo da logo (imagens/logo_icone.png) existe. */
    public static boolean temLogoIcone() {
        return new File("imagens/logo_icone.png").exists();
    }

    /** Botão "sanduíche" (três linhas) que abre e fecha o menu lateral. */
    public static class BotaoHamburguer extends JButton {
        private final Icon simbolo;

        public BotaoHamburguer() {
            JLabel img = carregarImagem(new String[]{"imagens/logo_icone.png"}, 40);
            simbolo = img == null ? null : img.getIcon();
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(46, 46));
            setToolTipText("Abrir / fechar menu");
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isRollover()) {
                g2.setColor(COR_FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            }
            int cx = getWidth() / 2, cy = getHeight() / 2;
            if (simbolo != null) {
                // símbolo da logo no lugar do "sanduíche"
                simbolo.paintIcon(this, g2, cx - simbolo.getIconWidth() / 2, cy - simbolo.getIconHeight() / 2);
            } else {
                g2.setColor(COR_ESCURA);
                g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for (int dy : new int[]{-6, 0, 6}) g2.drawLine(cx - 10, cy + dy, cx + 10, cy + dy);
            }
            g2.dispose();
        }
    }

    /** Item do menu lateral: ícone + texto. Recolhido, mostra só o ícone. */
    public static class BotaoMenu extends JToggleButton {
        private final String texto;
        private final IconeVetor icone;
        private boolean recolhido = false;

        public BotaoMenu(String texto, Icone tipo) {
            super(texto);
            this.texto = texto;
            this.icone = new IconeVetor(tipo, 20, TEXTO_SUAVE);
            setHorizontalAlignment(SwingConstants.LEFT);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
            aplicarEstado();
        }

        public void setRecolhido(boolean recolhido) {
            this.recolhido = recolhido;
            aplicarEstado();
        }

        private void aplicarEstado() {
            setText(recolhido ? "" : texto);
            setToolTipText(recolhido ? texto : null);
            setBorder(BorderFactory.createEmptyBorder(12, recolhido ? 0 : 52, 12, 10));
            revalidate();
            repaint();
        }

        @Override public Dimension getPreferredSize() {
            return new Dimension(super.getPreferredSize().width, 44);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (isSelected()) {
                g2.setColor(COR_FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(COR);
                g2.fillRoundRect(0, 10, 4, getHeight() - 20, 4, 4);
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(0xF8F3F3));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
            icone.setCor(isSelected() ? COR : TEXTO_SUAVE);
            int ix = recolhido ? (getWidth() - 20) / 2 : 18;
            icone.paintIcon(this, g2, ix, (getHeight() - 20) / 2);
            g2.dispose();
            setFont(isSelected() ? FONTE_NEGRITO : FONTE);
            setForeground(isSelected() ? COR : TEXTO);
            super.paintComponent(g);
        }
    }

    /** Painel com degradê azul (faixa de boas-vindas da tela inicial). */
    public static class Destaque extends JPanel {
        public Destaque(LayoutManager lm, int padding) {
            super(lm);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(padding, padding + 6, padding, padding + 6));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setClip(new RoundRectangle2D.Float(0, 0, w, h, 24, 24));
            g2.setPaint(new GradientPaint(0, 0, COR_ESCURA, w, h, COR));
            g2.fillRect(0, 0, w, h);
            g2.setColor(new Color(255, 255, 255, 18));
            g2.fillOval(w - 240, -90, 340, 340);
            g2.setColor(new Color(255, 255, 255, 12));
            g2.fillOval(w - 130, h - 110, 220, 220);
            g2.dispose();
        }
    }

    /** Painel branco com cantos arredondados, sombra suave e (opcional) faixa de cor à esquerda. */
    public static class Cartao extends JPanel {
        private final Color acento;
        private boolean hover;

        public Cartao(LayoutManager lm, int padding, Color acento) {
            super(lm);
            this.acento = acento;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(padding, padding + (acento != null ? 4 : 0), padding + 3, padding + 2));
        }

        public Cartao(LayoutManager lm, int padding) { this(lm, padding, null); }

        /** Torna o cartão clicável, com destaque na borda ao passar o mouse. */
        public Cartao clicavel(Runnable acao) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                @Override public void mouseClicked(MouseEvent e) { acao.run(); }
            });
            return this;
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth() - 2, h = getHeight() - 3;
            g2.setColor(new Color(60, 20, 20, 16));
            g2.fillRoundRect(1, 2, w, h, 18, 18);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, w, h, 16, 16);
            if (acento != null) {
                g2.setClip(new RoundRectangle2D.Float(0, 0, w, h, 16, 16));
                g2.setColor(acento);
                g2.fillRect(0, 0, 5, h);
                g2.setClip(null);
            }
            g2.setColor(hover ? COR_CLARA : BORDA);
            g2.drawRoundRect(0, 0, w - 1, h - 1, 16, 16);
            g2.dispose();
        }
    }

    /** Mostra uma imagem PNG ajustada ao espaço disponível, mantendo a proporção. */
    public static class ImagemAjustada extends JComponent {
        private BufferedImage img;
        private BufferedImage cache;
        private int cacheW = -1;
        private String mensagem = "";

        public void mostrar(File arquivo) {
            try {
                if (arquivo.exists()) {
                    img = ImageIO.read(arquivo);
                    cache = null;
                    mensagem = "";
                } else {
                    img = null;
                    mensagem = "O arquivo '" + arquivo.getName() + "' não foi encontrado na pasta '" + arquivo.getParent() + "'.";
                }
            } catch (Exception e) {
                img = null;
                mensagem = "Não foi possível abrir '" + arquivo.getName() + "'.";
            }
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (img != null) {
                double s = Math.min(getWidth() / (double) img.getWidth(), getHeight() / (double) img.getHeight());
                int w = (int) (img.getWidth() * s), h = (int) (img.getHeight() * s);
                if (cache == null || cacheW != w) { cache = reduzir(img, w, h); cacheW = w; }
                g2.drawImage(cache, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
            } else {
                g2.setColor(TEXTO_SUAVE);
                g2.setFont(FONTE);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(mensagem, (getWidth() - fm.stringWidth(mensagem)) / 2, getHeight() / 2);
            }
            g2.dispose();
        }
    }

    /** Indicador de conexão com o banco (bolinha + texto). Clicável. */
    public static class IndicadorStatus extends JComponent {
        public enum Estado { VERIFICANDO, ONLINE, OFFLINE }

        private Estado estado = Estado.VERIFICANDO;
        private boolean recolhido = false;
        private boolean hover = false;

        public IndicadorStatus() {
            setPreferredSize(new Dimension(10, 40));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setToolTipText("Verificando conexão…");
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            });
        }

        public IndicadorStatus aoClicar(Runnable acao) {
            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { acao.run(); }
            });
            return this;
        }

        public void setEstado(Estado e) {
            this.estado = e;
            switch (e) {
                case ONLINE:  setToolTipText("Banco de dados conectado. Clique para verificar de novo."); break;
                case OFFLINE: setToolTipText("Sem conexão com o banco. Clique para tentar novamente."); break;
                default:      setToolTipText("Verificando conexão…");
            }
            repaint();
        }

        public void setRecolhido(boolean r) { this.recolhido = r; repaint(); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color ponto, fundo, texto;
            String rotulo;
            switch (estado) {
                case ONLINE:
                    ponto = SUCESSO; fundo = new Color(0xE8F7EE); texto = new Color(0x166534);
                    rotulo = "Banco conectado"; break;
                case OFFLINE:
                    ponto = PERIGO; fundo = new Color(0xFDECEC); texto = new Color(0xB91C1C);
                    rotulo = "Banco desconectado"; break;
                default:
                    ponto = TEXTO_SUAVE; fundo = new Color(0xF1F1F1); texto = TEXTO_SUAVE;
                    rotulo = "Verificando…";
            }

            int w = getWidth(), h = getHeight();
            g2.setColor(hover ? fundo.darker() : fundo);
            g2.fillRoundRect(0, 0, w, h, 12, 12);

            int d = 10, cy = h / 2;
            int dx = recolhido ? (w - d) / 2 : 14;
            g2.setColor(new Color(ponto.getRed(), ponto.getGreen(), ponto.getBlue(), 60));
            g2.fillOval(dx - 3, cy - d / 2 - 3, d + 6, d + 6);
            g2.setColor(ponto);
            g2.fillOval(dx, cy - d / 2, d, d);

            if (!recolhido) {
                g2.setFont(FONTE_NEGRITO);
                g2.setColor(texto);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(rotulo, dx + d + 10, cy + (fm.getAscent() - fm.getDescent()) / 2);
            }
            g2.dispose();
        }
    }

    // ---------- Internos ----------
    private static class BordaArredondada implements Border {
        private final Color cor;
        private final int raio;
        private final Insets margens;

        BordaArredondada(Color cor, int raio, Insets margens) {
            this.cor = cor;
            this.raio = raio;
            this.margens = margens;
        }

        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(cor);
            g2.drawRoundRect(x, y, w - 1, h - 1, raio, raio);
            g2.dispose();
        }

        @Override public Insets getBorderInsets(Component c) { return margens; }
        @Override public boolean isBorderOpaque() { return false; }
    }

    private static class ScrollFino extends BasicScrollBarUI {
        @Override protected void configureScrollBarColors() {
            thumbColor = new Color(0xD8CFCF);
            trackColor = Color.WHITE;
        }
        @Override protected JButton createDecreaseButton(int o) { return botaoVazio(); }
        @Override protected JButton createIncreaseButton(int o) { return botaoVazio(); }
        private JButton botaoVazio() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0, 0));
            return b;
        }
        @Override public Dimension getPreferredSize(JComponent c) { return new Dimension(10, 10); }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            g.setColor(trackColor);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            if (r.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2, 8, 8);
            g2.dispose();
        }
    }
}