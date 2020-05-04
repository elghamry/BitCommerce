package sa.biotic.app.components

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import sa.biotic.app.R


class ProgressBottle : RelativeLayout {
    lateinit var typeface: Typeface
    private var mContext: Context? = null
    private var attrs: AttributeSet? = null
    private var styleAttr: Int = 0
    private var imageView: ImageView? = null
    private var imageFile: Drawable? = null
    private var view: View? = null
    private var progBg: View? = null
    private var textMsg: TextView? = null
    private var customMsg: String? = null
    private var textColor: Int = 0
    private var enlarge: Int = 0
    private var textSize: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        this.attrs = attrs
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context
        this.attrs = attrs
        this.styleAttr = defStyleAttr
        initView()
    }

    private fun initView() {
        this.view = this
        typeface = ResourcesCompat.getFont(this.context, R.font.grenale_conbla)!!

        View.inflate(mContext, sa.biotic.app.R.layout.progress_bar_layout, this)

        val arr = mContext!!.obtainStyledAttributes(
            attrs,
            sa.biotic.app.R.styleable.ProgBar,
            styleAttr,
            0
        )

        imageFile = arr.getDrawable(sa.biotic.app.R.styleable.ProgBar_barType)
        customMsg = arr.getString(sa.biotic.app.R.styleable.ProgBar_text)
        textColor = arr.getColor(sa.biotic.app.R.styleable.ProgBar_androidtextColor, Color.BLACK)
        textSize = arr.getDimension(sa.biotic.app.R.styleable.ProgBar_textSize, 16f)
        enlarge = arr.getInt(sa.biotic.app.R.styleable.ProgBar_enlarge, 2)
        imageView = findViewById<ImageView>(sa.biotic.app.R.id.progressImg)
        progBg = findViewById(sa.biotic.app.R.id.progBg)
        textMsg = findViewById(R.id.textMsg)



        if (imageFile != null) {
            setProgressVector(imageFile!!)
        }

        if (customMsg != null) {
            setTextMsg(customMsg!!)
        }

        setTextColor(textColor)
        setTextSize(textSize)
        enlarge(enlarge)

        arr.recycle()
    }

    fun setScaleType(scaleType: ImageView.ScaleType) {
        imageView!!.scaleType = scaleType
    }

    fun setProgressVector(imageFile: Drawable) {


        var imageViewTarget = DrawableImageViewTarget(imageView)
        Glide
            .with(mContext!!)

            .load(R.drawable.search_loader)

            .into(imageViewTarget)
    }

    fun enlarge(enlarge: Int) {
        if (enlarge >= 1 && enlarge <= 10)
            imageView!!.layoutParams.height = enlarge * 100
    }

    fun setTextMsg(message: String) {
        textMsg?.typeface = typeface
        textMsg!!.text = message
    }

    fun setTextColor(color: Int) {
        textMsg!!.setTextColor(color)
    }

    fun setTextSize(size: Float) {
        textMsg!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }


}