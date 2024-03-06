package ru.rsue.rubanova

import java.util.*

class Book {
    var id: UUID
        private set
    var title = ""
    var date: Date
    var isReaded = false
    init {
        id = UUID.randomUUID()
        date = Date()
    }
}