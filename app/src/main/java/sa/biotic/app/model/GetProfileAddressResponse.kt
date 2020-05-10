package sa.biotic.app.model

data class GetProfileAddressResponse(
    var AddressLine1: String,
    val AddressLine2: String,
    val City: String,
    val Country: String,
    val PostalCode: String,
     val longitude: String,
     val latitude:  String

)