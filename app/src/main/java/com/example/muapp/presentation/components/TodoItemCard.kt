package com.example.muapp.presentation.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.muapp.data.model.TodoItem


// in this file we define a muappitemcard composable that will be reusable
fun onCompleteChange (){}
// in diff screens
@Composable
fun TodoItemCard(
    todo: TodoItem,
    onCompleteChange: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: ()-> Unit

) {
    // check box, text to display text, image container d
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = null
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Text(
                    text = "To-DoItem",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        Row(
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onDeleteClick()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text("Delete", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onEditClick()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Edit", color = Color.White)
                }
            }
//        Image(
//            painter = painterResource(R.drawable.ic_menu_add)
//        )
//        Image(
//            painter = painterResource(R.),
//            contentDescription = null,
//            modifier = Modifier.size(48.dp).clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )

        }

    }
}

