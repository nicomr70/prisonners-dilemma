# Template Dilemme du Prisonnier

Ce template va avoir deux mains différentes :
* `Main` -> pour une application Java simple
* `PrisonersDilemmaApp` -> le nom complet de la seconde application doit être ici

## Launch Sonar verification 
To start code analysis with sonar, use the following line command:
```angular2html
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=dilemme_g2_1 \
  -Dsonar.host.url=http://im2ag-sonar.u-ga.fr:9000 \
  -Dsonar.login=5031f92260186145e416558fb8cb7dbd04d7d37f
```
