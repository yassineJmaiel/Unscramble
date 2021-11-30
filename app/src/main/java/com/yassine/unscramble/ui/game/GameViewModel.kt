package com.yassine.unscramble.ui.game

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameViewModel : ViewModel() {

        private var _score = MutableLiveData(0)
       val score: MutableLiveData<Int>
    get()=_score
        private var _currentWordCount = MutableLiveData(0)
         val currentWordCount: MutableLiveData<Int>
    get()=_currentWordCount
    private val _currentScrambledWord = MutableLiveData<String>()

    val currentScrambledWord: MutableLiveData<String>
        get() = _currentScrambledWord
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String
    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
    /*
* Updates currentWord and currentScrambledWord with the next word.
*/
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value= String(tempWord)
            _currentWordCount.value=(_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }
    fun nextWord(): Boolean {
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
    private fun increaseScore() {
        _score.value=(_score.value)?.plus( SCORE_INCREASE)
    }
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }


    }
