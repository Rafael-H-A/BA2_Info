package com.example.ba2_info

fun main() {
    val accessoires1 = Accessoires("Pantalon de paysan", 1, 0)
    val accessoires2 = Accessoires("Chemise de paysan", 1, 1)
    val accessoires3 = Accessoires("Chapeau de paille", 1, 2)
    val accessoires4 = Accessoires("Chaussures trouées", 1, 3)

    val accessoires5 = Accessoires("Pantalon renforcé ", 2, 0)
    val accessoires6 = Accessoires("Cotte de maille ", 2, 1)
    val accessoires7 = Accessoires("Casque de vélo", 2, 2)
    val accessoires8 = Accessoires("Nouvelles sandales", 2, 3)
}

class Accessoires(var name: String="NoName", var power: Int=0, val endroit: Int = 0) {


    fun dress(name: String, perso: Personnage){
        perso.equipment[endroit] = name                        /* Doit changer la puissance du perso */
        perso.power += power                       /* On change dans le dico/ le mec la valeur */


    }/* Problème de type*/
    fun undress(name : String, perso : Personnage){
        perso.equipment[endroit] = " "
        perso.power -= power
    }
}




/* Légende des endroits :
        0 -> Tête
        1 -> Haut
        2 -> Bas
        3 -> Pieds
 ?*/