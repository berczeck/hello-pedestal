(ns hello-pedestal.system
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [hello-pedestal.pedestal :as pedestal]
            [hello-pedestal.routes :as routes]
            [hello-pedestal.datomic :as datomic]))

(defn new-system
  [{:keys [env db-uri]}]
  (component/system-map
   :service-map
   {:env          env
    ::http/routes routes/routes
    ::http/type   :jetty
    ::http/port   8890
    ::http/join?  false}

   :pedestal
   (component/using
    (pedestal/new-pedestal)
    [:service-map])

   :datomic (datomic/new-datomic db-uri)))
