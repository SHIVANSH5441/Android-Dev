package com.yourname.swiftscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.yourname.swiftscope.ui.theme.SwiftScopeTheme
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(true) }

            SwiftScopeTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash" // Start with Splash
                ) {
                    //  Splash Screen
                    composable("splash") {
                        SplashScreen(navController = navController)
                    }
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            articles = mockArticles,
                            isDarkMode = isDarkMode,
                            onToggleTheme = { isDarkMode = !isDarkMode }
                        )
                    }

                    // 📜 News Detail Pager Screen
                    composable(
                        route = "detailPager/{startIndex}/{mode}",
                        arguments = listOf(
                            navArgument("startIndex") { type = NavType.IntType },
                            navArgument("mode") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
                        val mode = backStackEntry.arguments?.getString("mode") ?: "list"

                        NewsDetailPagerScreen(
                            articles = mockArticles,
                            startIndex = startIndex,
                            mode = mode,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}