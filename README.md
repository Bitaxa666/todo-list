# todo-list

こちらを見ながら勉強します。

http://practicalli.github.io/clojure-webapps/


# プロジェクト作成

```
$ lein new todo-list
```

# Ring を使ってみる

project.clj に ring を追加。

```clojure
(defproject todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]])
```

main 関数を指定。

```clojure
(defproject todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]]
  :main todo-list.core)
```

src/todo_list/core.clj を実装。

```clojure
(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty
   (fn [request] {:status 200
                  :body "<h1>Hello, Clojure World</h1>  <p>Welcome to your first Clojure app.  This message is returned regardless of the request, sorry</p>"
                  :headers {}})
   {:port (Integer. port-number)}))
```

実行。http://localhost:8000 でアクセスして確認。

```
$ lein run 8000
```

## reload を追加

project.clj

```clojure
(defproject
  todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]]
  :main todo-list.core
  :profiles {:dev
             {:main todo-list.core/-dev-main}})
```

src/todo_list/core.clj

wrap-reload Middleware の追加と、dev profile での dev-main を実装。

```clojure
(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn welcome
  "A ring handler to process all requests for the web server.  If a request is for something other than then an error message is returned"
  [request]
  (if (= "/" (:uri request))
    {:status 200
     :body "<h1>Hello, Clojure World</h1>
            <p>Welcome to your first Clojure app.</p>"
     :headers {}}
    {:status 404
     :body "<h1>This is not the page you are looking for</h1>
            <p>Sorry, the page you requested was not found!</p>"
     :headers {}}))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty welcome
                   {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server using Ring & Jetty that reloads code changes via the development profile of Leiningen"
  [port-number]
  (jetty/run-jetty (wrap-reload #'welcome)
                   {:port (Integer. port-number)}))
```

# Compojure でルーティング

dependencies に compojure を追加。

```clojure
(defproject
  todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]
                   [compojure "1.6.0"]]
  :main todo-list.core
  :profiles {:dev
              {:main todo-list.core/-dev-main}})
```

src/todo_list/core.clj で実装。

```clojure
(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))
```

defroutes にルーティングを定義して、各関数を実装する。(実装は略)

```clojure
(defroutes app
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (not-found "<h1>This is not the page you are looking for</h1>
              <p>Sorry, the page you requested was not found!</p>"))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty app
                   {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server using Ring & Jetty that reloads code changes via the development profile of Leiningen"
  [port-number]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port-number)}))
```

# Heroku に Deploy

Procfile を作る。

```
web: java $JVM_OPTS -cp target/todo-list.jar clojure.main -m todo-list.core $PORT
```

project.clj に jar のビルドの設定を追加。

```clojure
(defproject
  todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]
                   [compojure "1.6.0"]]
  :main todo-list.core
  :min-lein-version "2.7.1"
  :uberjar-name "todo-list.jar"
  :profiles {:dev
              {:main todo-list.core/-dev-main}})

```

アプリを作って push するとビルドされる。

```
$ heroku login
$ heroku create
Creating app... done, peaceful-mountain-13767
https://peaceful-mountain-13767.herokuapp.com/ | https://git.heroku.com/peaceful-mountain-13767.git

$ git remote -v
$ git push heroku master 
```

https://peaceful-mountain-13767.herokuapp.com/hello/heroku などにアクセスして確認。

# Hiccup

project.clj の dependencies に追加。

```clojure
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]
                   [compojure "1.6.0"]
                   [hiccup "1.0.5"]]
```

handler 関数で使ってみる。

リファレンスを見ると一通りありそう。
http://weavejester.github.io/hiccup/index.html

```clojure
(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]))

(defn welcome
  "A ring handler to respond with a simple welcome message"
  [request]
  (html [:h1 "Hello, Clojure World"]
        [:p "Welcome to your first Clojure app, I now update automatically"]))

(defn goodbye
  "A song to wish you goodbye"
  [request]
  (html5 {:lang "en"}
         [:head (include-js "myscript.js") (include-css "mystyle.css")]
         [:body
          [:div [:h1 {:class "info"} "Walking back to happiness"]]
          [:div [:p "Walking back to happiness with you"]]
          [:div [:p "Said, Farewell to loneliness I knew"]]
          [:div [:p "Laid aside foolish pride"]]
          [:div [:p "Learnt the truth from tears I cried"]]]))
```

# ファイルを分ける

core.clj から handlers/base.clj を参照するようにしてみる。

