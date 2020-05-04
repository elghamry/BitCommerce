package sa.biotic.app.components

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class MissMatchRule(val target: String) : BaseRule {

    override fun validate(text: String): Boolean {

        if (text.isEmpty())
            return false

        return text == target
    }

    override fun getErrorMessage(): String = "Password mismatch"

}