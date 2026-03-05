# Album Tracker

school assignment SIIIIIIIIIIIIIIIIIIGH

M295 // Januar 2026

## Setup

### Voraussetzungen
- MySQL
- Maven
- Node.js

### MySQL
MySQL muss auf dem gleichen System laufen (ggf. in `src/main/java/application.properties` den Port anpassen).
Datenbank und Benutzer können automatisch mithilfe `setup.sql` eingerichtet werden.

### Backend starten
Zuerst Dependencies installieren mit `mvn install`.

Danach einen Build erstellen mit `mvn clean compile package`.

Am Schluss kann das exportierte JAR-File mit `java -jar (Pfad zum JAR-File)` gestartet werden.

### Frontend starten
Ebenfalls zuerst alle Dependencies installieren mit `npm install`.

Danach einfach starten mit `npm run dev`.

Die Website sollte unter `localhost:5173` erreichbar sein.