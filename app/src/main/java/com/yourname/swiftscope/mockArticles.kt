package com.yourname.swiftscope

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String,
    val publishedAt: String,
    val source: String,
    val articleUrl: String
)

val mockArticles = listOf(
    Article(
        title = "SwiftScope Launches",
        description = "Stay updated with the latest tech news.",
        content = "SwiftScope is your go-to news app for everything tech. With real-time updates, sleek design and intuitive UI, never miss a headline again.",
        imageUrl = "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        publishedAt = "2 hours ago",
        source = "Tech Today",
        articleUrl = "https://example.com/article1"
    ),
    Article(
        title = "New AI Revolution",
        description = "AI is transforming the world rapidly.",
        content = "From education to healthcare, artificial intelligence is reshaping industries. Explore how tech giants are leading the charge.",
        imageUrl = "https://plus.unsplash.com/premium_photo-1681426687411-21986b0626a8?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        publishedAt = "4 hours ago",
        source = "AI Times",
        articleUrl = "https://example.com/article2"
    ),
    Article(
        title = "New AI Revolution",
        description = "AI is transforming the world rapidly.",
        content = "From education to healthcare, artificial intelligence is reshaping industries. Explore how tech giants are leading the charge.",
        imageUrl = "https://plus.unsplash.com/premium_photo-1661877737564-3dfd7282efcb?q=80&w=3000&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        publishedAt = "4 hours ago",
        source = "AI Times",
        articleUrl = "https://example.com/article2"
    ),
    Article(
        title = "New AI Revolution",
        description = "AI is transforming the world rapidly.",
        content = "From education to healthcare, artificial intelligence is reshaping industries. Explore how tech giants are leading the charge.",
        imageUrl = "https://via.placeholder.com/300.png",
        publishedAt = "4 hours ago",
        source = "AI Times",
        articleUrl = "https://example.com/article2"
    ),
    Article(
        title = "New AI Revolution",
        description = "AI is transforming the world rapidly.",
        content = "From education to healthcare, artificial intelligence is reshaping industries. Explore how tech giants are leading the charge.",
        imageUrl = "https://via.placeholder.com/300.png",
        publishedAt = "4 hours ago",
        source = "AI Times",
        articleUrl = "https://example.com/article2"
    )
)