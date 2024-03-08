/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client;

import com.google.inject.Injector;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * JavaFX/FXML helper class which loads scenes and injects stuff into controllers.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class MyFXML {

    private final Injector injector;
    private ResourceBundle bundle;

    /**
     * Constructor for a FXML helper class, which injects stuff into controllers.
     *
     * @param injector some injector
     */
    public MyFXML(Injector injector) {
        this.injector = injector;
        // TODO: get the default locale from the config file
        Locale locale = Locale.of("en");
        this.bundle = ResourceBundle.getBundle("client.bundles.Bundle", locale);
    }

    /**
     * Loads a scene and its controller. Also injects the controller
     *
     * @param c     controller class (only used for the type)
     * @param parts a parameter list pointing to the FXML file
     * @param <T>   type of the controller class, I think
     * @return a pair of a controller and scene
     */
    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            var loader = new FXMLLoader(getLocation(parts), bundle, null, new MyFactory(),
                StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    public void changeLocale(Locale locale) {
        this.bundle = ResourceBundle.getBundle("client.bundles.Bundle", locale);
    }

    public Locale getCurrentLocale() {
        return this.bundle.getLocale();
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {

        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}