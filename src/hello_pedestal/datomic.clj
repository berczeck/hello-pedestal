(ns hello-pedestal.datomic
  (:require [datomic.api :as d]
					 [com.stuartsierra.component :as component]))

(defrecord Datomic [uri]
 component/Lifecycle

 (start [this]
  (println "Datomic: start")
   (d/create-database uri)
	 (let [conn (d/connect uri)
         schema (load-file "resources/schema.edn")]
     (d/transact conn schema)
		 (assoc this :conn conn)))

 (stop [this]
   (println "Datomic: stop")
   (d/delete-database uri)
	 (let [conn (:conn this)]
			(d/release conn)
			(assoc this :conn nil))))

(defn new-datomic
 [uri]
 (map->Datomic {:uri uri}))
;datomic:mem://pet-owners-test-db
