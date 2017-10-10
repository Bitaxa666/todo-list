(ns todo-list.handlers.base
  (:require
            [compojure.route :refer [not-found]]
            [compojure.core :refer [defroutes GET]]
            [ring.handler.dump :refer [handle-dump]]))

(defroutes base-routes
  (GET "/request-info" [] handle-dump)
  (not-found "<h1>This is not the page you are looking for</h1>
              <p>Sorry, the page you requested was not found!</p>"))
