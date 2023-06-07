package com.example.luid.fragments.mainmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.luid.R
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.adapters.StoryAdapter
import com.example.luid.classes.SMLeitner
import com.example.luid.classes.StoryClass
import com.example.luid.fragments.gamemodes.WordAssociation
import com.example.luid.fragments.gamemodes.*


class StoryFragment : Fragment() {
    private val args: StoryFragmentArgs by navArgs()
    private val selectedLevel: Int by lazy { args.level }
    private val selectedPhase: Int by lazy { args.phase }
    private lateinit var storyList: ArrayList<StoryClass>
    private lateinit var storyAdapter: StoryAdapter

    private lateinit var contextExt: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contextExt = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sm = SMLeitner()
        var lives: Int =
            sm.displayLives(requireContext()) // function that display current lives in the database


        val view = inflater.inflate(R.layout.fragment_story, container, false)

        val intent1 = Intent(context, WordAssociation::class.java)
        val intent2 = Intent(context, SentenceFragment::class.java)
        val intent3 = Intent(context, SentenceConstruction::class.java)
        val start = view.findViewById<Button>(R.id.startGameButton)

        //==================================================================================================
        // RecyclerView for the story
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        storyList = ArrayList()
        addStory()

        storyAdapter = StoryAdapter(storyList)
        recyclerView.adapter = storyAdapter


        //==================================================================================================


        if (lives == 0) {
            start?.isEnabled = false
            //toast message that will appear if the user has no lives
            start?.text = "You have no lives left"

        } else {
            start?.isEnabled = true
            start?.text = "Start"
        }

        start?.setOnClickListener {


            when (selectedPhase) {
                1 -> {

                    intent1.putExtra("level", selectedLevel)
                    startActivity(intent1)

                }

                2 -> {
                    intent2.putExtra("level", selectedLevel)
                    startActivity(intent2)
                }

                3 -> {
                    intent3.putExtra("level", selectedLevel)
                    startActivity(intent3)
                }
            }
        }





        return view

    }

    private fun addStory() {

        storyList.add(
            StoryClass(
                "Kian",
                true,
                "Magandang umaga po, lola. Mano po.",
                "Mayap a abak pu, Apo. Siklod ku pu.",
                R.drawable.kian
            )
        )
        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Magandang umaga, apo.",
                "Mayap a abak, apú.",
                R.drawable.lola
            )
        )
        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Kumusta ka? Ngayon ka lang nadalaw dito.",
                "Komusta ka? Ngeni kamu mekaratang keni.",
                R.drawable.lola
            )
        )

        storyList.add(
            StoryClass(
                "Kian",
                true,
                "Okay naman po ako,  salamat.",
                "Mayap naku man pu. Salâmat.",
                R.drawable.kian
            )
        )

        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Halika at ipapakilala kita kina tito Noel, tita Marites, at sa pinsan mong si Elena.",
                "Mekeni, pakilala daka keng Bapa mung Noel, Darang Marites, ampong kng pisan mung Elena.",
                R.drawable.lola
            )
        )

        storyList.add(
            StoryClass(
                "Kian",
                true,
                "Sige po, lola.",
                "Opu, Apu",
                R.drawable.kian
            )
        )

        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Noel, pamangkin mo oh. Ngayon lang nakapunta dito.",
                "Noel, paunakan mu, o. Ngeni yamu miras keti.",
                R.drawable.lola
            )
        )

        storyList.add(
            StoryClass(
                "Noel",
                false,
                "Mabuti at napadalaw ka, iho. Tawagin ko lang si Elena. Elena!",
                "Mapnamu megbisita ka, itung. Awsan ke mu y Elena.",
                R.drawable.noel
            )
        )

        storyList.add(
            StoryClass(
                "Elena",
                false,
                "Ikaw pala ang pinsan kong taga-maynila, hello po.",
                "Ika pala ing pisan kung ibat Menila. Hello pu.",
                R.drawable.elena
            )
        )

        storyList.add(
            StoryClass(
                "Noel",
                false,
                "Mamaya ka na namin ipapakilala sa tiya mo.",
                "Pota pakilala da ka kang dara mu.",
                R.drawable.noel
            )
        )

        storyList.add(
            StoryClass(
                "Kian",
                true,
                "Nasaan po siya?",
                "",
                R.drawable.kian
            )
        )

        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Nasa palengke pa ang tita Marites mo, pinagsasabay ang pamimili sa kwentuhan.",
                "Atyu pa palengki y dara mung Marites, piyayagnan na ing pamipagkwentu keng pamanyali.",
                R.drawable.lola
            )
        )

        storyList.add(
            StoryClass(
                "Noel",
                false,
                "Magpahinga ka muna. Malayo ang byahe mo.",
                "Paynawa ka pa, marayu ka penibatán.",
                R.drawable.noel
            )
        )

        storyList.add(
            StoryClass(
                "Lola",
                false,
                "Elena, hatid mo muna pinsan mo sa magiging kwarto niya para makapag pahinga.",
                "Elena, atad me pamu ing pisan mu kng kwartu na, ba yang mipaynawa.",
                R.drawable.lola
            )
        )

        storyList.add(
            StoryClass(
                "Elena",
                false,
                "Sige po, lola.",
                "Opu, Apu.",
                R.drawable.elena
            )
        )



    }
}
