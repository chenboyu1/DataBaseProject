// 假設 money 是您要更新的變數，您應該在類別內定義它
var money = 0

//override fun onCreate(savedInstanceState: Bundle?) {
    //super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_main)

    // 呼叫方法來顯示彈跳視窗
    //showAlertDialog()
}//

private fun showAlertDialog() {
    // 使用 LayoutInflater 來載入自訂佈局
    val inflater: LayoutInflater = layoutInflater
    val dialogView: View = inflater.inflate(R.layout.dialog_daily_mission, null)

    // 建立 AlertDialog
    val builder = AlertDialog.Builder(this)
        .setView(dialogView)
        .setTitle("每日任務")

    val dialog: AlertDialog = builder.create()
    dialog.show()

    // 調整對話框大小
    dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    // 設定按鈕的動作
    val btnConfirm: Button = dialogView.findViewById(R.id.btn_confirm)
    val btnCancel: Button = dialogView.findViewById(R.id.btn_cancel)

    btnConfirm.setOnClickListener {
        // 按下確認按鈕後的操作
        // 增加25點
        money += 25 // 確保 money 是可變數 (var)，才能進行加法操作
        dialog.dismiss()
    }

    btnCancel.setOnClickListener {
        dialog.dismiss() // 關閉彈跳視窗
    }
}
