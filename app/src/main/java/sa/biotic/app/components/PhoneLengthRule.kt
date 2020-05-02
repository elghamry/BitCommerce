package sa.biotic.app.components

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class PhoneLengthRule : BaseRule {
    // add your validation logic in this method
    override fun validate(text: String): Boolean {
        // Apply your validation rule logic here
        return text.length == 9
    }

    // Add your invalid check message here
    override fun getErrorMessage(): String {
        return "Phone must be 9 numbers"
    }
}