package util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun displayCurrentDateTime(): String {

    val currentTimeStamp = Clock.System.now()
    val date = currentTimeStamp.toLocalDateTime(TimeZone.currentSystemDefault())

    // Format the LocalDate into the desired representation
    val dayOfMonth = date.dayOfMonth
    val month = date.month.toString().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val year = date.year

    val suffix = when {
        dayOfMonth in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    return "$dayOfMonth$suffix $month, $year."
}
