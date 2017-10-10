(defproject
  todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sugitk/todo-list"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                   [ring "1.6.2"]
                   [ring/ring-defaults "0.3.1"]
                   [compojure "1.6.0"]
                   [hiccup "1.0.5"]
                   [org.clojure/java.jdbc "0.7.3"]
                   [org.postgresql/postgresql "42.1.4"]]
  :main todo-list.core
  :min-lein-version "2.7.1"
  :uberjar-name "todo-list.jar"
  :profiles {:dev
              {:main todo-list.core/-dev-main}})
