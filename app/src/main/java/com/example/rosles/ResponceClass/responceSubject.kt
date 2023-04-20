package com.example.rosles.ResponceClass

// Класс для получения ответа из запроса
// для удобства классы которые получают ответ должны храниться в директории ResponceClass
data class responceSubject(
    var post:Post
)
data class Post(var name_subject_RF:String)
