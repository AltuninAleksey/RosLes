package com.example.rosles.ResponceClass

import java.io.Serializable

data class ReproductionResp(var get:List<GETReproductionResp>)

data class GETReproductionResp( var name_reproduction:String)

data class BaseResp(var msg:String)

