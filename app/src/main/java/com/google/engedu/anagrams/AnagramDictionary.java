package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    private HashMap<String,ArrayList<String>> lettersToWord;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet = new HashSet<>();
        wordList = new ArrayList<>();
        lettersToWord = new HashMap<>();
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String sortedString = sortString(word);
            popHashMap(word,sortedString);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(word.toLowerCase().contains(base)){
            return false;
        }else if (wordSet.contains(word.toLowerCase())){
            return true;
        }
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char [] charArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(int i = 0; i<charArray.length;i++){
            String newWord = word + charArray[i];
            String sortedString = sortString(newWord);
            if(lettersToWord.containsKey(sortedString)){
                ArrayList<String> arrayList = lettersToWord.get(sortedString);
                for (String s : arrayList) {
                    if (isGoodWord(s, word)) {
                        result.add(s);
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String str = "";
        ArrayList<String> arrayList = new ArrayList<>();
        str= wordList.get(random.nextInt(wordList.size()));
        arrayList= getAnagramsWithOneMoreLetter(str);
        while (arrayList.size() <= MIN_NUM_ANAGRAMS) {
            str= wordList.get(random.nextInt(wordList.size()));
            arrayList= getAnagramsWithOneMoreLetter(str);
        }
        return str;
    }

    public String sortString(String word) {
        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);
        return sortedString;
    }

    public void popHashMap(String word, String sortedString) {
        if(lettersToWord.containsKey(sortedString)){
            ArrayList arrayList = lettersToWord.get(sortedString);
            arrayList.add(word);
            lettersToWord.put(sortedString, arrayList);
        }else{
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(word);
            lettersToWord.put(sortedString, arrayList);
        }
    }
}
