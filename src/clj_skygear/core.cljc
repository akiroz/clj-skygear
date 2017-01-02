(ns clj-skygear.core
  (:require [promesa.core :as promesa]
            [clj-skygear.http :as http]
            [clj-skygear.user :as user]
            [clj-skygear.record :as record]
            [clj-skygear.event :as event]
            ))

(defn request
  "Skygear request entry point.

  state       : client state atom
  operations  : KV pairs of skygear operation keywords and its argument

  return      : promesa promise of results array

  The client state atom provides an API to access:
  - The current user
  - The server access token
  You can watch this atom for changes or initialize it with a persisted state
  before the first skygear call.
  
  This function expects a minimum of 2 keys in the state atom:
  :end-point    The skygear server URL
  :api-key      The skygear client API key
  Some operations require the :access-token key which is set by the
  :login and :signup operations.
  "
  [state & operations]
  (let [send-request (partial http/send-request state)]
    (->> (for [[op arg] (partition 2 operations)]
           (case op
             :login   (user/login   state arg)
             :logout  (user/logout  state arg)
             :signup  (user/signup  state arg)
             :passwd  (user/passwd  state arg)
             :save    (record/save  state arg)
             :query   (record/query state arg)
             :lambda  (http/lambda  state arg)
             #_else   (throw (str "[skygear] Error: invalid op " op))))
         (promesa/all))))

