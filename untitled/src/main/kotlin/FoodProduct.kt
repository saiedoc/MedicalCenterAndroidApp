class FoodProduct(id: Int, price: Double, quantity: Int, name: String, startDate: String, endDate: String)
    : Product(id, price, quantity,name) {

    private var p_startDate = startDate
    private var p_endDate = endDate

}