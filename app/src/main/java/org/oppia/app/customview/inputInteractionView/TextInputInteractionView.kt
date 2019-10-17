package org.oppia.app.customview.inputInteractionView

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.widget.EditText
import org.oppia.app.R
import org.oppia.app.model.InteractionObject

/** The custom EditText class for text input interaction view. */
class TextInputInteractionView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = android.R.attr.editTextStyle,
  private var placeholder: String = "Write here."
) : EditText(context, attrs, defStyle), InteractionAnswerRetriever {
  init {
    attributes()
  }

  constructor(context: Context?, placeholder: String) : this(context!!) {
    this.placeholder = placeholder
    attributes()
  }

  /** This function contains default attributes of [TextInputInteractionView]. */
  private fun attributes() {
    setBackgroundResource(R.drawable.edit_text_background)
    val paddingPixel = context.resources.getDimension(R.dimen.padding_8)
    val density = resources.displayMetrics.density
    val paddingDp = (paddingPixel * density).toInt()
    setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
    hint = placeholder
    this.setEditTextMaxLength(length = 200)
    setLongClickable(false)
  }

  private fun setEditTextMaxLength(length: Int) {
    val filterArray = arrayOfNulls<InputFilter>(1)
    filterArray[0] = InputFilter.LengthFilter(length)
    filters = filterArray
  }

  override fun getPendingAnswer(): InteractionObject {
    if (text.isEmpty()) {
      return InteractionObject.newBuilder().build()
    } else {
      return InteractionObject.newBuilder().setNormalizedString(text.toString()).build()
    }
  }
}