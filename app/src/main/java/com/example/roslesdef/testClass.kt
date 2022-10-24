package com.example.roslesdef

import com.google.gson.annotations.SerializedName

data class testClass(

  @SerializedName("to0_2") var to0_2: String,
  @SerializedName ("from0_21To0_5") var from0_21To0_5:String,
  @SerializedName ("from0_6To1_0") var from0_6To1_0:String,
  @SerializedName ("from1_1to1_5") var from1_1to1_5:String,
  @SerializedName ("from1_5") var from1_5:String,
  @SerializedName ("max_height") var max_height:String,
  @SerializedName ("id_breed") var id_breed_id:String,
  @SerializedName ("id_sample") var id_sample_id:String,
  @SerializedName ("id_type_of_reproduction") var id_type_of_reproduction_id:String,
)
