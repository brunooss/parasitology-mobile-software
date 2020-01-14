# parasitology-mobile-software

Este é o repositório do aplicativo **_Vermes & Márcia_**!

### 1. Clones, Commits e Pull Requests

Para clonar o repositório na sua máquina local, digite no terminal:
```
  git init
  git clone https://github.com/brunooss/parasitology-mobile-software
```

Para fazer um pull request, digite no terminal:

```
git checkout -b nome-do-branch   (cria um novo branch)
git chekout master               (retorna ao branch master)
git push origin nome-do-branch   (atualiza o repositório remoto com o branch criado)
```

Para fazer um commit, digite no terminal:

```
git add [arquivo]
git add *                        (para adicionar todos os arquivos)  
git commit -m "comentário"
```

Após fazer o _Pull Request_, delete o branch criado da seguinte forma:

```
git branch -d nome-do-branch
```

### 2. Formatação de Commits e Pull Requests

Para padronizar os commits, utilize as seguintes **_tags_** no início de cada commit:
- **build:** fazer uma nova build
- **alteração:** alterar ou atualizar o código
- **documentação:** alterar ou atualizar a documentação
- **funcionalidade:** adicionar uma nova funcionalidade
- **bugfix:** consertar bugs eventuais
- **performance:** melhorar a performance
- **refatoração:** refatorar partes do código
- **reversão:** reverter alterações de commits anteriores
- **estilização:** adicionar uma nova estilização
- **teste:** fazer um teste

Em seguida, adicionar uma _breve e elegante_ descrição do commit.

Observe um exemplo abaixo:

```
git commit -m "documentação: novas regras de formatação para commits"
```

Para padronizar os pull requests, crie novos branches de acordo com o padrão a seguir:

```
[nome_do_usuário]-[tag]-[número da alteração]
```

Por exemplo:

```
brunooss-documentacao-1
```

Por fim, o nome do _Pull Request_ deve ser o mesmo do commit efetuado.
