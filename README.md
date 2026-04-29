<div align="center">

<img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png" width="120px"/>

# ⚡ Pokemon App

> *Catch 'em all — even offline.*

A modern Android App built with **Jetpack Compose** and **Clean Architecture**, featuring offline-first support, authentication, and smooth pagination.

---

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Latest-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Hilt-DI-34A853?style=flat-square)](https://dagger.dev/hilt/)
[![CouchbaseLite](https://img.shields.io/badge/CouchbaseLite-Local_DB-EA2328?style=flat-square)](https://www.couchbase.com/products/lite/)
[![PokeAPI](https://img.shields.io/badge/PokeAPI-REST-FF6B6B?style=flat-square)](https://pokeapi.co/)

</div>

---

## 📱 Features

| Feature | Description |
|---|---|
| 🔐 **Authentication** | Register & login stored securely in CouchbaseLite |
| 📋 **Pokémon List** | Paginated list fetched from PokeAPI |
| 🔍 **Detail View** | Tap any Pokémon to see full details |
| 📡 **Offline First** | Data cached locally — works without internet |
| ✨ **Modern UI** | Built entirely with Jetpack Compose |

---

## 🏗️ Tech Stack

```
📦 App
├── 🎨 UI           → Jetpack Compose + Material 3
├── 🧠 State        → ViewModel + StateFlow
├── 💉 DI           → Dagger Hilt
├── 🗄️ Local DB     → CouchbaseLite (Auth & Cache)
├── 🌐 Remote       → Retrofit + PokeAPI
├── 📄 Pagination   → Jetpack Paging 3
└── 🧭 Navigation   → Jetpack Navigation Compose
```

---

## 🏛️ Architecture

This app follows **Clean Architecture** with 3 layers:

```
┌─────────────────────────────┐
│         Presentation        │  Composables · ViewModel
├─────────────────────────────┤
│           Domain            │  UseCases · Repository Interface
├─────────────────────────────┤
│            Data             │  CouchbaseLite · Retrofit · Mapper
└─────────────────────────────┘
```

Pattern: **MVVM** — UI observes ViewModel state, ViewModel delegates to UseCases.

---

## 🗂️ Project Structure

```
app/
├── di/                  # Hilt modules
├── navigation/          # NavGraph, routes
└── ui/                  # Compose screens

data/
├── local/               # CouchbaseLite (auth, cache)
├── remote/              # Retrofit + PokeAPI service
├── repository/          # Repository implementations
└── mapper/              # Data ↔ Domain mappers

domain/
├── model/               # Domain entities
├── repository/          # Repository contracts
└── usecase/             # Business logic
```

## 📡 API

This app uses **[PokéAPI](https://pokeapi.co/)** — a free, open RESTful Pokémon API.

```
GET https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
GET https://pokeapi.co/api/v2/pokemon/{id or name}
```

---

## 🔐 Local Database

Auth data (users, sessions) is stored using **CouchbaseLite** — a lightweight embedded NoSQL database. No server required.

- Passwords are hashed before storage
- Session persisted across app restarts
- Works fully offline

---

<div align="center">

Made with ❤️ Ceunah

*"We Wok De Tok, Not Only Tok De Tok."*

</div>
