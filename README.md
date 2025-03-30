# Ticket Shop - Sleep Ticket

🎟 **Sleep ticket** to aplikacja desktopowa umożliwiająca zakup biletów na koncerty. Projekt został zrealizowany w oparciu o wzorzec **MVC** i wykorzystuje technologie takie jak **JavaFX**, **Hibernate** oraz **Maven**. Aplikacja pozwala na zarządzanie koncertami oraz sprzedaż biletów zarówno dla użytkowników, jak i administratorów.

## ✨ Funkcjonalności

### 👤 Użytkownik
- Rejestracja i logowanie do systemu
- Przeglądanie dostępnych koncertów
- Zakup biletów na wybrane koncerty
- Sprawdzanie zakupionych biletów w profilu

### 🔧 Administrator
- Dodawanie oraz edycja koncertów
- Zarządzanie szczegółami wydarzeń (data, miejsce, cena, dostępność biletów)
- Przeglądanie listy użytkowników
- Oraz te same funkcjonalności co użytkownik

## 📂 Struktura katalogów

### 📁 `src/main/`
Główny katalog projektu, zawierający:
- `pom.xml` – konfiguracja **Maven**
- `.gitignore` – plik ignorujący niepotrzebne pliki w repozytorium
- `resources/` – zasoby aplikacji:
  - `fxml/` – pliki widoków **JavaFX**
  - `css/` – arkusze stylów
  - `images/` – obrazy używane w interfejsie
  - `hibernate.cfg.xml` – konfiguracja **Hibernate**

### 📁 `java/org/`
Kod źródłowy podzielony na kilka modułów:
- `controller/`
  - `gui/` – kontrolery interfejsu użytkownika
  - `business/` – logika biznesowa aplikacji
- `db/hibernate/`
  - Klasy mapowane do **Hibernate** oraz `HibernateUtil` do obsługi bazy danych
- `gui/fx/`
  - Klasy odpowiedzialne za renderowanie plików **FXML**
- `main/`
  - **Główna klasa uruchamiająca aplikację**
  - `ConnectionTester` – narzędzie do sprawdzania połączenia z bazą **MySQL** (wymaga XAMPP)

## 🔧 Technologie
- **Java 8 (SDK 1.8)**
- **JavaFX** – GUI aplikacji
- **Maven** – system budowania projektu
- **Hibernate** – ORM do obsługi bazy danych **MySQL**
- **Lombok** – automatyzacja kodu (gettery, settery, konstruktory)
- **BCrypt** – szyfrowanie haseł użytkowników

## 🚀 Uruchamianie aplikacji
1. **Sklonuj repozytorium:**
   ```bash
   git clone <adres_repo>
   ```
2. **Uruchom XAMPP** i włącz serwer MySQL oraz Apache
3. **Skonfiguruj bazę danych** zgodnie z `hibernate.cfg.xml`
4. **Zbuduj projekt za pomocą Maven** (`mvn clean install`)
5. **Uruchom aplikację z klasy głównej** w `main/`

Aplikacja powinna uruchomić się i umożliwić logowanie jako użytkownik lub administrator. 🎶🎫

