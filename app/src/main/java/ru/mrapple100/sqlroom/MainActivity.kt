package ru.mrapple100.sqlroom

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import ru.mrapple100.sqlroom.local.Belka
import ru.mrapple100.sqlroom.local.BelkaWithPhrase
import ru.mrapple100.sqlroom.local.Phrase
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
    var arrayMockPhrase = ArrayList<Phrase>().apply {
        add(Phrase(1,"Я сегодня поел орехи"))
    }

    val phraseMutableState = MutableStateFlow<List<Phrase>>(arrayMockPhrase)
    val _phraseState = phraseMutableState.asStateFlow()


    var arrayMockBelkaWithPhrase = ArrayList<BelkaWithPhrase>().apply {
        add(BelkaWithPhrase(Belka(123, "Black", "Murka",1),Phrase(0,"123")))
        add(BelkaWithPhrase(Belka(123, "Black", "Murka",1),Phrase(0,"123")))
        add(BelkaWithPhrase(Belka(123, "Black", "Murka",1),Phrase(0,"123")))
    }

    val BelkaWithPhraseMutableState = MutableStateFlow<List<BelkaWithPhrase>>(arrayMockBelkaWithPhrase)
    val _BelkaWithPhraseState = BelkaWithPhraseMutableState.asStateFlow()


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
        var phraseText by remember { mutableStateOf("") }

        var idPhraseNext by remember { mutableStateOf(1) }

        var arrayBelkaWithPhrase = _BelkaWithPhraseState.collectAsState().value
        var arrayPhrase = _phraseState.collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(value = phraseText, onValueChange = {it -> phraseText=it})
            Button(onClick = {
                GlobalScope.launch {
                    SingletoneBD.db.belkaDao().insertPhrase(Phrase(0,phraseText))
                    phraseMutableState.value = SingletoneBD.db.belkaDao().getAllPhrase()
                }
            }) {
                Text(text = "Insert Phrase")
            }
            TextField(value = belkaText, onValueChange = {it -> belkaText=it})
            Button(onClick = {
                GlobalScope.launch {

                    BelkaWithPhraseMutableState.value = SingletoneBD.db.belkaDao().getBelkaWithPhrase()
                    idPhraseNext = _BelkaWithPhraseState.value.size+1
                    SingletoneBD.db.belkaDao().insertBelka(Belka(0, "Black", belkaText,idPhraseNext))
                    BelkaWithPhraseMutableState.value = SingletoneBD.db.belkaDao().getBelkaWithPhrase()

                }
            }) {
                Text(text = "Insert Belka")
            }

            LazyColumn {
                items(arrayBelkaWithPhrase){belkaWithPhrase ->
                    Card(
                        modifier = Modifier.size(200.dp,100.dp)
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = belkaWithPhrase.belka.name)
                                Text(text = belkaWithPhrase.belka.colorTail)
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1.0f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = belkaWithPhrase.phrase.phrase)
                            }
                        }
                    }
                }
            }

            Text(text = "Фразы")

            LazyColumn {
                items(arrayPhrase){phrase ->
                    Card(
                        modifier = Modifier.size(200.dp,100.dp)
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = ""+phrase.phraseId)
                                Text(text = phrase.phrase)
                            }

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

