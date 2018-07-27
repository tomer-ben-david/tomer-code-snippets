package org.tomerbd.snippets.nlp;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.tomerbd.snippets.util.URLUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;

@Configuration
@ComponentScan
public class OpenNLPMain {

    @Named
    public static class OpenNLP {
        @Inject private URLUtils urlUtils;
        @Inject private NLPConfig nlpConfig;


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

        public void downloadModel() throws IOException {
            urlUtils.urlToFile(nlpConfig.getModelBaseURL() + "/" + nlpConfig.getTokenizerENModelName(), nlpConfig.getTargetLocalModelDir() + "/" + nlpConfig.getTokenizerENModelName());
        }

        public void runTokenizer() throws IOException {
            String paragraph = "this is some paragraph.  and another one.";
            try (
                    InputStream modelInputStream = new FileInputStream(nlpConfig.getTokenzierENModelFilePath());

                    ) {
                TokenizerModel tokenizerModel = new TokenizerModel(modelInputStream);
                Tokenizer tokenzier = new TokenizerME(tokenizerModel);
                String[] tokens = tokenzier.tokenize(paragraph);
                for (String token : tokens) {
                    System.out.println("Found token: " + token);
                }

            }
        }
    }


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.tomerbd");
        OpenNLP openNLP = ctx.getBean(OpenNLP.class);
        openNLP.downloadModel();
        openNLP.runTokenizer();
    }

}