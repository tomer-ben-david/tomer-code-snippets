package org.tomerbd.snippets.nlp;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class StanfordNLP {
    public static void main(String[] args) throws Exception {
        StanfordNLP stanfordNLP = new StanfordNLP();
        stanfordNLP.sentenceSplitter();
    }

    public void sentenceSplitter() {
        String paragraph = "The first sentence. The second sentence.";
        Reader reader = new StringReader(paragraph);
        DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(reader);
        List<String> sentenceList = new LinkedList<String>();
        for (List<HasWord> element: documentPreprocessor) {
            StringBuilder sentence = new StringBuilder();
            List<HasWord> hasWordList = element;
            for (HasWord token : hasWordList) {
                sentence.append(token).append(" ");
            }
            sentenceList.add(sentence.toString());
        }

        for (String sentence : sentenceList) {
            System.out.println(sentence);
        }
    }
}
