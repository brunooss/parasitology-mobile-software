# parasitology-mobile-software

Este é o repositório do aplicativo **_Parasite Combat_**!


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


### 3. Formatação do código

Para padronizar nomes de variáveis e funções, aplique o seguinte formato:

- Inicie o nome com letra minúscula. Ex.: ```var name = 0```
- As próximas palavras devem ser iniciadas com letra maiúscula. Ex.: ```var secondName = 1```
- _Variables and functions **in english**_.

Para padronizar nomes de classes, aplique o seguinte formato:

- Inicie o nome com letra maiúscula. Ex.: ```class Name { }```
- As próximas palavras devem ser iniciadas com letra maiúscula. Ex.: ```class SecondName { }```
- _As well, Classes **in english**_.

Para padronizar comentários, aplique o seguinte formato:

- Em comentários acima do código, aplicar o padrão ```/* Comentário de mais de uma linha! */```.
- Em comentários no código de apenas uma linha, aplicar o padrão ```// Comentário! ```.
- Em comentários seguidos no código, manter a mesma distância. Ex.: 
```
val x = 2           // Declarar a variável.
x += 2              // Atribuir outro valor.
Log.d(x.toString()) // Imprimir no Log.
```
