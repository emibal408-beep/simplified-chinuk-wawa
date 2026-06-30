package com.example.data

data class EmojiChainQuestion(
    val emojiChain: String,
    val correctSentence: String,
    val wrongSentences: List<String>
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
            wrongSentences = listOf("Mayka chako kopa house", "Klaska mamook kopa illahie")
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 🗣️ ➡️ 🧠",
            correctSentence = "Mayka wawa pe kumtux",
            wrongSentences = listOf("Nayka wawa pe peshak", "Yaka nanitch pe chako")
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ ❤️ ➡️ 🍽️",
            correctSentence = "Nsayka tikegh mukamak",
            wrongSentences = listOf("Msayka olo pe klatwa", "Klaska peshak pe olo")
        ),
        EmojiChainQuestion(
            emojiChain = "👤 ➡️ 👁️ ➡️ 🐦",
            correctSentence = "Yaka nanitch kalakala",
            wrongSentences = listOf("Nayka iskoom kalakala", "Mayka wawa kopa kalakala")
        ),
        EmojiChainQuestion(
            emojiChain = "👤 ➡️ 🛠️ ➡️ 🛶",
            correctSentence = "Yaka mamook canim",
            wrongSentences = listOf("Klaska marsh canim", "Nayka tikegh canim")
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🏃‍♂️ ➡️ 🌍",
            correctSentence = "Nayka klatwa kopa illahie",
            wrongSentences = listOf("Yaka chako kopa illahie", "Nsayka nanitch kopa illahie")
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 👁️ ➡️ 🐕",
            correctSentence = "Mayka nanitch kamooks",
            wrongSentences = listOf("Klaska wawa kopa kamooks", "Yaka iskoom kamooks")
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 🗣️ ➡️ 👍",
            correctSentence = "Klaska wawa kloshe",
            wrongSentences = listOf("Nsayka mamook peshak", "Mayka kumtux hiyu")
        ),
        EmojiChainQuestion(
            emojiChain = "💧 ➡️ 🚶‍♂️ ➡️ 🏠",
            correctSentence = "Chuck chako kopa house",
            wrongSentences = listOf("Piah chako kopa stick", "Cold chako kopa illahie")
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🤲 ➡️ 🪙",
            correctSentence = "Nayka iskoom chickamin",
            wrongSentences = listOf("Yaka marsh chickamin", "Mayka tikegh chickamin")
        )
    )

    val mediumQuestions = listOf(
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🏃‍♂️ ➡️ 🏠 ➡️ ➕ ➡️ 🍽️",
            correctSentence = "Nayka klatwa kopa house pe mukamak",
            wrongSentences = listOf(
                "Mayka chako kopa house pe mamook",
                "Klaska klatwa kopa illahie pe nanitch",
                "Yaka mitlite kopa house pe cly"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "⏳ ➡️ 🫵 ➡️ 🚶‍♂️ ➡️ 📍 ➡️ 🏫",
            correctSentence = "Alki mayka chako kopa iskooh",
            wrongSentences = listOf(
                "Ahnkuttie nayka klatwa kopa house",
                "Alki klaska mamook kopa illahie",
                "Alta nsayka nanitch kopa salt-chuck"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🛠️ ➡️ 🛶 ➡️ 🔙 ➡️ ❄️",
            correctSentence = "Nayka mamook canim kimta cole",
            wrongSentences = listOf(
                "Yaka mamook canim elip piah",
                "Mayka iskoom canim kopa sun",
                "Klaska klatwa kopa house kimta moon"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 👁️ ➡️ 🌊 ➡️ ➕ ➡️ 🚢",
            correctSentence = "Nsayka nanitch salt-chuck pe piah-ship",
            wrongSentences = listOf(
                "Klaska klatwa kopa salt-chuck pe pish",
                "Mayka kumtux salt-chuck pe boat",
                "Yaka mamook kopa salt-chuck pe canim"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "⏳ ➡️ 👤 ➡️ 🏃‍♂️ ➡️ 📍 ➡️ 🌍",
            correctSentence = "Alki yaka klatwa kopa illahie",
            wrongSentences = listOf(
                "Alta nsayka chako kopa house",
                "Alki mayka mamook kopa stick",
                "Ahnkuttie klaska mitlite kopa illahie"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫵 ➡️ 🧠 ➡️ 🗣️ ➡️ ➕ ➡️ 👍",
            correctSentence = "Mayka kumtux wawa pe kloshe",
            wrongSentences = listOf(
                "Nayka halo kumtux wawa pe peshak",
                "Yaka tikegh wawa kopa tillikum",
                "Klaska mamook wawa pe pelton"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "🫲 ➡️ 🛑 ➡️ 🛠️ ➡️ ➕ ➡️ 🏃‍♂️",
            correctSentence = "Nayka kopet mamook pe klatwa",
            wrongSentences = listOf(
                "Mayka kopet wawa pe mitlite",
                "Yaka elip mamook pe chako",
                "Klaska alki mamook pe klatwa"
            )
        ),
        EmojiChainQuestion(
            emojiChain = "👥 ➡️ 🤤 ➡️ ➕ ➡️ 🧠 ➡️ ❌",
            correctSentence = "Klaska olo pe halo kumtux",
            wrongSentences = listOf(
                "Nsayka kwan pe hiyu kumtux",
                "Msayka sick pe tikegh mukamak",
                "Nayka olo pe tikegh chickamin"
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
