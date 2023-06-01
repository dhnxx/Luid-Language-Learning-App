package com.example.luid.fragments.mainmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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


class PhaseFragment : Fragment() {


    //view model to store switch state
    private lateinit var levelSwitchStateViewModel: LevelSwitchStateViewModel

    // for Phase Selection
    private lateinit var contextExternal: Context
    private lateinit var recyclerView: RecyclerView
    private val phaseList = ArrayList<ParentPhase>()
    private lateinit var button : Button
    private val adapter = ParentPhaseAdapter(phaseList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phase, container, false)


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

        button = view.findViewById(R.id.button)
        button.setOnClickListener{
            val sm = SMLeitner()
            sm.buyLives(contextExternal)
            sm.updAchPH(contextExternal)
        }


        phaseList.clear()

        // Observe switch state LiveData
        levelSwitchStateViewModel.getSwitchState().observe(viewLifecycleOwner) { switchState ->
            when (switchState) {
                "Level 1" -> {




                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "Integer eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 1
                                    )
                                )


                            }
                        )
                    )


                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))



                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 1
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))



                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 1
                                    )
                                )

                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }
                "Level 2" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1.1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 2
                                    )
                                )
                            }
                        )
                    )

                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 2
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 2
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }
                "Level 3" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 3
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 3
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 3
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 3: Sentence Construction", childPhase2))


                }
                "Level 4" -> {

                    childPhase0.add(
                        ChildPhase(
                            "Phase 1",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 1,
                                        level = 4
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 1: Word Association", childPhase0))

                    childPhase1.add(
                        ChildPhase(
                            "Phase 2",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 2,
                                        level = 4
                                    )
                                )
                            }
                        )
                    )
                    phaseList.add(ParentPhase("Phase 2: Sentence Fragments", childPhase1))


                    childPhase2.add(
                        ChildPhase(
                            "Phase 3",
                            "nteger eu ante nec augue maximus blandit. Suspendisse sed tristique libero, sit amet blandit tellus. Quisque sagittis risus metus",
                            R.drawable.settings,
                            View.OnClickListener {
                                findNavController().navigate(
                                    TabPhaseReviewDirections.actionTabPhaseReviewToStoryFragment(
                                        phase = 3,
                                        level = 4
                                    )
                                )
                            }
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


}
