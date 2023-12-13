package com.example.rosles.ResponceClass

import retrofit2.http.Body
import java.io.Serializable

data class ReproductionResp(var get:List<GETReproductionResp>): BaseResponceInterface

data class GETReproductionResp( var name_reproduction:String): BaseResponceInterface

data class BaseResp(var msg:String,var body: Body): BaseResponceInterface

