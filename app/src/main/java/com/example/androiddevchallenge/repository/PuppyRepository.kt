package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Puppy

class PuppyRepository {
	private val puppies = listOf(
		Puppy(
			"1",
			"Kevin",
			"The Pug",
			"Kevin is tired of being home alone",
			R.drawable.kevin
		),
		Puppy(
			"2",
			"Pixel",
			"White Retriever",
			"Pixel can retrieve the treats from the deepest parts of the couch",
			R.drawable.pixel
		),
		Puppy(
			"3",
			"Ziggy",
			"The Street Breed",
			"Ziggy is already much stronger than his hooman Jaime",
			R.drawable.ziggy
		)
	)

	fun getPuppies(): List<Puppy> = puppies

	fun getPuppy(id: String): Puppy {
		return puppies.first { puppy -> puppy.id == id }
	}
}