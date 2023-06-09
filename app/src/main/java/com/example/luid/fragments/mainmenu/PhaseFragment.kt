package com.example.luid.fragments.mainmenu

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.LevelSwitchStateViewModel
import com.example.luid.adapters.*
import com.example.luid.classes.ChildPhase
import com.example.luid.classes.ParentPhase
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect
import com.google.android.material.snackbar.Snackbar
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.luid.database.DBConnect.Companion.user_records_tb

class PhaseFragment : Fragment() {


    //view model to store switch state
    private lateinit var levelSwitchStateViewModel: LevelSwitchStateViewModel

    // for Phase Selection
    private lateinit var contextExternal: Context
    private lateinit var recyclerView: RecyclerView
    private val phaseList = ArrayList<ParentPhase>()
    private lateinit var button: Button
    private lateinit var livesText: TextView
    private lateinit var timerText: TextView

    private lateinit var builder: AlertDialog.Builder
    private lateinit var timer: CountDownTimer
    private lateinit var currencyText: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phase, container, false)
        contextExternal = requireContext()

        ////////////////////////PHASE SELECTION/////////////////////////////

        recyclerView = view.findViewById(R.id.phaseRecyclerView)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        ///////////////////////////////////////////////////////////////////


        // Retrieve ViewModel
        levelSwitchStateViewModel =
            ViewModelProvider(requireActivity())[LevelSwitchStateViewModel::class.java]

        // Populating data for each switch/phase
        val childPhase0 = ArrayList<ChildPhase>()
        val childPhase1 = ArrayList<ChildPhase>()
        val childPhase2 = ArrayList<ChildPhase>()
        val sm = SMLeitner()
        val db = DBConnect(contextExternal).readableDatabase
        var lives =
            sm.displayLives(contextExternal) // function that display current lives in the database
        var currency = sm.getCurrency(contextExternal)
        val maxLives = 5
        val timerDuration = 1 * 60 * 1000L // 5 minutes in milliseconds

        livesText = view.findViewById(R.id.textLives)
        livesText.text = lives.toString()
        currencyText = view.findViewById(R.id.textCurrency)
        currencyText.text = "💵$currency"
        button = view.findViewById(R.id.button)

        // COUNTDOWN TIMER

        timerText = view.findViewById(R.id.timerText)

        startTimer(timerText, maxLives, timerDuration, livesText, button)



        button.setOnClickListener {
            buyLives(contextExternal, livesText)
        }

        ///////////////////////////////////////////////////////////////////
        phaseList.clear()

        // Observe switch state LiveData
        levelSwitchStateViewModel.getSwitchState().observe(viewLifecycleOwner) { switchState ->
            when (switchState) {
                "Level 1" -> {


                    childPhase0.add(
                        ChildPhase(
                            "Pagdating",
                            "The protagonist finally arrived at the province and was eventually welcomed in by their grandmother and house.",
                            R.drawable.bag,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 1
                                    )
                                )


                            },

                            true
                        )
                    )


                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0,1,1))



                    childPhase1.add(
                        ChildPhase(
                            "Pagkilala",
                            "The phase introduces the relatives to the protagonist and their corresponding honorifics in Kapampangan.",
                            R.drawable.handshake,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 1
                                    )
                                )
                            },

                            true
                           // sm.ifPassed(db, 1, 1)

                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1,1,2))



                    childPhase2.add(
                        ChildPhase(
                            "Pakikipagkapwa",
                            "The protagonist got out of the house and was greeted by a neighbor, new friend.",
                            R.drawable.wave,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 1
                                    )
                                )

                            },

//                            sm.ifPassed(db, 1, 2)
                        true

                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2,1,3))


                }

                "Level 2" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Pagbibili sa umaga",
                            "The phase focuses on a common encounter where the protagonist buys something from the local sari-sari store.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 2
                                    )
                                )
                            },
                          true
                        )
                    )

                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0,2,1))

                    childPhase1.add(
                        ChildPhase(
                            "Pagpasyal",
                            "This is the time where the protagonist along with their cousin and new found friend decides where to go where surface-level directions are introduced.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 2
                                    )
                                )
                            },
                            sm.ifPassed(db, 2, 1)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1,2,2))


                    childPhase2.add(
                        ChildPhase(
                            "Peryahan",
                            "The trio proceeded to the amusement park and explored with the rides and other sources of entertainment there.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 2
                                    )
                                )
                            },
                            sm.ifPassed(db, 2, 2)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2,2,3))


                }

                "Level 3" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Pagdayo",
                            "The family prepared and introduced local celebrations in the area during transport. Eventually, the protagonist learned that they are on their way to the Sinukwan Festival ",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 3
                                    )
                                )
                            },
                         true
                        )
                    )
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0,3,1))

                    childPhase1.add(
                        ChildPhase(
                            "Pagdiriwang",
                            "The trio finally set foot on the area of the Festival and had a great time.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 3
                                    )
                                )
                            },
                            sm.ifPassed(db, 3, 1)

                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1,3,2))


                    childPhase2.add(
                        ChildPhase(
                            "Pagliliwaliw",
                            "After visiting the Sinukwan Festival, the family decided to go for a detour since there's still a lot of time.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 3
                                    )
                                )
                            },
                            sm.ifPassed(db, 3, 2)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2,3,3))


                }

                "Level 4" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Pag-iimpake",
                            "The protagonist chatted with their relatives for a bit while preparing their personal stuff before traveling back to the city.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 4
                                    )
                                )
                            },
                           true
                        )
                    )
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0,4,1))

                    childPhase1.add(
                        ChildPhase(
                            "Pasalubong",
                            "The protagonist asked their relatives about what can they bring as gifts going back.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 4
                                    )
                                )
                            },
                            sm.ifPassed(db, 4, 1)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1,4,2))


                    childPhase2.add(
                        ChildPhase(
                            "Pagabalik sa kabihasnan",
                            "The protagonist expressed their gratitude to their relatives and bid farewell until the next visit.",
                            R.drawable.about,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 4
                                    )
                                )
                            },
                            sm.ifPassed(db, 4, 2)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2,4,3))


                }

                else -> {

                }
            }
            val adapter = ParentPhaseAdapter(phaseList, db)
            recyclerView.adapter = adapter
        }


        return view
    }

    private fun buyLives(context: Context, livesText: TextView) {

        val sm = SMLeitner()
        var currency = sm.getCurrency(context)
        var lives = sm.displayLives(contextExternal)


        builder = AlertDialog.Builder(context)



        if (currency >= 60) {
            builder.setTitle("Purchase Lives")
                .setMessage("1 life = 60 currency.\nDo you want to buy lives?")
                .setPositiveButton("Yes") { dialog, _ ->
                    sm.lifeGain(context)
                    sm.updAchPH(context)

                    var db = DBConnect(contextExternal).writableDatabase
                    var cursor = db.rawQuery("SELECT * FROM $user_records_tb", null)

                    var colID = cursor.getColumnIndex("_id")
                    var cv = ContentValues()

                    cursor.moveToLast()
                    var id = cursor.getInt(colID)
                    cv.put("currency", "${currency - 60}")
                    db.update("$user_records_tb", cv, "_id = $id", null)

                    showSnackbar("Purchase Successful! \nYour current currency is : ${currency - 60}")
                    dialog.dismiss()
                    lives = sm.displayLives(contextExternal)
                    currency = sm.getCurrency(contextExternal)
                    livesText.text = lives.toString()
                    currencyText.text = "💵$currency"

                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        } else {
            showSnackbar("You do not have enough currency 😢")
        }

        if (lives >= 5) {
            timerText.isVisible = false
            button.isVisible = false

        }

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(button, message, Snackbar.LENGTH_SHORT).show()

    }


    private fun startTimer(
        timerTxt: TextView,
        maxLives: Int,
        timerDuration: Long,
        livesText: TextView,
        button: Button
    ) {
        val sm = SMLeitner()
        var lives = sm.displayLives(contextExternal)
        livesText.text = lives.toString()


        if (lives >= maxLives) {
            timerText.isVisible = false
            button.isVisible = false
            return
        } else {


            timer = object : CountDownTimer(timerDuration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    val minutes = secondsRemaining / 60
                    val seconds = secondsRemaining % 60
                    val timerText = String.format("%2d:%02d", minutes, seconds)
                    timerTxt.text = timerText
                    lives = sm.displayLives(contextExternal)
                    livesText.text = lives.toString()

                }

                override fun onFinish() {
                    sm.lifeGain(contextExternal) // this function increases the lives by 1


                    if (lives <= maxLives) {
                        startTimer(timerText, maxLives, timerDuration, livesText, button)

                    } else {
                        timer.cancel()
                        timerText.isVisible = false
                        button.isVisible = false
                    }
                }
            }.start()
        }
    }


}
