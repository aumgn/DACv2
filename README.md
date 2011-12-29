DAC
====

DAC, est un plugin pour le jeu Minecraft francais du dé a coudre. Il 
permet de rendre les parties plus funs, en enlevant tous ce qui les rends moins fluides,
comme placer les colonnes de laines, monter en haut du plongeoir ou encore vider le bassin a la fin.

Ce plugin est un fork standalone de la version que j'ai réalisée pour le 
serveur francais Ethil Van. Il sera donc gardé a jour aussi longtemps que le sera l'original.

Le code est libre, vous etes donc libre de l'utiliser et de le modifier mais aucun support ne sera offert.

Dépendances
----

DAC requiert l'utilisation de WorldEdit (version 5.0) http://wiki.sk89q.com/wiki/WorldEdit pour la definition des differentes zones.

Mise en place
----

Une fois les jars de WorldEdit et de DAC installé dans votre dossier plugins, pour définir une arene :

    /dac define monArene

Pour en definir les zones de départ et du bassin, une fois avoir selectionné les zones avec WorldEdit :

    /dac set monArene pool
    /dac set monArene start

Pour definir l'emplacement ou le joueur est teleporté au debut de son tour, tenez vous a cet emplacement (l'angle de vue est important):

    /dac set monArene diving

Jouer
----

Pour jouer, chaque joueur doit se tenir dans la zone de départ et utiliser la commande :

    /dac join [couleur [couleur2 [...]]

Il peut specifier un certain nombre de couleurs, la premiere couleur disponible lui sera attribué
Les differentes couleurs disponible peuvent etre affiché avec la commande : 

    /dac colors 

Pour debuter la partie :

    /dac start

Pour la stopper: 

    /dac stop

Pour la quitter:

    /dac quit

Quand un joueur reussi un dé a coudre (c'est a dire qu'il a reussi a sauter dans un cube d'eau entouré aux quatre cotés par des colonnes de laines), il gagne une vie.
Les vies des joueurs peuvent etre affiché a tout moment de la partie en utilisant la commande : 

    /dac lives [joueur]

Pour reinitialiser le bassin, il suffit d'utiliser la commande :

    /dac reset monArene


NB : 
Le bassin est remis a jour automatiquement a chaque debut de partie. Mais apres la fin d'une partie la laine est toujours présente.
Il est donc conseillé d'utiliser un plugin de protection, comme WorldGuard, afin d'éviter que les joueurs profite du plugin pour recuperer un nombre illimité de laines.





