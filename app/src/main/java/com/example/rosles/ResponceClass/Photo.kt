package com.example.rosles.ResponceClass

import android.graphics.Bitmap
import java.sql.Blob

data class Photo(var photo:Bitmap?,val id_sample:Int,val latitude:Float,val longitude:Float,
                 val date:String) : BaseResponceInterface
