Dokument skrótowo opisze jakie są ograniczenia naszej bazy danych.

# Ograniczenia #

  * można filtrować przez nierówności tylko po jednym atrybucie
  * brak JOIN, ale jest "przodek" (coś w stylu struktury drzewiastej, można szukać w encjach "poniżej")
  * i wiele wiele innych


# Nasza baza #
## GoodleUser: ##
  * login //tymczasowo
  * hasło //tymczasowo
  * access\_token\_key
  * access\_token\_secret
  * request\_key

## GoodleSession ##

  * sessionId
  * sessionUser // może być "guest"


# Fajne linki #
http://code.google.com/intl/pl/appengine/docs/java/datastore/