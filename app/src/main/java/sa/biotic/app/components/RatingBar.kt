package sa.biotic.app.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import sa.biotic.app.R

/**
 * Created by Abdelrahman Elghamry on 2019/9/2.
 */

class RatingBar : View {

    private var mMaxStarNum = 5

    private var mRatingNum = 3.3f


    private val mRatingStepSize = 0.1f

    private var mRatingDrawable: Drawable? = null
    var ratingBackgroundDrawable: Drawable? = null

    private var mGapSize: Int = 0

    private var mOrientation = LinearLayout.HORIZONTAL

    var isIndicator = true

    private var mOnRatingBarChangeListener: OnRatingBarChangeListener? = null

    var maxStarNum: Int
        get() = mMaxStarNum
        set(maxStarNum) {
            mMaxStarNum = maxStarNum
            postInvalidate()
        }

    var ratingNum: Float
        get() = mRatingNum
        set(ratingNum) {
            mRatingNum = ratingNum
            postInvalidate()
            if (mOnRatingBarChangeListener != null) {
                mOnRatingBarChangeListener!!.onRatingChanged(this, ratingNum)
            }
        }

    var ratingDrawable: Drawable?
        get() = mRatingDrawable
        set(ratingDrawable) {
            mRatingDrawable = ratingDrawable
            postInvalidate()
        }

    var gapSize: Int
        get() = mGapSize
        set(gapSize) {
            mGapSize = gapSize
            postInvalidate()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    fun setOnRatingBarChangeListener(onRatingBarChangeListener: OnRatingBarChangeListener) {
        mOnRatingBarChangeListener = onRatingBarChangeListener
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartRatingBar)
            mRatingNum = typedArray.getFloat(R.styleable.SmartRatingBar_rating, 2.5f)
            mGapSize = typedArray.getDimensionPixelSize(R.styleable.SmartRatingBar_gap, 0)
            mMaxStarNum = typedArray.getInt(R.styleable.SmartRatingBar_maxRating, 5)
            isIndicator = typedArray.getBoolean(R.styleable.SmartRatingBar_indicator, true)
            mOrientation =
                typedArray.getInt(R.styleable.SmartRatingBar_orientation, LinearLayout.HORIZONTAL)
            mRatingDrawable = typedArray.getDrawable(R.styleable.SmartRatingBar_ratingDrawable)
            ratingBackgroundDrawable =
                typedArray.getDrawable(R.styleable.SmartRatingBar_backgroundDrawable)
            typedArray.recycle()
        }

        //here to change the icon of rating item
        if (mRatingDrawable == null && ratingBackgroundDrawable == null) {
            mRatingDrawable = context.resources.getDrawable(R.drawable.smart_ratingbar_rating)
            ratingBackgroundDrawable =
                context.resources.getDrawable(R.drawable.smart_ratingbar_background)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if (mOrientation == LinearLayout.HORIZONTAL) {
                    //added line
                    if (isIndicator) {
                        val drawableWidth = ratingBackgroundDrawable!!.bounds.width()
                        val x = event.x
                        val coverWidth = x - paddingLeft
                        val coverStarNum = (coverWidth / (drawableWidth + mGapSize)).toInt()
                        val remainCoverWidth = coverWidth % (drawableWidth + mGapSize)
                        var partStar = remainCoverWidth / drawableWidth
                        partStar = (partStar / mRatingStepSize).toInt() / 10.0f
                        ratingNum = coverStarNum + partStar
                    }

                } else {
                    if (isIndicator) {
                        val drawableHeight = ratingBackgroundDrawable!!.bounds.height()
                        val y = event.y
                        val coverHeight = y - paddingTop
                        val coverStarNum = (coverHeight / (drawableHeight + mGapSize)).toInt()
                        val remainCoverHeight = coverHeight % (drawableHeight + mGapSize)
                        var partStar = remainCoverHeight / drawableHeight
                        partStar = (partStar / mRatingStepSize).toInt() / 10.0f
                        ratingNum = coverStarNum + partStar
                    }

                }
                return true
            }
            else -> return isIndicator
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (mOrientation == LinearLayout.HORIZONTAL) {
                    if (isIndicator) {
                        val drawableWidth = ratingBackgroundDrawable!!.bounds.width()
                        val x = event.x
                        val coverWidth = x - paddingLeft
                        val coverStarNum = (coverWidth / (drawableWidth + mGapSize)).toInt()
                        val remainCoverWidth = coverWidth % (drawableWidth + mGapSize)
                        var partStar = remainCoverWidth / drawableWidth
                        partStar = (partStar / mRatingStepSize).toInt() / 10.0f
                        ratingNum = coverStarNum + partStar
                    }

                } else {
                    if (isIndicator) {
                        val drawableHeight = ratingBackgroundDrawable!!.bounds.height()
                        val y = event.y
                        val coverHeight = y - paddingTop
                        val coverStarNum = (coverHeight / (drawableHeight + mGapSize)).toInt()
                        val remainCoverHeight = coverHeight % (drawableHeight + mGapSize)
                        var partStar = remainCoverHeight / drawableHeight
                        partStar = (partStar / mRatingStepSize).toInt() / 10.0f
                        ratingNum = coverStarNum + partStar
                    }

                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()
        val ss = SavedState(parcelable)
        ss.rating = ratingNum
        ss.starNum = maxStarNum
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        ratingNum = ss.rating
        maxStarNum = ss.starNum
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth: Int
        val measuredHeight: Int
        if (mOrientation == LinearLayout.HORIZONTAL) {
            measuredHeight = measureHeight(heightMeasureSpec)
            if (mRatingDrawable!!.intrinsicHeight > measuredHeight) {
                mRatingDrawable!!.setBounds(0, 0, measuredHeight, measuredHeight)
                ratingBackgroundDrawable!!.setBounds(0, 0, measuredHeight, measuredHeight)
            } else {
                mRatingDrawable!!.setBounds(
                    0,
                    0,
                    mRatingDrawable!!.intrinsicWidth,
                    mRatingDrawable!!.intrinsicHeight
                )
                ratingBackgroundDrawable!!.setBounds(
                    0, 0, mRatingDrawable!!.intrinsicWidth,
                    mRatingDrawable!!.intrinsicHeight
                )
            }
            measuredWidth = measureWidth(widthMeasureSpec)

        } else {
            measuredWidth = measureWidth(widthMeasureSpec)
            if (mRatingDrawable!!.intrinsicWidth > measuredWidth) {
                mRatingDrawable!!.setBounds(0, 0, measuredWidth, measuredWidth)
                ratingBackgroundDrawable!!.setBounds(0, 0, measuredWidth, measuredWidth)
            } else {
                mRatingDrawable!!.setBounds(
                    0,
                    0,
                    mRatingDrawable!!.intrinsicWidth,
                    mRatingDrawable!!.intrinsicHeight
                )
                ratingBackgroundDrawable!!.setBounds(
                    0, 0, mRatingDrawable!!.intrinsicWidth,
                    mRatingDrawable!!.intrinsicHeight
                )
            }
            measuredHeight = measureHeight(heightMeasureSpec)

        }
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val ratingDrawable = mRatingDrawable
        val backgroundDrawable = ratingBackgroundDrawable
        val ratingNum = mRatingNum
        val drawableBounds = ratingDrawable!!.bounds

        val ratingNumInt = Math.floor(ratingNum.toDouble()).toInt()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        var i = 0
        while (i < ratingNumInt) {
            ratingDrawable.draw(canvas)
            translateCanvas(canvas, drawableBounds)
            i++
        }

        val ratingPart = ratingNum - ratingNumInt
        val partWidth: Int
        val partHeight: Int
        if (mOrientation == LinearLayout.HORIZONTAL) {
            partWidth = (drawableBounds.width() * ratingPart).toInt()
            partHeight = drawableBounds.height()
        } else {
            partWidth = drawableBounds.width()
            partHeight = (drawableBounds.height() * ratingPart).toInt()
        }
        // draw rating part
        canvas.save()
        canvas.clipRect(0, 0, partWidth, partHeight)
        ratingDrawable.draw(canvas)
        canvas.restore()

        // draw background part
        canvas.save()
        if (mOrientation == LinearLayout.HORIZONTAL) {
            canvas.clipRect(partWidth, 0, drawableBounds.right, drawableBounds.bottom)
        } else {
            canvas.clipRect(0, partHeight, drawableBounds.right, drawableBounds.bottom)
        }
        backgroundDrawable!!.draw(canvas)
        canvas.restore()

        translateCanvas(canvas, drawableBounds)
        i++ // move to next
        while (i < mMaxStarNum) {
            backgroundDrawable.draw(canvas)
            translateCanvas(canvas, drawableBounds)
            i++
        }
    }

    private fun translateCanvas(canvas: Canvas, rect: Rect) {
        if (mOrientation == LinearLayout.HORIZONTAL) {
            canvas.translate((mGapSize + rect.width()).toFloat(), 0f)
        } else {
            canvas.translate(0f, (mGapSize + rect.height()).toFloat())
        }
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        if (widthMode == View.MeasureSpec.EXACTLY) {
            return widthSize
        }
        var drawableWidth = mRatingDrawable!!.bounds.width()
        if (drawableWidth == 0) {
            drawableWidth = mRatingDrawable!!.intrinsicWidth
        }
        var maxSize = paddingLeft + paddingRight
        if (mOrientation == LinearLayout.HORIZONTAL) {
            maxSize += (mMaxStarNum - 1) * mGapSize + mMaxStarNum * drawableWidth
        } else {
            maxSize += drawableWidth
        }
        return if (widthMode == View.MeasureSpec.AT_MOST) {
            Math.min(maxSize, widthSize)
        } else maxSize
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == View.MeasureSpec.EXACTLY) {
            return heightSize
        }
        var drawableHeight = mRatingDrawable!!.bounds.height()
        if (drawableHeight == 0) {
            drawableHeight = mRatingDrawable!!.intrinsicHeight
        }
        var maxHeight = paddingBottom + paddingTop
        if (mOrientation == LinearLayout.HORIZONTAL) {
            maxHeight += drawableHeight
        } else {
            maxHeight += (mMaxStarNum - 1) * mGapSize + mMaxStarNum * drawableHeight
        }
        return if (heightMode == View.MeasureSpec.AT_MOST) {
            Math.min(heightSize, maxHeight)
        } else maxHeight
    }

    internal class SavedState : View.BaseSavedState {

        var rating: Float = 0.toFloat()

        var starNum: Int = 0

        constructor(source: Parcel) : super(source) {
            rating = source.readFloat()
            starNum = source.readInt()
        }

        constructor(superState: Parcelable?) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(rating)
            out.writeInt(starNum)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    interface OnRatingBarChangeListener {

        fun onRatingChanged(ratingBar: RatingBar, rating: Float)

    }

}