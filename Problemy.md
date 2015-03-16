<h2>Problemy podczas importu</h2>

<h3>Wyświetlają się ścieżki zaczynające się od src</h3>

Wejdź w <b>Configure Build Path</b>. Z dostępnych bibliotek usuń domyślne JRE, a następnie załaduj je ponownie.

<h2>Problemy podczas uruchamiania</h2>

<h3>Unable to restore the previous TimeZone</h3>

Problem pojawia się po aktualizacji wtyczki Google'a. Aby rozwiązać,
należy dodać: ```
-Dappengine.user.timezone.impl=UTC``` do:

```
Run As... -> Run Configurations -> zakładka Arguments -> box VM   arguments```

<h3>Provider org.apache.xerces.jaxp.SAXParserFactoryImpl not found.</h3>

Dodać: [xercesImpl.jar](http://xerces.apache.org/mirrors.cgi)

<h3>java.lang.NoClassDefFoundError: javax/validation/ValidationException</h3>

Upewnij się, że masz zainstalowaną najnowszą wersję GWT. Następnie przekopiuj z folderu:

```
~/.eclipse/[wersja]/plugins/...sdkbundle.../gwt-2.4.0```

do:

```
Goodle/war/WEB-INF/lib```

następujące jary: <b>gwt-servlet-deps.jar</b>, <b>validation-api.ga.jar</b> i <b>validation-api.ga-sources.jar</b>. Nie pobieraj ich z internetu! W powyższym folderze znajdują się wersje, których potrzebujesz. Dodaj skopiowane jary do Build Path.

<h3>The RequestFactory ValidationTool must be run for the RequestFactory type</h3>

Upewnij się, że masz zainstalowaną najnowszą wersję GWT. Następnie przekopiuj <b>requestfactory-apt.jar</b> z folderu:

```
~/.eclipse/[wersja]/plugins/...sdkbundle.../gwt-2.4.0```

do:

```
Goodle/war/WEB-INF/lib```

Nie pobieraj go z internetu! W powyższym folderze znajduje się wersja, której potrzebujesz. Następnie zaznacz opcję <b>Enable Project Specific Settings</b> w:

```
Project -> Properties -> Java Compiler -> Annotation Processing```

Wejdż teraz do <b>Factory Path</b>, zaznacz <b>Enable Project Specific Settings</b> i dodaj <b>requestfactory-apt.jar</b>

<h3>Unable to load server class ... </h3>

Usunąć app-engine z build path oraz run-config i dodać od nowa


<h2>Problemy podczas obsługi Eclipsa </h2>

<h3>Build workspace się zawiesza</h3>

Dopisz do <b>eclipse.ini</b> parametr `-clean` w pierwszej linii w pierwszym miejscu. Jeżeli problem przestał występować, parametr możesz usunąć parametr. Jeśli problem nie został rozwiązany, stwórz nowy workspace i przekopiuj tam wszystkie pliki.

<h3> Eclipse się nie uruchamia </h3>
Komunikat w logach Eclipsa
```
Could not initialize class org.eclipse.jface.preference.PreferenceConverter```

Usunąć <b>workspace/.metadata/.plugins/org.eclipse.core.resources/.snap</b>

<h2>Problemy podczas obsługi gita</h2>

<h3>Podczas próby pushu pojawia się komunikat: You may need to use your generated googlecode.com password; see <a href='https://code.google.com/hosting/settings'>https://code.google.com/hosting/settings</a>. Git nie prosi o hasło.</h3>

Zamiast ```
git push origin [branch]``` użyj ```
git push https://code.google.com/p/goodle [branch]``` Git zapyta o login i hasło. Po podaniu poprawnych danych push powinien przebiec normalnie.