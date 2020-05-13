package sa.biotic.app.components

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class FillRequiredRule : BaseRule {
    override fun validate(text: String): Boolean = !text.isEmpty()

    override fun getErrorMessage(): String = "Please fill the required field !"
}