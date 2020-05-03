/**
 * Copyright (c) 2019 Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of Gluon, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package hellofx;

import com.fasterxml.jackson.databind.ObjectMapper;
import hellofx.model.Quote;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class HelloFX extends Application {
        private String javaVersion;
        private String javafxVersion;
        private Label versionLabel;
        private Label quoteLabel;
        private ImageView ImageGoyo;
        private String restURL = "https://api.chucknorris.io/jokes/random";

    public void start(Stage stage) {
        javaVersion = System.getProperty("java.version");
        javafxVersion = System.getProperty("javafx.version");
        versionLabel = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        quoteLabel = new Label("Loading...");

        ImageGoyo = new ImageView(new Image(HelloFX.class.getResourceAsStream("/hellofx/goyo.png")));
        ImageGoyo.setFitHeight(200);
        ImageGoyo.setPreserveRatio(true);

        VBox root = new VBox(30, ImageGoyo, versionLabel,quoteLabel);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(HelloFX.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        retrieveQuote();
    }

    private void retrieveQuote() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Quote quote = objectMapper.readValue(new URL(restURL),Quote.class);
            updateQuote(quote);
        }catch (Throwable e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    private void updateQuote(Quote quote) throws MalformedURLException,URISyntaxException {
     if(quote != null) {
        if(quote.getValue()!=null){
            quoteLabel.setText(quote.getValue());
        }
     }
    }

    public static void main(String[] args) {
        launch(args);
    }
}