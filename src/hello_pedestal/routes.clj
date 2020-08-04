(ns hello-pedestal.routes
  (:require [io.pedestal.http.route :as route]
            [hello-pedestal.data :as data]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"})

(defn owner-post [request]
  {:status 200 :body "owner post!"})

(defn owner-get [{:keys [path-params datomic]}]
 (println (str "params: " path-params))
 (println (str "Datomic: " datomic))
  {:status 200 :body (data/find-pet-owner-id datomic (:name path-params))})

(def db-interceptor
  {:name :database-interceptor
   :enter (fn [context]
            (update context :request assoc :datomic {:conn "test"}))})

(def routes
  (route/expand-routes
    #{["/hello" :get respond-hello :route-name :hello]
      ["/owners/:name" :post owner-post :route-name :owner-post]
      ["/owners/:name" :get [db-interceptor owner-get] :route-name :owner-get]}))

;(route/try-routing-for hello/routes :prefix-tree "/greet" :get)
