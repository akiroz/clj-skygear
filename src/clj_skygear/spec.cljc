(ns clj-skygear.spec
  (:require [clojure.spec :as s]))

(defn conform [spec data]
  (let [conformed-data (s/conform spec data)]
    (when (= conformed-data :clojure.spec/invalid)
      (throw (str "[skygear] Error: invalid arguments\n"
                  (with-out-str (s/explain spec data)))))
    conformed-data))
