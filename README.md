# Sistema de Gestão de Internações Hospitalares

Sistema desenvolvido em **Java 17** para gerenciar pacientes, médicos, internações e pagamentos por meio de uma interface de linha de comando. O projeto utiliza uma arquitetura organizada em camadas, com fachada para centralizar as operações, persistência em arquivo e geração de relatórios.

<p align="center">
  **Projeto acadêmico · Linguagem de Programação · Java**
</p>

---

## 1. Sobre o projeto

O **Sistema de Gestão de Internações Hospitalares** tem como objetivo apoiar o gerenciamento das rotinas relacionadas às internações, desde o cadastro de pacientes e médicos até o registro de altas e pagamentos.

A aplicação permite acompanhar internações, calcular valores conforme o tipo de acomodação e as condições do plano de saúde, registrar pagamentos e gerar relatórios em formato TXT.

## 2. Funcionalidades

- Cadastro e consulta de pacientes;
- Atualização do plano de saúde;
- Cadastro, atualização e remoção lógica de médicos;
- Listagem de médicos ativos;
- Registro de internações e altas;
- Consulta de internações por paciente;
- Listagem de internações ativas e concluídas;
- Cálculo dos valores de internação;
- Registro de pagamentos com diferentes formas de pagamento;
- Geração de relatório de pagamentos em TXT;
- Persistência dos dados entre execuções.

## 3. Tecnologias e ferramentas

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem principal |
| Maven | Gerenciamento e compilação do projeto |
| JUnit 5 | Testes automatizados |
| PlantUML | Modelagem do diagrama de classes |
| Git e GitHub | Versionamento do código |

## 4. Organização do projeto

```text
Sistema-Hospitalar_Projeto-LP/
├── README.md
├── pom.xml
├── run.sh
├── .gitignore
├── data/
│   ├── hospital.dat
│   └── relatorio_pagamentos.txt
├── src/
│   ├── main/java/br/ufcg/hospital/
│   │   ├── app/             # Interface de linha de comando
│   │   ├── enums/           # Enumerações do domínio
│   │   ├── exception/       # Exceções específicas
│   │   ├── facade/          # Fachada de acesso ao sistema
│   │   ├── manager/         # Regras de negócio e gerenciamento
│   │   ├── model/           # Entidades do domínio
│   │   ├── persistence/     # Persistência dos dados
│   │   ├── report/          # Geração de relatórios
│   │   └── util/             # Validadores e calculadoras
│   └── test/java/br/ufcg/hospital/test/
│       ├── CalculadoraInternacaoTest.java
│       └── CalculadoraPagamentoTest.java
└── uml/
    └── diagrama-classes.puml
```

## 5. Arquitetura

A aplicação utiliza uma organização por responsabilidades:

- **App:** interação com o usuário pelo terminal;
- **Facade:** centraliza o acesso às operações do sistema;
- **Manager:** concentra os gerenciadores e regras de negócio;
- **Model:** representa pacientes, médicos, internações, pagamentos e planos de saúde;
- **Persistence:** salva e recupera os dados;
- **Report:** produz relatórios de pagamentos;
- **Util:** reúne validações e cálculos auxiliares.

### Diagrama de classes

O diagrama UML está disponível em PlantUML:

- [Visualizar diagrama de classes](uml/diagrama-classes.puml)

## 6. Como executar

É necessário ter o **JDK 17** e o **Maven** instalados.

### Compilar e executar os testes

```bash
mvn test
```

### Executar a aplicação

```bash
mvn package
java -Dfile.encoding=UTF-8 -cp target/classes br.ufcg.hospital.app.Main
```

Caso o plugin Exec não esteja configurado no ambiente, também é possível executar pelo IntelliJ IDEA, abrindo o projeto como projeto Maven e iniciando a classe:

`src/main/java/br/ufcg/hospital/app/Main.java`

## 7. Persistência e dados

Os dados da aplicação são armazenados em `data/hospital.dat`. O relatório de pagamentos é gerado em `data/relatorio_pagamentos.txt`.

Os arquivos dessa pasta fazem parte dos dados do projeto e não devem ser confundidos com arquivos temporários de compilação.

## 8. Valores de diárias

| Tipo de internação | Valor da diária |
|---|---:|
| Enfermaria | R$ 320,00 |
| Apartamento | R$ 780,00 |
| UTI | R$ 2.450,00 |

---

<p align="center">
  <strong>Sistema de Gestão de Internações Hospitalares</strong><br>
  Organização, controle e acompanhamento de internações.
</p>
