package org.oppia.app.databinding

import android.content.Context
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.oppia.app.R
import java.text.SimpleDateFormat
import java.util.*

/** Binds date text with relative time. */
@BindingAdapter("profile:created")
fun setProfileDataText(textView: TextView, timestamp: Long) {
  // TODO(#555): Create one central utility file from where we should access date format or even convert date timestamp to string from that file.
  val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
  val time = sdf.format(Date(timestamp))
  textView.text = String.format(textView.context.getString(R.string.profile_edit_created, time))
}

@BindingAdapter("profile:lastVisited")
fun setProfileLastVisitedText(textView: TextView, timestamp: Long) {
  // TODO(#555): Create one central utility file from where we should access date format or even convert date timestamp to string from that file.
  textView.text =
    String.format(textView.context.getString(R.string.profile_last_used) + " " + getTimeAgo(timestamp,textView.context))
}

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

private fun currentDate(): Date {
  val calendar = Calendar.getInstance()
  return calendar.time
}

fun getTimeAgo(time: Long, context: Context): String {
  var time = time
  if (time < 1000000000000L)
  // if timestamp given in seconds, convert to millis
    time *= 1000

  val now = currentDate().time
  if (time > now || time <= 0) return ""

  val res = context.resources
  val timeDifference = now - time
  return when {
    timeDifference < MINUTE_MILLIS -> context.getString(R.string.just_now)
    timeDifference < 50 * MINUTE_MILLIS -> context.getString(
      R.string.time_ago,
      res.getQuantityString(R.plurals.minutes, timeDifference.toInt() / MINUTE_MILLIS, timeDifference / MINUTE_MILLIS)
    )
    timeDifference < 24 * HOUR_MILLIS -> context.getString(
      R.string.time_ago,
      res.getQuantityString(R.plurals.hours, timeDifference.toInt() / HOUR_MILLIS, timeDifference / HOUR_MILLIS)
    )
    timeDifference < 48 * HOUR_MILLIS -> context.getString(R.string.yesterday)
    else -> context.getString(
      R.string.time_ago,
      res.getQuantityString(R.plurals.days, timeDifference.toInt() / DAY_MILLIS, timeDifference / DAY_MILLIS)
    )
  }
}
