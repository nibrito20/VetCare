# VetCare

Sistema de gerenciamento para uma clínica veterinária, desenvolvido em **Java** (interface Swing) com integração ao **MySQL** via **JDBC**, para a disciplina de Banco de Dados. O projeto também conta com análises estatísticas em **Python** e um painel de **BI interativo**.

## Funcionalidades

A interface é dividida em cinco telas, acessíveis pelo menu lateral (que pode ser recolhido):

| Tela | O que faz |
|---|---|
| **Início** | Indicadores gerais (clientes, animais, atendimentos e custo médio), atalhos rápidos e a lista dos últimos atendimentos |
| **Gestão (CRUD)** | Cadastro, atualização e remoção de donos e animais |
| **Consultas e Views** | 10 consultas SQL (filtros, JOINs, agregações, subconsultas, anti join, UNION e views), cada uma com uma breve explicação, e exportação do resultado em CSV |
| **Procedures** | Execução das procedures armazenadas `sp_RelatorioResumoAnimal` e `sp_AtualizarTelefoneDono` |
| **Dashboard** | 6 gráficos estatísticos dos custos de atendimento, cada um com uma explicação de como lê-lo, e botão para abrir o BI interativo |

Outros recursos:

- **Indicador de conexão** no menu lateral ("Banco conectado" / "Banco desconectado"). Ele é reverificado automaticamente e pode ser clicado para testar de novo.
- **Consultas em segundo plano**: a janela não trava em consultas lentas e mostra uma barra de carregamento.
- **Atalhos de teclado** (veja a seção [Atalhos de teclado](#atalhos-de-teclado)).

## Tecnologias

- **Java** (Swing) e **JDBC** (MySQL Connector/J)
- **MySQL**
- **Python**
    - `matplotlib` e `seaborn`: gráficos estáticos
    - `Streamlit` e `Plotly`: BI interativo
    - `pandas`, `numpy`, `SQLAlchemy` e `mysql-connector-python`: acesso e tratamento dos dados

## Estrutura do projeto

```
VetCare/
├── src/
│   ├── conexao/               # Classe responsável pela conexão com o banco (JDBC)
│   ├── dao/                   # Classes DAO (Data Access Object) — comandos SQL explícitos
│   ├── model/                 # Classes de modelo (entidades do banco)
│   ├── ui/
│   │   └── Tema.java          # Tema visual e componentes estilizados da interface
│   └── Main.java              # Ponto de entrada da aplicação
├── sql/
│   └── consultas.sql          # Consultas SQL do projeto
├── imagens/                   # Logo do sistema (logo.png e logo_icone.png)
├── graficos/                  # Gráficos (PNG) gerados pelo script Python
├── .streamlit/
│   └── config.toml            # Tema (cores) do BI interativo
├── app_bi.py                  # BI interativo (Streamlit + Plotly)
├── gerar_graficos.py          # Script que gera os gráficos estáticos a partir do banco
├── requirements.txt           # Dependências do Python
├── config.properties          # Configurações de conexão (não versionado)
├── config.properties.example  # Modelo de configuração
├── .venv/                     # Ambiente virtual do Python (não versionado)
├── LICENSE
└── README.md
```

> Se a pasta `imagens/` não tiver as logos, o sistema funciona normalmente e mostra o nome "VetCare" em texto.

## Modelo de dados

O banco `vetcare` é composto pelas seguintes tabelas:

| Tabela | Descrição |
|---|---|
| `Funcionario` | Veterinários e demais funcionários da clínica |
| `Especialidade` | Especialidades veterinárias (ex: Cardiologia, Ortopedia) |
| `Atua_em` | Associação N:N entre `Funcionario` e `Especialidade` |
| `Dono` | Tutores dos animais |
| `Animal` | Animais atendidos na clínica |
| `Medicamento` | Medicamentos disponíveis |
| `Plantao` | Escalas de plantão dos funcionários |
| `Atendimento` | Atendimentos realizados (agendados ou de emergência) |
| `Exame` | Exames vinculados a um atendimento |
| `Tratamento` | Extensão de `Atendimento` que indica tratamento associado |
| `Receita` | Medicamentos prescritos em um tratamento |

## Como rodar o projeto

### Pré-requisitos

- **JDK 17** (ou superior) instalado
- **MySQL** instalado e rodando localmente (ou acesso a um servidor MySQL), com o banco `vetcare` criado
- IntelliJ IDEA (ou outra IDE Java de sua preferência)
- Driver **MySQL Connector/J** (`mysql-connector-j-9.7.0.jar`, ou versão equivalente)
- **Python 3.10** (ou superior): necessário apenas para os gráficos e para o BI

### 1. Clonar o repositório

```bash
git clone https://github.com/nibrito20/VetCare.git
cd VetCare
```

### 2. Importar o banco de dados

O arquivo `sql/vetcare.sql` contém as tabelas, views, procedures, triggers e os dados de exemplo. Com o MySQL rodando, execute na raiz do projeto:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS vetcare"
mysql -u root -p vetcare < sql/vetcare.sql
```

> O script recria as tabelas (`DROP TABLE` + `CREATE TABLE`). Se você já tem um banco `vetcare` com dados próprios, eles serão substituídos.

Se preferir uma interface gráfica, no DBeaver clique com o botão direito no banco `vetcare` e use **Tools → Execute script**.

### 3. Configurar a conexão com o banco

O arquivo `config.properties` **não é versionado** (por conter dados sensíveis, como senha). Você precisa criá-lo localmente:

1. Duplique o arquivo `config.properties.example`.
2. Renomeie a cópia para `config.properties`.
3. Preencha com os dados do **seu** MySQL local:

```properties
db.url=jdbc:mysql://localhost:3306/vetcare
db.usuario=root
db.senha=SUA_SENHA_AQUI
```

Esse mesmo arquivo é usado pelo sistema Java, pelo `gerar_graficos.py` e pelo `app_bi.py`.

### 4. Rodar a aplicação Java

Abra `src/Main.java` e execute. Se tudo estiver configurado corretamente, a interface do sistema será exibida.

### 4. Preparar o ambiente Python (gráficos e BI)

Esta etapa é opcional para usar o CRUD, as consultas e as procedures, mas necessária para atualizar os gráficos e abrir o BI.

Crie um ambiente virtual **chamado `.venv`, na raiz do projeto** (o botão do BI na interface procura o Python nesse local) e instale as dependências:

```bash
# macOS / Linux
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

```bash
# Windows
python -m venv .venv
.venv\Scripts\activate
pip install -r requirements.txt
```

Sempre que abrir um terminal novo para rodar os scripts Python, ative o ambiente de novo (`source .venv/bin/activate` ou `.venv\Scripts\activate`).

## Gráficos estatísticos

A pasta `graficos/` contém 6 gráficos (histograma, KDE e boxplots), que correspondem às análises exigidas pela disciplina de Estatística. Eles são exibidos na tela **Dashboard**, cada um acompanhado de uma explicação de como interpretá-lo.

Para gerá-los novamente a partir do banco (com o ambiente virtual ativado):

```bash
python gerar_graficos.py
```

Pontos importantes:

- Os gráficos são **sempre gerados com os dados reais do banco**. Se não for possível conectar, o script mostra o erro e **não altera** os gráficos existentes (nenhum dado de exemplo é criado).
- Cada imagem traz no rodapé o número de atendimentos usados e a data de geração.
- Na tela Dashboard, o sistema mostra a data em que os gráficos foram gerados. Por serem imagens estáticas, eles **não acompanham** novos atendimentos: rode o script novamente ou use o BI para ver dados em tempo real.

## BI interativo

O painel de BI lê o banco diretamente e permite explorar os dados com filtros, zoom e hover.

**Como abrir:**

- **Pela interface:** na tela **Dashboard**, clique em **Abrir BI interativo**. O sistema inicia o painel e abre o navegador em `http://localhost:8501`. O painel é desligado quando o sistema Java é fechado.
- **Pelo terminal** (com o ambiente virtual ativado):

```bash
streamlit run app_bi.py
```

**O que o painel oferece:**

- **Filtros** por período (datas), espécie, veterinário, gravidade, status e faixa de custo, que atualizam todas as abas.
- **Indicadores**: atendimentos, faturamento total, custo médio e animais atendidos.
- **Visão geral**: faturamento por espécie (barras), atendimentos por gravidade (pizza), faturamento por veterinário e diagnósticos mais frequentes.
- **Tendência no tempo**: gráfico de linha com atendimentos, faturamento ou custo médio, agrupados por mês ou por dia e, se quiser, separados por espécie, gravidade, veterinário ou sexo.
- **Distribuição de custos**: 6 gráficos estatísticos (histograma, KDE e boxplots) que podem ser separados por grupo, junto com **média, mediana, moda, variância, desvio padrão, quartis e coeficiente de variação**.
- **Correlações**: gráfico de dispersão do custo contra a idade do animal ou a hora do atendimento, com reta de regressão, coeficiente de Pearson (r), R² e uma interpretação em texto, além de um mapa de correlações entre as variáveis numéricas.
- **Comparativo**: gráfico de radar que compara veterinários, espécies ou gravidades em várias métricas ao mesmo tempo.
- **Dados**: tabela completa com os filtros aplicados e download em CSV.

Os dados são atualizados a cada 60 segundos; o botão **Recarregar dados** (barra lateral) força a atualização imediata. O **Modo demonstração** usa dados fictícios, apenas para explorar o painel sem o banco, e só é ativado manualmente.

## Atalhos de teclado

Use `Cmd` no macOS ou `Ctrl` no Windows/Linux.

| Atalho | Ação |
|---|---|
| `Enter` nos campos dos formulários | Inserir (Gestão) ou executar (Procedures) |
| `Cmd/Ctrl` + `1` a `5` | Trocar de tela (Início, Gestão, Consultas, Procedures, Dashboard) |
| `Cmd/Ctrl` + `B` | Abrir ou recolher o menu lateral |
| `F5` | Executar a consulta selecionada, ou atualizar os dados (Início e Dashboard) |

## Solução de problemas

| Problema | O que fazer |
|---|---|
| `externally-managed-environment` ao rodar o `pip` (macOS) | Use o ambiente virtual descrito na etapa 4, em vez de instalar direto no Python do sistema |
| Indicador mostra "Banco desconectado" | Verifique se o MySQL está rodando e se o `config.properties` está correto; depois clique no indicador para testar de novo |
| O botão do BI mostra erro ao abrir | Confirme que o ambiente `.venv` existe na raiz do projeto e que o `pip install -r requirements.txt` foi executado. O log fica no arquivo `vetcare_bi.log`, na pasta temporária do sistema |
| Os gráficos do Dashboard estão antigos | Rode `python gerar_graficos.py` com o MySQL ligado |

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
