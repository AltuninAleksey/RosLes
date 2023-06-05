package com.example.rosles.ResponceClass

data class ForestlyResp(var data:List<Forestly>) : BaseResponceInterface

data class Forestly(var id:Int,var name_forestly:String) : BaseResponceInterface