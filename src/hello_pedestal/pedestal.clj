(ns hello-pedestal.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defn test?
  [service-map]
  (= :test (:env service-map)))

(defrecord Pedestal [service-map
                     service]
  component/Lifecycle
  (start [this]
    (println "Pedestal: start")
    (if service
      this
      (cond-> service-map
        true                      http/create-server
        (not (test? service-map)) http/start
        true                      ((partial assoc this :service)))))
  (stop [this]
    (println "Pedestal: stop")
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal
  []
  (map->Pedestal {}))
