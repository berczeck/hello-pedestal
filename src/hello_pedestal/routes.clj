(ns hello-pedestal.routes
  (:require [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"})

(defn owner-post [request]
  {:status 200 :body "owner post!"})

(defn owner-get [request]
  {:status 200 :body "owner get!"})

(def db-interceptor
  {:name :database-interceptor
   :enter (fn [context]
            (update context :request assoc :datomic ))})

(def routes
  (route/expand-routes
    #{["/hello" :get respond-hello :route-name :hello]
      ["/owners/:name" :post owner-post :route-name :owner-post]
      ["/owners/:name" :get owner-get :route-name :owner-get]}))

;(route/try-routing-for hello/routes :prefix-tree "/greet" :get)
