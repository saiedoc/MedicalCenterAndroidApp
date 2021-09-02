open abstract class Product(id : Int , price : Double , quantity : Int , name : String) {

    protected var p_id = id
    protected var p_price = price
    protected var p_quantity = quantity
    protected var p_name = name
    fun getID() : Int {return  this.p_id}
    fun getPrice() : Double {return this.p_price}
    fun getName() : String {return this.p_name}
    fun quantityIncrement(addedQuantity : Int) {this.p_quantity += addedQuantity}
    fun quantityDecrement(removedQuantity : Int) {this.p_quantity -= removedQuantity}


}