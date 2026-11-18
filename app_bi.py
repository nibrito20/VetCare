"""
VetCare BI - painel interativo (Streamlit + Plotly)

Como rodar (na raiz do projeto, onde fica o config.properties):
    pip install -r requirements.txt
    streamlit run app_bi.py
"""
import math
import os
import re

import numpy as np
import pandas as pd
import plotly.express as px
import plotly.graph_objects as go
import streamlit as st
from plotly.subplots import make_subplots
from sqlalchemy import create_engine, text
from sqlalchemy.engine import URL

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
CONFIG_PATH = os.path.join(BASE_DIR, "config.properties")

# Paleta da logo do VetCare
PALETA = ["#D4302A", "#7F0F0F", "#E8706A", "#F59E0B", "#16A34A", "#64748B", "#0EA5E9", "#A855F7"]
px.defaults.template = "plotly_white"
px.defaults.color_discrete_sequence = PALETA

ROTULOS = {
    "Custo": "Custo (R$)",
    "Especie": "Espécie",
    "Veterinario": "Veterinário",
    "Diagnostico": "Diagnóstico",
    "Faturamento": "Faturamento (R$)",
}

st.set_page_config(page_title="VetCare BI", page_icon="🐾", layout="wide")


# =====================================================================
#  Acesso ao banco (usa o mesmo config.properties do app Java)
# =====================================================================
def ler_config() -> dict:
    cfg = {}
    if os.path.exists(CONFIG_PATH):
        with open(CONFIG_PATH, encoding="utf-8") as f:
            for linha in f:
                linha = linha.strip()
                if "=" in linha and not linha.startswith("#"):
                    chave, valor = linha.split("=", 1)
                    cfg[chave.strip()] = valor.strip()

    url = cfg.get("db.url", "jdbc:mysql://localhost:3306/vetcare")
    host, porta, banco = "localhost", 3306, "vetcare"
    m = re.search(r"jdbc:mysql://([^:/]+)(?::(\d+))?/([^?]+)", url)
    if m:
        host = m.group(1)
        porta = int(m.group(2)) if m.group(2) else 3306
        banco = m.group(3)

    return {
        "host": host,
        "port": porta,
        "database": banco,
        "user": cfg.get("db.usuario", "root"),
        "password": cfg.get("db.senha", ""),
    }


@st.cache_resource
def get_engine():
    c = ler_config()
    url = URL.create("mysql+mysqlconnector", username=c["user"], password=c["password"],
                     host=c["host"], port=c["port"], database=c["database"])
    return create_engine(url, pool_pre_ping=True, connect_args={"connection_timeout": 5})


SQL_ATENDIMENTOS = """
SELECT a.Codigo AS Atendimento, a.Custo, a.Diagnostico, a.Nivel_de_Gravidade AS Gravidade,
       an.Codigo AS CodAnimal, an.Nome AS Animal, an.Especie, an.Raca,
       d.Nome AS Dono, d.Cidade, f.Nome AS Veterinario
FROM Atendimento a
JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo
JOIN Dono d ON an.fk_Dono_CPF = d.CPF
JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF
"""


def preparar(df: pd.DataFrame) -> pd.DataFrame:
    df = df.copy()
    df["Custo"] = pd.to_numeric(df["Custo"], errors="coerce")
    df = df.dropna(subset=["Custo"])
    for col in ["Especie", "Raca", "Gravidade", "Diagnostico", "Veterinario", "Cidade"]:
        df[col] = df[col].fillna("Não informado").astype(str)
    return df


@st.cache_data(ttl=60, show_spinner="Carregando dados do banco…")
def carregar_dados() -> pd.DataFrame:
    with get_engine().connect() as conn:
        df = pd.read_sql(text(SQL_ATENDIMENTOS), conn)
    return preparar(df)


def dados_demo(n: int = 300) -> pd.DataFrame:
    """Dados fictícios, só para explorar o painel sem banco. Só entram se o modo demonstração for ligado."""
    rng = np.random.default_rng(42)
    base = {"Canino": 170, "Felino": 140, "Ave": 110, "Roedor": 80}
    mult = {"Baixa": 0.8, "Média": 1.0, "Alta": 1.4, "Crítica": 2.0}

    esp_animais = rng.choice(list(base), 120, p=[0.45, 0.35, 0.12, 0.08])
    cod = rng.integers(0, 120, n)
    especies = esp_animais[cod]
    grav = rng.choice(list(mult), n, p=[0.4, 0.35, 0.18, 0.07])
    custo = np.array([max(30, rng.normal(base[e] * mult[g], 25)) for e, g in zip(especies, grav)]).round(2)

    df = pd.DataFrame({
        "Atendimento": np.arange(1, n + 1),
        "Custo": custo,
        "Diagnostico": rng.choice(["Consulta de rotina", "Vacinação", "Infecção de pele", "Fratura",
                                   "Gastroenterite", "Cirurgia", "Check-up geriátrico", "Otite"], n),
        "Gravidade": grav,
        "CodAnimal": cod + 1,
        "Animal": ["Animal " + str(c + 1) for c in cod],
        "Especie": especies,
        "Raca": "SRD",
        "Dono": ["Dono " + str(c % 40 + 1) for c in cod],
        "Cidade": rng.choice(["Recife", "Olinda", "Jaboatão", "Paulista"], n),
        "Veterinario": rng.choice(["Dra. Ana Lima", "Dr. Bruno Costa", "Dra. Carla Souza", "Dr. Diego Rocha"], n),
    })
    return preparar(df)


# =====================================================================
#  Utilitários
# =====================================================================
def brl(valor: float) -> str:
    return "R$ " + f"{valor:,.2f}".replace(",", "X").replace(".", ",").replace("X", ".")


def kde(valores, pontos: int = 300):
    """Densidade por kernel gaussiano (regra de Silverman). Sem depender do scipy."""
    x = np.asarray(valores, dtype=float)
    x = x[~np.isnan(x)]
    if len(x) < 2:
        return None
    desvio = x.std(ddof=1)
    iqr = np.subtract(*np.percentile(x, [75, 25]))
    escala = min(desvio, iqr / 1.34) if iqr > 0 else desvio
    h = 0.9 * escala * len(x) ** (-1 / 5)
    if not np.isfinite(h) or h <= 0:
        return None
    grade = np.linspace(x.min() - 3 * h, x.max() + 3 * h, pontos)
    z = (grade[:, None] - x[None, :]) / h
    dens = np.exp(-0.5 * z ** 2).sum(axis=1) / (len(x) * h * math.sqrt(2 * math.pi))
    return grade, dens


def estilo(fig, titulo: str, altura: int = 420):
    fig.update_layout(title=dict(text=titulo, font=dict(size=16)), height=altura,
                      margin=dict(t=60, b=20, l=10, r=10), legend_title_text="")
    return fig


def mostrar(fig, chave: str):
    st.plotly_chart(fig, width="stretch", theme=None, key=chave, config={"displaylogo": False})


# =====================================================================
#  Os 6 gráficos de distribuição (equivalentes aos do gerar_graficos.py)
# =====================================================================
def g1_histograma(df, grupo, bins):
    fig = px.histogram(df, x="Custo", color=grupo, nbins=bins, barmode="overlay",
                       opacity=0.75 if grupo else 1.0, labels=ROTULOS)
    fig.update_traces(marker_line_color="white", marker_line_width=1)
    fig.update_layout(yaxis_title="Frequência", bargap=0.03)
    return estilo(fig, "Gráfico 1 - Histograma Simples (Custos de Atendimento)")


def g2_kde(df, grupo):
    fig = go.Figure()
    grupos = df.groupby(grupo)["Custo"] if grupo else [("Todos", df["Custo"])]
    for i, (nome, s) in enumerate(grupos):
        curva = kde(s.to_numpy())
        if curva is None:
            continue
        fig.add_trace(go.Scatter(x=curva[0], y=curva[1], mode="lines", name=str(nome), fill="tozeroy",
                                 line=dict(color=PALETA[i % len(PALETA)], width=2), opacity=0.7,
                                 hovertemplate="R$ %{x:.2f}<br>densidade %{y:.4f}<extra>" + str(nome) + "</extra>"))
    if not fig.data:
        return None
    fig.update_layout(xaxis_title="Custo (R$)", yaxis_title="Densidade")
    return estilo(fig, "Gráfico 2 - Densidade KDE")


def g3_media_mediana(df, bins):
    s = df["Custo"]
    media, mediana = s.mean(), s.median()
    fig = go.Figure()
    fig.add_trace(go.Histogram(x=s, nbinsx=bins, histnorm="probability density", name="Histograma",
                               marker=dict(color="#E8706A", line=dict(color="white", width=1)), opacity=0.85))
    curva = kde(s.to_numpy())
    if curva is not None:
        fig.add_trace(go.Scatter(x=curva[0], y=curva[1], mode="lines", name="KDE", line=dict(color="black", width=2)))
    fig.add_vline(x=media, line_dash="dash", line_color="red", line_width=2,
                  annotation_text=f"Média: {brl(media)}", annotation_position="top left", annotation_font_color="red")
    fig.add_vline(x=mediana, line_color="green", line_width=2,
                  annotation_text=f"Mediana: {brl(mediana)}", annotation_position="top right", annotation_font_color="green")
    fig.update_layout(xaxis_title="Custo de Atendimento (R$)", yaxis_title="Densidade", bargap=0.03)
    return estilo(fig, "Gráfico 3 - Distribuição com KDE, Média e Mediana", 480)


def g4_boxplot(df, grupo):
    fig = px.box(df, y="Custo", x=grupo, color=grupo, points="outliers", labels=ROTULOS)
    fig.update_layout(showlegend=False)
    return estilo(fig, "Gráfico 4 - Boxplot Simples")


def g5_boxplot_detalhado(df, grupo):
    fig = px.box(df, y="Custo", x=grupo, color=grupo, points="all",
                 hover_data=["Animal", "Veterinario", "Diagnostico"], labels=ROTULOS)
    fig.update_traces(boxmean=True, jitter=0.45, marker_size=5)
    fig.update_layout(showlegend=False)
    return estilo(fig, "Gráfico 5 - Boxplot Detalhado dos Atendimentos (losango = média)", 520)


def g6_visao_completa(df, bins):
    s = df["Custo"]
    media, mediana = s.mean(), s.median()
    fig = make_subplots(rows=2, cols=1, shared_xaxes=True, row_heights=[0.25, 0.75], vertical_spacing=0.04)
    fig.add_trace(go.Box(x=s, orientation="h", name="", marker_color="#16A34A", showlegend=False), row=1, col=1)
    fig.add_trace(go.Histogram(x=s, nbinsx=bins, histnorm="probability density", name="Histograma",
                               marker=dict(color="#E8706A", line=dict(color="white", width=1)), opacity=0.85),
                  row=2, col=1)
    curva = kde(s.to_numpy())
    if curva is not None:
        fig.add_trace(go.Scatter(x=curva[0], y=curva[1], mode="lines", name="KDE",
                                 line=dict(color="#7F0F0F", width=2)), row=2, col=1)
    for r in (1, 2):
        nota_media = f"Média: {brl(media)}" if r == 2 else None
        nota_mediana = f"Mediana: {brl(mediana)}" if r == 2 else None
        fig.add_vline(x=media, line_dash="dash", line_color="red", line_width=2, row=r, col=1,
                      annotation_text=nota_media, annotation_position="top left", annotation_font_color="red")
        fig.add_vline(x=mediana, line_color="green", line_width=2, row=r, col=1,
                      annotation_text=nota_mediana, annotation_position="top right", annotation_font_color="green")
    fig.update_xaxes(title_text="Custo de Atendimento (R$)", row=2, col=1)
    fig.update_yaxes(title_text="Densidade", row=2, col=1)
    fig.update_layout(bargap=0.03)
    return estilo(fig, "Gráfico 6 - Visão Completa dos Custos de Atendimento", 680)


# =====================================================================
#  Página
# =====================================================================
with st.sidebar:
    st.title("🐾 VetCare BI")
    usar_demo = st.toggle("Modo demonstração", value=False,
                          help="Usa dados fictícios, só para explorar o painel sem o banco de dados.")
    if st.button("🔄 Recarregar dados", width="stretch"):
        st.cache_data.clear()
        st.rerun()

try:
    base = dados_demo() if usar_demo else carregar_dados()
except Exception as erro:
    st.error("Não foi possível carregar os dados do banco.")
    st.info("Confira se o MySQL está ligado e se o `config.properties` está correto. "
            "Para só explorar o painel, ative o **Modo demonstração** na barra lateral.")
    with st.expander("Detalhe técnico"):
        st.code(str(erro))
    st.stop()

if base.empty:
    st.info("Ainda não há atendimentos cadastrados.")
    st.stop()

# ----- Filtros -----
with st.sidebar:
    cfg = ler_config()
    st.caption("Fonte: dados fictícios" if usar_demo else f"Fonte: {cfg['database']} em {cfg['host']}")
    st.divider()
    st.subheader("Filtros")

    def filtro(rotulo, coluna):
        opcoes = sorted(base[coluna].unique())
        return st.multiselect(rotulo, opcoes, default=opcoes)

    sel_especie = filtro("Espécie", "Especie")
    sel_vet = filtro("Veterinário", "Veterinario")
    sel_grav = filtro("Gravidade", "Gravidade")

    cmin, cmax = float(math.floor(base["Custo"].min())), float(math.ceil(base["Custo"].max()))
    faixa = st.slider("Faixa de custo (R$)", cmin, cmax, (cmin, cmax), step=1.0) if cmin < cmax else (cmin, cmax)

f = base[
    base["Especie"].isin(sel_especie)
    & base["Veterinario"].isin(sel_vet)
    & base["Gravidade"].isin(sel_grav)
    & base["Custo"].between(faixa[0], faixa[1])
]

st.title("Painel de Atendimentos")
st.caption(f"Exibindo {len(f)} de {len(base)} atendimentos  •  os dados são atualizados a cada 60 s")

if f.empty:
    st.warning("Nenhum atendimento corresponde aos filtros escolhidos.")
    st.stop()

# ----- Indicadores -----
k1, k2, k3, k4 = st.columns(4)
k1.metric("Atendimentos", f"{len(f):,}".replace(",", "."), border=True)
k2.metric("Faturamento total", brl(f["Custo"].sum()), border=True)
k3.metric("Custo médio", brl(f["Custo"].mean()), border=True)
k4.metric("Animais atendidos", f["CodAnimal"].nunique(), border=True)

aba1, aba2, aba3 = st.tabs(["📊 Visão geral", "📈 Distribuição de custos", "🗂️ Dados"])

# ----- Aba 1: visão geral -----
with aba1:
    col_a, col_b = st.columns(2)

    with col_a:
        por_esp = (f.groupby("Especie", as_index=False)
                   .agg(Faturamento=("Custo", "sum"), Atendimentos=("Custo", "size"))
                   .sort_values("Faturamento", ascending=False))
        fig = px.bar(por_esp, x="Especie", y="Faturamento", text="Faturamento", labels=ROTULOS,
                     hover_data={"Atendimentos": True, "Faturamento": ":.2f"})
        fig.update_traces(texttemplate="R$ %{y:,.0f}", textposition="outside", cliponaxis=False)
        mostrar(estilo(fig, "Faturamento por espécie"), "g_especie")

    with col_b:
        por_grav = f["Gravidade"].value_counts().rename_axis("Gravidade").reset_index(name="Atendimentos")
        fig = px.pie(por_grav, names="Gravidade", values="Atendimentos", hole=0.55)
        fig.update_traces(textinfo="percent+label")
        mostrar(estilo(fig, "Atendimentos por gravidade"), "g_gravidade")

    col_c, col_d = st.columns(2)

    with col_c:
        por_vet = (f.groupby("Veterinario", as_index=False)
                   .agg(Faturamento=("Custo", "sum"), Atendimentos=("Custo", "size"))
                   .sort_values("Faturamento"))
        fig = px.bar(por_vet, x="Faturamento", y="Veterinario", orientation="h", labels=ROTULOS,
                     hover_data={"Atendimentos": True, "Faturamento": ":.2f"})
        mostrar(estilo(fig, "Faturamento por veterinário", max(320, 45 * len(por_vet) + 100)), "g_vet")

    with col_d:
        top_diag = (f["Diagnostico"].value_counts().head(10).rename_axis("Diagnostico")
                    .reset_index(name="Atendimentos").sort_values("Atendimentos"))
        fig = px.bar(top_diag, x="Atendimentos", y="Diagnostico", orientation="h", labels=ROTULOS)
        mostrar(estilo(fig, "Diagnósticos mais frequentes", max(320, 45 * len(top_diag) + 100)), "g_diag")

# ----- Aba 2: distribuição de custos -----
with aba2:
    GRAFICOS = [
        "Gráfico 1 - Histograma Simples",
        "Gráfico 2 - Densidade KDE",
        "Gráfico 3 - Distribuição com Média e Mediana",
        "Gráfico 4 - Boxplot Simples",
        "Gráfico 5 - Boxplot Detalhado",
        "Gráfico 6 - Visão Completa (Boxplot + Histograma + KDE)",
    ]
    GRUPOS = {"Nenhum": None, "Espécie": "Especie", "Gravidade": "Gravidade", "Veterinário": "Veterinario"}

    c1, c2, c3 = st.columns([3, 2, 2])
    escolha = GRAFICOS.index(c1.selectbox("Gráfico", GRAFICOS)) + 1
    grupo = GRUPOS[c2.selectbox("Separar por", list(GRUPOS))]
    bins = c3.slider("Faixas do histograma", 5, 60, 15)

    if grupo and escolha in (3, 6):
        st.caption("Este gráfico mostra a distribuição geral. A separação por grupo vale para os gráficos 1, 2, 4 e 5.")

    construtores = {
        1: lambda: g1_histograma(f, grupo, bins),
        2: lambda: g2_kde(f, grupo),
        3: lambda: g3_media_mediana(f, bins),
        4: lambda: g4_boxplot(f, grupo),
        5: lambda: g5_boxplot_detalhado(f, grupo),
        6: lambda: g6_visao_completa(f, bins),
    }
    figura = construtores[escolha]()
    if figura is None:
        st.info("Poucos dados para estimar a densidade (são necessários ao menos 2 atendimentos com custos diferentes).")
    else:
        mostrar(figura, "g_dist")

    s = f["Custo"]
    e1, e2, e3, e4, e5 = st.columns(5)
    e1.metric("Média", brl(s.mean()))
    e2.metric("Mediana", brl(s.median()))
    e3.metric("Desvio padrão", brl(s.std() if len(s) > 1 else 0.0))
    e4.metric("Mínimo", brl(s.min()))
    e5.metric("Máximo", brl(s.max()))

# ----- Aba 3: dados -----
with aba3:
    tabela = f.sort_values("Atendimento", ascending=False)
    st.dataframe(
        tabela, width="stretch", hide_index=True,
        column_config={"Custo": st.column_config.NumberColumn("Custo", format="R$ %.2f")},
    )
    csv = tabela.to_csv(index=False, sep=";", decimal=",").encode("utf-8-sig")
    st.download_button("⬇️ Baixar CSV (com os filtros aplicados)", csv, "atendimentos_vetcare.csv", "text/csv")