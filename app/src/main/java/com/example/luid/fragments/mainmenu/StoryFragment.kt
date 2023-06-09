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
import java.net.SocketTimeoutException


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

        if(selectedLevel == 1 && selectedPhase == 1){

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

        if(selectedLevel == 1 && selectedPhase == 2){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Puro Kapampangan ang sinasalita nila, naninibago ako. Pero sisikapin kong matuto.",
                    "Pane Kapampangan ing pamanyalita ra, manibayu ku. Pero panikwanan kung mabiasa.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Ay sa wakas, nakauwi rin si Marites.",
                    "Ay wa. Nandin naka pa panayan. Mekeni, \'tu.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Napasarap ang kwentuhan. Nakarating na ang pamangkin natin?",
                    "Mipanyaman ing satsatan. Míras ne ing paunakan tamo?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Aba’y oo. Kanina ka pa hinihintay niyan. Tara dito, pamangkin.",
                    "Ay wa. Nandin naka pa panayan. Mekeni, \'tu.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, Tito.",
                    "Opu, bapa.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Ay ang gandang/gwapong bata! Ilang taon ka na?",
                    "Ay, kasanting a yanak! Pilan nakang banua?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Salamat po, tita. 16 na po ako.",
                    "Salamat pu, Dara. Labing-anam a banua naku pu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Marites, akin na ang mga binili mong rekado at magluluto na tayo.",
                    "Marites, anto reng seli mu, ba tanang maglutu.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Ito po ang isda at gulay, \'Nay.",
                    "Oyni pung asan ampo gulé, Imá.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Salamat, anak. Oh siya, magpahinga muna kayo habang niluluto ang tanghalian.",
                    "Salamat, anak. Dâle, paynawa ko pamu kabang lulutu ing pagtuan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Tulungan ko po kayo, lola.",
                    "Saupan da ko pu, Apu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Huwag na, hindi ka pumunta dito para sa gawaing bahay. Mag libot-libot ka muna rito.",
                    "Ali na, e da ka pepuntan keni ba kang mag-obrang bale. Lumibut naka pamu.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Salamat po, lola. Labas lang po ako.",
                    "Salamat pu, Apo. Lumwal ku pamu pu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Mag-ingat ka.",
                    "Mimingat ka.",
                    R.drawable.home
                )
            )
        }

        if(selectedLevel == 1 && selectedPhase == 3){

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Magandang gabi, bago ka lang ba rito?",
                    "Mayap a bengi, bayu kamu keni?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Oo, ano\'ng pangalan mo?",
                    "Wa. Nanung lagyu mu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Ikaw, anong pangalan mo?",
                    "Ika, nanung lagyu mu?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ang ganda ng pangalan mo. Ako si Kian.",
                    "Kasanting ning lagyu mo. Aku y Kian.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "",
                    "Masanting ka.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "",
                    "Masanting ka?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Ang ibig sabihin noon, magandang lalaki ka. Di ka nagka-Kapampangan?",
                    "Buri nang sabyan nita, masanting kang lalaki. Eka manga-Pampangan?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ah. Hindi, eh. Ngayon lang nakarinig.",
                    "Ali, eh. Ngeni kumu daramdaman.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Tara, sama ka sa akin. Tuturuan kita ng kaunti.",
                    "Mekeni, tuki ka kanaku. Turu dakang bagya.",
                    R.drawable.home
                )
            )
        }

        if(selectedLevel == 2 && selectedPhase == 1){
            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Pamangkin, gising na magtatanghali na!",
                    "Talakad na \'tu, matas ne ing aldo!",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Pababa na po, tito. Hindi po ba alas 7 ng umaga pa lang?",
                    "Pakuldas naku pu, bapa. E pu wari alas siyeti pamu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Hindi ka ba sanay gumising ng maaga?",
                    "E ka sanéng migigising maranon?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Nasanay po sa oras ng Maynila.",
                    "Mesane ku pu kng oras Menila.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ayos lang yan. Pinapabili pala tayo ni lola ng rekado para sisig at adobong kamaro.",
                    "E ninanu. Pasalwan naka tamung rekadus y âpu para kng sisig ampong adobung kamarú.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Saan nga po ba uli ang tindahan?",
                    "Nokarin ne pin pu ing pisasalwan?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Kina Tiya Lourdes, sa bungad lang ng palengke. Alam ko na yun.",
                    "Kari Dang Lourdes (Lordis), keng bungad ning palengki. Balu ku ne yta.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Siyanga pala, gumawa na ko ng burong hipon para sa iyo, ilang araw ko nang nasimulang iburo. Masarap na sawsawan ng pritong hito at lagang talong at ampalaya! O, ingat sa mga sasakyan, ha.",
                    "Wa pala, pilan nakung aldong megburung paro, para kng tagilo. Manyaman yang sawsawan kng pritung itú, ligat balasenas ampo kapalya! O, mimingat ko kareng sakén, ne.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ay, salamat po,Tita! Opo, mag-iingat kami.",
                    "Ay, dakal pung salamat, Dar! Opu, mingat ke pu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Pabili po.",
                    "Pasalwan na po.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Da\'ng Lourdes",
                    false,
                    "Ngayon lang kita nakita, ikaw ba yung pamangkin ni Marites?",
                    "Ngeni daka pa ikit. Ika\'ng paunakan ng Marites?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, paano nyo po nalaman?",
                    "Opu, makananu yu pu abalu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Da\'ng Lourdes",
                    false,
                    "Nabanggit sa akin ni Marites na dumating ka. Ano pala\'ng bibilhin mo?",
                    "Asabi na kakung Marites a dintang ka. Nanung saliwan mu?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Isa\'t kalahating kilong pisngi at tenga daw po ng baboy, atay ng manok, sibuyas at kalamansi. Tapos lahat ng rekado po ng adobong kamaro. At talong at ampalaya. Magkano po lahat?",
                    "Metung ampo kapitnang kilung pisngi ampo balugbug babi pu kanu, aténg manuk, sibuyas ampo kalamansi. Ampo den ngan pung rekadus ning adobung kamarú. Ampo balasenas ampo kapalya. Magkanu pu kegana-gana?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Da\'ng Lourdes",
                    false,
                    "Ito Iho/Iha, nakalista lahat diyan. ₱750 lahat yan.",
                    "Oyni \'tung, \'nang. Nilista ko ngan ken. Pitung dalan ampo singkwenta pesus (₱750) mu ngan yan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Salamat po!",
                    "Salamat pu!",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Pabili po..",
                    "Pasalwan na po..",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Ano ang iyo?",
                    "Nanu ing keka?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Isang kahon po mg sansrival. (Nag-abot ng ₱500)",
                    "Metung pung kahun ning sansribal. (Nag-abot ng ₱500)",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Ito ang sukli mo, ₱125..",
                    "Oyni ing sukli mo, sientu beintisingku. (₱125)",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    true,
                    "Salamat po!",
                    "Salamat pu!",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Salamat po!",
                    "Salamat pu!",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Tara, uwi na tayo at kumain ng tanghalian.",
                    "Muli tana, magtu tana.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Gagala ba tayo pagkatapos?",
                    "Lumibut tamu kaybat?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Sige ba, isama mo yung kaibigan mo. Yiee.",
                    "Wa, ba, tuki me ing káluguran mu. Yiee.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Luh, bakit nang-aasar? Pero sige.",
                    "Aro, ot mamuysit ka ne? Pero sige.",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 2 && selectedPhase == 2){

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Grabi ang sarap ng mga luto ni lola!",
                    "Kanyámang-kanyaman da reng lutu nang Apo!",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sa totoo lang. Ano nga ba ulit yung mga iyon?",
                    "Sa totoo lang. Ano nga ba ulit yung mga iyon?",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Sisig, adobong kamaro, at burong hipon. Siksik sa sahog, no?",
                    "Sisig, adobung kamaru, ampong tagiló. Sapak lang rekadus, ne?",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ayun nga, paborito ko na ang sisig! Ang sarap kasi ng pagkakahalo ng karne sa linamnam ng atay at asim ng kalamansi.",
                    "Wa, paburitu ke ing sisig! Kanyaman ning pangayumu ampong kaslam ning pinya kng karni.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ay teka, ayun na ata yung kaibigan mo oh.",
                    "Kapamu, oyta na yata ing káluguran mu, o.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Hala, oo nga. Clara!",
                    "Ay wa. Clara!",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Kumusta? Saan tayo pupunta?",
                    "Komusta? No\' tamu mumunta?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Gusto niyo ba pumunta tayo ng peryahan?",
                    "Bisa kong munta kng peryahan?",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sige ba, ituro mo ang direksyon. Malayo ba iyon?",
                    "Wa ba, turu mu kami karin. Marayo?",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Malapit lang, tatlong kanto mula rito.",
                    "Malapit mu, atlu mung kantu ibat keni.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Kaliwa ba o kanan ng Palengke? Tapos papasók, no?",
                    "Kaili o wanan ning palengki? Kaybat palub, ne?)",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Mamaya na natin alamin yan pag nandoon na.",
                    "Abalu tamu patche tyu tana karin.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Halika na! Baka abutin tayo ng dilim.",
                    "Tara na! Pota mabengi ta pa.",
                    R.drawable.home
                )
            )

        }

        if(selectedLevel == 2 && selectedPhase == 3){

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ang liwanag at ganda ng mga ilaw!",
                    "Kasala ampo kasanting dareng suló!",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Ang nabago. Ang taas na ng rollercoaster oh.",
                    "Ing bayu.. kátás ning rollercoaster, o!",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ilibot niyo naman ako, saan ba muna tayo?",
                    "Ilibut yu naku man - nokarin tamu mumuna?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Lagi ka sa tabi amin, puno ng tao ang perya.",
                    "Pane ka kng lele mi, sapak tau ing perya.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Bihira ka lang mapunta rito, baka di ka na makauwi.",
                    "Malagad kamu keni, pota e naka miuli.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Grabi naman kayo sa akin. Tara na nga.",
                    "Sinobra nako man kaku. Tara na pin.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "may Gágaló (puppet show), oh! Doon na muna tayo.",
                    "Ating puppet show, o. Karin ta pa.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Sige, tas magrides na tayo. Sulitin natin!",
                    "A wa, kaybat sake tamung rides. Sulitan tamu!",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Napagod ako doon.",
                    "Kapagal ko.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Grabi, pinawisan ako dun. Napaos din sa kasisigaw!",
                    "Ayy, mepawas ku. Mepáyo kung mangulisak!",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Clara",
                    false,
                    "Yari, ginabi na tayo ng uwi. Sinulit at nagsaya tayo masyado.",
                    "Aro, mebengi tana. Mipasoso tamu.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Baka nag–aalala na sa atin sila lola. Umuwi na tayo.",
                    "Pota migaganaka no di Apu. Muli tana.",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 3 && selectedPhase == 1){

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Elena, anak. Pamangkin, gising na may pupuntahan pa tayo.",
                    "Elena, anak. \'Tung, talakad na kayu, atin tang puntalan.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Pababa na ‘tay.",
                    "Kuldas naku, Tang.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Nag-alala pa kami kahapon ginabi na kayo ng uwi.",
                    "Miganaka kami napun, mebengi kayung minuli.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Pasensya na po.",
                    "Pasensya na pu, Dara.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Walang problema, basta sa susunod magpaalam, ah?",
                    "Alang problema. Keng tutuki ma-mun kayu, ne?",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Pangako po hindi na mauulit.",
                    "E na pu mulit, Tang, pangaku.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Oh, siya. Maligo at mag-umagahan na kayo at may pupuntahan tayong kapistahan.",
                    "Dale. Mandilu na kayu, manalmusal, kaybat munta tamu king pyesta.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Kapistahan po?",
                    "Pyesta pu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Ano ba ang alam mong mga pistahan dito sa Pampanga?",
                    "Nanu la deng balu mung pyesta keti Pampanga?",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ako ‘tay, yung Giant Lantern Festival po.",
                    "Aku \'Tang, itang Giant Lantern Festival pu.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Dinarayo iyan tuwing Disyembre, ano pa?",
                    "Daraywan yan balang Disyembri. Nanu pa?",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Hot Air Balloon Festival po?",
                    "Hot Air Balloon Festival pu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Dinala ako ng Tito mo dyan bago kami ikasal. Pero hindi iyan iyon.",
                    "Dela naku ning bapa mu ken bayu ke mikasal. Pero aliwa pa ita.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ano po iyon?",
                    "Sanu pu ita?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Ilan lang ang mga sinabi niyo sa mga kilalang kapistahan dito.",
                    "Pilan lamu deng sinabi yu kareng kilalang piyesta keni.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Lingid sa kaalaman ng iba, marami pang ibang kapistahan sa Pampanga.",
                    "Dakal pang aliwang pyestang Pampanga a ali da balu deng keraklan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Isa na diyan ang Duman Festival kung saan ang malagkit na kanin, sarswela, at pagbabalik-tanaw sa simpleng pamumuhay noon ang tampok.",
                    "Metung ne ken ing Duman Festival nokarin papakit da ing duman (malagkit a berding nasi), sarswela, ampo deng simpling pamibie-bie.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Mayroon ding Pyestang Tugak (palaka) kung saan binibigyang pansin ang ‘di pangkaraniwang mga ulam at lutong palaka. Madami kasing palayan lalo na dati sa Pampanga.",
                    "Atin mu namang Piyestang Tugak a papakit do reng miyaliwang lutu kareng tugak, uli dakal tugak kareng masle ning Pampanga.)",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "At mayroon ding Ibun-ebun Festival na ginaganap sa Candaba na nagbibigay halaga sa mahigit 14,000 na klase ng ibon sa lugar.",
                    "Atin mu ring Ayup/Ebun Festival kng Candaba a pagdiwang do reng maygit labing-apat a pulung klasi dareng ayup karin.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ang dami po palang kakaibang kaganapan dito. Sana makapunta ako sa iba sa susunod. ",
                    "Karakal pu palang \"kakaibang\" migaganap keni ne. Sana mipunta ku karen keng tutuki.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Sa susunod. Sa ngayon, darayo tayo sa Sinukwan Festival kung saan makikilala niyo si Aring Sinukwan, ang diyos ng mga sinaunang Kapampangan.",
                    "Wa, keng tutuki. Ngeni, munta tamu king Sinukwan Festival nu\' ya karin y Apung Sinukwan, ing guinu dareng minunang Kapampangan.",
                    R.drawable.home
                )
            )

        }

        if(selectedLevel == 3 && selectedPhase == 2){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ang dami palang mga tao, ang nagdiriwang ng piyestang ito.",
                    "Karakal pu palang manamang migaganap keni ne. Sana mipunta ku karen keng tutuki.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Tignan mo doon, oh. Ang garbo at ang kulay ng mga suot ng mga sumasayaw!",
                    "Lawen meyta o, kagarbu ampo kakule da susulud deng teterak!",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Naririnig niyo ba ang kanta?",
                    "Daramdaman ye ing kanta?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Atin ku pong singsing ang naririnig ninyo.",
                    "Yapin ing Atin Ku Pung Singsing ita.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Tungkol saan po iyon, tito?",
                    "Nanu ya pu sasabyan ita, Bapa?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Sa singsing.",
                    "Kng singsing",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Singsing?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Ang singsing na iyon ay simbolo ng Aláya. Ang Aláya ay ang espiritu, kaluluwa, at kaibuturan ng mga Kapampangan.",
                    "Simbulu ne ning Aláya ing singsing. Yapin ing espiritu, kaladwa, ampo kalubluban dareng Kapampangan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Oh, siya. Lumibot muna kayo. Bumalik kayo rito mamaya dahil magaganap na ang mga paligsahan.",
                    "Dale, lumibut na ko pa. Mibalik kayu keni pota uli gawan do reng paligsa-an.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Salamat, ‘tay! Babalik po kami kaagad.",
                    "Salamat, Tang! Magbalik keng agad.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Elena, kita mo yung nakapaskil? May Patimpalak at Rampahan na magaganap din mamayang gabi oh.",
                    "Elena, ikit meytang makapaskil? Ating Paligsa-an ampo Rampa-an a malyari potang bengi.",
                    R.drawable.kian
                )
            )



            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Gusto ko manood. Sabihan natin sila mamaya.",
                    "Bisa kung manalbe. Sabyan tamu karela pota.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sige.",
                    "",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 3 && selectedPhase == 3){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Tito, maaari ba tayong pumunta sa patimpalak mamaya?",
                    "Bápa, malyári tamung munta keng patimpalak póta?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Pwede naman, pero papipiliin ko kayo.",
                    "Malyari naman, pero pamilinan da kayú.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ano po iyon?",
                    "Nánu pu itá?",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Gusto niyo bang manatili rito o pumunta sa ibang lugar?",
                    "Bisa kong magdatún keni o múnta kng alíwang lugál?",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Titingnan natin kung hanggang saan tayo aabot. Tanghali palang naman.",
                    "Lawén támu no' támu mírás. Ugtu pamu naman.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Punta po tayo sa ibang lugar, Tito.",
                    "Muntá tamu pú keng aliwang lugal, Bap.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Sige, ayain niyo si Clara at baka gusto niyang sumama.",
                    "Sige, agkat ye y Clara, pota bisa yang tuki.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Sige po, tito. Matutuwa si pinsan diyan.",
                    "Sige pu, 'Tang. Matula ya y pisan ku ken.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Anong lugar ba ang una nating pupuntahan?",
                    "Sánu ing múmuna támung puntalán?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Daanan ba natin ang Mt. Pinatubo?",
                    "Dalanan taya ing Mt. Pinatubó?",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sige po, Tito. Hindi po ba maraming nasalanta ang Pinatubo noong 1991?",
                    "Sige pu, Bap. E wari dakál la pu deng méte keng Pinatubu anyang 1991?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Maraming nasalanta at maraming nawalan.",
                    "Dakal méte ampo kéwalan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Ngayon, natutunan ng mga tao rito kung paano sila magkakaroon ng kabuhayan sa kahirapan na idinulot nito.",
                    "Ngeni mebyasa la deng tau nung makananu la mika-kabyayan keng kasákitang géwa na nini.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Grabe talaga ang dugong Pilipino. Kayang tapatan kahit anong problema.",
                    "Grabi ing dayang Pilipinu. Agyúng tapatán agyang nanung problema.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Hanggang ngayon po pala ay may mga abo parin na nakakalat? Ang dilim.",
                    "Angga ngeni pu pala atin pang abúng makákalat? Madalumdúm.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Isa lang yan sa palatandaan kung gaano katindi ang idinulot niyan.",
                    "Metung yamu kareng maragul nang pinsála yan.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Nakakalungkot naman. May iba pa ba tayong pwedeng puntahan?",
                    "Mákalungkut. Atin ta pang alíwang malyaring puntalan?",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Ang susunod na pupuntahan natin ay ang bukang ng Puning.",
                    "Ing tutuki tamung puntalan, ing bukang ng Puning.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Saan po iyon?",
                    "Nókarín pu itá?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Angeles lang, medyo malayo ang distansya sa Pinatubo.",
                    "Angeles mu, marayúng bagya kng Pinatúbu.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Kahit na malayo ito sa Pinatubo, produkto ito ng pagputok ng bulkan na iyon.",
                    "Agyang marayu ya kng Pinatubú, produktu ne ning pánga-akbúng ning bulkán.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "May nakukuha rin palang maganda sa destruksyon na iyon?",
                    "Atin palang máyap a disnán itang kalamidád ayta?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Bagong simula.",
                    "Báyung simulá.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Saan naman po tayo tutungo ngayon?",
                    "Nokarin ta naman pu munta ngeni?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Sa Museo ng Angeles.",
                    "Keng Museú ning Angeles.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Hindi man halata, dito niyo makikita ang historya ng Pampanga.",
                    "E man ûlatá, karin yu akit ing istorya ning Pampanga.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Malalaman niyo ang mayamang kultura at tradisyon ng mga Kapampangan.",
                    "Karin yu abalu ing masaplálang kultura ampong tradisyung Kapampángan, abe na ing sining.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Malalaman niyo ang mayamang kultura at tradisyon ng mga Kapampangan, kasama na diyan ang sining.",
                    "Karin yu abalu ing masaplálang kultura ampong tradisyung Kapampángan, abe na ing sining.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Mabilis bang umunlad ang Pampanga?",
                    "Mabilis ya pung mínasensú ing Pampánga",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Kung ikukumpara sa ibang mga lugar, hamak bilis ang pag-unlad ng lugar na ito.",
                    "Nung kumpará me kng alíwang lugal, mabilis ya ping minasensú.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Maraming mga proyekto at mga ipinatayong mga imprastraktura.",
                    "Dakál a mitalakád a proyektu ampong imprastuktura.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Ngunit kasabay ng pag-unlad, ay mayroon na ring mga nalilimutan.",
                    "Yamu, kayagnan ning asensú, atin mu ring mengakalingwán.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "May ilang mga kapistahan na bihira na lamang ganapin.",
                    "Ating pyestang malágad namung piyabalán.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "May mga tradisyon na ring hindi nagagawa.",
                    "Atin mu namang tradisyung e na magagawá.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Isa na ang lenguahe ng  Kapampangan diyan.",
                    "Metung ne kareng makakalingwan ing amánung Kapampángan.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Kahit sa eskwela, kapag sinasalita ko ang Kapampangan, hindi na nila ako.",
                    "Agyang keng eskwelahan, e da naku aintindyan patche mágsalita kung Kapampangan.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ako naman ay ngayon lang natututo.",
                    "Aku naman, ngeni ku pamu mababiása.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Sa walong mga lenguahe, Kapampangan ang nanganganib dahil kakaunti na lamang ang marunong magsalita nito.",
                    "Kareng walung amanung Pilipinú, delikadu neng lumbúg ing Kapampángan uling ditak na lamu deng magsálita kanini.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Mabuti po at tinuturuan ninyo ako. Sisikapin ko rin na ipaalam ito sa iba.",
                    "Mapnamu pu, tuturú yukú. Panikuanan ku mu ring turú ini karéng alíwa.",
                    R.drawable.kian
                )
            )
            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Oh siya, ginabi na tayo. Luluwas ka pa bukas.",
                    "O, mebengi tána. Magbyahi ka pa búkas.",
                    R.drawable.noel
                )
            )
            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Hindi ba kayo gutom? Kumain muna tayo sa sikat na sisigan bago umuwi. Sikat pa naman ang sisig dito.",
                    "E ko maranóp? Mangan tamu ketang Aling Lucing, itang sikàt a sísigán bayu ka múli.",
                    R.drawable.home
                )
            )
            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Tara na po at kailangan pa magpahinga at mag-impake ni pinsan.",
                    "Tara pu, ba neng mipaynawa ampo manimpaki y pisan ku.",
                    R.drawable.elena
                )
            )



        }

        if(selectedLevel == 4 && selectedPhase == 1){

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Apo, Nakapag-impake ka na ba?",
                    "Menimpaki naka \'Itung?",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, lola. Nandito na po lahat ng gamit ko.",
                    "Opu, Apu. Tyu no ngan pu deng gamit ku.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Mabuti naman. Siguraduhin mo ring wala kang naiwan sa kwarto mo.",
                    "Ma'p naman. Siguradwan mung alang melakwan kng kwartu mu.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, lola. Titingnan ko ulit.",
                    "Opu, Apo. Lon keng pasibayu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Wala na po akong nakikitang naiwan ko po.",
                    "Ala naku pung alakwan, Apo.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Mabuti. Oo nga pala nakalimutan kong itanong kung saan ka nag-aaral, Apo?",
                    "Mayap. Akalingwan ku palang kutang, nokarin ka magaral tung?",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Ako po ay nag-aaral sa isang Unibersidad sa Maynila (FEU Tech).",
                    "Ako po ay nag-aaral sa isang Unibersidad sa Maynila (FEU Tech).",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Napakagaling naman ng apo ko. Sige na apo, Sabihin mo lang kung may maitutulong ako.",
                    "Kabyasnan na ning apu ko. Sige, tung, sabyan mu kaku patche atin kung asaup, ne?",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Lola, bago po ako umalis, mayroon po akong gusto sabihin sa inyo.",
                    "Apu, bayu ku mako, atin ku pu sanang sabyan.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Ano iyon, apo? ",
                    "Nanu ita, tung?",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sana po ay patuloy ninyong alagaan ang iyong kalusugan.",
                    "Sana pu tuluy yung sesen ing katawan yu.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Salamat sa mga salitang iyan, apo.  Ingat sa iyong paglalakbay pabalik sa Maynila.",
                    "A wa, salamat, tung. Mimingat ka keng pamagbyai mu pabalik Menila.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Maraming salamat po, lola. Bibili muna po ako ng mga pasalubong bago umalis.",
                    "Dakal pung salamat, Apu. Sali ku pamu pung pasalubung bayu ku mako.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Sige, apo. Mayroon diyan sa bayan, makikita mo ang iba’t ibang delikasiya ng Pampanga.",
                    "Awa, tung. Atin keng balen, akit mo deng miyaliwang delikasyang Kapampangan.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sige po, lola. Mauna po muna ako.",
                    "Sige, Apu. Muna naku pu!",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 4 && selectedPhase == 2){

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Magandang umaga po! Ano po ang hanap n'yo?",
                    "Mayap a abak pu! Na'ng pantunan yu?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Magandang umaga rin po. Gusto ko po sana bumili ng mga pasalubong para sa pamilya ko. Ano po ang mairerekomenda niyong delicacies dito sa Pampanga?",
                    "Mayap a abak mu rin pu! Salí ko pung pasalubung deng pamilya ku. Nanu pung delikasyang Kapampangan ing arekumenda yu?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Ang Pampanga ay kilala sa mga kakanin na aking binebenta.",
                    "Kilala ya ing Pampanga karening pisasali ku.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Gusto ko po ng suman at espasol.",
                    "Bisa ku pung suman ampo espasol.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Meron po kaming mga bagong luto na suman at espasol. Ilang piraso po ang gusto n'yo?",
                    "Atin keng bayung lutu. Pilan ing buri mu?",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Paki-kwenta po, limang piraso ng suman at tatlong piraso ng espasol.",
                    "Magkanu la pu deng limang suman ampong atlung espasol?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Sige po, ito po ang inyong binili.",
                    "Oreni pu deng selinyo.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Salamat po! Magkano po ang kabuuan?",
                    "Magkanu ngan pu ing seli ku?",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Ito po ay nagkakahalaga ng isang daan at limampung piso.",
                    "Dinalan ampong singkwenta pesus pu.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Eto po, bayad na po. Salamat ulit!",
                    "Oyni pung bayad. Dakal salamat pasibayu!",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Tindero",
                    false,
                    "Maraming salamat din po! Ingat sa biyahe pauwi.",
                    "Dakal salamat mu naman. Mimingat keng pamanuli mu.",
                    R.drawable.home
                )
            )

        }

        if(selectedLevel == 4 && selectedPhase == 3){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Lola, narito na po ang mga pasalubong na binili ko. Ito po ang suman, at ang espasol.",
                    "Apo, oreni pu deng seli ku: suman ampo espasol.",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Salamat, apo! Ang sarap ng mga pasalubong mo.  Sigurado akong magugustuhan ito ng mama, papa at mga kapatid mo.",
                    "Salamat, tung! Kanyaman da reng pasalubung mo. Aburi do kanyan deng ima, tata, ampo deng kapatad mu.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sana nga po. Ipinagmamalaki ko po ang mga kakaning galing sa Pampanga.",
                    "Sana nga po. Ipinagmamalaki ko po ang mga kakaning galing sa Pampanga.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Magpatuloy ka lang sa pagpapakabuti, apo. Mahalaga ang iyong pag-aaral at pagiging responsableng anak.",
                    "Tuluy me ing pamag makayap mu, apú. Maulaga ing kekang pamagaral ampo pamag-responsabling anak.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, Lola. Hindi ko po kayo makakalimutan. Babalik po ako sa susunod na bakasyon.",
                    "Opu. E dako pu kalingwan. Magbalik ku kng tutuking bakasyun.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Ingat sa biyahe, apo. Nawa'y magpatuloy ang iyong tagumpay sa pag-aaral.",
                    "Mimingat ka kng bya-i mu. Samasan me ing kekang pamagaral.",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Mimingat ka kng bya-i mu. Samasan me ing kekang pamagaral.",
                    "Dakal pung salamat keng pamagsese ampong lugud yu kaku, Apu. Magpaalam naku kareng gang kamaganak tamu keni.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Lola",
                    false,
                    "Ingat ka sa paglalakbay, apo. Mag-iingat ka.",
                    "",
                    R.drawable.lola
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Tito Noel, Tita Marites, Elena, salamat po sa mainit na pagtanggap ninyo sa akin dito sa probinsya.",
                    "Bapang Noel, Darang Marites, dakal pung salamat keng malugud yung pamagtanggap kanaku keti probinsya.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Noel",
                    false,
                    "Salamat din, anak. Masaya kaming nakilala ka at nagkaroon ng pagkakataon na maging malapit sa iyo.",
                    "Salamat mu naman, 'tu. Buri mi ining mikilala ampo milapit tamu lub.",
                    R.drawable.noel
                )
            )

            storyList.add(
                StoryClass(
                    "Marites",
                    false,
                    "Oo nga, napakabuti mong pamangkin. Sana'y magpatuloy ang iyong tagumpay sa pag-aaral.",
                    "Wapin, maganaka kang paunakan. Samasan me ing kekang pamagaral.",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Elena",
                    false,
                    "Ingat ka sa pag-uwi, pinsan. Balik ka ulit ha? Marami pa tayong dapat maikwento at mapag-usapan.",
                    "Ingat ka sa pag-uwi, pinsan. Balik ka ulit ha? Marami pa tayong dapat maikwento at mapag-usapan.",
                    R.drawable.elena
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, tita, tito, at pinsan. Hindi po ako magsasawang bumalik at makipagkwentuhan sa inyo. Maraming salamat po ulit.",
                    "Opu, bap, dar, ampo pisan. Eku sumawang magbalik ampo mipagkwentu kekayu. Dakal a salamat pasibayu.",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 5 && selectedPhase == 1){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Magandang umaga sa inyong lahat! Ako po si Kian at nagmula po ako sa Pampanga. Sa ngayon, mas gugustuhin kong magsalita ng Kapampangan.",
                    "Mayap a abak kekayungan! Aku pu y Kian, ibat ku pu Pampanga. Mas buri ku pung manga-Pampangan ngeni.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Guro",
                    false,
                    "Magandang umaga, Kian ! Sigurado akong marami kaming gustong malaman tungkol sa iyong karanasan sa Pampanga. Maaari ba tayong magkaroon ng maliit na Q&A?",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Opo, guro! Handa po akong sagutin ang inyong mga tanong.",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Estudyante 1",
                    false,
                    "Ano po ang mga sikat na pagkain sa Pampanga?",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Mayroon kaming mga masasarap na mga delicacies, ang mga kakanin tulad ng espasol, suman at puto bumbong.",
                    "Detang sikat a delikasya deng espasol, suman, putu bumbung.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Estudyante 2",
                    false,
                    "Paano po ang mga tradisyon o kultura sa Pampanga?",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sa Pampanga, mayroon kaming mga tradisyonal na pagdiriwang tulad ng Duman Festival at Giant Lantern Festival.",
                    "Atin kaming festival kalupa da reng Duman Festival ampo Giant Lantern Festival.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Estudyante 3",
                    false,
                    "Ano po ang mga pampamilyang gawain o kahalagahan ng pamilya sa inyong lugar?",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sa Pampanga, malapit ang samahan ng pamilya. Madalas kami magtipon-tipon tuwing espesyal na okasyon at nagkakaroon kami ng malalaking handaan. Mahalaga ang respeto at pagmamahal sa mga magulang at nakatatanda.",
                    "Malapít la deng pamilyang Kapampangan. Mititipun kami kqreng okasyun ampo maghanda kaming dakal a pamangan. Maulaga ing apmamie respetu ampong lugud kareng pengari ampo mangatua.",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "Guro",
                    false,
                    "Salamat sa iyong mga kasagutan, Kian. Tunay na kaakit-akit ang iyong karanasan sa Pampanga at ang iyong pagmamahal sa iyong lugar ng pinagmulan.",
                    "",
                    R.drawable.home
                )
            )

        }

        if(selectedLevel == 5 && selectedPhase == 2){

            storyList.add(
                StoryClass(
                    "Kian",
                    true,
                    "Sa maikling pananatili ko sa Pampanga, ang dami kong natutunan na bago. Para bang nakatago sa mga Pilipino ang kayamanan na taglay ng lugar na iyon. Ang ganda ng mga tanawin, ang  yaman ng kultura, ang unlad na rin ng sinasabi nilang probinsya." +
                            "\n\nHindi ko inakala na magiging masaya ako sa pananatili ko doon. Sino ba namang mag-aakala na mayroong mga kapistahan ang mga palaka kilala sa pangalang (Pyestang Tugak), para sa mga ibon at itlog na tinatawag na (Ibun-ebun Festival). Ang (Duman Festival) na nagbibigay halaga sa malagkit at mga matatamis na pagkain na niluluto gamit iyon, at ang ming pinuntahan na (Sinukwan Festival) kung saan ipinakilala ang sinaunang diyos ng Kapampangan." +
                            "\n\nHindi ko rin malilimutan ang sarap ng lutong (Embutido) ni Lola. Ang dami ba namang sahog at masarap ang kumbinasyon ng baboy at baka rito. Ang mga pasalubong na (Suman) at (Espasol) na matamis at nabili ko sa malapit na palengke sa amin." +
                            "\n\nHindi na ako makapaghintay na makapag bakasyon mulli.",
                    "",
                    R.drawable.kian
                )
            )

        }

        if(selectedLevel == 5 && selectedPhase == 3){

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Maraming salamat at sinamahan mo ako sa aking munting paglalakbay.",
                    "",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "Maraming salamat din at may natutunan ako.",
                    "",
                    R.drawable.home
                )
            )


            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Maaari ko bang malaman ang iyong pangalan?",
                    "",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Pakilala in Kapampangan",
                    "",
                    R.drawable.home
                )
            )


            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Masaya akong makilala ka.",
                    "",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Paano mo mamarkahan ang istorya ko?",
                    "",
                    R.drawable.kian
                )
            )


            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )


            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Paano naman ang iyong mga natutunan?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Mayroon ka bang bagong natutunan sa kanilang kultura?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Mairerekomenda mo ba ang aplikasyon na ito sa iyong mga kaibigan, kamag-anak, at kakilala?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Ikaw ba ay nahirapan sa mga katanungan o nadalian? (1-madali hanggang 5-nahirapan)",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Mabisa ba ang pamamaraan ng aplikasyon na ito upang tumatak ang mga bagong salita?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Natalakay ba ang mga importante at tipikal na mga salita?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Paano mo mamarkahan ang pangkalahatan ng aplikasyon na ito?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Ano ang iyong masasabi at mairerekomenda sa aplikasyon na ito?",
                    "",
                    R.drawable.kian
                )
            )

            storyList.add(
                StoryClass(
                    "User",
                    true,
                    "*Choose rate 1-5*",
                    "",
                    R.drawable.home
                )
            )

            storyList.add(
                StoryClass(
                    "Kian",
                    false,
                    "Maraming salamat muli at magamit mo sana ang iyong bagong mga natutunan. Hanggang sa muli, paalam!",
                    "",
                    R.drawable.kian
                )
            )

        }



    }
}
