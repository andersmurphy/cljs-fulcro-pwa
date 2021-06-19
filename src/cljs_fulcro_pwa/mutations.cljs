(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(def routes
  {[:question/id 1] [:question/id 2]
   [:question/id 2] [:question/id 3]
   [:question/id 3] [:choice/id   1]
   [:choice/id   1] [:question/id 1]})

(defmutation next-screen
  [{:keys [current-screen]}]
  (action [{:keys [state]}]
          (swap! state assoc-in
                 [:container/id :main-container :container/content]
                 (routes current-screen))))
