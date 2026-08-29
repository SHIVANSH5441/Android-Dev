package com.yourname.swiftscope

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailPagerScreen(
    articles: List<Article>,
    startIndex: Int,
    mode: String,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { articles.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { page ->
            NewsDetailScreen(
                article = articles[page],
                mode = mode,
                onBack = {
                    // Optional: Navigate back using top bar only
                    scope.launch { pagerState.animateScrollToPage(0) }
                    onBack()
                }
            )
        }
    }
}