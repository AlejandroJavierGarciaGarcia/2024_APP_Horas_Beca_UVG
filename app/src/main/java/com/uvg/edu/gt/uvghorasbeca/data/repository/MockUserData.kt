package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.uvg.edu.gt.uvghorasbeca.data.models.UserData

object MockUserData {
    val userList = listOf(
        UserData(
            id = "1",
            email = "cru23110@uvg.edu.gt",
            password = "password123",
            completedHours = 40,
            pendingHours = 10,
            isAdmin = false
        ),
        UserData(
            id = "2",
            email = "admin@uvg.edu.gt",
            password = "adminpassword",
            completedHours = 50,
            pendingHours = 20,
            isAdmin = true
        )

    )
}

