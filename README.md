# Sistema de Gestão de Internações Hospitalares

Projeto em Java com interface via linha de comando, fachada, persistência em arquivo e testes simples por `assert`, estruturado de um jeito que dá para abrir tranquilamente no IntelliJ.

## Como executar

### Compilar
```bash
javac -d out $(find src/main/java -name "*.java") $(find src/test/java -name "*.java")
```

### Rodar o sistema
```bash
java -Dfile.encoding=UTF-8 -cp out br.ufcg.hospital.app.Main
```

### Rodar os testes
```bash
java -ea -cp out br.ufcg.hospital.test.CalculadoraInternacaoTest
java -ea -cp out br.ufcg.hospital.test.CalculadoraPagamentoTest
```

## Organização
- `facade`: ponto único de comunicação da camada de interface.
- `manager`: regras de negócio e CRUDs.
- `model`: entidades do domínio.
- `persistence`: serialização do estado do sistema.
- `report`: geração de relatório TXT.
- `util`: validadores e calculadoras.

## Persistência
Os dados ficam em `data/hospital.dat`.

## Observações
- Diárias adotadas no projeto:
  - Enfermaria: R$ 320,00
  - Apartamento: R$ 780,00
  - UTI: R$ 2.450,00
