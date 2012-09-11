---
layout: layout
---
Arènes
==================

Le plugin DACv2 est basé sur un système d'arènes.
Chaque arène possède un nom et appartient à un monde et définit son bassin, sa zone de départ, etc...
Plusieurs parties peuvent être jouées dans différentes arènes en même temps sans aucun problème.

Gestion des arènes
-------------------
### Pour obtenir la liste des arènes, vous pouvez utiliser la commande :

    /dac arenas
Qui vous renverra la liste des arènes existantes accompagnées du monde dans lequel elles sont définies.

### Pour créer une nouvelle arène :

    /dac define <nom> [monde]

Si le paramètre world est omis, la commande se basera sur le monde dans lequel se trouve l'utilisateur de la commande (A condition qu'il soit un joueur bien entendu).

### Pour supprimer une arène :

    /dac delete <nom>

Mise en place des arènes
-------------------------
Une arène est composé de différents éléments :
* Un bassin:
Là où les joueurs sauteront.
* Une zone de départ:
La zone dans laquelle les joueurs devront se trouver pour débuter la partie.
* Un plongeoir:
La position à laquelle sont téléportés les joueurs au début de leur tour (elle tient compte des coordonnées x, y et z mais aussi de l'orientation du regard).
* La région englobante:
C'est la région qu'utilise le plugin pour déterminer si un joueur qui est en train de faire une partie et qui reçoit des dégâts de chute doit être considéré comme ayant manqué son saut ou pas (et donc si les dommages doivent être annulées). Cette région est par défaut calculé automatiquement par le plugin en fonction du bassin et du plongeoir, il vous est quand même possible de redéfinir cette zone.

### Définition du plongeoir :
Commencons par le plus simple, la commande à utiliser pour définir le plongeoir d'une arène :

    /dac set diving [-p=<joueur>] <nom de l'arène>

Cette commande défini donc le plongeoir de l'arène donnée en fonction de la position et de l'orientation du regard du joueur donné. Si le paramètre "-p" est omis, ce sont celles de l'utilisateur de la commande qui sont utilisées.

### Définitions des régions
La définition des régions est un peu plus complexe, pour cela vous avez deux principaux méthodes, avec WorldEdit et ses sélections (recommandé car beaucoup plus pratique et flexible) ou sans.
Afin d'utiliser WorldEdit, il vous faut bien évidemment le plugin installé.
Notez toutefois que WorldEdit n'est utilisé que pour la définition, il vous est tout a fait possible d'enlever le plugin WorldEdit dès lors que vos région sont définis.

#### Définition des régions avec WorldEdit
Afin de définir les régions avec WorldEdit, une fois la sélection réalisée, il n'y a plus qu'à utiliser les commandes suivantes:

    # Bassin
    /dac setwe pool <nom de l’arène>
    # Zone de départ
    /dac setwe start <nom de l’arène>
    # Région englobante.
    /dac setwe surrounding <nom de l’arène>

Notez toutefois que, notamment pour des raisons de simplicité et de performances, la région utilisée pour le bassin doit nécessairement être plate (ce qui actuellement exclut uniquement les régions en forme d’ellipse et de sphère; cube, polygone et cylindre sont parfaitement acceptable)

#### Définition des régions sans WorldEdit
Afin de définir les régions sans WorldEdit, le schéma des commandes reste le mêm mais nécessite plus d'arguments (notez la différence "/dac set" et "/dac setwe"):

    # Bassin
    /dac set pool <nom de l’arène> [-p=<joueur>] [-s=<forme>] [arguments]
    # Zone de départ
    /dac set start <nom de l’arène> [-p=<joueur>] [-s=<forme>] [arguments]
    # Région englobante.
    /dac set surrounding <nom de l’arène> [-p=<joueur>] [-s=<forme>] [arguments]

L'un des arguments principal est la forme de la région qui doit être précisé avec le flag "-s".
Les formes acceptées sont les suivantes : 
* cuboid (cube)
* cylinder (cylindre)
* sphere (sphere)
* arbitrary (arbitraire)

Les autres arguments nécessaires sont (sauf pour la forme arbitraire, cf ci-dessous), le rayon et la hauteur.
Tout les régions sont construites en utilisant la position  du joueur donné (ou celle de l'utilisateur de la commande).
Quelques exemples : 

    # Défini un bassin cubique autour du joueur avec un rayon de 4 et une hauteur de 3.
    # La position du joueur sert ici de centre horizontal et sa position en y de y minimum.
    /dac set pool -s=cuboid 4 3
    # Défini un bassin cylindrique autour du joueur avec un rayon de 5 et une hauteur de 5
    # La position est utilisée comme pour le cas du cuboid.
    /dac set pool -s=cyl 5 5
    # Défini une zone de départ spherique autour du joueur avec un rayon de 5.
    # La hauteur est inutile et ignoré si précisé.
    /dac set start -s=sphere 5

#### Concernant les formes arbitraires :
Pour définir une forme arbitraire, il suffit d'utiliser un type de bloc témoin.
Par exemple pour définir un bassin, vous pouvez le construire au préalable comme bon vous semble (la seul contrainte étant qu'il doit être plat) et le remplir d'eau. Ensuite, il suffit de se placer dans un bloc d'eau et d'utiliser la commande :
    /dac set pool -s=arbitrary
Le plugin construira dès lors une région qui inclut tout les blocs adjacent (et du même type, en l'occurence l'eau) au bloc dans lequel vous vous trouvez.

#### "Resélection" des régions pour WorldEdit
Il vous est possible de sélectionner une élément d'une arène avec les commandes (il faut bien entendu que l'élément soit une séléction WorldEdit valide) :

    /dac select pool <nom de l'arene>
    /dac select start <nom de l'arene>
    /dac select surrounding <nom de l'arene>

A noter que vous pouvez très bien sélectionner la zone englobante même si vous ne l'avez pas redéfini. La région sélectionné sera celle défini par le plugin par défaut.