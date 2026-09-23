package com.example.rosles.ResponceClass

data class DachaResp(val count: Int, val data: List<DachaData>) : BaseResponceInterface

data class DachaData(val id: Int, val name: String) : BaseResponceInterface
