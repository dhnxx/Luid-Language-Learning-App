package com.example.luid.fragments.mainmenu

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
    private val adapter = ParentPhaseAdapter(phaseList)
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
                            "Phase 1",
                            "Learn Kapampangan as you embark on a captivating journey through Kian's first visit to his ancestral home, where language and culture intertwine.",
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


                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))



                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "Embrace the challenge of learning Kapampangan as Kian engages in heartfelt conversations with his Kapampangan-speaking relatives, forging deeper connections along the way.",
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
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))



                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "Join Kian as he embraces the challenge of learning Kapampangan and engages in meaningful conversations with his Kapampangan-speaking relatives.",
                            R.drawable.wave,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 1
                                    )
                                )

                            },
                            true
                           // sm.ifPassed(db, 1, 2)
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }

                "Level 2" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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

                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }

                "Level 3" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }

                "Level 4" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
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
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }

                else -> {

                }
            }

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
                    showSnackbar("Purchase Successful! \nYour current currency is : ${currency - 60}")
                    dialog.dismiss()
                    lives = sm.displayLives(contextExternal)
                    livesText.text = lives.toString()

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
