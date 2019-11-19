package `in`.company.contacts.util

import android.content.Context
import android.graphics.*
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import kotlin.random.Random


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.setVisible(visibility: Boolean) {
    this.visibility = if (visibility) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Context.logToast(
    message: String,
    length: Int = Toast.LENGTH_SHORT,
    logTAG: String = "LogToast"
) {
    Toast.makeText(
        this,
        message,
        length
    ).show()
    Log.d(logTAG, message)
}

fun log(
    message: String,
    logTAG: String = "Logger"
) {
    Log.d(logTAG, message)
}

fun String.isNullOrBlankOrNullString() =
    this.isNullOrBlank() || this == "null"


fun generateRandomColor(): Int {

    val mRandom = Random(System.currentTimeMillis())

    val baseColor = Color.argb(255, mRandom.nextInt(50), mRandom.nextInt(50), mRandom.nextInt(50))

    val baseRed = Color.red(baseColor)
    val baseGreen = Color.green(baseColor)
    val baseBlue = Color.blue(baseColor)

    val red = (baseRed + mRandom.nextInt(256)) / 2
    val green = (baseGreen + mRandom.nextInt(256)) / 2
    val blue = (baseBlue + mRandom.nextInt(256)) / 2

    return Color.rgb(red, green, blue)
}

fun ImageView.generateImage(toString: String) {
    val width = 200
    val height = 200
    val textSize = 40f
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = generateRandomColor()

    canvas.drawCircle(
        (bitmap.width / 2).toFloat(),
        (bitmap.height / 2).toFloat(),
        (bitmap.width / 2).toFloat(),
        paint
    )
    val scale = context.resources.displayMetrics.density

    paint.textAlign = Paint.Align.CENTER
    paint.color = Color.WHITE
    paint.textSize = textSize * scale

    val bounds = Rect()

    paint.getTextBounds(toString, 0, toString.length, bounds)

    canvas.drawText(
        toString,
        (bitmap.width / 2).toFloat(),
        (bitmap.height / 2 + bounds.height() / 2).toFloat(),
        paint
    )
    setImageBitmap(bitmap)
}

fun SpannableString.spanWith(target: String, apply: SpannableBuilder.() -> Unit) {
    val builder = SpannableBuilder()
    apply(builder)
    val start = this.indexOf(target)
    val end = start + target.length
    if (start > 0) {
        setSpan(builder.what, start, end, builder.flags)
        Log.d("spanWith", "Span is set")
    }
}

class SpannableBuilder {
    lateinit var what: Any
    var flags: Int = 0
}
