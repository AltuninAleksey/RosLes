package com.example.rosles.ResponceClass

import java.io.Serializable

data  class UserResp (
    var id: Int,
    var FIO: String,
    var phoneNumber: String,
    var email: String,
    var id_post: Int,
    var id_working_breeds: Int,
    var id_role: Int,
    var id_branches: Int
): Serializable, BaseResponceInterface