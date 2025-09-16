package com.example.employee_application.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ScannerOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 8f
        isAntiAlias = true
    }

    private val maskPaint = Paint().apply {
        color = Color.parseColor("#99000000") // semi-transparent mask
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left = width * 0.1f
        val top = height * 0.1f
        val right = width * 0.9f
        val bottom = height * 0.9f
        val rect = RectF(left, top, right, bottom)

        // Draw dim background
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), maskPaint)

        // Clear scanner window
        val clearPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        canvas.drawRect(rect, clearPaint)

        // Draw corner lines
        val cornerLength = 60f
        val cornerStroke = 12f
        paint.strokeWidth = cornerStroke

        // top-left
        canvas.drawLine(left, top, left + cornerLength, top, paint)
        canvas.drawLine(left, top, left, top + cornerLength, paint)

        // top-right
        canvas.drawLine(right, top, right - cornerLength, top, paint)
        canvas.drawLine(right, top, right, top + cornerLength, paint)

        // bottom-left
        canvas.drawLine(left, bottom, left + cornerLength, bottom, paint)
        canvas.drawLine(left, bottom, left, bottom - cornerLength, paint)

        // bottom-right
        canvas.drawLine(right, bottom, right - cornerLength, bottom, paint)
        canvas.drawLine(right, bottom, right, bottom - cornerLength, paint)
    }
}
