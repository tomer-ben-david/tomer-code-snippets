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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

@Configuration
@ComponentScan
public class OpenNLPMain {

    @Named
    public static class OpenNLP {
        @Inject private URLUtils urlUtils;


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
        }    }


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.tomerbd");
        OpenNLP openNLP = ctx.getBean(OpenNLP.class);
        openNLP.urlUtils.urlToFile("http://opennlp.sourceforge.net/models-1.5/da-token.bin", "/home/itamar/tmp/da-token.bin");
        System.out.println(openNLP.getModelDir());

    }

}
