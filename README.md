# VetCare

Sistema de gerenciamento de clínica veterinária, desenvolvido em **Java** com integração ao **MySQL** via **JDBC**, para a disciplina de Banco de Dados.

## Tecnologias

- Java
- JDBC (MySQL Connector/J)
- MySQL
- Python (geração dos gráficos estatísticos)

## Estrutura do projeto

```
VetCare/
├── src/
│   ├── conexao/
│   ├── dao/
│   ├── model/
│   └── Main.java
├── gerar_graficos.py
├── grafico1.png ... grafico6.png
├── config.properties (não versionado)
├── config.properties.example
├── LICENSE
└── README.md
```


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
- **MySQL** instalado e rodando localmente (ou acesso a um servidor MySQL)
- IntelliJ IDEA (ou outra IDE Java de sua preferência)
- Driver **MySQL Connector/J** (`mysql-connector-j-9.7.0.jar`, ou versão equivalente)

### 1. Clonar o repositório

```bash
git clone https://github.com/nibrito20/VetCare.git
cd VetCare
```

### 2. Configurar a conexão com o banco

O arquivo `config.properties` **não é versionado** (por conter dados sensíveis, como senha). Você precisa criá-lo localmente:

1. Duplique o arquivo `config.properties.example`.
2. Renomeie a cópia para `config.properties`.
3. Preencha com os dados do **seu** MySQL local:

```properties
db.url=jdbc:mysql://localhost:3306/vetcare
db.usuario=root
db.senha=SUA_SENHA_AQUI
```

### 3. Rodar o projeto

Abra `src/Main.java` e execute.

Se tudo estiver configurado corretamente, a saída no console deve abrir a interface.


## Gráficos estatísticos

Os gráficos `grafico1.png` a `grafico6.png` foram gerados a partir do script `gerar_graficos.py` e correspondem às análises exigidas pela disciplina de Estatística. Eles são exibidos na interface do sistema.

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
