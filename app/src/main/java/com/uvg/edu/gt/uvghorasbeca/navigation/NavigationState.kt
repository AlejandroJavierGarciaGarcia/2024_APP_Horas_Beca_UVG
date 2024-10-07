package com.uvg.edu.gt.uvghorasbeca.navigation

 sealed class NavigationState(val route: String){
     object WelcomeScreen : NavigationState("WelcomeScreen")
     object LoginScreen : NavigationState("LoginScreen")
     object  HistoryScreen : NavigationState("HistoryScreen")
     object AdminController : NavigationState("AdminController")
 }