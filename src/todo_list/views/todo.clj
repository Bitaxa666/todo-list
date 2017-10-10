(ns todo-list.views.todo
  (:require [todo-list.views.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->> `([:h1 "TODO 一覧"]
         [:ul
          ~@(for [{:keys [title]} todo-list]
             [:li title])])
       (layout/common req)))
