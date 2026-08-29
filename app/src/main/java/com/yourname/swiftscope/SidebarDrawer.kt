package com.yourname.swiftscope

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun SidebarDrawer(context: Context) {
    val drawerItems = listOf(
        DrawerItem("Shivansh Saraswat", "https://www.linkedin.com/in/shivansh-saraswat-21010033a/", "shivanshsaraswat492@gmail.com", R.drawable.shivansh),
        DrawerItem("Ayushmaan Joshi", "https://www.linkedin.com/in/ayushmaan-joshi-531120343/", "ayushh.x24@gmail.com", R.drawable.ayushmaan),
        DrawerItem("Manas Srivastava", "https://www.linkedin.com/in/manas-srivastava-69209a324/", "Manassri2006@gmail.com", R.drawable.manas),
        DrawerItem("Darsh Jaiswal", "https://www.linkedin.com/in/darsh-jaiswal-54969b373/", "Darshjaiswal58@gmail.com", R.drawable.darsh),
        DrawerItem("Rishabh Raj", "https://www.linkedin.com/in/rishabh-raj24/", "rishabhrajofficial02@gmail.com", R.drawable.rishabh)
    )

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Text(
                text = "Team Members",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
            )
            HorizontalDivider()

            drawerItems.forEach { member ->
                TeamMemberCardAligned(member, context, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun TeamMemberCardAligned(member: DrawerItem, context: Context, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image on the LEFT
            Image(
                painter = painterResource(id = member.imageRes),
                contentDescription = member.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Name and Buttons on the RIGHT
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, member.linkedInUrl.toUri())
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Default.Link, contentDescription = "LinkedIn")
                    }

                    IconButton(
                        onClick = {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = "mailto:${member.email}".toUri()
                            }
                            context.startActivity(emailIntent)
                        }
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                    }
                }
            }
        }
    }
}