package sa.biotic.app.components

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView


/**
 * Created by Abdelrahman Elghamry on 10/08/18.
 */
class ElegantNumberButton12 : RelativeLayout {

    lateinit var _context: Context
    private var attrs: AttributeSet? = null
    private var styleAttr: Int = 0
    private var mListener: OnClickListener? = null
    private var initialNumber: Int = 0
    private var lastNumber: Int = 0
    private var currentNumber: Int = 0
    private var finalNumber: Int = 0
    private var textView: TextView? = null
    private var mOnValueChangeListener: OnValueChangeListener? = null

    lateinit var addBtn: Button
    lateinit var subtractBtn: Button

    var number: String
        get() = currentNumber.toString()
        set(number) {
            lastNumber = currentNumber
            this.currentNumber = Integer.parseInt(number)
            if (this.currentNumber > finalNumber) {
                this.currentNumber = finalNumber
            }
            if (this.currentNumber < initialNumber) {
                this.currentNumber = initialNumber
            }
            textView!!.text = currentNumber.toString()
        }

    constructor(context: Context) : super(context) {
        this._context = context
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this._context = context
        this.attrs = attrs
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this._context = context
        this.attrs = attrs
        this.styleAttr = defStyleAttr
        initView()
    }

    private fun initView() {
        View.inflate(context, sa.biotic.app.R.layout.layout, this)
        val res = resources
        val defaultColor = res.getColor(sa.biotic.app.R.color.colorPrimary)
        val defaultTextColor = res.getColor(sa.biotic.app.R.color.colorText)
        val defaultDrawable = res.getDrawable(sa.biotic.app.R.drawable.background)

        val a = context!!.obtainStyledAttributes(
            attrs, sa.biotic.app.R.styleable.ElegantNumberButton,
            styleAttr, 0
        )

        initialNumber = a.getInt(sa.biotic.app.R.styleable.ElegantNumberButton_initialNumber, 0)
        finalNumber =
            a.getInt(sa.biotic.app.R.styleable.ElegantNumberButton_finalNumber, Integer.MAX_VALUE)
        val textSize = a.getDimension(sa.biotic.app.R.styleable.ElegantNumberButton_textSize, 13f)
        val color =
            a.getColor(sa.biotic.app.R.styleable.ElegantNumberButton_backGroundColor, defaultColor)
        val textColor =
            a.getColor(sa.biotic.app.R.styleable.ElegantNumberButton_textColor, defaultTextColor)
        var drawable =
            a.getDrawable(sa.biotic.app.R.styleable.ElegantNumberButton_backgroundDrawable)

        subtractBtn = findViewById(sa.biotic.app.R.id.subtract_btn)
        addBtn = findViewById(sa.biotic.app.R.id.add_btn)
        textView = findViewById(sa.biotic.app.R.id.number_counter)
        val mLayout = findViewById<LinearLayout>(sa.biotic.app.R.id.layout)

        subtractBtn.setTextColor(textColor)
        addBtn.setTextColor(textColor)
        textView!!.setTextColor(textColor)
        subtractBtn.textSize = textSize
        addBtn.textSize = textSize
        textView!!.textSize = textSize


//        addBtn.setBackgroundColor(resources.getColor(android.R.color.transparent))
//        subtractBtn.setBackgroundColor(resources.getColor(android.R.color.transparent))
//        addBtn.elevation=0F
//        subtractBtn.elevation=0F

        if (drawable == null) {
            drawable = defaultDrawable
        }
        assert(drawable != null)
        drawable!!.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC)
        mLayout.background = drawable

        textView!!.text = initialNumber.toString()

        currentNumber = initialNumber
        lastNumber = initialNumber

        subtractBtn.setOnClickListener {
            val num = Integer.valueOf(textView!!.text.toString())
            setNumber((num - 1).toString(), true)
        }
        addBtn.setOnClickListener {
            val num = Integer.valueOf(textView!!.text.toString())
            setNumber((num + 1).toString(), true)
        }
        a.recycle()
    }

    private fun callListener(view: View) {
        if (mListener != null) {
            mListener!!.onClick(view)
        }

        if (mOnValueChangeListener != null) {
            if (lastNumber != currentNumber) {
                mOnValueChangeListener!!.onValueChange(this, lastNumber, currentNumber)
            }
        }
    }

    fun setNumber(number: String, notifyListener: Boolean) {
        this.number = number
        if (notifyListener) {
            callListener(this)
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.mListener = onClickListener
    }

    fun setOnValueChangeListener(onValueChangeListener: OnValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener
    }

    @FunctionalInterface
    interface OnClickListener {
        fun onClick(view: View)
    }

    interface OnValueChangeListener {
        fun onValueChange(view: ElegantNumberButton12, oldValue: Int, newValue: Int)
    }

    fun setRange(startingNumber: Int?, endingNumber: Int?) {
        this.initialNumber = startingNumber!!
        this.finalNumber = endingNumber!!
    }

    fun updateColors(backgroundColor: Int, textColor: Int) {
        this.textView!!.setBackgroundColor(backgroundColor)
        this.addBtn.setBackgroundColor(backgroundColor)
        this.subtractBtn.setBackgroundColor(backgroundColor)

        this.textView!!.setTextColor(textColor)
        this.addBtn.setTextColor(textColor)
        this.subtractBtn.setTextColor(textColor)
    }

    fun updateTextSize(unit: Int, newSize: Float) {
        this.textView!!.setTextSize(unit, newSize)
        this.addBtn.setTextSize(unit, newSize)
        this.subtractBtn.setTextSize(unit, newSize)
    }
}