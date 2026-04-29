# E-Commerce Backend (Quarkus)

Ovo je backend projekat za e-commerce aplikaciju rađen u Quarkus framework-u. Projekat pokriva rad sa bazom podataka, relacijama, komunikacijom sa eksternim API-jima i sigurnošću.

## Tehnologije
- Java 17
- Quarkus (RESTEasy Classic)
- PostgreSQL & Hibernate (JPA)
- Keycloak (OIDC Auth)

## Glavne funkcionalnosti
- **Baza i relacije:** Upravljanje korisnicima, korpama i narudžbama (korišćene `@OneToMany`, `@ManyToOne` i `@OneToOne` relacije).
- **Sigurnost:** Zaštita ruta pomoću Keycloak-a i OIDC tokena.
- **REST Klijenti:** Aplikacija komunicira sa spoljnim servisima (`api.ipify.org` i `timeapi.io`) kako bi na osnovu IP adrese dohvatila vremensku zonu korisnika i sačuvala je u bazu.
- **Rješavanje JSON problema:** Korišćenje `@JsonIgnore` i `FetchType.EAGER` za kontrolisanje onoga što se šalje na front i sprečavanje pucanja aplikacije.

## Pokretanje projekta

Da bi se projekat pokrenuo, potrebno je prvo upaliti bazu (PostgreSQL) i Keycloak server.

Pokretanje u dev modu:
```bash
.\mvnw clean compile quarkus:dev
