# Uruchomienie serwera selenium #
  1. Testy uruchamiają się na Firefoxie, trzeba go wyłączyć na czas testów (przynajmniej ja nie umiem inaczej
  1. Znaleźć swój katalog firefoxa, prawdopodobnie ~.mozilla/firefox/mp41h6ve.default gdzie to ostatnie to wyglądająca na losową nazwa z rozszerzeniem .default
  1. uruchomić z konsoli (będąc w katalogu głównym projektu):
` java -jar war/WEB-INF/lib/selenium-server-standalone-2.16.1.jar -firefoxProfileTemplate ~/.mozilla/firefox/mp41h6ve.default `

# Uruchomienie pojedynczego testu #
  1. Run as JUnit na odpowiednim pliku. za jakiś czas powinno się pojawić zbiorowe testowanie