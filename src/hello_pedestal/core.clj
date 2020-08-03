(ns hello-pedestal.core
 (:require [clojure.data.json :as json]
           [io.pedestal.http :as http]
           [io.pedestal.http.route :as route]
           [io.pedestal.http.body-params :as body-params]
           [io.pedestal.http.content-negotiation :as conneg])
  (:gen-class))
;,https://stackoverflow.com/questions/36545570/getting-the-post-body-data-from-a-post-request-to-pedestal
(defn respond-hello
  [request]
  {:status 400 :body "Hello"})

(defn create-hello
  [request]
  {:status 200 :body (:json-params request)})

(defn view-user
  [request]
  {:status 400 :body "Hello"})

(defn test-user
  [request]
  (let [id (:uuid request)]
    {:status 200 :body (str "test user " id)}))

(defn create-test
  [request]
  (let [id (:uuid request)
        req (:json-params request)]
    {:status 200 :body {:status :created :id id}}))

(def numeric #"[0-9]+")
(def user-id {:user-id numeric})

(def path-id->uuid
  {:name ::path-id->uuid
   :enter (fn [context]
            (let [id (get-in context [:request :path-params :id])]
              (update context :request assoc :uuid (java.util.UUID/fromString id))))})

(def supported-types ["text/html" "application/edn" "application/json" "text/plain"])

(def content-neg-intc (conneg/negotiate-content supported-types))

(defn accepted-type
  [context]
  (get-in context [:request :accept :field] "text/plain"))

(defn as-uuid-string [v] (str v))

(defn uuid-aware-value-writer [key value]
  (if (= key :id) (as-uuid-string value) value))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html"        body
    "text/plain"       body
    "application/edn"  (pr-str body)
    "application/json" (json/write-str body :value-fn uuid-aware-value-writer)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

(def coerce-body
  {:name ::coerce-body
   :leave
   (fn [context]
     (if (get-in context [:response :headers "Content-Type"])
       context
       (update-in context [:response] coerce-to (accepted-type context))))})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet-view]
     ["/greet" :post [(body-params/body-params) create-hello] :route-name :greet-post]
     ["/user/:id" :get view-user :route-name :view-user :constraints user-id]
     ["/test/:id" :get [path-id->uuid test-user] :route-name :test-user]
     ["/test/:id" :post [coerce-body content-neg-intc (body-params/body-params) path-id->uuid create-test] :route-name :test-create]}))

;(route/try-routing-for routes :prefix-tree "/greet" :get)

(def service
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890})

(defn create-server
  []
  (http/create-server service))

(defn start []
  (http/start (create-server)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
