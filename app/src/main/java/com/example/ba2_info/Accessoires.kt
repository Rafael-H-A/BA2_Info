package com.example.ba2_info

class Accessoires(var name: String="NoName", var power: Int=0, val endroit: Int = 0) {


    fun dress(name: String, perso: Personnage){
        perso.equipment[endroit] =    GameConstants.accessoires1                     /* Doit changer la puissance du perso */ // Attention à changer
        perso.power += power                       /* On change dans le dico/ le mec la valeur */


    }/* Problème de type*/
    fun undress(name : String, perso : Personnage){
        perso.equipment[endroit] = GameConstants.accessoires1
        perso.power -= power
    }
}




/* Légende des endroits :
        0 -> Tête
        1 -> Haut
        2 -> Bas
        3 -> Pieds
 ?*/