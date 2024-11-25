package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.MainActivity
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.navigation.NavigationState
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel


@Composable
fun ProfileProgressView(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    val authState by authViewModel.authState.observeAsState()
    val userDetails by authViewModel.userDetails.observeAsState()

    val userName = userDetails?.email as? String ?: stringResource(R.string.user)
    val hoursCompleted = userDetails?.completedHours as? Int ?: 0
    val hoursRemaining = userDetails?.pendingHours as? Int ?: 0
    val totalHours = hoursCompleted + hoursRemaining
    val progress = if (totalHours > 0) hoursCompleted.toFloat() / totalHours else 0f

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(CustomColors.SliderBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = stringResource(R.string.user_icon),
                    modifier = Modifier.size(50.dp),
                    tint = CustomColors.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userName,
                    fontWeight = FontWeight.Bold,
                    color = CustomColors.Black,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = CustomColors.PrimaryGreen,
                trackColor = CustomColors.PrimaryGrayLight
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.hours_completed_text, hoursCompleted),
                    color = CustomColors.Black,
                    fontSize = 12.sp
                )

                Text(
                    text = stringResource(id = R.string.hours_remaining_text, hoursRemaining),
                    color = CustomColors.Black,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier.fillMaxWidth()) {
            OptionRow(icon = R.drawable.help_icon, text = stringResource(R.string.help))
            //OptionRow(icon = R.drawable.group_icon, text = "Cambiar usuario")

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        authViewModel.logout()
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout_icon),
                    contentDescription = stringResource(R.string.log_out),
                    tint = CustomColors.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.log_out), color = CustomColors.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun OptionRow(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = CustomColors.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = CustomColors.Black, fontSize = 16.sp)
    }
}

