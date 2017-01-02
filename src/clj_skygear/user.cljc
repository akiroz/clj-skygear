(ns clj-skygear.user
  (:require [clojure.spec :as s]
            [promesa.core :as p]
            [clj-skygear.http :as http]
            [clj-skygear.spec :refer [conform]]))


(s/def ::username   string?)
(s/def ::email      string?)
(s/def ::password   string?)
(s/def ::provider   string?)
(s/def ::auth_data  map?)

(s/def ::login (s/keys :req-un [(or (and ::username  ::password)
                                    (and ::email     ::pasword)
                                    (and ::provider  ::auth_data))]))

(s/def ::signup (s/or :credential (s/keys :req-un [(or (and ::username          ::password)
                                                       (and             ::email ::password)
                                                       (and ::username  ::email ::password))])
                      :anonymous  empty?))

(s/def ::old ::password)
(s/def ::new ::password)
(s/def ::passwd (s/keys :req-un [::old ::new]))


(defn- auth-update
  "Internal: update state atom with new auth data"
  [state
   {{:keys [access_token
            _id user_id email username roles
            last_login_at last_seen_at]} :result}]
  (let [user-id (or user_id _id)
        user (when user-id
               {:id          user-id
                :username    username
                :email       email
                :last-login  (when last_login_at (js/Date. last_login_at))
                :last-seen   (when last_seen_at (js/Date. last_seen_at))
                :roles       (or roles [])})]
    (swap! state merge {:access-token access_token
                        :user user})
    user))


(defn login
  "Login using either username, email or 3rd party auth provider."
  [state arg]
  (->> (conform ::login arg)
       (http/request state "auth:login")
       (p/map (partial auth-update state)))


(defn signup
  "Signup with either username, email, both or anonymously."
  [state arg]
  (->> (second (conform ::signup arg))
       (http/request state "auth:signup")
       (p/map (partial auth-update state))))


(defn logout
  "Logout"
  [state _]
  (->> (http/request state "auth:logout" {})
       (p/map (partial auth-update state))))


(defn passwd
  "Change Password"
  [state arg]
  (let [password  (conform ::passwd arg)]
    (->> {:old_password  (:old password)
          :password      (:new password)}
         (http/request state "auth:password")
         (p/map (partial auth-update state)))))


