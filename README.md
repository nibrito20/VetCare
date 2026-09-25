# VetCare

Sistema de gerenciamento de clínica veterinária, desenvolvido em **Java** com integração ao **MySQL** via **JDBC**, para a disciplina de Banco de Dados.

## Tecnologias

- Java
- JDBC (MySQL Connector/J)
- MySQL

## Estrutura do projeto

```
VetCare/
├── src/
│   ├── conexao/        # Classe responsável pela conexão com o banco (JDBC)
│   ├── dao/             # Classes DAO (Data Access Object) — comandos SQL explícitos
│   ├── model/            # Classes de modelo (entidades do banco)
│   └── Main.java         # Ponto de entrada da aplicação
├── config.properties       # Configurações de conexão (não versionado)
├── config.properties.example # Modelo de configuração
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

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.