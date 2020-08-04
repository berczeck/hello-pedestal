(ns hello-pedestal.data
  (:require [datomic.api :as d])
  (:gen-class))

(defn add-pet-owner [conn owner-name]
  @(d/transact conn [{:db/id (d/tempid :db.part/user)
                      :owner/name owner-name}]))

(defn find-pet-owner-id [conn owner-name]
  (ffirst
    (d/q '[:find ?eid
         :in $ ?owner-name
         :where [?eid :owner/name ?owner-name]]
    (d/db conn)
    owner-name)))
