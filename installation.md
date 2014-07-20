---
layout: layout
---

Téléchargement
======================

Version stable : [standalone](/downloads/dac2-0.8.0-standalone.jar) [classique](/downloads/dac2-0.8.0.jar).

Version développement : [standalone](/downloads/dac2-1.0.0-SNAPSHOT-standalone.jar) [classique](/downloads/dac2-1.0.0-SNAPSHOT.jar).


Version classique et standalone
======================

Chaque version est disponible sous deux formes, un première classique et une seconde dites "standalone".
La différence et que la version "standalone" ne nécessite pas l'installation de la librarie BukkitUtils comme expliqué plus bas car elle l'intègre déjà.

BukkitUtils
------------

Pour fonctionner DACv2 nécessite la librairie BukkitUtils. Cette librairie est déjà intégré dans les versions "standalone".

Dans le cas de la version classique, il faut l'installer manuellement. Pour cela, vous pouvez également la télécharger :

[Version 1.0.3-SNAPSHOT pour la version stable](/downloads/bukkitutils-1.0.3-SNAPSHOT.jar)

[Version 1.0.4-SNAPSHOT pour la version développement](/downloads/bukkitutils-1.0.4-SNAPSHOT.jar)

Pour l'installer il suffit de la placer dans un dossier "lib" se situant à la racine du serveur sous le nom exact de "BukkitUtils.jar".

WorldEdit
------------

Contrairement à la première version du plugin, WorldEdit est une dépendance optionnelle.

Elle est bien sûr toujours nécessaire afin de définir les arènes à partir des sélections (les commandes associées ne seront pas activées si WorldEdit n'est pas présent).
Mais il existe d'autre moyen (bien que moins commode) de définir les régions de l'arène, et surtout, une fois les arènes définis, WorldEdit peut être enlevé, le plugin dac tournera sans aucun problème.