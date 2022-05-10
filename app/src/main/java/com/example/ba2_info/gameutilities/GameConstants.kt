package com.example.ba2_info.gameutilities

import com.example.ba2_info.gameclasses.Accessoires
import com.example.ba2_info.gameclasses.Porte
import com.example.ba2_info.gameclasses.bonus.*
import com.example.ba2_info.gameclasses.platforms.Hole
import com.example.ba2_info.gameclasses.platforms.Obstacle
import com.example.ba2_info.gameclasses.platforms.Trap

object GameConstants {
        const val enemyPower = 5
        var gameOver = false
        var message : String = ""
        const val timeLeft = 30.0
        //Accessories
        val accessoireA = Accessoires(0, 0, 0f, 0f, 0f, 0f)
        val accessoire1 = Accessoires(2, 0, 0f, 0f, 0f, 0f)
        val accessoire2 = Accessoires(4, 0, 0f, 0f, 0f, 0f)
        val accessoire3 = Accessoires(5, 0, 0f, 0f, 0f, 0f)
        var listeaccess = listOf(accessoire1, accessoire2, accessoire3)
        //Obstacles, traps and holes
        var floor1 =    Obstacle(0f,0f,0f,0f)
        var floor2 =    Obstacle(0f,0f,0f,0f)
        var floor3 =    Obstacle(0f,0f,0f,0f)
        var pltf11 =    Obstacle(0f,0f,0f,0f)
        var pltf12 =    Obstacle(0f,0f,0f,0f)
        var pltf13 =    Obstacle(0f,0f,0f,0f)
        var pltf21 =    Obstacle(0f,0f,0f,0f)
        var pltf22 =    Obstacle(0f,0f,0f,0f)
        var pltf31 =    Obstacle(0f,0f,0f,0f)
        var pltf32 =    Obstacle(0f,0f,0f,0f)
        var pltf33 =    Obstacle(0f,0f,0f,0f)
        var pltf34 =    Obstacle(0f,0f,0f,0f)
        var trapvert =    Trap(1,0f, 0f, 0f, 0f)
        var trap1 =        Trap(1,0f, 0f, 0f, 0f)
        var trap2 =        Trap(1,0f, 0f, 0f, 0f)
        var hole1 =        Hole(0f, 0f, 0f, 0f)
        var hole2 =        Hole(0f, 0f, 0f, 0f)
        var holeup =       Hole(0f, 0f, 0f, 0f)
        var obstacles = listOf(floor1, floor2, floor3,pltf11, pltf12, pltf13,  pltf21, pltf22,
                               pltf31, pltf32, pltf33, pltf34, trap1, trapvert, trap2, hole1,
                               hole2, holeup)
        // Final door
        var porte = Porte()
        // Bonuses
        var petitcoeur1 = PetitCoeur()
        var potion1 = Potions(2)
        var peaudebanane1 = Peaudebanane()
        var sablier1 = Sablier()
        var bonuses = listOf(petitcoeur1, potion1, sablier1, peaudebanane1)

        var levelSetup = listOf(
                floor1, floor2, floor3,
                pltf11, pltf12, pltf13, pltf21, pltf22, pltf31, pltf32, pltf33, pltf34,
                trap1, trapvert, trap2,
                hole1, hole2, holeup,
                accessoire1, accessoire2, accessoire3,
                porte,
                petitcoeur1, potion1, sablier1, peaudebanane1)

}
