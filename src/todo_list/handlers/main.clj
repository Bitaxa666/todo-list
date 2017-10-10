(ns todo-list.handlers.main
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [todo-list.response :as res]
            [todo-list.views.main :as view]))

(defn home [req]
  (-> (view/home-view req)
      res/response
      res/html))

(defroutes main-routes
  (GET "/" _ home))