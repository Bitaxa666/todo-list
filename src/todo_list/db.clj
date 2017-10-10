(ns todo-list.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:dbtype "postgresql"
   :dbname "d21d11tce0rii6"
   :host "ec2-50-19-113-219.compute-1.amazonaws.com"
   :port 5432
   :user "xsqvscvshrnbhg"
   :password "2aac27d999d06a9f097d1a6e9ea4b2fc4466ec6dff10f7af10887a339949e08b"})

(defn migrate []
  (jdbc/db-do-commands
   db-spec
   (jdbc/create-table-ddl :todo [[:id :serial] [:title :varchar]])))
