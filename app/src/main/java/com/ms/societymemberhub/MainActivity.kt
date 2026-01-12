package com.ms.societymemberhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ms.societymemberhub.ui.auth.LoginScreen
import com.ms.societymemberhub.ui.dashboard.HomeScreen
import com.ms.societymemberhub.ui.dashboard.StartScreen
import com.ms.societymemberhub.utils.UserRole
import com.ms.societymemberhub.viewmodel.AnnouncementsViewModel
import com.ms.societymemberhub.viewmodel.MembersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val membersViewModel = MembersViewModel()
            val announcementsViewModel = AnnouncementsViewModel()

            NavHost(
                navController = navController,
                startDestination = "start"
            ) {

                composable("start") {
                    StartScreen(
                        onAdminClick = {
                            navController.navigate("admin_login")
                        },
                        onMemberClick = {
                            navController.navigate("member_home")
                        }
                    )
                }

                composable("admin_login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("admin_home") {
                                popUpTo("admin_login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("member_home") {
                    HomeScreen(
                        role = UserRole.MEMBER,
                        membersViewModel = membersViewModel,
                        announcementsViewModel = announcementsViewModel,
                        onLogout = {
                            navController.navigate("start") {
                                popUpTo("member_home") { inclusive = true }
                            }
                        }
                    )
                }

                composable("admin_home") {
                    HomeScreen(
                        role = UserRole.ADMIN,
                        membersViewModel = membersViewModel,
                        announcementsViewModel = announcementsViewModel,
                        onLogout = {
                            navController.navigate("start") {
                                popUpTo("admin_home") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
