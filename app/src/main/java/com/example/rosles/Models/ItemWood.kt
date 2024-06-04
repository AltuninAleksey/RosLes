package com.example.roslesdef.Models

class ItemWood(var name:String,var id:Int=0 )


class SpinerItem(var name:String,var id:Int=0,var check:Boolean=false ){

    override fun toString(): String {
        return name
    }
}

