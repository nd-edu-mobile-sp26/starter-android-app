package edu.nd.pmcburne.hello

import kotlinx.serialization.Serializable

/**
 * This file lists the navigation routes in our app
 */

/**
 * The home screen of our app - MainScreen
 */
@Serializable
object HomeRoute

/**
 * A screen for editing a single counter
 */
@Serializable
data class EditRoute(val id: Long)

