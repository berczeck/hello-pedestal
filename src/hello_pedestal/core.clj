(ns hello-pedestal.core
 (:require [hello-pedestal.system :as system]
           [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main
 "I don't do a whole lot ... yet."
 [& args]
 (component/start
  (system/new-system {:env :prod :db-uri "datomic:mem://pedestal-db"})))
