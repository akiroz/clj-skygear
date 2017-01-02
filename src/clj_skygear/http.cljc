(ns clj-skygear.http
  (:require [ajax.core :refer [POST]]
            [promesa.core :as p]
            ))


(defn request
  "Make Skygear API request"
  [state action data]
  (let [{:keys [end-point api-key access-token]} @state]
    (when-not (and end-point api-key)
      (throw "[skygear] Error: missing :end-point or :api-key in state atom."))
    (p/promise
      (fn [success fail]
        (POST end-point
              {:format :json
               :params (->> {:action action
                             :api_key api-key
                             :access_token access-token}
                            (merge data))
               :response-format :json
               :keywords? true
               :handler successs
               :error-handler fail})))))

(defn upload
  "Upload Skygear asset"
  [])

(defn lambda
  "Call Skygear lambda"
  [state lambda args]
  (when-not (or (vector? args) (seq? args))
    (throw "[skygear] Error: lambda function expects a vector or seq of arguments"))
  (->> (request lambda {:args args})
       (p/map :result)))
