package com.sg.spacy12.model
import com.google.firebase.Timestamp

class Comment constructor(
    val username: String,
    val timestamp: Timestamp?,
    val commentTxt: String,
    val documentId: String,
    val userId:String
)