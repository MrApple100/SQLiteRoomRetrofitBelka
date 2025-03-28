package ru.mrapple100.sqlroom

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mrapple100.sqlroom.ui.theme.SQLRoomTheme

class MainActivity : ComponentActivity() {

    init{
        context = this
    }

    companion object{
        private var context:MainActivity? =null
        fun getContext():Context{
            return context!!.applicationContext
        }
    }

    var arrayMockBelka = ArrayList<Belka>().apply {
        add(Belka(123, "Black", "Murka"))
        add(Belka(122, "Black", "Murka"))
        add(Belka(121, "Black", "Murka"))
    }

    val belkaMutableState = MutableStateFlow<List<Belka>>(arrayMockBelka)
    val _belkaState = belkaMutableState.asStateFlow()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SQLRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        
        var belkaText by remember { mutableStateOf("") }
        var arrayBelka = _belkaState.collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = belkaText, onValueChange = {it -> belkaText=it})
            Button(onClick = {
                GlobalScope.launch {
                    SingletoneBD.db.belkaDao().insertBelka(Belka(0, "Black", belkaText))
                    belkaMutableState.value = SingletoneBD.db.belkaDao().getAllBelka()
                }
            }) {
                Text(text = "Insert Belka")
            }

            LazyColumn {
                items(arrayBelka){belka ->
                    Card(
                        modifier = Modifier.size(200.dp,100.dp)
                    ) {
                        Column {
                            Text(text = belka.name)
                            Text(text = belka.colorTail)
                        }
                    }
                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SQLRoomTheme {
            Greeting("Android")
        }
    }
}

