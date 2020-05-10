package sa.biotic.app.components

import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class NoSpecialCharacterRuleExcept : BaseRule
{
    override fun validate(text: String): Boolean {
        if (text.isEmpty())
            return false

        return Validator(text).regex("[A-Za-z\u0621-\u064A0-9_.,@ ]+").check()
    }

    override fun getErrorMessage(): String = "Should not contain any special characters"
}