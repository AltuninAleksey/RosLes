package com.example.rosles.ResponceClass

data class CvartalResp(var data:List<Cvartal>) : BaseResponceInterface

data class Cvartal(var id:Int,var quarter_name:String) : BaseResponceInterface