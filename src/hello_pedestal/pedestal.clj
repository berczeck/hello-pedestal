(ns hello-pedestal.pedestal
	(:require [datascript.core :as d]
						[com.stuartsierra.component :as component]
						[io.pedestal.http :as http]))
;http://pedestal.io/guides/pedestal-with-component
(defn test? [service-map]
	(= :test (:env service-map)))

(defrecord Pedestal [service-map service]
	component/Lifecycle

	(start [this]
		(if service
			this
			(cond-> service-map
				true 											http/create-server
				(not (test? service-map)) http/start
				true 											((partial assoc this :service)))))

	(stop [this]
		(when (and service (not (test? service-map)))
			(http/stop service))
		(assoc this :service nil)))

(defn new-pedestal
	[]
	(map->Pedestal {}))
