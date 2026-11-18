"""
Gera os 6 gráficos estáticos exibidos no Dashboard do app Java (pasta graficos/).

Uso (com o ambiente virtual ligado):
    python gerar_graficos.py

Os gráficos vêm SEMPRE do banco de dados. Se o banco não responder, nada é gerado
(nenhum dado fictício é criado) e os gráficos antigos continuam como estavam.
"""
import math
import os
import re
import sys
from datetime import datetime

import matplotlib

matplotlib.use("Agg")  # só gera arquivos, não abre janelas
import matplotlib.pyplot as plt
import mysql.connector
import pandas as pd
import seaborn as sns
from matplotlib.lines import Line2D
from matplotlib.ticker import MaxNLocator

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
CONFIG_PATH = os.path.join(BASE_DIR, "config.properties")
PASTA_GRAFICOS = os.path.join(BASE_DIR, "graficos")

# Paleta da logo do VetCare
COR_BARRA = "#E8706A"
COR = "#D4302A"
COR_ESCURA = "#7F0F0F"
COR_MEDIANA = "#16A34A"
COR_TEXTO = "#64748B"

sns.set_theme(style="whitegrid", rc={"axes.edgecolor": "#CBD5E1", "grid.color": "#E2E8F0"})


# =====================================================================
#  Dados
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

    return {"host": host, "port": porta, "database": banco,
            "user": cfg.get("db.usuario", "root"), "password": cfg.get("db.senha", "")}


def carregar_custos() -> pd.Series:
    c = ler_config()
    conn = mysql.connector.connect(host=c["host"], port=c["port"], user=c["user"],
                                   password=c["password"], database=c["database"],
                                   connection_timeout=5)
    try:
        cur = conn.cursor()
        cur.execute("SELECT Custo FROM Atendimento")
        valores = [float(linha[0]) for linha in cur.fetchall() if linha[0] is not None]
    finally:
        conn.close()
    return pd.Series(valores, dtype=float, name="Custo")


# =====================================================================
#  Utilitários de desenho
# =====================================================================
def brl(valor: float) -> str:
    return "R$ " + f"{valor:,.2f}".replace(",", "X").replace(".", ",").replace("X", ".")


def num_faixas(n: int) -> int:
    return max(5, min(15, math.ceil(math.sqrt(n))))


def titulo(ax, texto):
    ax.set_title(texto, fontsize=14, fontweight="bold", color=COR_ESCURA, pad=14)


def linhas_referencia(ax, dados):
    """Média (tracejada) e mediana (contínua), com o valor na legenda."""
    ax.axvline(dados.mean(), color=COR_ESCURA, linestyle="--", linewidth=2,
               label=f"Média: {brl(dados.mean())}")
    ax.axvline(dados.median(), color=COR_MEDIANA, linestyle="-", linewidth=2,
               label=f"Mediana: {brl(dados.median())}")


def salvar(fig, nome, n, pasta):
    """Grava o PNG com um rodapé que diz quantos atendimentos foram usados e quando foi gerado."""
    quando = datetime.now().strftime("%d/%m/%Y %H:%M")
    fig.text(0.99, 0.012, f"{n} atendimentos  •  gerado em {quando}",
             ha="right", va="bottom", fontsize=8, color=COR_TEXTO)
    fig.tight_layout(rect=[0, 0.03, 1, 1])
    fig.savefig(os.path.join(pasta, nome), dpi=150)
    plt.close(fig)


# =====================================================================
#  Os 6 gráficos
# =====================================================================
def grafico1(dados, pasta):
    fig, ax = plt.subplots(figsize=(9, 5.5))
    ax.hist(dados, bins=num_faixas(len(dados)), color=COR_BARRA, edgecolor="white")
    linhas_referencia(ax, dados)
    titulo(ax, "Gráfico 1 - Histograma Simples (Custos de Atendimento)")
    ax.set_xlabel("Custo do atendimento (R$)", fontsize=11)
    ax.set_ylabel("Número de atendimentos", fontsize=11)
    ax.yaxis.set_major_locator(MaxNLocator(integer=True))
    ax.legend()
    salvar(fig, "grafico1.png", len(dados), pasta)


def grafico2(dados, pasta):
    fig, ax = plt.subplots(figsize=(9, 5.5))
    sns.kdeplot(dados, fill=True, color=COR, alpha=0.35, linewidth=2, clip=(0, None), ax=ax)
    titulo(ax, "Gráfico 2 - Densidade KDE")
    ax.set_xlabel("Custo do atendimento (R$)", fontsize=11)
    ax.set_ylabel("Densidade (onde os custos se concentram)", fontsize=11)
    salvar(fig, "grafico2.png", len(dados), pasta)


def grafico3(dados, pasta):
    fig, ax = plt.subplots(figsize=(10, 6))
    sns.histplot(dados, kde=True, bins=num_faixas(len(dados)), color=COR_BARRA, edgecolor="white",
                 line_kws={"color": "black", "linewidth": 2}, ax=ax)
    linhas_referencia(ax, dados)
    titulo(ax, "Gráfico 3 - Distribuição com KDE, Média e Mediana")
    ax.set_xlabel("Custo do atendimento (R$)", fontsize=11)
    ax.set_ylabel("Número de atendimentos", fontsize=11)
    ax.legend()
    salvar(fig, "grafico3.png", len(dados), pasta)


def grafico4(dados, pasta):
    fig, ax = plt.subplots(figsize=(7.5, 6.5))
    sns.boxplot(y=dados, color=COR_BARRA, width=0.35, ax=ax,
                flierprops=dict(marker="o", markerfacecolor=COR_ESCURA, markeredgecolor="white", markersize=7))
    q1, mediana, q3 = dados.quantile([0.25, 0.5, 0.75])
    ax.set_xlim(-0.6, 1.3)
    ax.set_xticks([])
    for valor, rotulo in [(q3, "3º quartil (75%)"), (mediana, "Mediana"), (q1, "1º quartil (25%)")]:
        ax.annotate(f"{rotulo}: {brl(valor)}", xy=(0.18, valor), xytext=(0.32, valor),
                    va="center", fontsize=10, color="#334155",
                    arrowprops=dict(arrowstyle="-", color="#94A3B8"))
    titulo(ax, "Gráfico 4 - Boxplot Simples")
    ax.set_ylabel("Custo do atendimento (R$)", fontsize=11)
    salvar(fig, "grafico4.png", len(dados), pasta)


def grafico5(dados, pasta):
    fig, ax = plt.subplots(figsize=(7.5, 6.5))
    sns.boxplot(y=dados, color="#F9D5D2", width=0.4, showfliers=False, showmeans=True, ax=ax,
                meanprops=dict(marker="D", markerfacecolor=COR_ESCURA, markeredgecolor="white", markersize=10))
    sns.stripplot(y=dados, color=COR, size=7, jitter=0.12, alpha=0.8, ax=ax)
    ax.set_xticks([])
    titulo(ax, "Gráfico 5 - Boxplot Detalhado dos Atendimentos")
    ax.set_ylabel("Custo do atendimento (R$)", fontsize=11)
    ax.legend(handles=[
        Line2D([0], [0], marker="o", color="w", markerfacecolor=COR, markersize=8, label="Um atendimento"),
        Line2D([0], [0], marker="D", color="w", markerfacecolor=COR_ESCURA, markersize=9,
               label=f"Média: {brl(dados.mean())}"),
    ], loc="upper right")
    salvar(fig, "grafico5.png", len(dados), pasta)


def grafico6(dados, pasta):
    fig, (ax_box, ax_hist) = plt.subplots(2, sharex=True, figsize=(10, 8),
                                          gridspec_kw={"height_ratios": (0.8, 1.2)})
    sns.boxplot(x=dados, ax=ax_box, color=COR_BARRA)
    ax_box.set_title("Boxplot da Distribuição de Custos", fontsize=12, fontweight="bold", color=COR_ESCURA)
    ax_box.set(xlabel="")

    sns.histplot(dados, ax=ax_hist, kde=True, bins=num_faixas(len(dados)), color=COR_BARRA,
                 edgecolor="white", line_kws={"color": "black", "linewidth": 2})
    for ax in (ax_box, ax_hist):
        ax.axvline(dados.mean(), color=COR_ESCURA, linestyle="--", linewidth=2,
                   label=f"Média: {brl(dados.mean())}" if ax is ax_hist else None)
        ax.axvline(dados.median(), color=COR_MEDIANA, linestyle="-", linewidth=2,
                   label=f"Mediana: {brl(dados.median())}" if ax is ax_hist else None)
    ax_hist.set_title("Histograma e KDE dos Custos", fontsize=12, fontweight="bold", color=COR_ESCURA)
    ax_hist.set_xlabel("Custo do atendimento (R$)", fontsize=11)
    ax_hist.set_ylabel("Número de atendimentos", fontsize=11)
    ax_hist.legend()

    fig.suptitle("Gráfico 6 - Visão Completa dos Custos de Atendimento",
                 fontsize=15, fontweight="bold", color=COR_ESCURA, y=0.985)
    salvar(fig, "grafico6.png", len(dados), pasta)


def gerar_todos(dados, pasta=PASTA_GRAFICOS):
    os.makedirs(pasta, exist_ok=True)
    for funcao in (grafico1, grafico2, grafico3, grafico4, grafico5, grafico6):
        funcao(dados, pasta)


def main():
    try:
        dados = carregar_custos()
    except Exception as erro:
        print(f"ERRO: não foi possível ler os custos do banco de dados: {erro}", file=sys.stderr)
        print("Os gráficos NÃO foram atualizados (nenhum dado fictício é gerado).", file=sys.stderr)
        sys.exit(1)

    if len(dados) < 2 or dados.nunique() < 2:
        print(f"ERRO: há poucos dados para gerar os gráficos ({len(dados)} atendimento(s) "
              "com custos diferentes são necessários: pelo menos 2).", file=sys.stderr)
        sys.exit(1)

    gerar_todos(dados)
    print(f"Sucesso! Os 6 gráficos foram gerados com {len(dados)} atendimentos do banco e salvos em 'graficos/'.")


if __name__ == "__main__":
    main()