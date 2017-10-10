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

