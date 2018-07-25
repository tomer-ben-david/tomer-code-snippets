package org.tomerbd.snippets.nlp;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

public class OpenNLP {
    public static void main(String[] args) throws Exception {
        new OpenNLP().simpleTokenize();
    }

    public static File getModelDir(){
        String modelsDirProp = System.getProperty("model.dir");

        return new File(modelsDirProp);
    }

    public void simpleTokenize() throws Exception {
        TokenizerModel model = new TokenizerModel(new FileInputStream(new File(System.getProperty("model.dir"), "en-token.bin")));
        Tokenizer tokenizer = new TokenizerME(model);
        String tokens[] = tokenizer.tokenize("this is some string");
        for (String token : tokens) {
            System.out.println("token: " + token);
        }
    }
}
