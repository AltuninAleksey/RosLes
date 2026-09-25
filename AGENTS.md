# AGENTS.md — RosLes (Рослес)

Single-module Android app (`:app`, namespace `com.example.rosles`). No README, no tests, no CI.
`app/src` contains only `main` — there is nothing to run except the full build.

## Build

- Windows repo; build with `gradlew.bat` (e.g. `.\gradlew.bat :app:assembleDebug`).
- Requires Android SDK 34 + JDK for AGP 8.13.2. `local.properties` (`sdk.dir`) is machine-local, never commit it.
- Dependency versions live in root `build.gradle` (`ext`: coroutines 1.6.1, nav 2.4.0, okhttp 4.9.3, retrofit 2.9.0). `lifecycle-runtime-ktx:2.4.0` is pinned to match `nav_version` — keep them in sync.
- Version skew warning: root declares `kotlin_version = 1.5.31` but plugins use Kotlin 2.2.21. Do not "fix" this casually; verify a full build.
- `app/build.gradle` contains a release signing config with a hardcoded password/keystore path. Do not touch or log it.
- `settings.gradle` contains a hardcoded Mapbox token. Do not rotate/remove; build needs it for dependency resolution.

## Architecture (not obvious from filenames)

- No fragments, no NavController despite `navigation-*-ktx` deps. Screens are plain Activities in `Screens/`, launched via explicit `Intent`s. Entry point: `Screens/Authorization` (LAUNCHER in manifest).
- Two competing base activities: `BaseActivity` (custom ActionBar + popup menu) and `Screens/BaseAppClass` (options menu). New screens should extend one of them, not `AppCompatActivity` directly.
- One giant `Network/ViewModels` class serves all screens (not per-screen ViewModels). `Network/Singletons.accountsRepository` is the hand-rolled DI graph; backend URL is `Network.Const.BASE_URL` (plain `http`, hence `usesCleartextTraffic=true` — required, do not remove).
- Auth: token stored in SharedPreferences file `"PreferencesName"`, key `"access_token"`; read via `utils.getToken()`. Retrofit `Authorization` header is built per call (`"Bearer $token"`), there is no auth interceptor.
- `DBCountWood` is the single SQLiteOpenHelper (raw `execSQL` with string interpolation). It is **not** a singleton: always construct with `applicationContext` (never an Activity — leaks), and `close()` helpers you create locally.
- `res/layout/` and `res/layout-large/` are near-mirrors; a UI change usually must be applied in both.

## Hard-earned gotchas (do not regress)

- `StateFlow` only emits on `value = newObject`. Mutating a field (`state.value.isLoading = true`) silently emits nothing — always `copy()`, always reset flags in `finally`.
- Subscribe to flows once in `onCreate` (`lifecycleScope` + `repeatOnLifecycle`), never `collect()` inside a click listener after starting work.
- Large server lists (e.g. `getListRegionList`) + `HttpLoggingInterceptor.Level.BODY` = OOM. Logging stays at `BASIC`.
- Bulk DB inserts must run on `Dispatchers.IO`, inside one transaction, chunked (~500 rows). Single `execSQL` per row on the main thread ANRs and exhausts resources. Use `writeDachaList` / `writeFCListRegionList`, not the single-row writers in loops.
- Guard parallel loads (`if (isLoading) return` + disable the button). Double-tap otherwise launches duplicate network+DB work.
- `DBCountWood` SQL escaping convention is manual `replace("'", "''")`. Preserve it in any new writer; never pass uninterpolated user text into `execSQL`.
