package com.example.lab8_beta

import java.util.ArrayList
import java.util.HashMap

object Route{

    val route: MutableList<Item> = ArrayList()
    val route_map: MutableMap<String, Item> = HashMap()

    init {
        addItem(Item("1", "Okolice jeziora Rusałka", "Teren ten daje sporo możliwości różnorodnych wariantów tras. Przebiegnięcie dookoła jeziora Rusałka to 4 km. Można jednak pobiec do Strzeszynka, albo dalej do Kiekrza, jeszcze inna opcja to bieg do Krzyżownik. Możemy tu zaplanować zarówno bardzo krótkie bieganie, jak i pokonać dystans maratonu, a nawet ultramaratonu. Dodatkowo ścieżki są bardzo urokliwe. Nic więc dziwnego, że trasy w tych okolicach są pełne ludzi, zarówno biegaczy jak i rowerzystów czy spacerowiczów."))
        addItem(Item("2", "Lasek Marceliński", "W Lasku Marcelińskim jest sporo ścieżek do biegania. W jednym miejscu jest niewielkie wzniesienie. Są także wytyczone dwie trasy biegowe, jedna o długości 4,12 km a druga 2,45."))
        addItem(Item("3", "Jezioro Malta", "To najpopularniejsze miejsce wśród Poznaniaków nie tylko do biegania, ale do uprawiania rekreacji w ogóle. Asfaltowa pętla dookoła jeziora Malta to 5,4 km, odcinki trasy są oznaczone. Można wydłużyć trasę i pobiec w kierunku ulicy Browarnej. Tam już pobiegniemy po drogach leśnych w przyjemnej okolicy. Można zrobić pętlę i wrócić nad Maltę. Nad Maltą organizowanych jest sporo zawodów biegowych, najczęściej na 5 albo na 10 km."))
        addItem(Item("4", "Cytadela", "Park Cytadela położony jest na Wzgórzu Winiarskim, a więc są tu spore różnice w nachyleniu terenu. Jest sporo alejek, w większości asfaltowych, ale są też wąskie parkowe ścieżki."))
        addItem(Item("5", "Park Szachty", "Park Szachty to propozycja dla tych, którzy lubią biegać w malowniczym terenie pośród zieleni. W tej chwili to jeden z najpiękniejszych parków Poznania. Teren Szacht rozciąga się od Junikowa poprzez Fabionowo, Świerczewo, aż do Lubonia. Od strony Lubonia zostały wybudowane asfaltowe alejki, jest też sporo ścieżek pomiędzy stawami."))
    }

    private fun addItem(item: Item) {
        route.add(item)
        route_map.put(item.id, item)
    }

    data class Item(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}