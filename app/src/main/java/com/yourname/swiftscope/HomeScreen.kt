package com.yourname.swiftscope

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.animation.Crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    articles: List<Article>,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current //  Get context here
    var isGridMode by rememberSaveable { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SidebarDrawer(context = context) //  Pass context to drawer
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SwiftScope") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { isGridMode = !isGridMode }) {
                            Icon(
                                imageVector = if (isGridMode)
                                    Icons.AutoMirrored.Filled.ViewList
                                else Icons.Default.GridView,
                                contentDescription = "Toggle View"
                            )
                        }
                        IconButton(onClick = onToggleTheme) {
                            Icon(
                                imageVector = if (isDarkMode)
                                    Icons.Default.LightMode
                                else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme"
                            )
                        }
                    }
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    val onClick: (Article) -> Unit = { article ->
                        val encoded = URLEncoder.encode(
                            Gson().toJson(article),
                            StandardCharsets.UTF_8.toString()
                        )
                        val mode = if (isGridMode) "grid" else "list"
                        Log.d("NAV_DEBUG", "Navigating to detail/$encoded/$mode")
                        val index = articles.indexOf(article)
                        navController.navigate("detailPager/$index/$mode")
                    }

                    // 🔄 Crossfade between list and grid views
                    Crossfade(targetState = isGridMode, label = "ViewToggleAnimation") { grid ->
                        if (grid) {
                            NewsCardPager(articles = articles, onArticleClick = onClick)
                        } else {
                            NewsCardList(articles = articles, onArticleClick = onClick)
                        }
                    }
                }
            }
        )
    }
}