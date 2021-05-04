package com.example.money.enumitem

import com.example.money.R


enum class ItemExpenceSpinner(var Name: String, var img: Int, var ID: Int) {
    PRODUCTS("Продукты", R.drawable.products, 1),
    HOME("Дом", R.drawable.home, 2),
    CAFE_END_RESTORANT("Кафе или Ресторан", R.drawable.cafe_end_restorant, 3),
    TRANSPORT("Транспорт", R.drawable.transport, 4),
    ENTERTAINMENTS("Развлечения", R.drawable.entertainments, 5),
    CLOTHES("Одежда", R.drawable.clothes, 6),
    CAR("Автомобиль", R.drawable.car, 7),
    ANIMAL("Домашние животное", R.drawable.animal, 8),
    CHILDREN("Дети", R.drawable.children, 9),
    HEALTH("Здоровье", R.drawable.health, 10),
    BAUTY("Красота и уход", R.drawable.bauty, 11),
    VACATION("Путешествия", R.drawable.vacation, 12),
    ELECTRONICS("Техника", R.drawable.electronics, 13),
    CONNECTION("Услуги связи", R.drawable.connection, 14),
    OTHER("Другое", R.drawable.other, 15),
}