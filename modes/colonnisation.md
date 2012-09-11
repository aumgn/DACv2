---
layout: layout
---
Colonnisation
=============

Colonnisation est un mode de jeu qui mêle efficacement stratégie et skill.

## Principe
Les joueurs sautent les uns après les autres et marquent un certain nombre de point pour ce saut. Le jeu s’arrête une fois le bassin remplis et le gagnant est le joueur ayant le meilleur score.
Le nombre de point que gagne un joueur à chaque saut est le nombre de colonne de sa couleur adjacentes (directement ou indirectement) à la colonne qu'il vient de placer.

Par exemple : 

o o o o o # # o o

o o X # # # # o o

o # # # # Y o Z o

o o o o W # # # o

Si le joueur saute en `X` et que les `#` représente les colonnes de sa couleur, les `o` l'eau, il va marquer 11 points (10 dièses + la nouvelle colonne). S'il saute en `Y` ou en `W`, il marquera 14 points. A noter que les diagonales ne sont pas prises en compte, par exemple, sauter en `Z` ne ferait gagner que 4 points.