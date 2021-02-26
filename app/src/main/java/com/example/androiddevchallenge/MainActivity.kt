/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.repository.PuppyRepository

class MainActivity : AppCompatActivity() {
	private val puppyRepository = PuppyRepository()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyTheme {
				MyApp(puppyRepository)
			}
		}
	}
}

// Start building your app here!
@Composable
fun MyApp(puppyRepository: PuppyRepository) {
	val navController = rememberNavController()
	Surface(color = MaterialTheme.colors.background) {
		NavHost(navController, startDestination = "list") {
			composable("list") {
				PuppyCardList(
					navController = navController,
					puppies = puppyRepository.getPuppies()
				)
			}
			composable("detail/{puppyId}") { backStackEntry ->
				PuppyDetail(puppyRepository.getPuppy(backStackEntry.arguments?.getString("puppyId")!!))
			}
		}
	}
}

@Composable
fun PuppyCardList(navController: NavController, puppies: List<Puppy>) {
	LazyColumn {
		items(puppies) { puppy ->
			PuppyCard(puppy = puppy) {
				navController.navigate("detail/${puppy.id}")
			}
		}
	}
}

@Composable
fun PuppyCard(puppy: Puppy, onclick: () -> Unit) {
	Card(
		elevation = 4.dp,
		modifier = Modifier.padding(16.dp)
	) {
		Column {
			Image(
				painter = painterResource(id = puppy.imageResId),
				contentDescription = null,
				modifier = Modifier
					.clickable(onClick = onclick)
					.height(320.dp)
					.fillMaxWidth(),
				contentScale = ContentScale.Crop
			)

			Text(
				modifier = Modifier.padding(start = 8.dp, top = 8.dp),
				text = puppy.name,
				style = TextStyle(fontSize = 20.sp, fontWeight = Bold)
			)
			Text(
				modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
				text = puppy.breed,
				style = TextStyle(fontSize = 18.sp)
			)
		}
	}
}

@Composable
fun PuppyDetail(puppy: Puppy) {
	ConstraintLayout {
		// Create references for the composables to constrain
		val (textName, textBreed, image, textDescription, button) = createRefs()

		Text(
			modifier = Modifier.constrainAs(textName) {
				top.linkTo(parent.top, margin = 16.dp)
				start.linkTo(parent.start, margin = 16.dp)
				end.linkTo(parent.end, margin = 16.dp)
			},
			text = puppy.name,
			style = TextStyle(fontSize = 20.sp, fontWeight = Bold)
		)
		Text(
			modifier = Modifier.constrainAs(textBreed) {
				top.linkTo(textName.bottom, margin = 8.dp)
				start.linkTo(parent.start, margin = 16.dp)
				end.linkTo(parent.end, margin = 16.dp)
			},
			text = puppy.breed,
			style = TextStyle(fontSize = 18.sp)
		)

		Image(
			painter = painterResource(id = puppy.imageResId),
			contentDescription = null,
			modifier = Modifier
				.constrainAs(image) {
					top.linkTo(textBreed.bottom, margin = 8.dp)
					start.linkTo(parent.start, margin = 16.dp)
					end.linkTo(parent.end, margin = 16.dp)
				}
				.height(320.dp)
				.fillMaxWidth(),
			contentScale = ContentScale.Crop
		)

		Text(
			modifier = Modifier
				.constrainAs(textDescription) {
					top.linkTo(image.bottom, margin = 8.dp)
					start.linkTo(parent.start, margin = 16.dp)
					end.linkTo(parent.end, margin = 16.dp)
				}
				.padding(start = 8.dp, top = 8.dp),
			text = puppy.description,
			style = TextStyle(fontSize = 18.sp)
		)

		Button(
			modifier = Modifier
				.constrainAs(button) {
					top.linkTo(textDescription.bottom, margin = 16.dp)
					start.linkTo(parent.start)
					end.linkTo(parent.end)
					bottom.linkTo(parent.bottom)
				}
				.height(48.dp)
				.fillMaxWidth()
				.padding(start = 16.dp, end = 16.dp), onClick = {}) {
			Text("Adopt me <3")
		}
	}
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
	MyTheme {
		MyApp(PuppyRepository())
	}
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
	MyTheme(darkTheme = true) {
		MyApp(PuppyRepository())
	}
}
