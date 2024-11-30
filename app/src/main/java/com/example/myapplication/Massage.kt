import com.example.myapplication.GlobalVariable

object Manager {
    var money: Int = GlobalVariable.getmoney()
        set(value) {
            field = value
            onMoneyChanged?.invoke(value) // 通知觀察者金錢變化
        }

    var affectionLevel: Int = GlobalVariable.getAffection()
        set(value) {
            field = value
            onAffectionLevelChanged?.invoke(value) // 通知觀察者好感度變化
            updateLevel(value) // 根據好感度更新等級
        }

    var level: String = "Lv. 1"
        private set // 等級只允許內部修改

    // 回調，用於通知 UI 更新
    var onMoneyChanged: ((Int) -> Unit)? = null
    var onAffectionLevelChanged: ((Int) -> Unit)? = null
    var onLevelChanged: ((String) -> Unit)? = null

    // 更新等級
    private fun updateLevel(affection: Int) {
        val newLevel = when {
            affection < 50 -> "Lv. 1"
            affection < 100 -> "Lv. 2"
            affection < 150 -> "Lv. 3"
            else -> "Lv. 4"
        }
        if (level != newLevel) {
            level = newLevel
            onLevelChanged?.invoke(newLevel) // 通知觀察者等級變化
        }
    }
}
