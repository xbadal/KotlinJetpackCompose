package com.example.wishlistapp.data

data class Wish(
    val id: Long = 0L,
    val title: String = "",
    val description: String = ""
)


object DummyWish {
    val wishList = arrayOf(
        Wish(
            title = "Apple watch 6", description = "Apple watch has quick notification and best " +
                    "for fitness"
        ),
        Wish(
            title = "Apple watch 6", description = "Apple watch has quick notification and best " +
                    "for fitness"
        ),
        Wish(
            title = "Apple watch 6", description = "Apple watch has quick notification and best " +
                    "for fitness"
        ),
        Wish(
            title = "Apple watch 6", description = "Apple watch has quick notification and best " +
                    "for fitness"
        ),
        Wish(
            title = "Apple watch 6", description = "Apple watch has quick notification and best " +
                    "for fitness"
        )
    )
}