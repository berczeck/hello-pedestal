(ns hello-pedestal.system
	(:require [com.stuartsierra.component :as component]
						[io.pedestal.http :as http]
						[hello-pedestal.pedestal :as pedestal]))

(defn new-system
	[env]
	(component/system-map
		:service-map
		{:env env
		 ::http/routes
		 ::http/type :jetty
		 ::http/port 8890
		 ::http/join?	false}
		:pedestal
		(component/using
			(pedestal/new-pedestal)
			[:service-map])))
