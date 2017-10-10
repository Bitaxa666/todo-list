(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.resource :as resource]
            [ring.middleware.defaults :as defaults]
            [compojure.core :refer [routes]]
            [todo-list.handlers.base :refer [base-routes]]
            [todo-list.handlers.todo :refer [todo-routes]]
            [todo-list.handlers.main :refer [main-routes]]))

(defn- wrap [handler middleware opt]
  (if (true? opt)
    (middleware handler)
    (if opt
      (middleware handler opt)
      handler)))

(def app
  (-> (routes
       todo-routes
       main-routes
       base-routes)
      (wrap resource/wrap-resource "public")
      (wrap defaults/wrap-defaults defaults/site-defaults)))

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
