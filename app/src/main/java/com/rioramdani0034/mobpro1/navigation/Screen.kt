package com.rioramdani0034.mobpro1.navigation

import com.rioramdani0034.mobpro1.ui.screen.KEY_ID_NOTES

sealed class Screen(val route: String){
    data object Home : Screen("mainScreen")
    data object FormBaru : Screen("detailScreen")
    data object FormUbah : Screen("detailScreen/{$KEY_ID_NOTES}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
}