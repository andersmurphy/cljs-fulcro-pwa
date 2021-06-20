(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(def routes
  {[:question/id 1] [:question/id 2]
   [:question/id 2] [:choice/id   1]
   [:choice/id   1] [:question/id 1]})

(defmutation next-screen [_]
  (action [{:keys [state]}]
          (swap! state update :root/current-screen routes)))
