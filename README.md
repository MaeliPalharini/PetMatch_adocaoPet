# 🐾 PataMansa – Site de Adoção de Pets

Bem-vindo(a) ao repositório do **PataMansa**, um projeto pessoal de duas amigas, dedicado a facilitar a adoção responsável de animais de estimação. A ideia é construir uma aplicação completa com backend em Java (Spring Boot + DynamoDB) e frontend moderno e responsivo.

---

## 🎯 Objetivo

Criar uma plataforma onde pessoas possam:

- 📋 Ver a lista de pets disponíveis
- 🔍 Filtrar pets por tipo, porte e status (adotado/disponível)
- 📝 Cadastrar, editar ou excluir pets (admin)
- 🐶 Compartilhar histórias de adoção (futuro)

---

## 🧠 Tecnologias utilizadas

### Backend (Java)
- Java 17
- Spring Boot 3
- Spring Security
- AWS DynamoDB (com SDK e Enhanced Client)
- Maven

### Frontend (em desenvolvimento)
- HTML, CSS, JavaScript
- Framework a definir (provavelmente React ou similar)

---

## 📁 Estrutura do projeto
    backend/
        ├── src/
        │ ├── main/
        │ │ └── java/com/patamansa/backend/
        │ │ ├── model/
        │ │ ├── controller/
        │ │ ├── service/
        │ │ ├── repository/
        │ │ └── config/
        ├── pom.xml
        └── .gitignore


---

## 👩‍💻 Equipe

| Nome            | Função           | GitHub               |
|-----------------|------------------|----------------------|
| Maeli Palharini | Backend Developer | [@MaeliPalharini](https://github.com/seu-usuario) |
| Steph Gurgel    | Frontend Developer | [@Steph7478](https://github.com/Steph7478) |

---

## 🚧 Status do Projeto

- ✅ Backend configurado com Spring Boot + DynamoDB
- ✅ Entidade `Pet` modelada
- ✅ CRUD em andamento
- 🚧 Frontend em desenvolvimento
- 📝 Autenticação e autorização em progresso

---

## 📌 Próximos passos

- [ ] Finalizar CRUD completo
- [ ] Criar autenticação com JWT
- [ ] Subir frontend e conectar com backend
- [ ] Criar painel de admin
- [ ] Publicar a aplicação (talvez com render, vercel ou EC2)

---

## 📄 Licença

Este projeto está sob a licença MIT.
