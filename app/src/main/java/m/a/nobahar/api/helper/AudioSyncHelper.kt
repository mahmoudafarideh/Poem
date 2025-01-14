package m.a.nobahar.api.helper

import m.a.nobahar.api.contract.PoemApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import javax.inject.Inject

class AudioSyncHelper @Inject constructor(
    private val poemApi: PoemApi
) {
    suspend fun getAudioSync(syncUrl: String): List<Pair<Int, Int>> {
        val response = poemApi.loadRecitationAudioSync(syncUrl)
        val text = response.byteStream().bufferedReader()
            .use { it.readText() }
            .removeSurrounding("\"", "\"")
            .replace("\\n", "")
            .replace("\\r", "")
        return parseVerseOrderToStartTime(text)
    }

    fun parseVerseOrderToStartTime(xmlString: String): List<Pair<Int, Int>> {
        val verseList = mutableListOf<Pair<Int, Int>>()
        var currentTag: String? = null
        var verseOrder: Int? = null
        var audioMilliseconds: Int? = null

        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(xmlString.reader())

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    currentTag = parser.name
                }

                XmlPullParser.TEXT -> {
                    currentTag?.let {
                        when (it) {
                            "VerseOrder" -> verseOrder = parser.text.toIntOrNull()
                            "AudioMiliseconds" -> audioMilliseconds = parser.text.toIntOrNull()
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.name == "SyncInfo" && verseOrder != null && audioMilliseconds != null) {
                        verseList.add(Pair(verseOrder, audioMilliseconds))
                        verseOrder = null
                        audioMilliseconds = null
                    }
                    currentTag = null
                }
            }
            eventType = parser.next()
        }

        return verseList.filter { it.first >= 0 }.sortedBy { it.first }
    }
}