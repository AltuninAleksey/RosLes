package com.example.rosles.ResponceClass

import java.io.Serializable


data class temp_data_userresp(val get:UserResp) : BaseResponceInterface {}

data  class UserResp (
    var id: Int,
    var FIO: String,
    var phoneNumber: String?,
    var email: String?,
    var id_post: Int?,
    var id_working_breeds: Int?,
    var id_role: Int?,
    var id_branches: Int?,
    var id_subject_rf:Int?=0
): Serializable, BaseResponceInterface