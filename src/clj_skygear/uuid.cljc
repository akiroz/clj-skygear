(ns clj-skygear.uuid
  #?(:clj (:require [clj-uuid :as uuid])))

(defn gen-str []
  (str #?(:clj  (uuid/v1)
          :cljs (random-uuid))))
