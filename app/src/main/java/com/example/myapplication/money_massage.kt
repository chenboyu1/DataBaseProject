import com.example.myapplication.GlobalVariable

object MoneyManager {
    var money: Int = GlobalVariable.getmoney()
    set(value) {
        field = value
        onMoneyChanged?.invoke(value) // 通知觀察者
    }

    var onMoneyChanged: ((Int) -> Unit)? = null // 觀察者
}
