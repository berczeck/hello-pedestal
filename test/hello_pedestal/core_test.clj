(ns hello-pedestal.core-test
  (:require [clojure.test :refer :all]
            [hello-pedestal.core :as core]
            [clojure.data.json :as json]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.test :as test]))

(def service
  (::http/service-fn (http/create-servlet core/service)))

(def url-for
  (route/url-for-routes core/routes))

(deftest service-test
  (is (= 400
         (:status (test/response-for service :get (url-for :greet-view)))))
  (is (= 200
         (:status (test/response-for service :get (url-for :test-user :path-params {:id "4fe5d828-6444-11e8-8222-720007e40350"})))))
  (is (= {:status :created :id "4fe5d828-6444-11e8-8222-720007e40350"}
         (:body (test/response-for service :post (url-for :test-create :path-params {:id "4fe5d828-6444-11e8-8222-720007e40350"})
                                           :headers {"Content-Type" "application/json"})))))
