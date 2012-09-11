---
layout: layout
---

Lancer une partie
=================

Il y a deux moyens de démarrer une partie. La méthode classique qui était celle de l'ancien plugin et la méthode "quickstart".

Démarrer une partie de manière classique :
------------------------------------------

Cette méthode contrairement à la seconde permet notamment au joueur de choisir la couleur de leur laine.
Pour démarrer une partie avec cette méthode, il suffit de l'initier :
    /dac init [-j[=<couleur>]] [arène]

Vous pouvez omettre l'arène dans laquelle vous voulez débuter la partie si vous vous trouvez dans sa zone de départ.

Ensuite les joueurs désirant participer n'ont qu'à se placer dans la zone de départ et à s'enregistrer en utilisant la commande :
    /dac join [couleur] ...
Le joueur peut préciser plusieurs couleurs. La première couleur disponible lui sera attribué.

Une fois tous les joueurs enregistré, il n'y a plus qu'à démarrer la partie avec :
    /dac start [mode de jeu]
Si le mode de jeu n'est pas précisé, la partie débutera en mode de jeu classique.
Vous pouvez trouver la liste des modes de jeu sur la page [Modes de jeu](/modes.html).


Démarrer une partie de manière rapide :
---------------------------------------
Afin de démarrer une partie avec cette méthode, il n'y a qu'une commande à connaitre :
    /dac quikstart [mode de jeu]

Cette commande débute la partie avec tout les joueurs se trouvant dans la zone de départ en leur assignant des couleurs automatiquement.
Autrement son fonctionnement est exactement le même que celui de de "/dac start".