import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sun.jdi.Value
import java.io.File

abstract class Inventory () {

    protected var i_products : MutableList<Product> = ArrayList<Product>()
    protected lateinit var inventory_name : String
    protected var inventory_id : Int = -1


    fun getNumberOfProducts(): Int {return this.i_products.size}
    fun getTotalPrice() : Double {

        var totalPrice : Double = 0.0

        for(product in this.i_products){
            totalPrice += product.getPrice()
        }

        return totalPrice

    }
    fun getProductInfo(name : String): Product? {

        for(product in this.i_products ){
            if (product.getName().equals(name))
                return product
        }
        return null
    }
    fun removeProduct(id : Int) {

        for(product in this.i_products){
            if(product.getID() == id)
                this.i_products.remove(product)
        }

    }
    fun getInventoryName() : String {return this.inventory_name}
    fun setInventoryName(name : String) {this.inventory_name = name}
    fun getInventoryID() : Int {return this.inventory_id}
    fun setInventoryID(id : Int) {this.inventory_id = id}
    fun getProducts() : MutableList<Product> {return this.i_products}


}