package com.example.translatorfromeng.data.remote.dto

data class WordResponse(
    val text: String,
    val meanings: List<Meaning>
)
