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
