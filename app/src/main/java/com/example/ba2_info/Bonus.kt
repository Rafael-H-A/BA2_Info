package com.example.ba2_info

class Bonus(val bonus : Int =0) {
    fun agir(perso : Personnage){    /* Si c'est bonus != 0 -> Bonus,Si c'est malus != 0 -> Malus*/
            when {
                bonus == 1 -> { perso.power += 1}       /* Si c'est bonus != 0 -> Bonus,Si c'est malus != 0 -> Malus*/
                bonus == 2 -> { perso.power += 2}
                bonus == 3 -> { perso.power -= 1}
                bonus == 4 -> { perso.power += 2}
            }
    }
}