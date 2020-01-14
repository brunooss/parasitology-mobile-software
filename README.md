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
- **build: **
- **alteração:**
- **documentação:**
- **funcionalidade:**
- **bugfix:**
- **performance:**
- **refatoração:**
- **reversão:**
- **estilização:**
- **teste:**
