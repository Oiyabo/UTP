 package com.example.utp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utp.ui.theme.UTPTheme
import modul.KumpulanMakanan
import modul.Makanan

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTPTheme {
                Surface(modifier = Modifier.fillMaxSize().safeDrawingPadding(), color = androidx.compose.material3.MaterialTheme.colorScheme.background) {
                    val navCon = rememberNavController()
                    AppNavigation(navCon)
                }
            }
        }
    }
}


@Composable
fun AppNavigation(navCon: NavHostController) {
    NavHost(navCon, startDestination = "home") {
        composable("home") {
            HomeScreen(navCon)
        }
        composable("tambah") {
            TambahScreen(navCon)
        }
        composable("detail/{nama}") { be ->
            val nama = be.arguments?.getString("nama")
            val makanan = KumpulanMakanan.data.find {
                it.nama == nama
            }
            if (makanan != null) {
                Detail(makanan, navCon)
            }
        }
    }
}

@Composable
fun TambahScreen(navCon: NavHostController) {
    var nama by remember { mutableStateOf("") }
    var des by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }

    Column() {
        Text(text = "Tambah Makanan")
        Column() {
            TextField(value = nama, onValueChange = { nama = it }, placeholder = { Text("Nama Burger") })
            TextField(value = des, onValueChange = { des = it }, placeholder = { Text("Deskripsi") })
            TextField(value = harga, onValueChange = { harga = it }, placeholder = { Text("Harga") })
            TextField(value = rating, onValueChange = { rating = it }, placeholder = { Text("Rating") })
            Button(onClick = {navCon.popBackStack()}) { Text("Simpan") }
            Button(onClick = {navCon.popBackStack()}) { Text("Kembali") }
        }
    }
}

@Composable
fun Detail(makanan: Makanan, navCon: NavHostController) {

    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 150.dp, bottom = 150.dp ).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        Image(modifier = Modifier.height(150.dp).width(150.dp), painter = painterResource(id = makanan.gambar), contentDescription = makanan.nama)
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = makanan.nama, fontSize = 20.sp)
            Text(text = makanan.deskripsi, fontSize = 20.sp)
            Text(text = makanan.harga.toString(), fontSize = 20.sp)
            Text(text = makanan.rating.toString(), fontSize = 20.sp)
        }
        Button(onClick = {navCon.popBackStack()}) { Text("Pesan Sekarang") }
        Button(onClick = {navCon.popBackStack()}) { Text("Kembali") }
    }
}

@Composable
fun HomeScreen(navCon: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {
        Text(text = "Aldi's Burger", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
        TextField(modifier = Modifier.padding(10.dp), value = searchText, onValueChange = { searchText = it }, placeholder = { Text("Search for a burger...") })
        Button(modifier = Modifier.padding(10.dp),onClick = {navCon.navigate("tambah")}) { Text("Tambah Burger") }

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            val searched = if (searchText.isEmpty()) KumpulanMakanan.data else KumpulanMakanan.data.filter { it.nama.contains(searchText, ignoreCase = true) }
            items(searched) {
                MakananCard(it, navCon)
            }
        }
    }
}

@Composable
fun MakananCard(makanan: Makanan, navCon: NavHostController) {
    Card(modifier = Modifier
        .padding(20.dp).fillMaxWidth().height(200.dp)
        ,onClick = {navCon.navigate("detail/${makanan.nama}")}
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            Image(painter = painterResource(id = makanan.gambar), contentDescription = makanan.nama)
            Text(text = makanan.nama)
            Text(text = makanan.deskripsi)
            Text(text = makanan.harga.toString())
            Text(text = makanan.rating.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UTPTheme {

    }
}