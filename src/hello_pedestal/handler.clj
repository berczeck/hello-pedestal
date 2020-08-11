(ns hello-pedestal.handler
	(:require [hello-pedestal.data :as data]))


(defn respond-hello [request]
	{:status 200 :body "Hello, world!"})

(defn owner-post [{:keys [json-params datomic]}]
	#_(data/add-pet-owner datomic (:name json-params))
	{:status 200 :body "Ok"})

(defn owner-get [{:keys [path-params datomic]}]
	#_{:status 200 :body (data/find-pet-owner-id datomic (:name path-params))}
	{:status 200 :body (str "Owner " (:name path-params))})

(defn greet-get
	[{:keys [int-id db]}]
	{:status 200 :body {:message (str "Greetings!!! " int-id) :conn db}})

(defn error-get
	[{:keys [int-id db]}]
	(if (= int-id 1)
		(throw (ex-info "Not found" {:type :not-found}))
		{:status 200 :body {:response :ok}}))
