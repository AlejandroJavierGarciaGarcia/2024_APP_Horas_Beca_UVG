package com.uvg.edu.gt.uvghorasbeca.navigation

 sealed class NavigationState(val route: String){
     object WelcomeScreen : NavigationState("WelcomeScreen")
     object LoginScreen : NavigationState("LoginScreen")
     object AdminScreen : NavigationState("name")
     object  HistoryScreen : NavigationState("HistoryScreen")
     object AdminController : NavigationState("AdminController")
     object UserController : NavigationState("UserController")
     object AssignedActivitiesScreen : NavigationState("AssignedActivitiesScreen")
 }