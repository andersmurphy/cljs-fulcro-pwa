(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(def routes
  {1 [:question/id 2]
   2 [:question/id 3]
   3 [:question/id 1]})

(defmutation next-screen
  [{id :question/id}]
  (action [{:keys [state]}]
          (swap! state assoc-in
                 [:container/id :main-container :container/content]
                 (routes id))))
