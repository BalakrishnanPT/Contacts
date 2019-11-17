package `in`.balakrishnan.contacts.util

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import kotlin.random.Random


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
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
