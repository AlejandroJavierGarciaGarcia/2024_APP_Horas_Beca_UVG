package com.uvg.edu.gt.uvghorasbeca.data.models

data class UserData(
    var id: String = "",
    var email: String = "",
    var completedHours: Int = 0,
    var pendingHours: Int = 0,
    var isAdmin: Boolean = false,
    var assignedActivities: List<String> = emptyList(),
    var publishedActivities: List<String> = emptyList()
) {

}
