package com.example.ba2_info.gameutilities

import com.example.ba2_info.gameclasses.Accessoires
import com.example.ba2_info.gameclasses.Porte
import com.example.ba2_info.gameclasses.platforms.Hole
import com.example.ba2_info.gameclasses.platforms.Obstacle
import com.example.ba2_info.gameclasses.platforms.Trap

object GameConstants {
        //Game over ?
        var gameOver = false
        //Time left
        var timeLeft = 10.0

        //Stock de tous les accessoires
        val accessoireA = Accessoires("rien dans le crâne",      0, 0, 0f, 0f, 0f, 0f)
        val accessoireB = Accessoires("tout nu et tout bronzé",  0, 1, 0f, 0f, 0f, 0f)
        val accessoireC = Accessoires("rien dans les guiboles",  0, 2, 0f, 0f, 0f, 0f)
        val accessoireD = Accessoires("Yannick Noah",            0, 3, 0f, 0f, 0f, 0f)

        val accessoire1 = Accessoires("Chapeau de paille",       1, 0, 0f, 0f, 0f, 0f)
        val accessoire2 = Accessoires("Chemise de paysan",       1, 1, 0f, 0f, 0f, 0f)
        val accessoire3 = Accessoires("Pantalon de paysan",      1, 2, 0f, 0f, 0f, 0f)
        val accessoire4 = Accessoires("Chaussures trouées",      1, 3, 0f, 0f, 0f, 0f)

        val accessoire7 = Accessoires("Casque de vélo",          2, 0, 0f, 0f, 0f, 0f)
        val accessoire6 = Accessoires("Cotte de maille ",        2, 1, 0f, 0f, 0f, 0f)
        val accessoire5 = Accessoires("Pantalon renforcé ",      2, 2, 0f, 0f, 0f, 0f)
        val accessoire8 = Accessoires("Nouvelles sandales",      2, 3, 0f, 0f, 0f, 0f)

        val accessoire9 = Accessoires("Grèves en acier",         3, 2, 0f, 0f, 0f, 0f)
        val accessoire10 = Accessoires("Plastron en acier",      3, 1, 0f, 0f, 0f, 0f)
        val accessoire11 = Accessoires("Heaume de chevalier",    3, 0, 0f, 0f, 0f, 0f)
        val accessoire12 = Accessoires("Solerets renforcés",     3, 3, 0f, 0f, 0f, 0f)

        var listeaccess = listOf(
                accessoireA, accessoireB, accessoireC, accessoireD,
                accessoire1, accessoire2, accessoire3, accessoire4,
                accessoire5, accessoire6, accessoire7, accessoire8,
                accessoire9, accessoire10, accessoire11, accessoire12
        )

        var sol =               Obstacle(0f,0f,0f,0f)
        var plateforme2 =       Obstacle(0f,0f,0f,0f)
        var plateforme3 =       Obstacle(0f,0f,0f,0f)
        var plateformeDebut =   Obstacle(0f,0f,0f,0f)
        var plateforme4 =       Obstacle(0f,0f,0f,0f)

        var trap1 =             Trap(1,0f, 0f, 0f, 0f)
        var trapVerti =         Trap(1,0f, 0f, 0f, 0f)
        var trap2 =             Trap(1,0f, 0f, 0f, 0f)
        var trap3 =             Trap(1,0f, 0f, 0f, 0f)
        var hole =              Hole(0f, 0f, 0f, 0f)

        var obstacles = listOf(
                sol, plateforme2, plateforme3, plateformeDebut, plateforme4, trap1,
                               trapVerti, trap2, trap3, hole
        )
        var porte = Porte()

        var levelSetup = listOf(
                accessoire1, accessoire2, sol, plateforme2, plateforme3,
                                plateformeDebut, plateforme4, trap1, trapVerti, trap2,
                                trap3, hole)

}
