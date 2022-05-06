package com.example.ba2_info

import android.graphics.RectF

object GameConstants {
        //Stock de tous les accessoires
        val accessoireA = Accessoires("rien dans les guiboles", 0, 2, 0f, 0f, 0f, 0f)
        val accessoireB = Accessoires("tout nu et tout bronzé", 0, 1, 0f, 0f, 0f, 0f)
        val accessoireC = Accessoires("rien dans le crâne", 0, 0,0f, 0f, 0f, 0f)
        val accessoireD = Accessoires("Yannick Noah", 0, 3,0f, 0f, 0f, 0f)

        val accessoire1 = Accessoires("Pantalon de paysan", 1, 2, 0f, 0f, 0f, 0f)
        val accessoire2 = Accessoires("Chemise de paysan", 1, 1, 0f, 0f, 0f, 0f)
        val accessoire3 = Accessoires("Chapeau de paille", 1, 0, 0f, 0f, 0f, 0f)
        val accessoire4 = Accessoires("Chaussures trouées", 1, 3, 0f, 0f, 0f, 0f)

        val accessoire5 = Accessoires("Pantalon renforcé ", 2, 2, 0f, 0f, 0f, 0f)
        val accessoire6 = Accessoires("Cotte de maille ", 2, 1,0f, 0f, 0f, 0f)
        val accessoire7 = Accessoires("Casque de vélo", 2, 0, 0f, 0f, 0f, 0f)
        val accessoire8 = Accessoires("Nouvelles sandales", 2, 3,0f, 0f, 0f, 0f)

        val accessoire9 = Accessoires("Grèves en acier",3,2, 0f, 0f, 0f, 0f)
        val accessoire10 = Accessoires("Plastron en acier",3,1, 0f, 0f, 0f, 0f)
        val accessoire11 = Accessoires("Heaume de chevalier",3,0, 0f, 0f, 0f, 0f)
        val accessoire12 = Accessoires("Solerets renforcés",3,3, 0f, 0f, 0f, 0f)

        var listeaccess = listOf<Accessoires>(accessoireA, accessoireB, accessoireC, accessoireD,
            accessoire1, accessoire2, accessoire2, accessoire3, accessoire4, accessoire5, accessoire6,
            accessoire7, accessoire8, accessoire9, accessoire10, accessoire11, accessoire12)



    }
