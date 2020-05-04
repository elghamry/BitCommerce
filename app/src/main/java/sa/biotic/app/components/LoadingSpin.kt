package sa.biotic.app.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import sa.biotic.app.R

class LoadingSpin : View {

    // Paint
    internal var mBackgroundPaint = Paint()
    internal var mPrimaryCircleColor = Paint()
    internal var mSecondaryCircleColor = Paint()
    internal var mTextPaint = Paint()


    // Metrics
    internal var mTotalWidth: Int? = null
    internal var mTotalHeight: Int? = null
    internal var mLeftGrid: Int? = null
    internal var mTopGrid: Int? = null
    internal var mRightGrid: Int? = null
    internal var mBottomGrid: Int? = null
    internal var mMargin: Int? = null
    internal var mRotateDegrees = 0f

    internal var mPrimaryCicleRadius: Int? = null
    internal var mSecondaryCircleRadius: Int? = null


    internal var backGroundColor: Int? = null
    internal var spinnerPrimaryColor: Int? = null
    internal var spinnerSecondaryColor: Int? = null
    internal var textColor: Int? = null
    internal var textSize: Int? = null


    private var mValueAnimator: ValueAnimator? = null


    // Flags
    internal var mIsVisible = false

    // Speed
    internal var mRotationSpeedInMs: Int? = 1200


    internal var text: String? = "Please wait..."

    // Path
    internal var mCircleHolePath = Path()
    internal var mRectanglePath = Path()


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SmartRatingBar,
            0, 0
        )


        try {
//            text = a.getString(R.styleable.LoadingSpin_text)
//            mRotationSpeedInMs = a.getInteger(R.styleable.LoadingSpin_rotationSpeedInMs, 1300)
//            backGroundColor = a.getColor(R.styleable.LoadingSpin_backgroundColor, -0x77f6bc8d)
//            spinnerPrimaryColor = a.getColor(R.styleable.LoadingSpin_primarySpinnerColor, 0xffffff)
//            spinnerSecondaryColor =
//                a.getColor(R.styleable.LoadingSpin_secondarySpinnerColor, 0xff5723)
//            textColor = a.getColor(R.styleable.LoadingSpin_textColor, 0xffffff)
//            textSize = a.getInteger(R.styleable.LoadingSpin_textSize, 70)
        } finally {
            a.recycle()
        }


        mBackgroundPaint.color = backGroundColor!!
        mBackgroundPaint.style = Paint.Style.FILL
        mBackgroundPaint.isAntiAlias = true

        mPrimaryCircleColor.color = spinnerPrimaryColor!!
        mPrimaryCircleColor.style = Paint.Style.FILL
        mPrimaryCircleColor.isAntiAlias = true

        mSecondaryCircleColor.color = spinnerSecondaryColor!!
        mSecondaryCircleColor.style = Paint.Style.FILL
        mSecondaryCircleColor.isAntiAlias = true

        mTextPaint.color = textColor!!
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.isAntiAlias = true
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.textSize = textSize!!.toFloat()


    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!mIsVisible) return

        init()
        canvas.translate(mLeftGrid!!.toFloat(), mTopGrid!!.toFloat())
        canvas.drawPaint(mBackgroundPaint) // Draw background

        canvas.save()
        drawHoledCircle(canvas)
        canvas.restore()
    }

    private fun init() {

        mMargin = 40
        mTotalHeight = height
        mTotalWidth = width

        mLeftGrid = mMargin
        mTopGrid = mMargin
        mRightGrid = mTotalWidth!! - mMargin!!
        mBottomGrid = mTotalHeight!! - mMargin!!

        val workingWidth = (mRightGrid!! - mLeftGrid!!) / 2
        val workingHeight = (mBottomGrid!! - mTopGrid!!) / 2


        if (mSecondaryCircleRadius == null || mPrimaryCicleRadius == null) {
            mSecondaryCircleRadius = Math.min(workingWidth, workingHeight) / 5
            mPrimaryCicleRadius = Math.min(workingWidth, workingHeight) / 4
        }

    }

    protected fun drawHoledCircle(canvas: Canvas) {

        val workingWidth = (mRightGrid!! - mLeftGrid!!) / 2
        val workingHeight = (mBottomGrid!! - mTopGrid!!) / 2

        canvas.translate(workingWidth.toFloat(), workingHeight.toFloat()) //Move it to center

        canvas.save()
        canvas.rotate(mRotateDegrees)
        mCircleHolePath.reset()
        mCircleHolePath.addCircle(0f, 0f, mSecondaryCircleRadius!!.toFloat(), Path.Direction.CW)
        canvas.clipPath(mCircleHolePath, Region.Op.DIFFERENCE)

        canvas.drawCircle(0f, 0f, mPrimaryCicleRadius!!.toFloat(), mPrimaryCircleColor)

        mRectanglePath.addRect(
            0f, (-mPrimaryCicleRadius!!).toFloat(),
            mPrimaryCicleRadius!!.toFloat(),
            0f, Path.Direction.CCW
        )
        canvas.clipPath(mRectanglePath, Region.Op.INTERSECT)

        canvas.drawCircle(0f, 0f, mPrimaryCicleRadius!!.toFloat(), mSecondaryCircleColor)
        canvas.restore()

//        if (text != null) {
//            val lines = StringUtils.splitStringInLines(text)
//
//            var i = 1
//            for (line in lines) {
//                canvas.drawText(
//                    line,
//                    0f,
//                    mPrimaryCicleRadius!!.toFloat() +
//                            i * ((mTextPaint.descent() - mTextPaint.ascent()) / 2) + 40f + (i * 20).toFloat(),
//                    mTextPaint
//                )
//                i++
//            }
//        }
    }

    /**
     *
     * @param isVisible Determines the the LoadingSpin should be visible
     */
    fun setIsVisible(isVisible: Boolean?) {
        mIsVisible = isVisible!!
        invalidate()

    }

    /**
     * Stops the animation and LoadingSpin automatically hides itself
     */
    fun stopAnimation() {
        mValueAnimator!!.removeAllUpdateListeners()
        setIsVisible(false)
        invalidate()
    }


    /**
     * Starts the animation.
     * This does not mean that the spinner is visible
     */
    fun startAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator!!.removeAllUpdateListeners()
        }
        mValueAnimator = ValueAnimator.ofFloat(0F, 360F)
        mValueAnimator!!.removeAllUpdateListeners()
        mValueAnimator!!.duration = mRotationSpeedInMs!!.toLong()
        mValueAnimator!!.interpolator = LinearInterpolator()
        mValueAnimator!!.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                mRotateDegrees = animation.animatedValue as Float
                invalidate()


                if (mRotateDegrees == 360f) {
                    startAnimation()
                }


            }
        })
        mValueAnimator!!.start()
    }


    /**
     *
     * @param size The size of the circle
     * @param fat How thin your spinner want to be?
     */
    fun changeSpinnerDimension(size: Int?, fat: Int?) {
        mPrimaryCicleRadius = size


        mSecondaryCircleRadius = mPrimaryCicleRadius!! - fat!!
        if (mSecondaryCircleRadius!! < 0) mSecondaryCircleRadius = 0
    }


}