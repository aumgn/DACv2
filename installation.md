---
layout: layout
---

Installation
======================

Toutes les versions du plugin peuvent être trouvés sur mon dépôt à l'adresse suivante : [http://maven.aumgn.fr/fr/aumgn/dac2/](http://maven.aumgn.fr/fr/aumgn/dac2/).

Les versions qui se situent dans un dossier se terminant par SNAPSHOT sont des versions de développement, compilé automatiquement, et ne sont pas nécessairement testé ni stable (à utiliser avec précaution donc).

Chaque version est disponible sous deux formes, un première classique et une seconde dites "standalone".
La différence et que la version "standalone" ne nécessite pas l'installation de la librarie BukkitUtils comme expliqué plus bas car elle l'intègre déjà.

BukkitUtils
------------

Pour fonctionner DACv2 nécessite la librairie BukkitUtils. Cette librairie est déjà intégré dans les versions "standalone". Dans le cas de la version classique, il faut l'installer manuellement.
Pour cela, vous pouvez également la télécharger sur le dépôt à l'adresse suivante : [http://maven.aumgn.fr/fr/aumgn/bukkitutils/](http://maven.aumgn.fr/fr/aumgn/bukkitutils/).
Pour l'installer il suffit de la placer dans un dossier "lib" se situant à la racine du serveur sous le nom exact de "BukkitUtils.jar".

WorldEdit
------------

Contrairement à la première version du plugin, WorldEdit est une dépendance optionnelle.
Elle est bien sûr toujours nécessaire afin de définir les arènes à partir des sélections (les commandes associées ne seront pas activées si WorldEdit n'est pas présent).
Mais il existe d'autre moyen (bien que moins commode) de définir les régions de l'arène, et surtout, une fois les arènes définis, WorldEdit peut être enlevé, le plugin dac tournera sans aucun problème.