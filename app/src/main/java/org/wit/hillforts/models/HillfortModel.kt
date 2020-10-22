package org.wit.hillforts.models

data class HillfortModel(var title: String = "",
                         var description: String = "",
                         var location: Float = 0.0f,
                        var visited: Boolean = false,
                        var dateVisited: Int= 0,
                        var additionalNotes: String = "",
                         var image: String = "")