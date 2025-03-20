package com.example.rosles.ResponceClass


//{"put":200,"ids":[{"obj":{"last":1,"new":734}},{"obj":{"last":2,"new":735}}]}
data class text(val put:Int, val ids:List<id>):BaseResponceInterface


data class id( val obj: OBJ )

data class OBJ ( val last:Int,val new : Int)


