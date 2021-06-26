(ns cljs-fulcro-sandbox.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
            [clojure.set :as set]))

(def routes
  {[:question/id 1] [:question/id 2]
   [:question/id 2] [:choice/id   1]
   [:choice/id   1] [:question/id 1]})

(defmutation next-screen [_]
  (action [{:keys [state]}]
          (swap! state update :root/current-screen routes)))

(def prev-routes
  (set/map-invert routes))

(defmutation prev-screen [_]
  (action [{:keys [state]}]
          (swap! state update :root/current-screen prev-routes)))
