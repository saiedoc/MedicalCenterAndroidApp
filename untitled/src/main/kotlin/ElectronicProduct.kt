
class ElectronicProduct(id: Int , price : Double, quantity : Int , name : String , status : Status ) : Product(id,price,quantity,name) {
    private var p_status = status

    override fun printProductInfo() {
        super.printProductInfo()
        println("Product status: " + this.p_status.name)
    }


}