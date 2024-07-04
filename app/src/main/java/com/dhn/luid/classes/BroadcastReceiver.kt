import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.widget.TextView

class TimerBroadcastReceiver : BroadcastReceiver() {

    private lateinit var timer: CountDownTimer
    private lateinit var timerTextView: TextView

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (action == "TIMER_ACTION") {
            val timerDuration = 1 * 60 * 1000L // 5 minutes in milliseconds

            val sharedPreferences = context.getSharedPreferences("TimerPrefs", Context.MODE_PRIVATE)
            val remainingTime = sharedPreferences.getLong("remaining_time", timerDuration)

            timer = object : CountDownTimer(remainingTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    val minutes = secondsRemaining / 60
                    val seconds = secondsRemaining % 60
                    var timerText = String.format("%2d:%02d", minutes, seconds)
                    timerTextView.text = timerText
                }

                override fun onFinish() {

                }
            }.start()
        }
    }
}
