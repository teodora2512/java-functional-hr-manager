# Programare Java (Stream API & Jackson)

## 📖 Descriere

Acest proiect Java demonstrează utilizarea bibliotecilor **Jackson** pentru lucrul cu fișiere JSON și a **Stream API** pentru prelucrarea colecțiilor de obiecte.  
Aplicația gestionează o listă de angajați (`Angajat`), citind datele dintr-un fișier JSON și aplicând diverse operații funcționale (filtrare, sortare, mapare, statistici).

---

## 🧩 Structura proiectului

```
Laborator6/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── Angajat.java
│ │ │ └── MainApp.java
│ │ └── resources/
│ │ └── angajati.json
│ └── test/
│ └── ...
├── pom.xml
└── README.md

```


---

## ⚙️ Funcționalități principale

1. **Citirea** listei de angajați dintr-un fișier JSON (`angajati.json`)
2. **Scrierea** listei actualizate în fișier JSON
3. **Filtrare** angajați după condiții (ex: salariu > 2500 RON)
4. **Căutare** angajați angajați într-o anumită lună/an
5. **Sortare** descrescătoare după salariu
6. **Conversie** a numelor în majuscule
7. **Statistici** despre salarii (minim, maxim, mediu, număr total)
8. **Identificarea** celui mai vechi angajat (primul angajat)
9. **Verificarea** existenței unui angajat cu numele „Ion”
10. **Numărarea** angajaților angajați în vara anului precedent

---

## 🧠 Clase

### `Angajat`
- `String nume`
- `String post`
- `LocalDate data_angajarii`
- `float salariul`

Reprezintă un angajat și conține metode getter/setter și `toString()`.

### `MainApp`
- Metode statice pentru:
  - `citire()` – citește lista din JSON
  - `scriere()` – scrie lista în JSON
  - `main()` – conține logica aplicației și prelucrările cu Stream API

---

## 📦 Dependințe Maven

Fișierul `pom.xml` include:
```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.14.2</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.datatype</groupId>
        <artifactId>jackson-datatype-jsr310</artifactId>
        <version>2.14.2</version>
    </dependency>
</dependencies>
