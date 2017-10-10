(ns todo-list.handlers.main
  (:require [compojure.core :refer [defroutes GET]]
            [todo-list.db :as db]
            [compojure.route :as route]
            [todo-list.response :as res]
            [todo-list.views.main :as view]))

(defn home [req]
  (-> (view/home-view req)
      res/response
      res/html))

(defn migrate [req]
  (db/migrate)
  (-> (view/home-view req)
      res/reponse
      res/html))

(defroutes main-routes
  (GET "/migrate" _ migrate)
  (GET "/" _ home))