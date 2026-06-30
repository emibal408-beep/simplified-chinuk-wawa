package com.example.data

data class EmojiChainQuestion(
    val emojiChain: String,
    val correctSentence: String,
    val correctEnglish: String,
    val wrongSentences: List<Pair<String, String>>
)

data class AssembleQuestion(
    val englishSentence: String,
    val correctBlocks: List<String>,
    val distractors: List<String>
)

data class ProQuestion(
    val historicalPhrase: String,
    val correctMeaning: String,
    val wrongMeanings: List<String>
)

object GameData {
    val easyQuestions = listOf(
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🏃‍♂️ ➡️ 🏠",
            correctSentence = "Nayka klatwa kopa house",
            correctEnglish = "I go to the house",
            wrongSentences = listOf(
                "Mayka chako kopa house" to "You come to the house",
                "Klaska mamook kopa illahie" to "They work on the land"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 🗣️ ➡️ 🧠",
            correctSentence = "Mayka wawa pe kumtux",
            correctEnglish = "You speak and understand",
            wrongSentences = listOf(
                "Nayka wawa pe peshak" to "I speak and am bad",
                "Yaka nanitch pe chako" to "He sees and comes"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ ❤️ ➡️ 🍽️",
            correctSentence = "Nsayka tikegh mukamak",
            correctEnglish = "We want to eat",
            wrongSentences = listOf(
                "Msayka olo pe klatwa" to "You all are hungry and go",
                "Klaska peshak pe olo" to "They are bad and hungry"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👤 ➡️ 👁️ ➡️ 🐦",
            correctSentence = "Yaka nanitch kalakala",
            correctEnglish = "He sees a bird",
            wrongSentences = listOf(
                "Nayka iskoom kalakala" to "I take a bird",
                "Mayka wawa kopa kalakala" to "You talk to a bird"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👤 ➡️ 🛠️ ➡️ 🛶",
            correctSentence = "Yaka mamook canim",
            correctEnglish = "He builds a canoe",
            wrongSentences = listOf(
                "Klaska marsh canim" to "They throw away a canoe",
                "Nayka tikegh canim" to "I want a canoe"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🏃‍♂️ ➡️ 🌍",
            correctSentence = "Nayka klatwa kopa illahie",
            correctEnglish = "I go to the land",
            wrongSentences = listOf(
                "Yaka chako kopa illahie" to "He comes to the land",
                "Nsayka nanitch kopa illahie" to "We look at the land"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 👁️ ➡️ 🐕",
            correctSentence = "Mayka nanitch kamooks",
            correctEnglish = "You see a dog",
            wrongSentences = listOf(
                "Klaska wawa kopa kamooks" to "They talk to a dog",
                "Yaka iskoom kamooks" to "He takes a dog"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 🗣️ ➡️ 👍",
            correctSentence = "Klaska wawa kloshe",
            correctEnglish = "They speak well",
            wrongSentences = listOf(
                "Nsayka mamook peshak" to "We do bad",
                "Mayka kumtux hiyu" to "You know a lot"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "💧 ➡️ 🚶‍♂️ ➡️ 🏠",
            correctSentence = "Chuck chako kopa house",
            correctEnglish = "Water comes to the house",
            wrongSentences = listOf(
                "Piah chako kopa stick" to "Fire comes to the forest",
                "Cold chako kopa illahie" to "Cold comes to the land"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🤲 ➡️ 🪙",
            correctSentence = "Nayka iskoom chickamin",
            correctEnglish = "I take the money",
            wrongSentences = listOf(
                "Yaka marsh chickamin" to "He throws away the money",
                "Mayka tikegh chickamin" to "You want the money"
            )
        )
    )

    val mediumQuestions = listOf(
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🏃‍♂️ ➡️ 🏠 ➡️ ➕ ➡️ 🍽️",
            correctSentence = "Nayka klatwa kopa house pe mukamak",
            correctEnglish = "I go to the house and eat",
            wrongSentences = listOf(
                "Mayka chako kopa house pe mamook" to "You come to the house and work",
                "Klaska klatwa kopa illahie pe nanitch" to "They go to the land and look",
                "Yaka mitlite kopa house pe cly" to "He stays at the house and cries"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "⏳ ➡️ 🫵 ➡️ 🚶‍♂️ ➡️ 📍 ➡️ 🏫",
            correctSentence = "Alki mayka chako kopa iskooh",
            correctEnglish = "Soon you will come to school",
            wrongSentences = listOf(
                "Ahnkuttie nayka klatwa kopa house" to "In the past I went to the house",
                "Alki klaska mamook kopa illahie" to "Soon they will work on the land",
                "Alta nsayka nanitch kopa salt-chuck" to "Now we look at the ocean"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🛠️ ➡️ 🛶 ➡️ 🔙 ➡️ ❄️",
            correctSentence = "Nayka mamook canim kimta cole",
            correctEnglish = "I build a canoe after winter",
            wrongSentences = listOf(
                "Yaka mamook canim elip piah" to "He builds a canoe before the fire",
                "Mayka iskoom canim kopa sun" to "You take a canoe in the daytime",
                "Klaska klatwa kopa house kimta moon" to "They go to the house after a month"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 👁️ ➡️ 🌊 ➡️ ➕ ➡️ 🚢",
            correctSentence = "Nsayka nanitch salt-chuck pe piah-ship",
            correctEnglish = "We see the ocean and the steamship",
            wrongSentences = listOf(
                "Klaska klatwa kopa salt-chuck pe pish" to "They go to the ocean for fish",
                "Mayka kumtux salt-chuck pe boat" to "You know the ocean and the boat",
                "Yaka mamook kopa salt-chuck pe canim" to "He works at the ocean and the canoe"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "⏳ ➡️ 👤 ➡️ 🏃‍♂️ ➡️ 📍 ➡️ 🌍",
            correctSentence = "Alki yaka klatwa kopa illahie",
            correctEnglish = "Soon he will go to the land",
            wrongSentences = listOf(
                "Alta nsayka chako kopa house" to "Now we come to the house",
                "Alki mayka mamook kopa stick" to "Soon you will work in the woods",
                "Ahnkuttie klaska mitlite kopa illahie" to "In the past they stayed on the land"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 🧠 ➡️ 🗣️ ➡️ ➕ ➡️ 👍",
            correctSentence = "Mayka kumtux wawa pe kloshe",
            correctEnglish = "You understand speech and are good",
            wrongSentences = listOf(
                "Nayka halo kumtux wawa pe peshak" to "I do not understand speech and am bad",
                "Yaka tikegh wawa kopa tillikum" to "He wants to speak to people",
                "Klaska mamook wawa pe pelton" to "They make speech and are crazy"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🛑 ➡️ 🛠️ ➡️ ➕ ➡️ 🏃‍♂️",
            correctSentence = "Nayka kopet mamook pe klatwa",
            correctEnglish = "I stop working and leave",
            wrongSentences = listOf(
                "Mayka kopet wawa pe mitlite" to "You stop speaking and sit",
                "Yaka elip mamook pe chako" to "He first works and comes",
                "Klaska alki mamook pe klatwa" to "They will soon work and go"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 🤤 ➡️ ➕ ➡️ 🧠 ➡️ ❌",
            correctSentence = "Klaska olo pe halo kumtux",
            correctEnglish = "They are hungry and don't understand",
            wrongSentences = listOf(
                "Nsayka kwan pe hiyu kumtux" to "We are glad and understand a lot",
                "Msayka sick pe tikegh mukamak" to "You all are sick and want to eat",
                "Nayka olo pe tikegh chickamin" to "I am hungry and want money"
            )
        )
    )

    val assembleQuestions = listOf(
        AssembleQuestion(
            englishSentence = "I want to eat salmon",
            correctBlocks = listOf("Nayka", "tikegh", "mukamak", "salmon"),
            distractors = listOf("Mayka", "klatwa", "pish")
        ),
        AssembleQuestion(
            englishSentence = "You go to school",
            correctBlocks = listOf("Mayka", "klatwa", "kopa", "iskooh"),
            distractors = listOf("Nayka", "mamook", "house")
        ),
        AssembleQuestion(
            englishSentence = "We see a big steamship",
            correctBlocks = listOf("Nsayka", "nanitch", "hyas", "piah-ship"),
            distractors = listOf("Yaka", "canim", "stick")
        ),
        AssembleQuestion(
            englishSentence = "He knows how to speak",
            correctBlocks = listOf("Yaka", "kumtux", "wawa"),
            distractors = listOf("Nayka", "tikegh", "kloshe")
        ),
        AssembleQuestion(
            englishSentence = "Good morning, friend",
            correctBlocks = listOf("Kloshe", "sun", "tilikum"),
            distractors = listOf("Peshak", "moon", "house")
        ),
        AssembleQuestion(
            englishSentence = "I stop working now",
            correctBlocks = listOf("Nayka", "kopet", "mamook", "alta"),
            distractors = listOf("Mayka", "alki", "chako")
        ),
        AssembleQuestion(
            englishSentence = "You will see a bird soon",
            correctBlocks = listOf("Alki", "mayka", "nanitch", "kalakala"),
            distractors = listOf("Ahnkuttie", "yaka", "kamooks")
        ),
        AssembleQuestion(
            englishSentence = "They are hungry but happy",
            correctBlocks = listOf("Klaska", "olo", "pe", "kwan"),
            distractors = listOf("Nsayka", "sick", "peshak")
        )
    )

    val proQuestions = listOf(
        ProQuestion(
            historicalPhrase = "ikta mayka tyee chako kopa town?",
            correctMeaning = "Why did your chief come to town?",
            wrongMeanings = listOf(
                "When will you go to the city?",
                "What is your friend doing in the house?",
                "Where did the white man build the boat?"
            )
        ),
        ProQuestion(
            historicalPhrase = "tenas kloshe yaka tumtum",
            correctMeaning = "His heart is quite good / He is very glad",
            wrongMeanings = listOf(
                "He is a very small child",
                "She thinks it is a bad day",
                "We want to speak well"
            )
        ),
        ProQuestion(
            historicalPhrase = "halo tillikum mitlite kopa house",
            correctMeaning = "There are no people in the house",
            wrongMeanings = listOf(
                "My friends are sitting in the building",
                "They are coming to my house now",
                "Who is living inside the forest?"
            )
        ),
        ProQuestion(
            historicalPhrase = "kopa alta nesika chako kloshe",
            correctMeaning = "Now we are becoming well / successful",
            wrongMeanings = listOf(
                "Later we will work together",
                "Yesterday we walked into the woods",
                "I am very happy to see you"
            )
        ),
        ProQuestion(
            historicalPhrase = "hyas chako tsola kopa salt-chuck",
            correctMeaning = "A big storm is coming on the ocean",
            wrongMeanings = listOf(
                "A large boat is sailing on the river",
                "The water is very cold today",
                "The ocean has many big fish"
            )
        ),
        ProQuestion(
            historicalPhrase = "cheeco mika nanitch ya-ka?",
            correctMeaning = "Have you just seen him/her?",
            wrongMeanings = listOf(
                "When did you look at the bird?",
                "Do you want to visit them soon?",
                "How long has he been waiting?"
            )
        ),
        ProQuestion(
            historicalPhrase = "halo lakles pe halo sapleel",
            correctMeaning = "No lard and no flour",
            wrongMeanings = listOf(
                "More bread and water",
                "A little fat and grease",
                "Nothing to eat and drink"
            )
        )
    )
}
