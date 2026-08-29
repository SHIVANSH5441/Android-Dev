package com.yourname.swiftscope

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun NewsCardPager(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { articles.size })

    VerticalPager(
        state = pagerState
    ) { index ->
        val article = articles[index]
        ArticlePage(article = article, onClick = { onArticleClick(article) })
    }
}

@Composable
fun ArticlePage(article: Article, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = rememberAsyncImagePainter(article.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 6
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Published: ${article.publishedAt}",
            style = MaterialTheme.typography.labelSmall
        )
    }
}