package com.example.drugaddictscounselingsystem.data.model

data class ChatMessages(val id: String, val fromId: String, val massageText: String, val toId: String, val messageTime: Long) {
    constructor() : this("", "", "", "", -1)
}