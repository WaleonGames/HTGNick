# 🎨 HTGNick

**HTGNick** to plugin do Minecraft (Spigot/Paper), który dodaje system **kolorowych nicków**, **weryfikacji graczy (✔ / ✖)** oraz **proste API** do integracji z innymi pluginami (np. HTGChat).  
Plugin działa **bez PlaceholderAPI**, ale oferuje **opcjonalne placeholdery**, jeśli PlaceholderAPI jest zainstalowane.

---

## ✨ Funkcje

- 🎨 Kolorowe nicki:
  - klasyczne kolory (`&a`, `&b`, `&c`, itd.)
  - wsparcie dla HEX (`#RRGGBB`)
- 🧭 GUI do wyboru i usuwania koloru nicku
- ✅ System weryfikacji graczy (`verified`)
- 🏷 Ikony weryfikacji:
  - ✔ dla zweryfikowanych
  - ✖ dla niezweryfikowanych
- 🔌 **NickAPI** do integracji z innymi pluginami (bez placeholderów)
- 🧩 Opcjonalna integracja z PlaceholderAPI
- 📋 Automatyczna aktualizacja:
  - `TAB` (`playerListName`)
  - `displayName` (chat)
- ⚙️ Automatyczny zapis danych w `config.yml`

---

## 📦 Wymagania

- Java 17+
- Spigot / Paper `1.18+`
- *(opcjonalnie)* PlaceholderAPI

---

## 📥 Instalacja

1. Wrzuć `HTGNick.jar` do folderu `plugins/`
2. Uruchom serwer
3. Plugin automatycznie utworzy `config.yml`
4. *(opcjonalnie)* Zainstaluj **PlaceholderAPI**, jeśli chcesz używać placeholderów

---

## ⚙️ Konfiguracja (`config.yml`)

```yml
settings:
  apply-tab: true
  apply-displayname: true

verified: {}
colors: {}
```

---

## ⌨️ Komendy

| Komenda | Opis |
|------|------|
| `/nick` | Otwiera GUI wyboru koloru nicku |
| `/zweryfikowany` | Wyświetla informację o statusie weryfikacji |

---

## 🧩 PlaceholderAPI (opcjonalnie)

| Placeholder | Opis |
|-----------|-----|
| `%htgnick_name%` | Kolorowy nick gracza |
| `%htgnick_color%` | Surowy kolor nicku |
| `%htgnick_verified%` | Status weryfikacji |
| `%htgnick_verified_tab%` | Prefix do TAB |
| `%htgnick_verified_tag%` | Prefix do chatu |

---

## 🔌 NickAPI

HTGNick udostępnia proste API do komunikacji między pluginami.

---

## 🗺️ Roadmapa

- Integracja z HTGChat
- Rozszerzenie systemu weryfikacji
- Dodatkowe style nicków

---

## 📜 Licencja
Projekt prywatny / HTGMC
